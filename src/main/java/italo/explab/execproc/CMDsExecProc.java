package italo.explab.execproc;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.msg.Erro;

public class CMDsExecProc {
        
    public void exec( InterAplic aplic, Codigo codigo ) {
        InterManager manager = aplic.getInterManager();
                       
        Grupo grupoRaiz = aplic.getGrupoRaiz();
        
        Erro erro = null;
        
        InterResult result = manager.getBlocoInter().interpreta( grupoRaiz, grupoRaiz, aplic, codigo, 0, codigo.getCodlen() );                        
        if ( result.getErro() != null ) {
            erro = result.getErro();
        } else {              
            Grupo grupo = (Grupo)result.getInstrucaoOuExp();
                        
            ExecResult er = aplic.getExecutor().exec( grupo );        
            if ( er.getErro() != null )
                erro = er.getErro();        
            
            grupoRaiz.getBloco().getRecursos().addRecursos( grupo.getBloco().getRecursos() );
        }
        
        if ( erro != null ) {
            aplic.getSessaoManager().getMSGController().addErro( erro );            
            aplic.getSessaoManager().getMSGController().enviaErros();        
        }
    }
    
}
