package italo.explab.inter.instrucao;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.InterManager;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class BlocoInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();                        
       
        Grupo grupo = aplic.getExecutor().getExecManager().getExecNoFactory().novoGrupo( i, no, codigo );

        List<Instrucao> lista = new ArrayList();
        
        int j = 0;
        j += contUtil.contaEsps( codigo, i+j );
        int k = j;
        while ( i+k < i2 ) {
            j = k;
            InterResult result = manager.getInstrucaoInter().interpreta( grupo, base, aplic, codigo, i+j, i2 );                                                
            if ( result.getJ() == 0 ) {
                if ( result.getErro() != null )
                    return result;                         
                
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.INSTRUCAO_DESCONHECIDA );
                return new InterResult( erro ); 
            }
            
            if ( result.getInstrucaoOuExp() != null )
                lista.add( result.getInstrucaoOuExp() );                         
            
            j += result.getJ();

            k = j;
            k += contUtil.contaEspsEPontoEVirgulas( codigo, i+k );                                        
        }                                         
                
        int len = 0;
        if ( grupo.getBloco().getInstrucoes() != null )            
            len = grupo.getBloco().getInstrucoes().length;        
        
        Instrucao[] instrucoes = new Instrucao[ len + lista.size() ];
        
        k = 0;        
        if ( grupo.getBloco().getInstrucoes() != null )
            for( Instrucao inst : grupo.getBloco().getInstrucoes() )
                instrucoes[ k++ ] = inst;
        
        for( Instrucao inst : lista )
            instrucoes[ k++ ] = inst;
        
        grupo.getBloco().setInstrucoes( instrucoes ); 
        
        return new InterResult( grupo, j );
    }
            
}
