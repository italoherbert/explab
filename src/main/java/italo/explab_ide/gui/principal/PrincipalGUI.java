package italo.explab_ide.gui.principal;

import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.gui.principal.codigofonte.CodigoFonteGUI;
import italo.explab_ide.gui.principal.projetos.ProjetosGUI;
import italo.explab_ide.gui.principal.saida.SaidaGUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import italo.explab_ide.gui.IDEGUIConfig;
import italo.explab_ide.gui.icones.GUIIcones;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import libs.gui.btn.JIconeButton;

public class PrincipalGUI extends JFrame implements ActionListener, WindowListener {
    
    private final JSplitPane principalSplit;
    private final JSplitPane topSplit;
    
    private final ProjetosGUI projetosGUI;
    private final SaidaGUI saidaGUI;
    private final CodigoFonteGUI codigoFonteEditorGUI;
    
    private final JToolBar ferramentasTBar;
    
    private final JIconeButton novoProjetoBT;
    private final JIconeButton abrirProjetoBT;
    private final JIconeButton salvarTudoBT;
    private final JIconeButton executarBT;
        
    private ImageIcon novoIcone;
    private ImageIcon abrirIcone;
    private ImageIcon salvarIcone;
    private ImageIcon salvoIcone;
    private ImageIcon execIcone;
    
    private PrincipalJMenuBar principalJMenuBar;
        
    private final PrincipalGUITO to = new PrincipalGUITO( this );
    private PrincipalGUIListener listener;
    
    public PrincipalGUI( IDEGUIConfig config ) {
        this.codigoFonteEditorGUI = new CodigoFonteGUI( config );
        this.principalJMenuBar = new PrincipalJMenuBar( config, this );
        
        this.projetosGUI = new ProjetosGUI( config );
        this.saidaGUI = new SaidaGUI();
        
        try {
            novoIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "novo-projeto.png" ) ) );
            abrirIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "abrir-projeto.png" ) ) );
            salvarIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "salvar.png" ) ) );
            salvoIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "salvo.png" ) ) );
            execIcone = new ImageIcon( ImageIO.read( GUIIcones.class.getResourceAsStream( "executar.png" ) ) );
        } catch ( IOException e ) {
            
        }                
        
        novoProjetoBT = new JIconeButton( config.getTextoRotulo( IDEGUI.BT_NOVO ), novoIcone );        
        abrirProjetoBT = new JIconeButton( config.getTextoRotulo( IDEGUI.BT_ABRIR ), abrirIcone );        
        salvarTudoBT = new JIconeButton( config.getTextoRotulo( IDEGUI.BT_SALVAR ), salvarIcone );        
        executarBT = new JIconeButton( config.getTextoRotulo( IDEGUI.BT_EXECUTAR ), execIcone );
                                
        ferramentasTBar = new JToolBar();
        ferramentasTBar.setLayout( new FlowLayout( FlowLayout.LEFT ) ); 
        ferramentasTBar.add( novoProjetoBT );
        ferramentasTBar.add( abrirProjetoBT );
        ferramentasTBar.add( salvarTudoBT );
        ferramentasTBar.add( executarBT );
        
        principalSplit = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        topSplit = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
        
        principalSplit.setLeftComponent( topSplit );
        principalSplit.setRightComponent( new JScrollPane( saidaGUI ) );
        
        topSplit.setLeftComponent( new JScrollPane( projetosGUI ) );        
        topSplit.setRightComponent( codigoFonteEditorGUI );

        JPanel conteudoPNL = new JPanel( new BorderLayout() );
        conteudoPNL.add( BorderLayout.NORTH, ferramentasTBar );
        conteudoPNL.add( BorderLayout.CENTER, principalSplit );
        
        super.setTitle( config.getTextoRotulo(IDEGUI.PRINCIPAL_JFRAME_TITULO ) ); 
        super.setContentPane( conteudoPNL );
        super.setJMenuBar( principalJMenuBar ); 
        super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        super.setSize( 800, 600 );
        super.setLocationRelativeTo( this ); 
        super.setExtendedState( JFrame.MAXIMIZED_BOTH );

        principalSplit.setResizeWeight( 0.75 ); 
        topSplit.setResizeWeight( 0.2 );

        this.salvoIcone();
        
        novoProjetoBT.addActionListener( this );
        abrirProjetoBT.addActionListener( this );
        salvarTudoBT.addActionListener( this );                 
        executarBT.addActionListener( this ); 
        
        super.addWindowListener( this );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == novoProjetoBT ) {            
            listener.novoProjetoBTAcionado( to );
        } else if ( e.getSource() == abrirProjetoBT ) {               
            listener.abrirProjetoBTAcionado( to );
        } else if ( e.getSource() == salvarTudoBT ) {              
            listener.salvarTudoBTAcionado( to ); 
        } else if ( e.getSource() == executarBT ) {                   
            listener.executarBTAcionado( to ); 
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if ( listener != null )
            listener.sairBTAcionado( to ); 
    }
    
    @Override
    public void windowOpened(WindowEvent e) {}
    
    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
        
    public final void salvoIcone() {
        salvarTudoBT.setIcone( salvoIcone ); 
        salvarTudoBT.setEnabled( false ); 
    }
    
    public final void salvarIcone() {
        salvarTudoBT.setIcone( salvarIcone );
        salvarTudoBT.setEnabled( true ); 
    }
    
    public PrincipalGUITO getGUITO() {
        return to;
    }
    
    public void setPrincipalGUIListener( PrincipalGUIListener listener ) {
        this.listener = listener;
    }
    
    public void setPrincipalJMenuBarListener( PrincipalJMenuBarListener listener ) {
        principalJMenuBar.setPrincipalJMenuBarListener( listener ); 
    }

    public ProjetosGUI getProjetosGUI() {
        return projetosGUI;
    }

    public SaidaGUI getSaidaGUI() {
        return saidaGUI;
    }

    public CodigoFonteGUI getCodigoFonteGUI() {
        return codigoFonteEditorGUI;
    }

    public JIconeButton getSalvarTudoBT() {
        return salvarTudoBT;
    }
    
}
