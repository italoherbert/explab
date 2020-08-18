package italo.explab_ide.logica.format.el;

import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.format.CodigoFonteFormatConfig;
import javax.swing.text.AttributeSet;

public class ComentarioFormat implements ValorFormat {
    
    private final ExpLabIDEAplic aplic;

    public ComentarioFormat(ExpLabIDEAplic aplic) {
        this.aplic = aplic;
    }
    
    @Override
    public AttributeSet getStyle( CodigoFonteFormatConfig cfg ) {
        return cfg.getComentarioStyle();
    }
    
    @Override
    public AnaliseResult processa( Codigo codigo, int i ) {
        return aplic.getAnalisadorSintaticoManager().getComentarioAnalisador().analisa( codigo, i );
    }
    
}
