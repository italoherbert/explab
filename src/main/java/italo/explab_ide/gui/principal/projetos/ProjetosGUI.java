package italo.explab_ide.gui.principal.projetos;

import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.gui.IDEGUIConfig;
import italo.explab_ide.gui.principal.projetos.popupmenu.ProjetosPopupMenu;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import libs.gui.arv.ArvGUITO;
import libs.gui.arv.ArvNo;
import libs.gui.arv.JTreeSource;

public class ProjetosGUI extends JPanel implements MouseListener, KeyListener, JTreeSource {
    
    private final ArvGUITO arqArvGUITO = new ArvGUITO( this );   
    private final ELArvCellRenderer arvCellRenderer = new ELArvCellRenderer();
    private final JTree raizJTree = new JTree();
    private final ArvNo raizArvNo = new ArvNo();
    
    private final ProjetosPopupMenu projetosPopupMenu;
    
    private DataFlavor treePathsDataFlavor;                

    private ProjetosGUIListener listener;
    
    public ProjetosGUI( IDEGUIConfig cfg ) {        
        this.projetosPopupMenu = new ProjetosPopupMenu( cfg );
        
        try {
            treePathsDataFlavor = new DataFlavor(
                    DataFlavor.javaJVMLocalObjectMimeType + ";class=\""+TreePath[].class.getName()+"\"" );                    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProjetosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        super.setLayout( new GridLayout() ); 
        super.add( raizJTree );
        
        raizArvNo.setSisArqCaminho( "" );
        raizArvNo.setNome( "raiz" );
        raizArvNo.setEhPasta( true );
        raizArvNo.setEhVazio( false ); 
        
        raizJTree.setCellRenderer( arvCellRenderer ); 
        raizJTree.setDragEnabled( true );
        raizJTree.setDropMode( DropMode.ON_OR_INSERT ); 
        raizJTree.setTransferHandler( new ArquivosTransferHandler()  );        
        raizJTree.setScrollsOnExpand( true ); 
        raizJTree.getSelectionModel().setSelectionMode( TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION ); 
        raizJTree.addMouseListener( this );        
                        
        raizJTree.addKeyListener( this ); 
    }
    
    public void addProjeto( String nome, ArvNo no ) {        
        raizArvNo.addNoFilho( no );         
    }
    
    
    public void carrega() { 
        String caminho = arqArvGUITO.getCaminhoNoSelecionado();
        
        arqArvGUITO.carrega( raizArvNo );
        raizJTree.setRootVisible( false );         
        
        if ( caminho != null )
            arqArvGUITO.rolarParaEOuExpande( caminho, true );
    }
        
    @Override
    public void keyPressed(KeyEvent e) {
        if ( listener == null )
            return;
        
        if ( e.getKeyCode() == KeyEvent.VK_DELETE ) {
        String caminho = arqArvGUITO.getCaminhoNoSelecionado();
            if ( caminho != null )
                listener.deleteAcionado( arqArvGUITO ); 
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
    
    @Override
    public void mouseClicked(MouseEvent e) {                    
        int row = raizJTree.getRowForLocation( e.getX(), e.getY() );
        if ( row != -1 ) {
            TreePath path = raizJTree.getPathForRow( row );
            if ( e.getClickCount() == 2 ) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
                ArvNo no = arqArvGUITO.getNoPorTreeNo( node ); 

                if ( listener != null )
                    listener.duploClique( no, e );                
            }
        }        
    } 
                
    @Override
    public void mousePressed(MouseEvent e) {                 
        if ( e.getButton() == MouseEvent.BUTTON3 ) {
            projetosPopupMenu.show( this, e.getX(), e.getY() );
            
            int row = raizJTree.getRowForLocation( e.getX(), e.getY() );
            if ( row != -1 )
                raizJTree.setSelectionRow( row );            
        }
    }       

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    
    public void setProjetosGUIListener( ProjetosGUIListener listener ) {
        this.listener = listener;
    }
        
    public ArvGUITO getArvGUITO() {
        return arqArvGUITO;
    }
        
    @Override
    public JTree getJTree() {
        return raizJTree;
    }          

    public ProjetosPopupMenu getProjetosPopupMenu() {
        return projetosPopupMenu;
    }

    public ELArvCellRenderer getArvCellRenderer() {
        return arvCellRenderer;
    }
    
    class ArquivosTransferHandler extends TransferHandler {    

        @Override
        public boolean canImport(TransferSupport support) {
            if ( !support.isDataFlavorSupported( treePathsDataFlavor ) || !support.isDrop() )
                return false;
                                    
            JTree.DropLocation location = (JTree.DropLocation)support.getDropLocation();
            return location.getPath() != null && support.getComponent().getMousePosition() != null;            
        }

        @Override
        public boolean importData(TransferSupport support) {
            if ( listener == null || !this.canImport( support ) ) 
                return false;
                            
            JTree.DropLocation location = (JTree.DropLocation)support.getDropLocation();
            TreePath destPath = location.getPath();

            try {
                Transferable tr = support.getTransferable();
                DataFlavor[] flavors = tr.getTransferDataFlavors();
                for( int i = 0; i < flavors.length; i++ ) {
                    if ( tr.isDataFlavorSupported( flavors[i] ) ) {
                        TreePath[] paths = (TreePath[])tr.getTransferData( flavors[i] );                                                
                        listener.moveAcionado( arqArvGUITO, paths, destPath );                        
                        return true;
                    }
                }
            } catch ( UnsupportedFlavorException | IOException ex ) {
                Logger.getLogger(ProjetosGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }
                     
        @Override
        protected Transferable createTransferable( JComponent c ) {
            JTree tree = (JTree)c;
            TreePath[] paths = tree.getSelectionPaths();
            return new NodesTransferiveis( c, paths );
        }
        
    }
    
    class NodesTransferiveis implements Transferable {
    
        private final Component comp;
        private final TreePath[] nodesSelecionados;

        private final DataFlavor[] flavors = { treePathsDataFlavor };
        
        public NodesTransferiveis( Component comp, TreePath[] nodesSelecionados ) {
            this.comp = comp;
            this.nodesSelecionados = nodesSelecionados;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return Arrays.asList( flavors ).contains( flavor );
        }

        @Override
        public Object getTransferData( DataFlavor flavor ) throws UnsupportedFlavorException, IOException {
            if ( this.isDataFlavorSupported( flavor ) ) {
                return nodesSelecionados;
            } else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public Component getComponent() {
            return comp;
        }

        public TreePath[] getNodesSelecCaminhos() {
            return nodesSelecionados;
        }        

    }
    
}


