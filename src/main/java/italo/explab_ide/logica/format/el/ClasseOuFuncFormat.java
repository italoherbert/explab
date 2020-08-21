package italo.explab_ide.logica.format.el;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.format.CodigoFonteDocumento;
import italo.explab_ide.logica.format.CodigoFonteManager;
import java.util.List;
import javax.swing.text.AttributeSet;

public abstract class ClasseOuFuncFormat implements LeituraFormat, AnalisadorSintatico {
            
    protected final ExpLabIDEAplic aplic;

    public ClasseOuFuncFormat( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public AnaliseResult processa( CodigoFonteManager manager, CodigoFonteDocumento doc, List<CodigoErro> erros, Codigo codigo, int i ) {
        AnaliseResult aresult = this.analisa( codigo, i );                 
                            
        int j = 0;
        if ( aresult.getJ() > 0 ) {            
            AttributeSet normalStyle = manager.getFormatConfig().getNormalStyle();
            AttributeSet palResStyle = manager.getFormatConfig().getPalavraReservadaStyle();
            AttributeSet classeOuFuncStyle = manager.getFormatConfig().getClasseOuFuncNomeStyle();

            int cont = aresult.getJ();
            if ( cont > 0 )
                doc.append( codigo.getCodigo().substring( i+j, i+j+cont ), palResStyle  );            
            j += cont;

            cont = aplic.getContadorUtil().contaEsps( codigo, i+j );
            doc.append( codigo.getCodigo().substring( i+j, i+j+cont ), normalStyle );
            j += cont;

            cont = aplic.getContadorUtil().contaClasseOuPacoteNomeTam( codigo, i+j );                    
            if ( cont > 0 )
                doc.append( codigo.getCodigo().substring( i+j, i+j+cont ), classeOuFuncStyle  );
            j += cont;

            cont = aplic.getContadorUtil().contaEsps( codigo, i+j );
            doc.append( codigo.getCodigo().substring( i+j, i+j+cont ), normalStyle );
            j += cont;
        }
        
        return new AnaliseResult( j ); 
    }
    
}
