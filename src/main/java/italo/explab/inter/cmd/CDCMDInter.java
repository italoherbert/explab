package italo.explab.inter.cmd;

import italo.explab.InterAplic;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.exp.string.node.StringExp;
import italo.explab.arvore.cmd.node.CD;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.ContadorUtil;
import italo.explab.var.StringVar;

public class CDCMDInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
                               
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.CD );
        if ( cont == 0 )
            return new InterResult();
                                
        CD cd = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoCD( i, no, codigo );

        int j = 0;        
        j += contUtil.contaEsps( codigo, i+j );
               
        j += cont;
        
        int jj = contUtil.contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < i2 )
            return new InterResult();
                
        j += jj;
        
        int caminhoJ = j;
                
        StringExp strexp = null;
        
        InterResult result = (InterResult)manager.getStringExpInter().interpreta( cd, no, aplic, codigo, i+j, i2 );
        if ( result.getJ() > 0 ) {
            strexp = (StringExp)result.getInstrucaoOuExp();
            j += result.getJ();
        } else {                    
            cont = contUtil.contaSequenciaCHs( codigo, i+j, true, ' ', '\r', '\t', '\n', ';' );        
            if ( cont > 0 ) {
                if ( i+j+cont > i2 )
                    cont = i2 - (i+j);

                String caminho = codigo.getCodigo().substring( i+j, i+j+cont );
                
                StrValor strval = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i+caminhoJ, cd, codigo );
                strval.setValor( new StringVar( caminho ) ); 
                
                strexp = strval;
                
                j += cont;
            }
        }
                            
        cd.setCaminhoExp( strexp );        
        
        return new InterResult( cd, j );
    }
    
}
