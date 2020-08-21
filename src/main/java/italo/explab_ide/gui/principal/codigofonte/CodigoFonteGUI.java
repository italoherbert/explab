package italo.explab_ide.gui.principal.codigofonte;

import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.gui.IDEGUIConfig;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUI;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUIListener;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import libs.gui.tpcombtfechar.TPComBTFechar;
import libs.gui.tpcombtfechar.TPComBTFecharListener;

public class CodigoFonteGUI extends JPanel implements ChangeListener, ActionListener, MouseListener, TPComBTFecharListener, CodigoFonteTPPainelGUIListener {
        
    private final TPComBTFechar tp = new TPComBTFechar();            
   
    private final CodigoFonteGUITO to = new CodigoFonteGUITO( this );
    
    private CodigoFonteGUIListener listener;
    
    private final CFTPPopupMenu cftpPopupMenu;
    private int ultimaTabClicadaI = -1;
    
    public CodigoFonteGUI( IDEGUIConfig cfg ) {        
        this.cftpPopupMenu = new CFTPPopupMenu( cfg );
        
        super.setLayout( new GridLayout() );
        super.add( tp );
             
        tp.setTPListener( this );                
        tp.addChangeListener( this ); 
        tp.addMouseListener( this ); 
        
        cftpPopupMenu.getFecharTodosBT().addActionListener( this );
        cftpPopupMenu.getFecharOutrosBT().addActionListener( this );
        cftpPopupMenu.getFecharEstaBT().addActionListener( this );
    }
    
    public CodigoFonteTPPainelGUITO addTabPainel( String tabRotulo ) {
        CodigoFonteTPPainelGUI gui = new CodigoFonteTPPainelGUI( tp, tabRotulo );
        gui.setCodigoFonteTPPainelGUIListener( this ); 
        tp.addTabPainel( tabRotulo, gui );        
        return gui.getGUITO();
    }
    
    public CodigoFonteTPPainelGUITO getTPPainelGUITO( int i ) {
        Component c = tp.getComponentAt( i );
        if ( c != null )
            return ((CodigoFonteTPPainelGUI)c).getGUITO();
        return null;
    }
    
    public CodigoFonteTPPainelGUITO getSelecionadoTPPainelGUITO() {
        Component c = tp.getSelectedComponent();
        if ( c != null )
            return ((CodigoFonteTPPainelGUI)c).getGUITO();
        return null;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Component c = tp.getSelectedComponent();
        if ( c != null )            
            ((CodigoFonteTPPainelGUI)c).getDocTextoPane().requestFocusInWindow();                                           
    }    
           
    @Override
    public void actionPerformed( ActionEvent e ) {        
        if ( listener == null )
            return;
        
        if ( e.getSource() == cftpPopupMenu.getFecharTodosBT() ) {
            listener.removeTodasAsTabs( to, ultimaTabClicadaI );
        } else if ( e.getSource() == cftpPopupMenu.getFecharOutrosBT() ) {
            listener.removeOutrasTabs( to, ultimaTabClicadaI );
        } else if ( e.getSource() == cftpPopupMenu.getFecharEstaBT() ) {
            listener.removeEstaTab( to, ultimaTabClicadaI );
        }
    }
   
    @Override
    public void mousePressed(MouseEvent e) {
        if ( e.getButton() == MouseEvent.BUTTON3 ) {
            int len = tp.getTabCount();
            ultimaTabClicadaI = -1;
            for( int i = 0; ultimaTabClicadaI == -1 && i < len; i++ )
                if ( tp.getBoundsAt( i ).contains( e.getPoint() ) )
                    ultimaTabClicadaI = i;
            
            cftpPopupMenu.show( this, e.getX(), e.getY() ); 
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    

    @Override
    public void teclaDigitada( CodigoFonteTPPainelGUITO guiTO, Point p ) {
        if ( listener != null )
            listener.teclaDigitada( guiTO, p );
    }

    @Override
    public boolean perguntaSeFechar(int i) { 
        if ( listener == null )
            return true;
        
        CodigoFonteTPPainelGUITO selectTO = this.getSelecionadoTPPainelGUITO();
        return listener.verificaSeFechar( selectTO );
    }

    @Override
    public void antesTabRemovida(int i) {
        if ( listener != null ) {
            CodigoFonteTPPainelGUI pgui = (CodigoFonteTPPainelGUI)tp.getComponentAt( i );
            listener.antesTabRemovida( pgui.getGUITO(), i );
        }
    }
    
    @Override
    public void aposTabRemovida( int i ) {
        
    }
                
    public void setCodigoFonteGUIListener( CodigoFonteGUIListener listener ) {
        this.listener = listener;
    }

    public TPComBTFechar getTP() {
        return tp;
    }
    
    public CodigoFonteGUITO getGUITO() {
        return to;
    }
    
    class CFTPPopupMenu extends JPopupMenu {
        
        private final JMenuItem fecharTodosBT;
        private final JMenuItem fecharOutrosBT;
        private final JMenuItem fecharEsteBT;
        
        public CFTPPopupMenu( IDEGUIConfig cfg ) {
            super.add( this.fecharTodosBT = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_FECHAR_TODOS ) ) );
            super.add( this.fecharOutrosBT = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_FECHAR_OUTROS ) ) );
            super.add( this.fecharEsteBT = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_FECHAR_ESTE ) ) );
        }

        public JMenuItem getFecharTodosBT() {
            return fecharTodosBT;
        }

        public JMenuItem getFecharOutrosBT() {
            return fecharOutrosBT;
        }

        public JMenuItem getFecharEstaBT() {
            return fecharEsteBT;
        }
        
    }
    
}
