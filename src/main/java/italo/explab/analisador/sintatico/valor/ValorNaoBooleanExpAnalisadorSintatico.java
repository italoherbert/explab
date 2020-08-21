package italo.explab.analisador.sintatico.valor;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public class ValorNaoBooleanExpAnalisadorSintatico extends AbstractValorAnalisadorSintatico {

    public ValorNaoBooleanExpAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        super( manager );
    }

    @Override
    protected AnaliseResult analisaEXPs(Codigo codigo, int i) {
        AnaliseResult result = manager.getNumericaExpAnalisador().analisa( codigo, i );        
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
        return manager.getStringExpAnalisador().analisa( codigo, i );                           
    }
    
}
