package italo.explab.inter.exp.mat;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.matriz.valor.GenericaMatrizValor;
import italo.explab.arvore.exp.matriz.valor.MatrizValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMatInter extends Inter {       
    
    protected abstract Inter getELValorInter( InterManager manager );
    
    protected abstract MatrizValor novoMatrizValor( InterAplic aplic, Codigo codigo, int i, ExecNo parente, Exp[][] matriz );

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        InterManager manager = aplic.getInterManager();        
        ContadorUtil contUtil = aplic.getContUtil();
                            
        List<List<Exp>> elementos = null;
        int matrizI = 0;    
        
        int j = 0; 
        
        int charI = 0;
        int cont;
        char ch;
        
        boolean fim = false;
        while( !fim && i+j < i2 ) {
            switch ( charI ) {
                case 0:
                    ch = codigo.getSEGCH( i+j );
                    if ( ch != '[' )                        
                        return new InterResult();                    
                    
                    if ( codigo.getSEGCH( i+j+1 ) == ']' ) {
                        j += 2;
                        
                        fim = true;
                        continue;
                    }
                    
                    charI++;
                    break;
                case 1:
                    j += contUtil.contaEsps( codigo, i+j );
                    
                    InterResult result = this.getELValorInter( manager ).interpreta( no, base, aplic, codigo, i+j, i2 );
                    if ( result.getJ() > 0 ) {
                        j += result.getJ()-1;
                        charI = 2;                        
                    } else {
                        ch = codigo.getSEGCH( i+j );
                        if ( ch == '[' ) {
                            result = this.interpreta( no, base, aplic, codigo, to, i+j, i2 );
                            if ( result.getJ() == 0 ) {
                                return result;
                            } else {
                                j += result.getJ()-1;
                                charI = 2;
                            }
                        } else {
                            return result;
                        }                        
                    }      
                    if ( elementos == null ) {
                        elementos = new ArrayList();                                       
                        elementos.add( new ArrayList() );
                    }
                    elementos.get( matrizI ).add( (Exp)result.getInstrucaoOuExp() );
                    break;
                case 2:
                    cont = contUtil.contaEsps( codigo, i+j );
                    ch = codigo.getSEGCH( i+j+cont );
                    switch( ch ) {
                        case ',':
                        case ';':
                            if ( ch == ';' ) {
                                if ( elementos == null )
                                    elementos = new ArrayList();
                                elementos.add( new ArrayList() );
                                matrizI++;
                            }
                            j += cont;
                            charI = 1;
                            break;
                        case ']':
                            j += cont;
                            fim = true;
                            break;
                        default:
                            if ( cont > 0 ) {
                                j += cont-1;
                            } else {
                                ch = codigo.getSEGCH( i+j );
                                if ( ch != ':' ) {
                                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ESPACO_ESPERADO );
                                    return new InterResult( erro ); 
                                } 
                                return new InterResult();
                            }
                            charI = 1;
                            break;
                    }   
                    break;
            }
            j++;
        }              
        
        if ( !fim ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_COLCHETES_ESPERADO );
            return new InterResult( erro );
        }
        
        if ( elementos == null ) {
            GenericaMatrizValor mv = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoMatrizValor( i+j, no, codigo );
            mv.setMatriz( new Exp[0][0] ); 
            return new InterResult( mv, j ); 
        }
                
        int size = elementos.size();
        Exp[][] matriz = new Exp[ size ][];
        
        for( int k = 0; k < size; k++ ) {
            int size2 = elementos.get( k ).size();
            matriz[ k ] = new Exp[ size2 ];
            for( int k2 = 0; k2 < size2; k2++ )
                matriz[k][k2] = elementos.get( k ).get( k2 );            
        }
        
        Exp exp = this.novoMatrizValor( aplic, codigo, i, no, matriz ).getInstancia();
        return new InterResult( exp, j );
    }
}