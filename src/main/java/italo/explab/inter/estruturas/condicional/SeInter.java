package italo.explab.inter.estruturas.condicional;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.estruturas.node.Se;
import italo.explab.arvore.exp.Exp;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.estruturas.ComandoInter;
import italo.explab.util.ContadorUtil;

public class SeInter extends ComandoInter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
                        
        AnaliseResult aresult = asManager.getSeAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
        
        ContadorUtil contUtil = aplic.getContUtil();        
                                               
        int j = 0;
        j += contUtil.contaEsps( codigo, i+j );
                
        j += contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.SE );
        j += contUtil.contaEsps( codigo, i+j );        
        
        Se se = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoSe( i, no, codigo );
        
        InterResult result = super.interpretaCondicao( se, se, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        j += contUtil.contaEsps( codigo, i+j );
                                               
        se.setCondicao( (Exp)result.getInstrucaoOuExp() );          
        
        result = super.interpretaBloco( se.getEntaoGrupo(), se.getEntaoGrupo(), aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )   
            return result;
        
        j += result.getJ();        
        j += contUtil.contaEsps( codigo, i+j );
        
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.SENAO );
        if ( cont > 0 ) {
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            result = super.interpretaBloco( se.getSenaoGrupo(), se.getSenaoGrupo(), aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0 )
                return result;
            
            j += result.getJ();
        }            
                        
        return new InterResult( se, j );
    }
            
}
