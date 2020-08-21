package italo.explab.analisador.sintatico.estruturas.tentecapture;

import italo.explab.ErroMSGs;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class TenteCaptureAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public TenteCaptureAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa(Codigo codigo, int i) {        
        int j = 0;
        
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.TENTE );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        char ch = codigo.getSEGCH( i+j );
        if ( ch != '{' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO );
            return new AnaliseResult( erro );
        }
                
        AnaliseResult result = manager.getBlocoCodigoAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();        
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CAPTURE );
        if ( cont == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRA_RESERVADA_ESPERADA, PalavrasReservadas.CAPTURE );
            return new AnaliseResult( erro );
        }
        while ( cont > 0 ) {                
            j += cont;
            j += manager.getContUtil().contaEsps( codigo, i+j );

            ch = codigo.getSEGCH( i+j );
            if ( ch != '(' ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_PARENTESES_ESPERADO );
                return new AnaliseResult( erro );
            }

            j++;

            boolean fim = false;
            boolean prim = true;
            while( !fim && codigo.isCHValido( i+j ) ) {
                j += manager.getContUtil().contaEsps( codigo, i+j );
                ch = codigo.getSEGCH( i+j );
                if ( ch == ')' ) {
                    if ( prim ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_DE_EXCECAO_ESPERADA );
                        return new AnaliseResult( erro );
                    } else {
                        j++;
                        fim = true;
                        continue;
                    }
                }

                prim = false;

                cont = manager.getContUtil().contaClasseOuPacoteNomeTam( codigo, i+j );
                if ( cont == 0 ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_DE_EXCECAO_ESPERADA );
                    return new AnaliseResult( erro );
                }

                j += cont;
                j += manager.getContUtil().contaEsps( codigo, i+j );

                ch = codigo.getSEGCH( i+j );
                if ( ch == '|' ) {
                    j++;
                    continue;
                }

                cont = manager.getContUtil().contaVarNomeTam( codigo, i+j );
                if ( cont == 0 ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VAR_NOME_ESPERADO );
                    return new AnaliseResult( erro );
                }

                j += cont;
                j += manager.getContUtil().contaEsps( codigo, i+j );

                ch = codigo.getSEGCH( i+j );
                if ( ch != ')' ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
                    return new AnaliseResult( erro );
                }

                j++;
                fim = true;            
            }

            j += manager.getContUtil().contaEsps( codigo, i+j );

            ch = codigo.getSEGCH( i+j );
            if ( ch != '{' ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO );
                return new AnaliseResult( erro );
            }

            result = manager.getBlocoCodigoAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 )
                return result;            

            j += result.getJ();        
            j += manager.getContUtil().contaEsps( codigo, i+j );

            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CAPTURE );
        }
        
        cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FINALIZE );
        if ( cont > 0 ) {
            j += cont;
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            ch = codigo.getSEGCH( i+j );
            if ( ch != '{' ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO );
                return new AnaliseResult( erro );
            }
            
            result = manager.getBlocoCodigoAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 )
                return result;
            
            j += result.getJ();
        }
            
        return new AnaliseResult( j );
    }
        
}
