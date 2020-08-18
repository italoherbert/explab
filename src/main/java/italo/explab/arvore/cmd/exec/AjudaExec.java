package italo.explab.arvore.cmd.exec;

import italo.explab.ErroMSGs;
import italo.explab.InfoMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.cmd.node.Ajuda;
import italo.explab.codigo.Codigo;
import italo.explab.config.ajuda.AjudaURLParams;
import italo.explab.config.ajuda.AjudaXMLManager;
import italo.explab.msg.CodigoErro;
import italo.explab.msg.Erro;
import italo.explab.msg.MSGManager;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AjudaExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Ajuda ajuda = (Ajuda)no;
        Exp[] termoExps = ajuda.getTermos();
        
        Codigo codigo = no.getCodigo();
        
        String[] termos = new String[ termoExps.length ];
        for( int i = 0; i < termos.length; i++ ) {            
            ExecResult er = executor.exec( termoExps[i] );
            if ( er.isErroOuEx() )
                return er;
                       
            if ( er.getVar() == null ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, termoExps[i].getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
                return new ExecResult( erro );
            }
            
            if ( er.getVar().getTipo() != Var.STRING ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, termoExps[i].getI(), ErroMSGs.VALOR_STRING_ESPERADO );
                return new ExecResult( erro );
            }
            
            termos[ i ] = ((StringVar)er.getVar()).getValor();
        }
        
        AjudaXMLManager ajudaXMLManager = executor.getAplic().getAjudaXMLManager();
        MSGManager msgManager = executor.getAplic().getMSGManager();
        
        AjudaURLParams ajudaURLParams = ajudaXMLManager.buscaAjuda( termos );        
        if ( ajudaURLParams.getPagina() == null ) {
            Erro erro = new Erro( this.getClass(), ErroMSGs.AJUDA_TERMO_NAO_ENCONTRADO );
            return new ExecResult( erro );
        }
        
        String pagina = ajudaURLParams.getPagina();
        String ancora = ajudaURLParams.getAncoraID();
        
        String docURL = ajudaXMLManager.getDocURL();
        String uri = docURL + (docURL.endsWith( "/" ) ? "" : "/" ) + "index.html?pag="+pagina+"&aid="+ancora;

        msgManager.enviaInfo( InfoMSGs.CONFIG_XML_PAGINA_CARREGANDO );
        msgManager.envia( "\n" );
        
        try {                            
            if ( Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported( Desktop.Action.BROWSE ) ) {                
                Desktop.getDesktop().browse( new URI( uri ) );
                return new ExecResult();
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.AJUDA_FALHA_NAVEGADOR_INI );
                return new ExecResult( erro );
            }
        }  catch ( URISyntaxException | IOException ex ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.AJUDA_FALHA_CARREGAMENTO_PAGINA, pagina );
            return new ExecResult( erro );
        }
    }
    
}
