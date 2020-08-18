package italo.explab_ide.gui.principal.projetos.popupmenu;

import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.gui.IDEGUIConfig;
import italo.explab_ide.gui.icones.GUIIcones;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ProjetosPopupMenu extends JPopupMenu implements ActionListener {
  
    private final JMenuItem executarMI;
    private final JMenuItem atualizarMI;
    private final JMenuItem renomearMI;
    private final JMenuItem deletarMI;
    
    private ImageIcon executarIcone;
    private ImageIcon atualizarIcone;
    private ImageIcon renomearIcone;
    private ImageIcon removerIcone;
        
    private final NovoArquivoMenu novoArqMenu;
    
    private ProjetosPopupMenuListener listener;
    
    public ProjetosPopupMenu( IDEGUIConfig cfg ) {
        try {
            executarIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "executar16x16.png" ) ) );
            atualizarIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "atualizar16x16.png" ) ) );
            renomearIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "renomear16x16.png" ) ) );
            removerIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "deletar16x16.png" ) ) );            
        } catch ( IOException e ) {
            
        }
        
        executarMI = new MI( cfg.getTextoRotulo( IDEGUI.BT_EXECUTAR ), executarIcone );
        atualizarMI = new MI( cfg.getTextoRotulo( IDEGUI.BT_ATUALIZAR ), atualizarIcone );
        renomearMI = new MI( cfg.getTextoRotulo( IDEGUI.BT_RENOMEAR ), renomearIcone );
        deletarMI = new MI( cfg.getTextoRotulo( IDEGUI.BT_DELETAR ), removerIcone );
        
        novoArqMenu = new NovoArquivoMenu( cfg );
        
        super.add( novoArqMenu );
        super.addSeparator();
        super.add( executarMI );
        super.addSeparator();
        super.add( atualizarMI );
        super.addSeparator();
        super.add( renomearMI );
        super.add( deletarMI );
        
        executarMI.addActionListener( this );
        atualizarMI.addActionListener( this );
        renomearMI.addActionListener( this );
        deletarMI.addActionListener( this ); 
        
        novoArqMenu.getNovaPastaMI().addActionListener( this );
        novoArqMenu.getNovoArqExpLabMI().addActionListener( this );
        novoArqMenu.getNovoArqVasioMI().addActionListener( this ); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == executarMI ) {
            listener.executarAcionado();
        } else if ( e.getSource() == atualizarMI ) {
            listener.recarregarAcionado();
        } else if ( e.getSource() == renomearMI ) {
            listener.renomearAcionado();
        } else if ( e.getSource() == deletarMI ) {
            listener.deletarAcionado();
        } else if ( e.getSource() == novoArqMenu.getNovoArqExpLabMI() ) {
            listener.novoArqExpLabAcionado();
        } else if ( e.getSource() == novoArqMenu.getNovoArqVasioMI() ) {
            listener.novoArqVasioAcionado();
        } else if ( e.getSource() == novoArqMenu.getNovaPastaMI() ) {
            listener.novaPastaAcionado();
        }
    }
    
    public void setProjetoPopupMenuListener( ProjetosPopupMenuListener listener ) {
        this.listener = listener;
    }
    
    class NovoArquivoMenu extends JMenu {
        
        private final JMenuItem novaPastaMI;
        private final JMenuItem novoArqExpLabMI;
        private final JMenuItem novoArqVasioMI;

        private ImageIcon novaPastaIcone;
        private ImageIcon novoArqExpLabIcone;
        private ImageIcon novoArqVazioIcone;
        
        public NovoArquivoMenu( IDEGUIConfig cfg ) {
            super.setText( cfg.getTextoRotulo( IDEGUI.BT_NOVO ) );
            
            try {
                novaPastaIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "nova-pasta16x16.png" ) ) );
                novoArqExpLabIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "arquivo-explab16x16.png" ) ) );
                novoArqVazioIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "arquivo16x16.png" ) ) );
            } catch ( IOException e ) {

            }
            
            novaPastaMI = new MI( cfg.getTextoRotulo( IDEGUI.BT_NOVA_PASTA ), novaPastaIcone );
            novoArqExpLabMI = new MI( cfg.getTextoRotulo( IDEGUI.BT_NOVO_ARQ_EXPLAB ), novoArqExpLabIcone );
            novoArqVasioMI = new MI( cfg.getTextoRotulo( IDEGUI.BT_NOVO_ARQ_VASIO ), novoArqVazioIcone );
            
            super.add( novaPastaMI );
            super.add( novoArqExpLabMI );
            super.add( novoArqVasioMI );        
        }

        public JMenuItem getNovaPastaMI() {
            return novaPastaMI;
        }

        public JMenuItem getNovoArqExpLabMI() {
            return novoArqExpLabMI;
        }

        public JMenuItem getNovoArqVasioMI() {
            return novoArqVasioMI;
        }
        
    }
    
    class MI extends JMenuItem {
        
        public MI( String nome, Icon icon ) {
            super( nome, icon );
        }
        
        @Override
        public void paintComponent( Graphics g ) {
            Graphics2D g2d = (Graphics2D)g;
            
            g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            g2d.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY ); 
            
            super.paintComponent( g ); 
        }
        
    }
    
}
