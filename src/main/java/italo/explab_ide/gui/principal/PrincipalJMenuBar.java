package italo.explab_ide.gui.principal;

import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.gui.IDEGUIConfig;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class PrincipalJMenuBar extends JMenuBar implements ActionListener {
    
    private final JMenu arquivoMenu;
    private final JMenu editarMenu;
    private final JMenu codigoFonteMenu;
    private final JMenu ajudaMenu;
    
    private final JMenuItem novoProjetoMI;
    private final JMenuItem novoArquivoMI;
    private final JMenuItem abrirProjetoMI;
    private final JMenuItem salvarMI;
    private final JMenuItem sairMI;
    
    private final JMenuItem desfazerMI;
    private final JMenuItem refazerMI;    
    private final JMenuItem copiarMI;
    private final JMenuItem moverMI;
    private final JMenuItem colarMI;
    private final JMenuItem selecionarTudoMI;
        
    private final JMenuItem moverFrenteMI;
    private final JMenuItem moverTrazMI;
    private final JMenuItem completarCodigoMI;
    
    private final JMenuItem ajudaLinkMI;
    private final JMenuItem ajudaPorTermoMI;
    
    private PrincipalJMenuBarListener listener;
    
    private final PrincipalGUI gui;
    
    public PrincipalJMenuBar( IDEGUIConfig cfg, PrincipalGUI gui ) {                
        this.gui = gui;
        
        novoProjetoMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_NOVO_PROJETO ) );
        novoArquivoMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_NOVO_ARQUIVO ) );
        abrirProjetoMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_ABRIR_PROJETO ) );
        salvarMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_SALVAR_TODOS ) );
        sairMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_SAIR ) );
    
        desfazerMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_DESFAZER ) );
        refazerMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_REFAZER ) );
        copiarMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_COPIAR ) );
        moverMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_MOVER ) );
        colarMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_COLAR ) );
        selecionarTudoMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_SELECIONAR_TUDO ) );
        
        moverFrenteMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_TAB_FRENTE ));
        moverTrazMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_TAB_TRAZ ) );
        completarCodigoMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_COMPLETAR_CODIGO ) );
    
        ajudaLinkMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_AJUDA_LINK ) );
        ajudaPorTermoMI = new JMenuItem( cfg.getTextoRotulo( IDEGUI.BT_AJUDA_POR_TERMO ));
                
        arquivoMenu = new JMenu( cfg.getTextoRotulo( IDEGUI.MENU_ARQUIVO ) );
        arquivoMenu.add( novoProjetoMI );
        arquivoMenu.add( novoArquivoMI );
        arquivoMenu.add( abrirProjetoMI );
        arquivoMenu.add( salvarMI );
        arquivoMenu.add( sairMI );
        
        editarMenu = new JMenu( cfg.getTextoRotulo( IDEGUI.MENU_EDITAR ) );
        editarMenu.add( desfazerMI );
        editarMenu.add( refazerMI );
        editarMenu.add( copiarMI );
        editarMenu.add( moverMI );
        editarMenu.add( colarMI );
        editarMenu.add( selecionarTudoMI );
        
        codigoFonteMenu = new JMenu( cfg.getTextoRotulo( IDEGUI.MENU_CODIGO_FONTE ) );
        codigoFonteMenu.add( moverFrenteMI );
        codigoFonteMenu.add( moverTrazMI );
        codigoFonteMenu.add( completarCodigoMI );
        
        ajudaMenu =  new JMenu( cfg.getTextoRotulo( IDEGUI.MENU_AJUDA ) );
        ajudaMenu.add( ajudaLinkMI );
        ajudaMenu.add( ajudaPorTermoMI );
                       
        super.add( arquivoMenu );
        super.add( editarMenu );
        super.add( codigoFonteMenu );
        super.add( ajudaMenu );
        
        novoProjetoMI.addActionListener( this );
        novoArquivoMI.addActionListener( this );
        abrirProjetoMI.addActionListener( this );
        salvarMI.addActionListener( this );
        sairMI.addActionListener( this );
        
        KeyStroke strokeCtrlS = KeyStroke.getKeyStroke( KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK );
        KeyStroke strokeCtrlZ = KeyStroke.getKeyStroke( KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK );
        KeyStroke strokeCtrlY = KeyStroke.getKeyStroke( KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK );
        KeyStroke strokeCtrlA = KeyStroke.getKeyStroke( KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK );
        KeyStroke strokeCtrlX = KeyStroke.getKeyStroke( KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK );
        KeyStroke strokeCtrlC = KeyStroke.getKeyStroke( KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK );
        KeyStroke strokeCtrlV = KeyStroke.getKeyStroke( KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK );
        KeyStroke strokeTab = KeyStroke.getKeyStroke( KeyEvent.VK_TAB, 0 );
        KeyStroke strokeShiftTab = KeyStroke.getKeyStroke( KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK );
        KeyStroke strokeCtrlEsp = KeyStroke.getKeyStroke( KeyEvent.VK_SPACE, KeyEvent.CTRL_DOWN_MASK );
                
        desfazerMI.setAccelerator( strokeCtrlZ );        
        refazerMI.setAccelerator( strokeCtrlY );
        copiarMI.setAccelerator( strokeCtrlC );
        colarMI.setAccelerator( strokeCtrlV );
        moverMI.setAccelerator( strokeCtrlX );
        selecionarTudoMI.setAccelerator( strokeCtrlA );
        
        moverFrenteMI.setAccelerator( strokeTab );
        moverTrazMI.setAccelerator( strokeShiftTab );
        completarCodigoMI.setAccelerator( strokeCtrlEsp );
        
        salvarMI.setAccelerator( strokeCtrlS ); 
        
        desfazerMI.setAccelerator( strokeCtrlZ );        
        refazerMI.setAccelerator( strokeCtrlY );
        copiarMI.setAccelerator( strokeCtrlC );
        colarMI.setAccelerator( strokeCtrlV );
        moverMI.setAccelerator( strokeCtrlX );
        selecionarTudoMI.setAccelerator( strokeCtrlA );
        
        moverFrenteMI.setAccelerator( strokeTab );
        moverTrazMI.setAccelerator( strokeShiftTab );
        completarCodigoMI.setAccelerator( strokeCtrlEsp );
                
        desfazerMI.addActionListener( this );
        refazerMI.addActionListener( this );
        copiarMI.addActionListener( this );
        moverMI.addActionListener( this );
        colarMI.addActionListener( this );
        selecionarTudoMI.addActionListener( this );
        
        moverFrenteMI.addActionListener( this );
        moverTrazMI.addActionListener( this );
        completarCodigoMI.addActionListener( this );
        
        ajudaLinkMI.addActionListener( this );
        ajudaPorTermoMI.addActionListener( this );
    }
    
    @Override
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        PrincipalGUITO guiTO = gui.getGUITO();
        
        if ( e.getSource() == novoProjetoMI ) {
            listener.novoProjetoMIAcionado( guiTO );
        } else if ( e.getSource() == novoArquivoMI ) {
            listener.novoArquivoMIAcionado( guiTO );
        } else if ( e.getSource()  == abrirProjetoMI ) {
            listener.abrirProjetoMIAcionado( guiTO );
        } else if ( e.getSource() == salvarMI ) {
            listener.salvarMIAcionado( guiTO );
        } else if ( e.getSource() == sairMI ) {
            listener.sairMIAcionado( guiTO );
        } else if ( e.getSource() == desfazerMI ) {
            listener.desfazerMIAcionado( guiTO );
        } else if ( e.getSource() == refazerMI ) {
            listener.refazerMIAcionado( guiTO );
        } else if ( e.getSource() == copiarMI ) {
            listener.copiarMIAcionado( guiTO );
        } else if ( e.getSource() == moverMI ) {
            listener.moverMIAcionado( guiTO );
        } else if ( e.getSource() == colarMI ) {
            listener.colarMIAcionado( guiTO );
        } else if ( e.getSource() == selecionarTudoMI ) {
            listener.selecionarTudoMIAcionado( guiTO );
        } else if ( e.getSource() == moverFrenteMI ) {
            listener.moverFrenteMIAcionado( guiTO );
        } else if ( e.getSource() == moverTrazMI ) {
            listener.moverTrazMIAcionado( guiTO );
        } else if ( e.getSource() == completarCodigoMI ) {
            listener.completarCodigoMIAcionado( guiTO );
        } else if ( e.getSource() == ajudaLinkMI ) {
            listener.ajudaLinkMIAcionado( guiTO );
        } else if ( e.getSource() == ajudaPorTermoMI ) {
            listener.ajudaPorTermoMIAcionado( guiTO );
        } 
    }
    
    public void setPrincipalJMenuBarListener( PrincipalJMenuBarListener listener ) {
        this.listener = listener;
    }
    
}
