package italo.explab.inter.exp;

import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;

public class NaoBoolExpValorInter extends AbstractValorInter {

    @Override
    protected InterResult interpretaEXPs( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();                        
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();

        AnaliseResult aresult1 = asManager.getVarTalvezComParentesesAnalisador().analisa( codigo, i );
        AnaliseResult aresult2 = asManager.getNumericaExpAnalisador().analisa( codigo, i );
        
        if ( aresult1.getJ() != aresult2.getJ() ) {
            InterResult result = manager.getNumExpInter().interpreta( no, base, aplic, codigo, i, i2 );
            if ( result.getJ() > 0 || result.getErro() != null )
                return result;          
        } 
                                                                
        aresult2 = asManager.getStringExpAnalisador().analisa( codigo, i );
        if ( aresult1.getJ() != aresult2.getJ() ) 
            return manager.getStringExpInter().interpreta( no, base, aplic, codigo, i, i2 );                                                           
        
        return new InterResult();
    }

    @Override
    public AnalisadorSintatico getValorAnalisador(AnalisadorSintaticoManager asManager) {
        return asManager.getValorNaoBooleanExpAnalisador();
    }
    
}

