package italo.explab_ide.ctrl.exec;

import italo.explab.ExpLab;
import italo.explab.func.Func;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.ctrl.exec.cmd.CMD;
import italo.explab_ide.ctrl.exec.cmd.ExecCMD;
import italo.explab_ide.ctrl.exec.cmd.NormalExecCMD;
import italo.explab_ide.ctrl.exec.cmd.ScriptExecCMD;
import italo.explab_ide.logica.arquivo.ArqArvNo;
import italo.explab_ide.logica.arquivo.projeto.Projeto;
import italo.explab_ide.logica.arquivo.projeto.ProjetoConfig;
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
    
    public void executaArquivo() {
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        String noCaminho = arvGUITO.getCaminhoNoSelecionado();
        ArvNo arvNo = arvGUITO.getNoPorCaminho( noCaminho );
        
        ArqArvNo arqNo = (ArqArvNo)arvNo;
        if ( arqNo.isPastaDeProjeto() ) {
            this.executaProjeto();
        } else {        
            this.executaScript( arvNo.getSisArqCaminho() );
        }
    }
    
    public void executaProjeto() {
         this.executaScript( null );
    }
    
    public void executaScript( String execScript ) {        
        this.fechaTabsExecucoesConcluidas();
        
        String caminho = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO().getCaminhoNoSelecionado();        
        if ( caminho == null ) {                             
            aplic.getMSGManager().mostraErro( IDEErroMSGs.NENHUM_PROJETO_SELECIONADO ); 
        } else {               
            ArvNo no = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO().getNoPorCaminho( caminho );
                                 
            Projeto proj = ((ArqArvNo)no).getProjeto();

            ProjetoConfig projCFG = aplic.getProjetoConfigLeitor().le( proj );
            String charset = projCFG.getCharset();
            
            ExecCMD execCMD = new ScriptExecCMD( aplic, proj, projCFG, execScript );            
            ExecGUIThread thread = new ExecGUIThread( aplic, execCMD, charset );
            thread.start();
            
            execucoes.add( thread );
        }                            
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
