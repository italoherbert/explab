package italo.explab.inter.instrucao;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.InterManager;
import italo.explab.inter.instrucao.to.ScriptInterVO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class ScriptInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        String conteudo = ((ScriptInterVO)to).getCodigo();
        String arquivoNome = ((ScriptInterVO)to).getArquivoNome();
        
        Codigo codigoCompleto = new Codigo( aplic, conteudo, arquivoNome );                                                   
        Codigo scriptCodigo = codigoCompleto.codigoSemComentarios();
                
        int j = 0;
        j += contUtil.contaEsps( scriptCodigo, j );
                          
        List<Instrucao> lista = new ArrayList();
        
        int len = scriptCodigo.getCodlen();
        while( j < len ) {
            InterResult result = manager.getInstrucaoInter().interpreta( no, base, aplic, scriptCodigo, j, len );                                                
            if ( result.getJ() == 0 )                
                return result;    
            
            if ( result.getInstrucaoOuExp() != null )
                lista.add( result.getInstrucaoOuExp() );                        
                        
            j += result.getJ();
            j += contUtil.contaEspsEPontoEVirgulas( scriptCodigo, j ); 
        }
              
        BlocoNo bno = no.blocoNo();
        if ( bno == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.ERRO_BLOCO_NO_ESPERADO );
            return new InterResult( erro );
        }
        
        if ( bno.getBloco().getInstrucoes() == null ) {
            len = 0;
        } else {
            len = bno.getBloco().getInstrucoes().length;
        }
        
        Instrucao[] instrucoes = new Instrucao[ len + lista.size() ];
        int k = 0;
        
        if ( bno.getBloco().getInstrucoes() != null )
            for( Instrucao inst : bno.getBloco().getInstrucoes() )
                instrucoes[ k++ ] = inst;
        
        for( Instrucao inst : lista )
            instrucoes[ k++ ] = inst;
        
        bno.getBloco().setInstrucoes( instrucoes ); 
        
        return new InterResult( j );
    }
    
}
