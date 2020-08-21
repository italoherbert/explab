package italo.explab.analisador.sintatico.cmd;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class LeiaLNAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public LeiaLNAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa(Codigo codigo, int i) {
        int j = 0;
                
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.LEIALN );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        
        int jj = manager.getContUtil().contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < codigo.getCodlen() )
            return new AnaliseResult();
                
        j += jj;
                                
        AnaliseResult result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;
        
        cont = result.getJ();
                
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );                
                    
        char ch = codigo.getSEGCH( i+j );
        if ( ch == ':' ) {
            j++;
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.STRING );
            if ( cont > 0 ) {                                                            
                j += cont;
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRA_RESERVADA_ESPERADA, PalavrasReservadas.STRING );
                return new AnaliseResult( erro );
            }
        }    
        
        return new AnaliseResult( j );
    }
    
}
