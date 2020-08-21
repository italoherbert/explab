package italo.explab.matriz;

import italo.explab.matriz.indice.ArrayVO;
import italo.explab.matriz.indice.MatrizIndice;
import italo.explab.ErroMSGs;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.mat.VetorMatrizVar;

public class MatrizManager {
        
    private final MatrizUtil util = new MatrizUtil();
        
    public MatrizELResult set( Codigo codigo, MatrizVar matvar, MatrizIndice[] matIndices, int valorPos, Var valor ) {
        int linha, coluna;
                        
        switch ( matIndices.length ) {
            case 1:
                linha = matIndices[0].getIndice();
                if ( linha == ArrayVO.VETOR ) {
                    if ( valor.getTipo() == Var.MATRIZ ) {
                        MatrizVar matvalor = (MatrizVar)valor;
                        try {
                            util.setVetor( matvar, matvalor );
                            return new MatrizELResult( matvalor );                                                                        
                        } catch ( MatVetorDIMException e ) {
                            int nl1 = matvar.getNL();
                            int nc1 = matvar.getNC();
                            int nl2 = matvalor.getNL();
                            int nc2 = matvalor.getNC();
                            CodigoErro erro = this.trataDIMsIncompativeisErro( codigo, nl1, nc1, nl2, nc2, valorPos );

                            return new MatrizELResult( erro );
                        }
                    } else {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, valorPos, ErroMSGs.MATRIZ_TIPO_MATRIZ_ESPERADO );
                        return new MatrizELResult( erro );
                    }
                } else {
                    try {
                        util.setElemento( matvar, 0, linha, valor );
                        return new MatrizELResult( valor ); 
                    } catch ( MatrizException ex ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, valorPos, ErroMSGs.MATRIZ_TIPO_NAO_VETOR_ESPERADO );
                        return new MatrizELResult( erro );
                    }                        
                }
            case 2:
                linha = matIndices[0].getIndice();
                coluna = matIndices[1].getIndice();                                
                                                
                try {
                    util.setElemento( matvar, linha, coluna, valor );                                    
                    return new MatrizELResult( valor );
                } catch ( EsperadoTipoMatrizException ex ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, valorPos, ErroMSGs.MATRIZ_TIPO_MATRIZ_ESPERADO );
                    return new MatrizELResult( erro );
                } catch (EsperadoTipoNaoMatrizException ex) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, valorPos, ErroMSGs.MATRIZ_TIPO_NAO_VETOR_ESPERADO );
                    return new MatrizELResult( erro );
                } catch (MatVetorDIMException ex) {
                    int nl1 = matvar.getNL();
                    int nc1 = matvar.getNC();
                    int nl2 = ((MatrizVar)valor).getNL();
                    int nc2 = ((MatrizVar)valor).getNC();
                    CodigoErro erro = this.trataDIMsIncompativeisErro( codigo, nl1, nc1, nl2, nc2, valorPos );
                    
                    return new MatrizELResult( erro );
                }                        
        }
        
        CodigoErro erro =  new CodigoErro( this.getClass(), codigo, valorPos, ErroMSGs.MATRIZ_GET_EL_NUM_INDICES_INVALIDO );
        return new MatrizELResult( erro );
    }
    
    public MatrizELResult get( Codigo codigo, MatrizVar matvar, MatrizIndice[] matIndices, int varPos ) {
        Var m_el_var;

        int arrayI, arrayJ;       
        
        switch ( matIndices.length ) {
            case 1:
                arrayI = matIndices[0].getIndice();
                if ( arrayI == ArrayVO.VETOR ) {
                    m_el_var = new VetorMatrizVar( matvar );
                } else {
                    if ( matvar.getNC() == 1 ) {
                        if ( arrayI < matvar.getNL() ) {
                            m_el_var = matvar.getElemento( arrayI, 0 );                        
                        } else {
                            String snl = String.valueOf( matvar.getNL() );
                            String snc = String.valueOf( matvar.getNC() );
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, varPos, ErroMSGs.MATRIZ_GET_EL_NUM_INDICES_INVALIDO, snl, snc );
                            return new MatrizELResult( erro );
                        }
                    } else {
                        if ( arrayI < matvar.getNC() ) {
                            m_el_var = matvar.getElemento( 0, arrayI );                        
                        } else {
                            String snl = String.valueOf( matvar.getNL() );
                            String snc = String.valueOf( matvar.getNC() );
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, varPos, ErroMSGs.MATRIZ_GET_EL_NUM_INDICES_INVALIDO, snl, snc );
                            return new MatrizELResult( erro );
                        }
                    }
                }
                break;                                
            case 2:
                arrayI = matIndices[0].getIndice();
                arrayJ = matIndices[1].getIndice();
                if ( arrayI == ArrayVO.VETOR && arrayJ == ArrayVO.VETOR ) {
                    m_el_var = matvar;
                } else {                 
                    if ( arrayI == ArrayVO.VETOR ) {
                        if ( arrayJ >= matvar.getNC() ) {
                            String snl = String.valueOf( matvar.getNL() );
                            String snc = String.valueOf( matvar.getNC() );
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, varPos, ErroMSGs.MATRIZ_GET_EL_NUM_INDICES_INVALIDO, snl, snc );
                            return new MatrizELResult( erro );
                        }
                        
                        m_el_var = new VetorMatrizVar( matvar, arrayJ, VetorMatrizVar.LINHA_ID );                         
                    } else if ( arrayJ == ArrayVO.VETOR ) {
                        if ( arrayI >= matvar.getNL() ) {
                            String snl = String.valueOf( matvar.getNL() );
                            String snc = String.valueOf( matvar.getNC() );
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, varPos, ErroMSGs.MATRIZ_GET_EL_NUM_INDICES_INVALIDO, snl, snc );
                            return new MatrizELResult( erro );
                        }
                            
                        m_el_var = new VetorMatrizVar( matvar, arrayI, VetorMatrizVar.COLUNA_ID );                        
                    } else {
                        if ( arrayI >= matvar.getNL() ) {
                            String snl = String.valueOf( matvar.getNL() );
                            String snc = String.valueOf( matvar.getNC() );
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, varPos, ErroMSGs.MATRIZ_GET_EL_NUM_INDICES_INVALIDO, snl, snc );
                            return new MatrizELResult( erro );
                        }
                        if ( arrayJ >= matvar.getNC() ) {
                            String snl = String.valueOf( matvar.getNL() );
                            String snc = String.valueOf( matvar.getNC() );
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, varPos, ErroMSGs.MATRIZ_GET_EL_NUM_INDICES_INVALIDO, snl, snc );
                            return new MatrizELResult( erro );
                        }
                        
                        m_el_var = matvar.getElemento( arrayI, arrayJ );                        
                    }                                                                        
                }   
                break;
            default:
                CodigoErro erro =  new CodigoErro( this.getClass(), codigo, varPos, ErroMSGs.MATRIZ_GET_EL_NUM_INDICES_INVALIDO );
                return new MatrizELResult( erro );
        }
        
        return new MatrizELResult( m_el_var );
    }                     
      
    public MatrizIndice[] calculaIndicesParaAddAoFim( MatrizVar matvar, int pos ) {                
        return new MatrizIndice[] {
            new MatrizIndice( matvar.getNL()-1, pos ),
            new MatrizIndice( matvar.getNC(), pos )
        };
    }
    
    private CodigoErro trataDIMsIncompativeisErro( Codigo codigo, int nl_esp, int nc_esp, int nl_enc, int nc_enc, int pos ) {
        String nl1 = String.valueOf( nl_esp );
        String nc1 = String.valueOf( nc_esp );
        String nl2 = String.valueOf( nl_enc );
        String nc2 = String.valueOf( nc_enc );
        return new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.MATRIZES_DIMS_INCOMPATIVEIS, nl1, nc1, nl2, nc2 );
    }
    
    public MatrizUtil getMatrizUtil() {
        return util;
    }
    
}
