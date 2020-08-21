package italo.explab.analisador.sintatico.cmd;

import italo.explab.ErroMSGs;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class LanceAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public LanceAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;      
                
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.LANCE );
        if ( cont == 0 )
            return new AnaliseResult();
                
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
                        
                
        AnaliseResult result = manager.getValorAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 ) {
            if ( result.getErro() != null )
                return result;        
            
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
            return new AnaliseResult( erro );
        }
        
        j += result.getJ();
                                
        return new AnaliseResult( j );
    }
    
}
