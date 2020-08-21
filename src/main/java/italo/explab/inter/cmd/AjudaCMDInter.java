package italo.explab.inter.cmd;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.exp.string.node.StringExp;
import italo.explab.arvore.cmd.node.Ajuda;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import italo.explab.var.StringVar;
import java.util.ArrayList;
import java.util.List;

public class AjudaCMDInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        ContadorUtil contUtil = aplic.getContUtil();

        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.AJUDA );
        if ( cont == 0 )
            return new InterResult();
        
        Ajuda ajuda = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoAjuda( i, no, codigo );                

        InterManager manager = aplic.getInterManager();
        
        int j = 0;        
            
        j += contUtil.contaEsps( codigo, i+j );
        
        j += cont;
        int jj = contUtil.contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < i2 )
            return new InterResult();
                
        j += jj;
                        
        List<StringExp> termos = new ArrayList( 1 );
        boolean fim = false;

        char ch = codigo.getSEGCH( i+j );
        while ( !fim && i+j < i2 ) {
            if ( ch == ';' ) {
                fim = true;
                continue;
            }
        
            InterResult result = (InterResult)manager.getStringExpInter().interpreta( ajuda, no, aplic, codigo, i+j, i2 );
            if ( result.getJ() > 0 ) {
                termos.add( (StringExp)result.getInstrucaoOuExp() );
                j += result.getJ();
            } else {                    
                cont = contUtil.contaSequenciaCHs( codigo, i+j, true, ' ', '\r', '\t', '\n', ';' );        
                if ( cont > 0 ) {
                    if ( i+j+cont > i2 )
                        cont = i2 - (i+j);

                    String termo = codigo.getCodigo().substring( i+j, i+j+cont );
                    
                    StrValor svalor = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i, ajuda, codigo );
                    svalor.setValor( new StringVar( termo ) );
                    termos.add( svalor );
                    
                    j += cont;
                } else {
                    j++;
                }
            }
            ch = codigo.getSEGCH( i+j );
        }
        
        if ( termos.isEmpty() ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.AJUDA_TERMO_ESPERADO );
            return new InterResult( erro ); 
        }
                        
        Exp[] expTermos = new Exp[ termos.size() ];
        expTermos = termos.toArray( expTermos );
        
        ajuda.setTermos( expTermos );
        
        return new InterResult( ajuda, j );        
    }
    
}
