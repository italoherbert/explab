package italo.explab.analisador.sintatico.var;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public class LeituraVarAnalisadorSintatico extends AbstractLeituraVarAnalisadorSintatico {

    public LeituraVarAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        super( manager );
    }

    @Override
    public AnaliseResult analisaValor( Codigo codigo, int i ) {
        AnaliseResult result = manager.getValorAnalisador().analisa( codigo, i );
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
                        
        return manager.getLeituraFuncValorAnalisador().analisa( codigo, i );
    }
        
}
