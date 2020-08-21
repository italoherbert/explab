package italo.explab.msg;

import italo.explab.InfoMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.recursos.classe.Objeto;
import italo.explab.var.FuncVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.NullVar;
import italo.explab.var.ObjetoVar;
import italo.explab.var.BooleanVar;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumericaVar;
import italo.explab.var.num.RealVar;
import java.text.DecimalFormat;

public class InterImpr {
    
    private final InterAplic aplic;
    
    public InterImpr( InterAplic aplic ) {
        this.aplic = aplic;
    }
        
    public void imp( Var var ) {
        String valor = null;
        if ( var == null ) {
            this.enviaString( PalavrasReservadas.NULL ); 
            return;
        }
            
        int tipo = var.getTipo();
        switch( tipo ) {
            case Var.REAL:
                valor = this.formataReal( (RealVar)var );
                break;
            case Var.BOOLEAN:
                valor = this.formataBool((BooleanVar)var );
                break;
            case Var.STRING:
                valor = ((StringVar)var).getValor();
                break;
            case Var.MATRIZ:                
                valor = this.formataMatriz( (MatrizVar)var );
                break;
            case Var.OBJETO_REF:
                valor = this.formataObjeto( ((ObjetoVar)var).getObjeto() );
                break;
            case Var.FUNC:
                valor = this.formataFunc( (FuncVar)var );
                break;
            default:
                this.impVarTipo( var ); 
        }
        
        
        if ( valor != null )
            this.enviaString( valor ); 
        aplic.getMSGManager().envia( "\n" ); 
    }
    
    public void impVarTipo( Var var ) {
        switch( var.getTipo() ) {
            case Var.REAL: this.enviaString( "REAL" ); break; 
            case Var.BOOLEAN: this.enviaString( "BOOLEAN" ); break; 
            case Var.STRING: this.enviaString( "STRING" ); break;  
            case Var.MATRIZ: this.enviaString( "MATRIZ" ); break;  
            case Var.OBJETO_REF: this.enviaString( "OBJETO REF" ); break;  
            case Var.VOID: this.enviaString( "VOID" ); break;  
            default:        
                System.err.println( "TIPO INVALIDO="+var.getTipo() );
                break;
        }
    }
    
    public void enviaString( String string ) {
        aplic.getMSGManager().envia( string );
    }
        
    public void enviaVar( Var var ) {
        if ( var instanceof NumericaVar ) {
            this.imprimeNVar( (NumericaVar)var ); 
        } else if ( var instanceof MatrizVar ) {
            this.imprimeMatVar( (MatrizVar)var ); 
        } else if ( var instanceof BooleanVar ) {
            this.imprimeBoolVar((BooleanVar)var );
        } else if ( var instanceof StringVar ) {
            this.imprimeStringVar( (StringVar)var ); 
        } else if ( var instanceof ObjetoVar ) {
            this.imprimeObjetoRefVar((ObjetoVar)var ); 
        } else if ( var instanceof Objeto ) {
            this.imprimeObjetoVar((Objeto)var ); 
        } else if ( var instanceof FuncVar ) {
            this.imprimeFuncVar( (FuncVar)var ); 
        } else if ( var instanceof NullVar ) {
            this.imprimeNullVar(); 
        } else {
            System.err.println( "TIPO DE VARIAVEL DESCONHECIDO. VAR= "+var );
        }
        
        aplic.getMSGManager().envia( "\n" ); 
    }
    
    public String formataVar( Var var ) {
        if ( var instanceof RealVar ) {
            return this.formataReal( (RealVar)var ); 
        } else if ( var instanceof MatrizVar ) {
            return this.formataMatriz( (MatrizVar)var );
        } else if ( var instanceof BooleanVar ) {
            return this.formataBool((BooleanVar)var );
        } else if ( var instanceof StringVar ) {            
            return ((StringVar)var).getValor(); 
        } else if ( var instanceof Objeto ) {            
            return this.formataObjeto( (Objeto)var );
        } else if ( var instanceof ObjetoVar ) {
            Objeto obj = ((ObjetoVar)var).getObjeto();
            if ( obj != null ) {
                return this.formataObjeto( obj );
            } else {
                return PalavrasReservadas.NULL;
            }
        } else if ( var instanceof NullVar ) {
            return PalavrasReservadas.NULL;
        }
        return null;
    }
    
    public void imprimeNullVar() {
        aplic.getMSGManager().envia( PalavrasReservadas.NULL ); 
    }
    
    public void imprimeObjetoRefVar( ObjetoVar objvar ) {
        Objeto obj = objvar.getObjeto();
        if ( obj == null ) {
            this.imprimeNullVar();
        } else {
            this.imprimeObjetoVar( obj ); 
        }
    }
    
    public void imprimeObjetoVar( Objeto obj ) {
        String valor = this.formataObjeto( obj );
        aplic.getMSGManager().envia( valor ); 
    }
    
    public void imprimeStringVar( StringVar var ) {
        aplic.getMSGManager().envia( var.getValor() );
    }
    
    public void imprimeBoolVar( BooleanVar var ) {
        aplic.getMSGManager().envia( this.formataBool( var ) );       
    }
    
    public void imprimeFuncVar( FuncVar var ) {
        aplic.getMSGManager().envia( this.formataFunc( var ) );       
    }
    
    public void imprimeNVar( NumericaVar var ) {
        String resultadoTexto = aplic.getMSGManager().getInfoMSG( InfoMSGs.IMP_RESULTADO );
        if ( var.getTipo() == NumericaVar.REAL ) {
            String real = this.formataReal( (RealVar)var );
            aplic.getMSGManager().envia( resultadoTexto+"= " + real );        
        } else {
            String mat = this.formataMatriz((MatrizVar)var );
            aplic.getMSGManager().envia( resultadoTexto+"=\n" + mat );
        }
    }    
    
    public void imprimeMatVar( MatrizVar var ) {
        String resultadoTexto = aplic.getMSGManager().getInfoMSG( InfoMSGs.IMP_RESULTADO );
        String mat = this.formataMatriz( var );
        aplic.getMSGManager().envia( resultadoTexto+"= \n" + mat );
    }
    
    public String formataReal( RealVar var ) {
        return aplic.getInterIni().getDecimalFormat().format( var.getValor() );
    }

    public String formataBool( BooleanVar var ) {
        if ( var.getValor() )
            return PalavrasReservadas.VERDADE;
        return PalavrasReservadas.FALSO;                
    }
        
    public String formataFunc( FuncVar var ) {
        String fnome = var.getUsuarioFunc().getNome();
        String npars = String.valueOf( var.getUsuarioFunc().getQuantParametros() );
        return aplic.getMSGManager().getInfoMSG( InfoMSGs.IMP_FORMATA_FUNC_VAR, fnome, npars );
    }
        
    public String formataObjeto( Objeto obj ) {
        return obj.getClasse().getNome()+"#"+obj.hashCode();
    }
            
    public String formataMatriz( MatrizVar matvar ) {
        String matrizTexto = aplic.getMSGManager().getInfoMSG( InfoMSGs.IMP_MATRIZ );
        
        int nl = matvar.getNL();
        int nc = matvar.getNC();
        
        StringBuilder sb = new StringBuilder();
        sb.append( matrizTexto );
        sb.append( " " );
        sb.append( nl );
        sb.append( "x" );
        sb.append( nc );
        sb.append( "(\n" );
        
        int numMaxCaracteres = this.contaMaxCaracteres(matvar );
        int numMaxFracDigitos = this.calculaMaxFracDigitos(matvar );
        int quantMatElEsp = aplic.getConfig().getQuantMatElEsp();        
        
        int desloc = numMaxCaracteres + quantMatElEsp;
        
        Var[][] matriz = matvar.getMatriz();
        for( int i = 0; i < nl; i++ ) {                    
            for( int j = 0; j < nc; j++ ) {
                Var el = matriz[i][j];
                if ( matvar.getTipoCompativel() == Var.REAL ) {
                    double real;
                    if ( el == null ) {
                        real = Double.NaN;
                    } else {
                        real = ((RealVar)el).getValor();
                    }
                    DecimalFormat ddf = new DecimalFormat();
                    ddf.setMinimumFractionDigits( numMaxFracDigitos );
                    ddf.setMaximumFractionDigits( numMaxFracDigitos );
                    sb.append( String.format( "%"+desloc+"s", ddf.format( real ) ) );
                } else {                    
                    String svar = this.formataVar( el );
                    if ( svar == null )
                        svar = "null";
                    sb.append( String.format( "%"+desloc+"s", svar ) );
                }
            }
            sb.append( "\n" ); 
        }               
        sb.append( ")" );
                
        return sb.toString();
    }       
    
    private int contaMaxCaracteres( MatrizVar mat ) {
        DecimalFormat df = aplic.getInterIni().getDecimalFormat();

        int nl = mat.getNL();
        int nc = mat.getNC();
        
        int max = 0;
        
        for( int i = 0; i < nl; i++ ) {                    
            for( int j = 0; j < nc; j++ ) {
                Var el = mat.getElemento( i, j );
                if ( el != null ) {
                    if ( el.getTipoCompativel() == Var.TC_NUMERICO ) {
                        double d = ((RealVar)el).getValor();
                        String dstr = df.format( d );
                        int cont = dstr.length();
                        
                        int k = dstr.length()-1;
                        boolean dec = true;
                        while ( k >= 0 && dec ) {
                            dec = dstr.charAt( k ) == '0' || dstr.charAt( k ) == '.';
                            if( dec )
                                cont--;
                            k--;
                        }           
                                                
                        if ( cont > max )
                            max = cont;
                    } else {
                        String svar = this.formataVar( el );                        
                        if ( svar == null )
                            svar = "null";
                        int tam = svar.length();
                        if ( tam > max )
                            max = tam;
                    }
                } else {
                    String svar = this.formataVar( el );                        
                    if ( svar == null )
                        svar = "null";
                    int tam = svar.length();
                    if ( tam > max )
                        max = tam;
                }               
            }
        }
        return max;
    }
    
    private int calculaMaxFracDigitos( MatrizVar mat ) {
        DecimalFormat df = aplic.getInterIni().getDecimalFormat();

        if ( mat.getTipoCompativel() != Var.TC_NUMERICO )
            return df.getMaximumFractionDigits();
        
        int nl = mat.getNL();
        int nc = mat.getNC();
        
        int max = 0;
        
        for( int i = 0; i < nl; i++ ) {                    
            for( int j = 0; j < nc; j++ ) {
                Var el = mat.getElemento( i, j );
                if ( el != null ) {
                    if ( el.getTipoCompativel() == Var.TC_NUMERICO ) {
                        double d = ((RealVar)el).getValor();
                        int cont = this.contaFracDigitos( d );
                        if ( cont > df.getMaximumFractionDigits() )
                            cont = df.getMaximumFractionDigits();
                        
                        if ( cont > max )
                            max = cont;
                    }
                } else {
                    String nan = String.valueOf( Double.NaN ); 
                    int len = nan.length();
                    if ( len > max )
                        max = len;
                }               
            }
        }
        return max;
    }
    
    private int contaFracDigitos( double n ) {
        String s = String.valueOf( n );
        
        int cont = 0;
        
        int len = s.length();
        boolean contar = false;
        for( int i = 0; i < len; i++ ) {
            if ( contar ) {
                cont++;
            } else if ( s.charAt( i ) == '.' ) {
                contar = true;
            }
        }
        int i = len-1;
        if ( i > 0 ) {
            boolean zero = true;
            while ( i >= 0 && zero ) {
                zero = s.charAt( i ) == '0';
                if( zero )
                    cont--;
                i--;
            }
                
        }
        return cont;
    }    
    
}
