package italo.explab_ide.gui.deletarprojeto;

import italo.explab_ide.gui.IDEGUIConfig;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeletarProjetoPNL extends JPanel {
    
    private final DeletarProjetoGUITO to = new DeletarProjetoGUITO( this );

    private final JLabel mensagemLB = new JLabel();
    private final JCheckBox removerProjArqsCB = new JCheckBox();
    
    public DeletarProjetoPNL( IDEGUIConfig cfg ) {
        removerProjArqsCB.setSelected( false ); 
        
        JPanel mensagemPNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        mensagemPNL.add( mensagemLB );
                       
        JPanel remProjArqsPNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        remProjArqsPNL.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) ); 
        remProjArqsPNL.add( removerProjArqsCB );
        
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.CENTER, mensagemPNL );
        super.add( BorderLayout.SOUTH, removerProjArqsCB );                
    }               
        
    public DeletarProjetoGUITO getGUITO() {
        return to;
    }

    public JLabel getMensagemLB() {
        return mensagemLB;
    }

    public JCheckBox getRemoverProjArqsCB() {
        return removerProjArqsCB;
    }
    
}
