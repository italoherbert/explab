package italo.explab.analisador.sintatico.estruturas.loop;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class FacaEnquantoAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager manager;

    public FacaEnquantoAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }

    @Override
    public AnaliseResult analisa(Codigo codigo, int i) {
        int j = 0;
        char ch;
                
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FACA );
        if ( cont == 0 )
            return new AnaliseResult();
                
        j+= cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        AnaliseResult result;
        if( codigo.getSEGCH( i+j ) == '{' ) {                
            result = manager.getBlocoCodigoAnalisador().analisa( codigo, i+j );
        } else {
            result = manager.getInstrucaoAnalisador().analisa( codigo, i+j );
        }
        
        if ( result.getJ() == 0 )
            return result;

        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.ENQUANTO );
        if ( cont == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRA_RESERVADA_ESPERADA, PalavrasReservadas.ENQUANTO );            
            return new AnaliseResult( erro );
        }        
        
        j += cont;        
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        result = manager.getBoolExpAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;        
        
        j += result.getJ();                       
        
        return new AnaliseResult( j ); 
    }
    
}
