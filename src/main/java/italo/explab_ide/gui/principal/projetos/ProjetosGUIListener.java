package italo.explab_ide.gui.principal.projetos;

import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;
import libs.gui.arv.ArvGUITO;
import libs.gui.arv.ArvNo;

public interface ProjetosGUIListener {
    
    public void duploClique( ArvNo no, MouseEvent e );
    
    public void moveAcionado( ArvGUITO arvGUITO, TreePath[] origs, TreePath dest );
    
    public void deleteAcionado( ArvGUITO arvGUITO );
    
}
