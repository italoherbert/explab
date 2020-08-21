package italo.explab_ide.logica.arquivo.projeto;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.ctrl.ProjetoCtrl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjetoConfigLeitor {
    
    private ExpLabIDEAplic aplic;
    
    public ProjetoConfigLeitor( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
    
    public ProjetoConfig le( Projeto proj ) {
        String execScript = aplic.getConfig().getProjetoExecScriptPadrao();
        String charset = aplic.getConfig().getProjetoCharsetPadrao();
        Properties p = new Properties();
        try {
            String projFile = proj.getBasedir().replaceAll( "\\\\", "/" );
            if  ( !projFile.endsWith( "/" ) )
                projFile += "/";

            projFile += aplic.getConfig().getProjetoConfigArqPadrao();

            p.load( new FileInputStream( projFile ) );

            execScript = p.getProperty( "principal" );             

            String charsetP = p.getProperty( "charset" );
            if ( charsetP != null )
                charset = charsetP;                
        } catch (IOException ex) {
            if ( !( ex instanceof FileNotFoundException ) )
                Logger.getLogger(ProjetoCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ProjetoConfig( proj, charset, execScript );
    }
    
}
