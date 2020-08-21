package italo.explab.execproc;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumericaVar;

public class NumericaExpExecProc {
    
    public NumericaExpResult exec( InterAplic aplic, Codigo codigo, double[] vetorX ) {        
        InterManager manager = aplic.getInterManager();

        Grupo grupoRaiz = aplic.getGrupoRaiz();
        
        if ( vetorX != null ) {
            MatrizVar mat = aplic.getMatrizManager().getMatrizUtil().matriz( vetorX );
            grupoRaiz.getBloco().getRecursos().getVarLista().addVar( "x", mat ); 
        }
        
        InterResult result = manager.getNumExpInter().interpreta( grupoRaiz, grupoRaiz, aplic, codigo, 0, codigo.getCodlen() );                
                
        if ( result.getJ() > 0 ) {                        
            ExecResult er = aplic.getExecutor().exec( result.getInstrucaoOuExp() );
            if ( er.getVar() == null ) {
                aplic.getSessaoManager().getMSGController().enviaErros();
                return new NumericaExpResult( null, false );
            }
            return new NumericaExpResult( (NumericaVar)er.getVar() , true );
        } else {
            if ( result.getErro() != null )
                aplic.getSessaoManager().getMSGController().addErro( result.getErro() );
            
            aplic.getSessaoManager().getMSGController().enviaErros();
            return new NumericaExpResult( null, false );
        }
    }
        
}
