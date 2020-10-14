package italo.explab_ide.ctrl.exec;

import italo.explab.ExpLab;
import italo.explab.func.Func;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.ctrl.exec.cmd.CMD;
import italo.explab_ide.ctrl.exec.cmd.ExecCMD;
import italo.explab_ide.ctrl.exec.cmd.NormalExecCMD;
import italo.explab_ide.ctrl.exec.cmd.ScriptExecCMD;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import italo.explab_ide.logica.arquivo.ArqArvNo;
import italo.explab_ide.logica.arquivo.projeto.ProjetoConfig;
import italo.explab_ide.logica.arquivo.projeto.ProjetoXMLNo;
import java.util.ArrayList;
import java.util.List;
import libs.gui.arv.ArvGUITO;
import libs.gui.arv.ArvNo;

public class ExecCtrl {
    
    private final ExpLabIDEAplic aplic;   
    private final List<ExecGUIThread> execucoes = new ArrayList();
    
    public ExecCtrl( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
    
    public ExecGUIThread getExec1() {
        if ( execucoes.isEmpty() )
            return null;
        return execucoes.get( 0 );
    }
    
    public void executaProjetoSelecionado() {        
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
                   
        String noCaminho = arvGUITO.getCaminhoNoSelecionado();
        ArvNo no = arvGUITO.getNoPorCaminho( noCaminho );        
              
        if ( no == null ) {                                                       
            aplic.getMSGManager().mostraErro( IDEErroMSGs.NENHUM_PROJETO_SELECIONADO ); 
        } else {         
            ProjetoXMLNo proj = ((ArqArvNo)no).getProjeto();                        
            String projNome = proj.getNome();
            String projBasedir = proj.getBasedir();

            ProjetoConfig projCFG = aplic.getProjetoConfigLeitor().le( proj );
            String charset = projCFG.getCharset();
            String execScript = projCFG.getExecScript();
            this.executaScript( projNome, projBasedir, execScript, charset );        
        }
    }
    
    public void executaArquivoSelecionado() {        
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
                     
        CodigoFonteTPPainelGUITO guiTO = aplic.getGUI().getPrincipalGUI().getCodigoFonteGUI().getSelecionadoTPPainelGUITO();
        if ( guiTO != null ) {            
            String sisarqCaminho = aplic.getCodigoFonteCtrl().getSisArqPorTPPainelGUITO( guiTO );
            ArvNo no = aplic.getArquivoManager().getArvNo( arvGUITO.getNoRaiz(), sisarqCaminho, aplic.getConfig().getComparador() );
            
            if ( no != null ) {              
                ProjetoXMLNo proj = ((ArqArvNo)no).getProjeto();                        

                ProjetoConfig projCFG = aplic.getProjetoConfigLeitor().le( proj );
                String charset = projCFG.getCharset();
                
                String projNome = proj.getNome();
            
                this.executaScript( projNome, "", sisarqCaminho, charset );            
            }
        }        
    }
        
    public void executaScript( String projNome, String projBasedir, String execScript, String charset ) {        
        this.fechaTabsExecucoesConcluidas();
        
        ExecCMD execCMD = new ScriptExecCMD( aplic, projNome, projBasedir, execScript );            
        ExecGUIThread thread = new ExecGUIThread( aplic, execCMD, charset );
        thread.start();

        execucoes.add( thread );
    }
    
    public void executaCMD( CMD cmd )  {
        this.fechaTabsExecucoesConcluidas();
        
        NormalExecCMD exec = new NormalExecCMD( cmd );
        ExecGUIThread thread = new ExecGUIThread( aplic, exec );
        thread.start();
    }
    
    private void fechaTabsExecucoesConcluidas() {
        int size = execucoes.size();
        for( int i = 0; i < size; i++ ) {
            ExecGUIThread exec = execucoes.get( i );
            if ( exec.isExecucaoConcluida() ) {                
                aplic.getGUI().getPrincipalGUI().getSaidaGUI().removeTabPainel( 
                        exec.getSaidaTPPainelGUITO().getSaidaTPPainelGUI() ); 
                
                execucoes.remove( i );
                i--;
                size--;
            }
        }
    }
    
    public boolean removeExec( ExecGUIThread exec ) {
        return execucoes.remove( exec );
    }
        
    public List<String> funcoesNativas( ExpLab explab ) {        
        List<String> funcs = new ArrayList();
        for( Func f : explab.getAplic().getGlobalRecursoManager().getFuncLista().getFuncs() )
            funcs.add( f.getNome() );
        return funcs;
    }
    
}
