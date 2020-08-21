package italo.explab.analisador.sintatico.cmd;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class AjudaAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public AjudaAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;        
            
        j += manager.getContUtil().contaEsps( codigo, i+j );
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.AJUDA );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        int jj = manager.getContUtil().contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < codigo.getCodlen() )
            return new AnaliseResult();
                
        j += jj;
        
        cont = manager.getContUtil().contaSequenciaCHs( codigo, i+j, ';' );
        if ( cont == 0 ) {
            if ( j < codigo.getCodlen() ) {
                j = codigo.getCodlen();    
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.AJUDA_TERMO_ESPERADO );
                return new AnaliseResult( erro );
            }
        } else {
            j += cont;
        }
        
        return new AnaliseResult( j );
    }
    
}
