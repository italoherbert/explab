package italo.explab.inter.leitura;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.leitura.result.LeituraClasseInterResult;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.RecursoManager;

public class LeituraGlobalClasseInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();        
                
        BlocoNo bno = no.blocoNo();
        if ( bno == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.ERRO_BLOCO_NO_ESPERADO );
            return new InterResult( erro );
        }
        
        RecursoManager recursos = bno.getBloco().getRecursos();
                
        LeituraClasseInterResult result = (LeituraClasseInterResult)manager.getLeituraClasseInter().interpreta( no, base, aplic, codigo, to, i, i2 );        
        if ( result.getJ() > 0 )
            recursos.getClasseLista().criaOuAltera( result.getClasse() );                                                               
        
        return result;
    }
    
}
