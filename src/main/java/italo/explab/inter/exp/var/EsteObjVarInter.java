package italo.explab.inter.exp.var;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;import italo.explab.util.ContadorUtil;

public class EsteObjVarInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContUtil();
                                
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.ESTE );
        int cont2 = contUtil.contaVarNomeTam( codigo, i );
        if ( cont == cont2 ) {
            if ( cont > 0 && codigo.getSEGCH( i+cont ) != '.' ) {
                VariavelExp exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoVariavelExp( i, no, codigo );
                exp.setNome( null );
                exp.setAcesso( ExpRecurso.ESTE );
             
                return new InterResult( exp, cont );                                   
            }
        }   
        
        return new InterResult();           
    }
    
}
