package italo.explab_ide.gui.principal.projetos;

import italo.explab_ide.gui.icones.GUIIcones;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import libs.gui.arv.ArvNode;

public class ELArvCellRenderer extends DefaultTreeCellRenderer {

    private ImageIcon pastaIcon;
    private ImageIcon arqELIcon;
    private ImageIcon arqIcon;
    
    private String elext = ".exp";
            
    public ELArvCellRenderer() {
        try {
            pastaIcon = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "pasta-aberta16x16.png" ) ) );
            arqELIcon = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "arquivo-explab16x16.png" ) ) );
            arqIcon = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "arquivo16x16.png" ) ) );
        } catch (IOException ex) {
            Logger.getLogger(ELArvCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g2d.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY ); 

        super.paintComponent( g ); 
    }
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {        
        super.getTreeCellRendererComponent( tree, value, selected, expanded, leaf, row, hasFocus );
        
        if ( leaf ) {
            ArvNode arvNode = (ArvNode)value;
            if ( arvNode.getArvUserObject().isEhPasta() ) {
                super.setIcon( pastaIcon );
            } else {
                if ( arvNode.getArvUserObject().getNome().endsWith( elext ) ) {
                    super.setIcon( arqELIcon );
                } else {
                    super.setIcon( arqIcon ); 
                }
            }
        } else {
            super.setIcon( pastaIcon );
        }
        
        return this;
    }
    
    public String getELExt() {
        return elext;
    }
    
    public void setELExt( String elext ) {
        this.elext = elext;
    }
    
}
