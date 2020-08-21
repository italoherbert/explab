package italo.explab.inter.exp.var;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;

public class VarInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContUtil();
                
        int j = contUtil.contaEsps( codigo, i );
        
        int acesso = ExpRecurso.NORMAL;        
        
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.ESTE );
        int cont2 = contUtil.contaVarNomeTam( codigo, i+j );
        if ( cont == cont2 && cont > 0 ) {
            acesso = ExpRecurso.ESTE;        
        } else {
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.SUPER );
            if ( cont == cont2 && cont > 0 )
                acesso = ExpRecurso.SUPER;                
        } 
   
        if ( acesso == ExpRecurso.ESTE || acesso == ExpRecurso.SUPER ) {
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            if ( codigo.getSEGCH( i+j ) == '.' ) {
                j++;
            } else {
                if ( acesso == ExpRecurso.ESTE ) {                    
                    VariavelExp exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoVariavelExp( i+j, no, codigo );
                    exp.setNome( null );
                    exp.setAcesso( acesso );
             
                    return new InterResult( exp, j );
                } else {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PONTO_ESPERADO );
                    return new InterResult( erro );
                }
            }
        }
                        
        cont = contUtil.contaVarNomeTam( codigo, i+j );        
        if ( cont == 0 )
            return new InterResult();
        
        String nome = codigo.getCodigo().substring( i+j, i+j+cont );        
        j += cont;
                
        VariavelExp exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoVariavelExp( i, no, codigo );
        exp.setNome( nome );
        exp.setAcesso( acesso ); 
                                 
        return new InterResult( exp, j );
    }
    
}
