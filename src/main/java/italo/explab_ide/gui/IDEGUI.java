package italo.explab_ide.gui;

import italo.explab_ide.gui.autocomplete.AutoCompleteGUI;
import italo.explab_ide.gui.deletarprojeto.DeletarProjetoPNL;
import italo.explab_ide.gui.novoprojeto.NovoProjetoGUI;
import italo.explab_ide.gui.principal.PrincipalGUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IDEGUI implements IDEGUIConfig {
        
    public final static String BT_NOVO = "bt.novo";
    public final static String BT_CRIAR = "bt.criar";
    public final static String BT_ABRIR = "bt.abrir";
    public final static String BT_SALVAR = "bt.salvar";
    public final static String BT_SALVAR_TODOS = "bt.salvar.todos";

    public final static String BT_OK = "bt.ok";
    public final static String BT_CANCELAR = "bt.cancelar";
    
    public final static String BT_PROCURAR = "bt.procurar";
    public final static String BT_EXECUTAR = "bt.executar";
    public final static String BT_ATUALIZAR = "bt.atualizar";
    public final static String BT_RENOMEAR = "bt.renomear";
    public final static String BT_DELETAR = "bt.deletar";
    
    public final static String BT_NOVO_PROJETO = "bt.novo.projeto";
    public final static String BT_NOVO_ARQUIVO = "bt.novo.arquivo";        
    public final static String BT_ABRIR_PROJETO = "bt.abrir.projeto";
    public final static String BT_SAIR = "bt.sair";
    
    public final static String BT_DESFAZER = "bt.desfazer";
    public final static String BT_REFAZER = "bt.refazer";
    public final static String BT_COPIAR = "bt.copiar";
    public final static String BT_COLAR = "bt.colar";
    public final static String BT_MOVER = "bt.mover";
    public final static String BT_TAB_FRENTE = "bt.tab.frente";
    public final static String BT_TAB_TRAZ = "bt.tab.traz";
    public final static String BT_SELECIONAR_TUDO = "bt.selecionar.tudo";
    public final static String BT_COMPLETAR_CODIGO = "bt.completar.codigo";
    
    public final static String BT_AJUDA_LINK = "bt.ajuda.link";
    public final static String BT_AJUDA_POR_TERMO = "bt.ajuda.por.termo";
    public final static String BT_AJUDA_SOBRE = "bt.ajuda.sobre";
        
    public final static String BT_NOVO_ARQ_EXPLAB = "bt.novo.arq.explab";
    public final static String BT_NOVO_ARQ_VASIO = "bt.novo.arq.vasio";
    public final static String BT_NOVA_PASTA = "bt.nova.pasta";
    
    public final static String BT_FECHAR_TODOS = "bt.fechar.todos";
    public final static String BT_FECHAR_OUTROS = "bt.fechar.outros";
    public final static String BT_FECHAR_ESTE = "bt.fechar.este";

    public final static String MENU_ARQUIVO = "menu.arquivo";
    public final static String MENU_EDITAR = "menu.editar";
    public final static String MENU_CODIGO_FONTE = "menu.codigo.fonte";
    public final static String MENU_AJUDA = "menu.ajuda";
    
    public final static String LB_CAMINHO = "lb.caminho";
    public final static String LB_NOME = "lb.nome";
    public final static String LB_DADOS = "lb.dados";
    
    public final static String NOVO_PROJETO_TITULO = "novo.projeto.titulo";
    public final static String ABRIR_PROJETO_TITULO = "abrir.projeto.titulo";
    public final static String DELETAR_PROJETO_TITULO = "deletar.projeto.titulo";
    
    public final static String PRINCIPAL_JFRAME_TITULO = "principal.jframe.titulo";
    
    private final Map<String, String> mensagensMap = new HashMap();
    private final Font tituloFont = new Font( Font.SANS_SERIF, Font.BOLD, 24 ); 
        
    private final PrincipalGUI principalGUI;
    private final NovoProjetoGUI novoProjetoGUI;               
    private final AutoCompleteGUI autoCompleteGUI;
    
    public IDEGUI( String guiPropArq ) throws IOException {
        Properties p = new Properties();
        p.load( new FileInputStream( guiPropArq ) );
        
        Set<Object> keys = p.keySet();
        for( Object key : keys ) {
            String chave = String.valueOf( key );
            mensagensMap.put( chave, p.getProperty( chave ) );                        
        }
        
        novoProjetoGUI = new NovoProjetoGUI( this );
        autoCompleteGUI = new AutoCompleteGUI( this );
        principalGUI = new PrincipalGUI( this );
    }
         
    @Override
    public String getTextoRotulo( String chave ) {
        return mensagensMap.get( chave ); 
    }

    public JPanel criaTituloPNL( String msgchave ) {
        return this.criaTituloPNL( msgchave, -1 ); 
    }
    
    @Override
    public JPanel criaTituloPNL( String msgchave, int largura ) {
        String msg = mensagensMap.get( msgchave );
        
        JLabel tituloLB = new JLabel( msg );         
        tituloLB.setFont( tituloFont ); 
                
        JPanel tituloPNL = new JPanel( new FlowLayout() );
        if ( largura != -1 ) {
            FontMetrics fm = tituloPNL.getFontMetrics( tituloFont );;
            tituloLB.setPreferredSize( new Dimension( largura, (int)fm.getHeight() ) );
        }
        
        tituloPNL.add( tituloLB );
        
        return tituloPNL;
    }
        
    public DeletarProjetoPNL criaDeletarProjetoPNL() {
        return new DeletarProjetoPNL( this );
    }

    public PrincipalGUI getPrincipalGUI() {
        return principalGUI;
    }
    
    public NovoProjetoGUI getNovoProjetoGUI() {
        return novoProjetoGUI;
    }

    public AutoCompleteGUI getAutoCompleteGUI() {
        return autoCompleteGUI;
    }
    
}


