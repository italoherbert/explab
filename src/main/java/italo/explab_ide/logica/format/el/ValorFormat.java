package italo.explab_ide.logica.format.el;

import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab_ide.logica.format.CodigoFonteFormatConfig;
import javax.swing.text.AttributeSet;

public interface ValorFormat {        
    
    public AttributeSet getStyle( CodigoFonteFormatConfig cfg );
    
    public AnaliseResult processa( Codigo codigo, int i );
       
        
}
