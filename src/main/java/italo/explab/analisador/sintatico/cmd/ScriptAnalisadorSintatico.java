package italo.explab.analisador.sintatico.cmd;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class ScriptAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public ScriptAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
                        
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.SCRIPT );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        
        int jj = manager.getContUtil().contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < codigo.getCodlen() )
            return new AnaliseResult();
                
        j += jj;
                
        AnaliseResult result = manager.getStringExpAnalisador().analisa( codigo, i+j );
        if ( result.getJ() > 0 ) {
            j += result.getJ();
        } else {
            cont = manager.getContUtil().contaSequenciaCHs( codigo, i+j, true, ' ', '\r', '\t', '\n', ';' );
            if ( cont > 0 ) {
                j += cont;
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ARQ_NOME_ESPERADO );
                return new AnaliseResult( erro ) ;
            }
        }
                      
        return new AnaliseResult( j );
    }
    
}
