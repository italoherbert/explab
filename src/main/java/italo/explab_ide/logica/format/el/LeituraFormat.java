package italo.explab_ide.logica.format.el;

import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab_ide.logica.format.CodigoFonteManager;
import java.util.List;
import italo.explab_ide.logica.format.CodigoFonteDocumento;

public interface LeituraFormat {
    
    public AnaliseResult processa( CodigoFonteManager manager, CodigoFonteDocumento doc,  List<CodigoErro> erros, Codigo codigo, int i );
    
}
