package italo.explab.inter.estruturas.loop;

import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.estruturas.node.FacaEnquanto;
import italo.explab.arvore.exp.Exp;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.estruturas.ComandoInter;
import italo.explab.util.ContadorUtil;

public class FacaEnquantoInter extends ComandoInter {
            
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        ContadorUtil contUtil = aplic.getContUtil();
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();       
                       
        AnaliseResult aresult = asManager.getFacaEnquantoAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
                
        int j = 0;        
        
        j += contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.FACA );        
        j += contUtil.contaEsps( codigo, i+j );
        
        FacaEnquanto facaEnquanto = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoFacaEnquanto( i, no, codigo );
                
        InterResult result = super.interpretaBloco( facaEnquanto, facaEnquanto, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        
        j += contUtil.contaEsps( codigo, i+j );

        j += contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.ENQUANTO );
        j += contUtil.contaEsps( codigo, i+j );
                                    
        InterResult condicaoResult = super.interpretaCondicao( facaEnquanto, facaEnquanto, aplic, codigo, i+j, i2 );                                        
        if ( condicaoResult.getJ() == 0 )
            return condicaoResult;
                        
        facaEnquanto.setCondicao( (Exp)condicaoResult.getInstrucaoOuExp() ); 
            
        j += condicaoResult.getJ();
        
        return new InterResult( facaEnquanto, j );                
    }
        
}
