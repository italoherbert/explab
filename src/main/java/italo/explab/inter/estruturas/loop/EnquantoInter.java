package italo.explab.inter.estruturas.loop;

import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.estruturas.node.Enquanto;
import italo.explab.arvore.exp.Exp;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.estruturas.ComandoInter;
import italo.explab.util.ContadorUtil;

public class EnquantoInter extends ComandoInter {
            
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        ContadorUtil contUtil = aplic.getContUtil();
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        
        AnaliseResult aresult = asManager.getEnquantoAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
                        
        int j = 0;
                
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.ENQUANTO );
        if ( cont == 0 )
            return new InterResult();
        
        Enquanto enquanto = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoEnquanto( i+j, no, codigo );
        
        j += cont;        
        j += contUtil.contaEsps( codigo, i+j );                                              
        
        InterResult result = super.interpretaCondicao( enquanto, enquanto, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;
        
        enquanto.setCondicao( (Exp)result.getInstrucaoOuExp() ); 
                
        j += result.getJ();
        j += contUtil.contaEsps( codigo, i+j );

        result = super.interpretaBloco( enquanto, enquanto, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();               
                
        return new InterResult( enquanto, j );
    }
    
}
