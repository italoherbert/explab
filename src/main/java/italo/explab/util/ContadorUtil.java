package italo.explab.util;

import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public class ContadorUtil {
                
    public int contaBlocoCodigoTam( Codigo codigo, int i ) {
        int j = 0;
        int chaveCont = 1;
        while( codigo.isCHValido( i+j ) ) {
            char ch = codigo.getCH( i+j );
            switch( ch ) {
                case '{':
                    chaveCont++;
                    break;
                case '}':
                    chaveCont--;                    
                    if ( chaveCont == 0 )
                        return j;
                    break;
            }
            j++;
        }       
                                
        return -1;
    }
        
    public int contaSequenciaCHs( Codigo codigo, int i, char... charsFIM ) {
        return this.contaSequenciaCHs( codigo, i, false, charsFIM );
    }
    
    public int contaSequenciaCHs( Codigo codigo, int i, boolean retornarFimJ, char... charsFIM ) {
        int j = 0;
        
        while( codigo.isCHValido( i+j ) ) {
            char ch = codigo.getCH( i+j );
            for( char c : charsFIM )
                if ( c == ch )
                    return j;
            j++;
        }
        
        if ( retornarFimJ )
            return j;
        return 0;
    }
    
    public int contaTextoValor( Codigo codigo, int i, String texto ) {
        int j = 0;    
        int len = texto.length();
        int textoI = 0;
        while( textoI < len && codigo.isCHValido( i+j ) ) {
            if ( Character.toLowerCase( texto.charAt( textoI ) ) == Character.toLowerCase( codigo.getCH( i+j ) ) ) {
                j++;
                textoI++;
            } else {
                return 0;
            }
        }
        if ( j == len )
            return j;
        return 0;
    }
            
    public int contaVarNomeTam( Codigo codigo, int i ) {
        return this.contaVarNomeTam( codigo, i, false );
    }    
    
    
    public int contaClasseOuPacoteNomeTam( Codigo codigo, int i ) {
        return this.contaVarNomeTam( codigo, i, true );
    }
    
    public int contaVarNomeTam( Codigo codigo, int i, boolean aceitarPonto ) {
        int j = 0;
        boolean prim = true;
        boolean fim = false;
        boolean ultEhPonto = false;
        while( !fim && codigo.isCHValido( i+j ) ) {
            char ch = codigo.getCH( i+j );
            if ( prim ) {
                if ( !Character.isLetter( ch ) && ch != '_' )
                    return 0;
                prim = false;
                j++;
            } else if ( Character.isLetterOrDigit( ch ) || ch == '_' || ( aceitarPonto && ch == '.' ) ) {
                ultEhPonto = ch == '.';                
                j++;
            } else {
                fim = true;
            }           
        }
        
        if ( aceitarPonto && ultEhPonto )
            return 0;                 
        return j;
    }
        
    public int contaExpEntreParentesesTam( Codigo codigo, int i ) {
        int j = 1;        
        int parsCont = 1;
        while( parsCont > 0 && codigo.isCHValido( i+j )  ) {
            switch( codigo.getCH( i + j ) ) {                
                case '(':
                    parsCont++;
                    break;
                case ')':
                    parsCont--;
                    break;                
            }
            j++;
        }
        return j;
    }
    
    public int contaEspsEPontoEVirgulas( Codigo codigo, int i ) {                        
        int j = 0;
        boolean fim = false;
        while( !fim && codigo.isCHValido( i+j ) ) {
            j += this.contaEsps( codigo, i+j );
            char ch = codigo.getSEGCH( i+j );
            if ( ch == ';' ) {
                j++;
            } else {
                fim = true;            
            }
        }
                  
        return j;
    }
    
    public int contaEsps( Codigo codigo, int i ) {
        boolean ehesp = true;
        int j = 0;
        while( ehesp && codigo.isCHValido( i+j ) ) {
            char ch = codigo.getCH( i + j );
            switch( ch ) {
                case ' ':
                case '\n':
                case '\r':
                case '\t':
                    j++;
                    break;
                default:                    
                    ehesp = false;
                    break;
            }
        }
        return j;
    }
    
    public int contaComentariosTam( Codigo codigo, int i ) {                
        if ( !this.temComentario( codigo, i ) ) 
            return 0;        
        
        int j = 0;        
        while( codigo.isCHValido( i+j ) ) {                        
            char ch = codigo.getCH( i+j );
            j++;
            
            if ( !codigo.isCHValido( i+j ) )
                return j;            
            
            char ch2 = codigo.getCH( i+j );                            
            j++;
            
            if ( !codigo.isCHValido( i+j ) )
                return j;            
            
            if ( ch == '/' && ch2 == '/' ) {                
                ch = codigo.getCH( i+j );
                int cont = this.contaTextoValor( codigo, i+j, "<br" );
                while( ch != '\n' && cont == 0 ) {
                    j++;                    
                    if ( codigo.isCHValido( i+j ) ) {
                        ch = codigo.getCH( i+j );                                        
                        cont = this.contaTextoValor( codigo, i+j, "<br" );
                    } else {
                        return j;
                    }                    
                }
            }
            
            if ( ch == '/' && ch2 == '*' ) {
                while( ch != '*' || ch2 != '/' ) {
                    j++;
                    if ( codigo.isCHValido( i+j ) ) {
                        ch = codigo.getCH( i+j );
                        if ( codigo.isCHValido( i+j+1 ) ) {
                            ch2 = codigo.getCH( i+j+1 );
                        } else {
                            return j;
                        }
                    } else {
                        return j;
                    }
                }
                j += 2;
            }                                               
         
            int k = j;            
            k += this.contaEsps( codigo, i+k );
                                            
            if( this.temComentario( codigo, i+k ) ) {
                j = k;
            } else {                
                return j;
            }       
        }       
        
        return j;
    }
    
    public boolean temComentario( Codigo codigo, int i ) {        
        char ch = codigo.getSEGCH( i );
        if ( ch == '/' ) {
            char ch2 = codigo.getSEGCH( i+1 );
            if ( ch2 == '/' || ch2 == '*' )
                return true;                                    
        }
        return false;
    }
    
    public String codigoLN( Codigo codigo, int i ) {                
        int[] lc = this.lin_col( codigo, i );
        int linha = lc[0];
        int col = lc[1];
        
        int j = 0;
        
        boolean fim = false;
        while( !fim && codigo.isCHValido( i+j ) ) {            
            char ch = codigo.getCH( i+j );
            if ( ch == '\n' ) {
                fim = true;
            } else {
                j++;
            }
        }
        
        return "("+linha+","+col+"):   \""+codigo.getCodigo().substring( i, i+j )+"\"";
    }
    
    public int[] lin_col( Codigo codigo, int i ) {
        int linha = 1;
        int coluna = 0;               
        
        int j = 0;        
        while( j < i ) {
            char ch = codigo.getCH( j );
            if ( ch == '\n' ) {
                linha++;
                coluna = 0;
            } else {
                coluna++;
            }
            j++;
        }
        return new int[] { linha, coluna };
    }
    
    public int contaEsps( String str, int i ) {
        boolean ehesp = true;
        int j = 0;
        int len = str.length();
        while( ehesp && i+j < len ) {
            char ch = str.charAt( i + j );
            switch( ch ) {
                case ' ':
                case '\n':
                case '\r':
                case '\t':
                    j++;
                    break;
                default:                    
                    ehesp = false;
                    break;
            }
        }
        return j;
    }
    
}
