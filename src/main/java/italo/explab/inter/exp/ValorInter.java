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

public class ValorInter extends AbstractValorInter {

    @Override
    protected InterResult interpretaEXPs( ExecNo bno, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();      
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
                                                
        AnaliseResult aresult1 = asManager.getBoolExpAnalisador().analisa( codigo, i );                
        AnaliseResult aresult2 = asManager.getNumericaExpAnalisador().analisa( codigo, i );
        AnaliseResult aresult3 = asManager.getVarTalvezComParentesesAnalisador().analisa( codigo, i );
        
        InterResult result = null;        
        if ( aresult1.getJ() == aresult2.getJ() && aresult1.getJ() == aresult3.getJ() ) {
            result = manager.getVarTalvezComParentesesInter().interpreta( bno, base, aplic, codigo, i, i2 );
        } else {        
            if ( aresult1.getJ()+aresult2.getJ() > 0 ) {
                if ( aresult1.getJ() > aresult2.getJ() ) {
                    result = manager.getBoolExpInter().interpreta( bno, base, aplic, codigo, i, i2 );                                        
                } else if ( aresult1.getJ() < aresult2.getJ() ) {
                    result = manager.getNumExpInter().interpreta( bno, base, aplic, codigo, i, i2 );    
                }     
            }            
        }

        if ( result != null )
            if ( result.getJ() > 0 || result.getErro() != null )
                return result;        
                        
        AnaliseResult aresult4 = asManager.getStringExpAnalisador().analisa( codigo, i );
        if ( aresult4.getJ() != aresult3.getJ() )
            return manager.getStringExpInter().interpreta( bno, base, aplic, codigo, i, i2 );                                   
        
        return new InterResult(); 
    }

    @Override
    public AnalisadorSintatico getValorAnalisador(AnalisadorSintaticoManager asManager) {
        return asManager.getValorAnalisador();
    }
    
}
