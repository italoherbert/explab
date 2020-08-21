package italo.explab_ide.gui.novoprojeto;

import italo.explab_ide.gui.IDEGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import italo.explab_ide.gui.IDEGUIConfig;

public class NovoProjetoGUI extends JDialog implements ActionListener {
    
    private final JTextField projNomeTF;
    private final JTextField projCaminhoTF;
    private final JButton projCaminhoProcurarBT;
    
    private final JButton criarBT;
    private final JButton cancelarBT;
    
    private final NovoProjetoGUITO to = new NovoProjetoGUITO( this );
    private NovoProjetoGUIListener listener;
    
    public NovoProjetoGUI( IDEGUIConfig config ) {        
        projNomeTF = new JTextField();        
        projCaminhoTF = new JTextField();
        
        projCaminhoTF.setEditable( false ); 
        
        criarBT = new JButton( config.getTextoRotulo(IDEGUI.BT_CRIAR ) );
        cancelarBT = new JButton( config.getTextoRotulo(IDEGUI.BT_CANCELAR ) );
        
        projCaminhoProcurarBT = new JButton( config.getTextoRotulo(IDEGUI.BT_PROCURAR ) );
        
        JPanel tituloPNL = config.criaTituloPNL( IDEGUI.NOVO_PROJETO_TITULO, 500 );
        
        JPanel projNomePNL = new JPanel();
        projNomePNL.setLayout(  new BoxLayout( projNomePNL, BoxLayout.Y_AXIS ) ); 
        projNomePNL.setBorder( new TitledBorder( config.getTextoRotulo( IDEGUI.LB_NOME ) ) );
        projNomePNL.add( projNomeTF );
        
        JPanel projCaminhoPNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        projCaminhoPNL.setBorder(new TitledBorder( config.getTextoRotulo(IDEGUI.LB_CAMINHO ) ) );
        projCaminhoPNL.add( projCaminhoTF );
        projCaminhoPNL.add( projCaminhoProcurarBT );
        
        JPanel camposPNL = new JPanel();
        camposPNL.setLayout( new BorderLayout() );
        camposPNL.add( BorderLayout.CENTER, projNomePNL );
        camposPNL.add( BorderLayout.SOUTH, projCaminhoPNL );
        
        JPanel botoesPNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) ); 
        botoesPNL.add( criarBT );
        botoesPNL.add( cancelarBT );

        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
        conteudoPNL.setLayout( new BorderLayout() );
        conteudoPNL.add( BorderLayout.NORTH, tituloPNL );
        conteudoPNL.add( BorderLayout.CENTER, camposPNL );
        conteudoPNL.add( BorderLayout.SOUTH, botoesPNL );        
        
        super.setTitle(config.getTextoRotulo(IDEGUI.NOVO_PROJETO_TITULO ) ); 
        super.setContentPane( conteudoPNL ); 
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        super.pack();
        super.setLocationRelativeTo( this );         
                
        criarBT.addActionListener( this );
        cancelarBT.addActionListener( this ); 
        projCaminhoProcurarBT.addActionListener( this ); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == criarBT ) {
            listener.criarBTAcionado( to ); 
        } else if ( e.getSource() == projCaminhoProcurarBT ) {
            listener.procurarBTAcionado( to ); 
        } else if ( e.getSource() == cancelarBT ) {
            to.setVisivel( false );
        }
    }
    
    public NovoProjetoGUITO getGUITO() {
        return to;
    }
    
    public void setNovoProjetoGUIListener( NovoProjetoGUIListener listener ) {
        this.listener = listener;
    }

    public JTextField getProjNomeTF() {
        return projNomeTF;
    }

    public JTextField getProjCaminhoTF() {
        return projCaminhoTF;
    }
    
}
