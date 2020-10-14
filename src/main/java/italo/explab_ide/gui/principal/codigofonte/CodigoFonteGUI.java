package italo.explab_ide.gui.principal.codigofonte;

import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.gui.IDEGUIConfig;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUI;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUIListener;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
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
    private int ultimaTabClicadaBT3_i = -1;
    
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
        cftpPopupMenu.getExecutarArquivoBT().addActionListener( this );
    }
    
    public CodigoFonteTPPainelGUITO addTabPainel( String tabRotulo ) {
        CodigoFonteTPPainelGUI gui = new CodigoFonteTPPainelGUI( tp, tabRotulo );
        gui.setCodigoFonteTPPainelGUIListener( this ); 
        tp.addTabPainel( tabRotulo, gui );        
        return gui.getGUITO();
    }
    
    public void removeTabPainels() {
        while( tp.getTabCount() > 0 )
            tp.removeTabAt( 0 ); 
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
    
    private void processaCFTPPopupMenu( MouseEvent e, Rectangle tabBounds ) {
        if ( e.getButton() == MouseEvent.BUTTON3 ) {      
            int len = tp.getTabCount();
            ultimaTabClicadaBT3_i = -1;
            for( int i = 0; ultimaTabClicadaBT3_i == -1 && i < len; i++ )
                if ( tp.getBoundsAt( i ).contains( e.getPoint() ) )
                    ultimaTabClicadaBT3_i = i;
            
            cftpPopupMenu.show( (Component)e.getSource(), e.getX(), e.getY() );
        }
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
            listener.removeTodasAsTabs( to, ultimaTabClicadaBT3_i );
        } else if ( e.getSource() == cftpPopupMenu.getFecharOutrosBT() ) {
            listener.removeOutrasTabs( to, ultimaTabClicadaBT3_i );
        } else if ( e.getSource() == cftpPopupMenu.getFecharEstaBT() ) {
            listener.removeEstaTab( to, ultimaTabClicadaBT3_i );
        } else if ( e.getSource() == cftpPopupMenu.getExecutarArquivoBT() ) {
            listener.executarArquivo( to ); 
        }
    }
   
    @Override
    public void mousePressed(MouseEvent e) {        
        if ( e.getButton() == MouseEvent.BUTTON3 ) {      
            int len = tp.getTabCount();
            ultimaTabClicadaBT3_i = -1;
            for( int i = 0; ultimaTabClicadaBT3_i == -1 && i < len; i++ )
                if ( tp.getBoundsAt( i ).contains( e.getPoint() ) )
                    ultimaTabClicadaBT3_i = i;
            
            cftpPopupMenu.show( tp, e.getX(), e.getY() );
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
    public void mousePressionadoEmTab( int i, MouseEvent e ) {
        if ( e.getButton() == MouseEvent.BUTTON3 ) {      
            ultimaTabClicadaBT3_i = -1;    
            cftpPopupMenu.show( (Component)e.getSource(), e.getX(), e.getY() ); 
        }
    }

    @Override
    public void mouseSobreTab( int i, MouseEvent e ) {        
        if ( listener == null )
            return;
        
        CodigoFonteTPPainelGUI tpPNLGUI = (CodigoFonteTPPainelGUI)tp.getComponentAt( i );
                
        String texto = listener.textoMouseSobreTab( tpPNLGUI.getGUITO(), i );
        if ( texto != null )
            tp.setTabToolTipTexto( texto, i );                                    
    }        

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
        private final JMenuItem executarArquivoBT;
        
        public CFTPPopupMenu( IDEGUIConfig cfg ) {
            super.add( this.fecharTodosBT = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_FECHAR_TODOS ) ) );
            super.add( this.fecharOutrosBT = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_FECHAR_OUTROS ) ) );
            super.add( this.fecharEsteBT = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_FECHAR_ESTE ) ) );
            super.addSeparator();
            super.add( this.executarArquivoBT = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_EXECUTAR_ARQUIVO ) ) );
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

        public JMenuItem getExecutarArquivoBT() {
            return executarArquivoBT;
        }
        
    }
    
}
