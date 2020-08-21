package italo.explab.inter.cmd;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.InterConfig;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.exp.string.node.StringExp;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.instrucao.to.ScriptInterVO;
import italo.explab.util.ArquivoUtil;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import italo.explab.var.StringVar;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScriptCMDInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                                
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        ArquivoUtil arquivoUtil = aplic.getArquivoUtil();
        InterConfig config = aplic.getConfig();
        
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.SCRIPT );
        if ( cont == 0 )
            return new InterResult();
                                                        
        int j = contUtil.contaEsps( codigo, i );
        
        j += cont;
        
        int jj = contUtil.contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < i2 )
            return new InterResult();
                
        j += jj;
        
        int caminhoJ = j;
        
        StringExp caminhoExp = null;
        
        InterResult result = manager.getStringExpInter().interpreta( no, base, aplic, codigo, i+j, i2 );
        if ( result.getJ() > 0 ) {
             caminhoExp = (StringExp)result.getInstrucaoOuExp();
             j += result.getJ();
        } else {
            cont = contUtil.contaSequenciaCHs( codigo, i+j, true, ' ', '\r', '\t', '\n', ';' );
            if ( cont > 0 ) {
                String caminho = codigo.getCodigo().substring( i+j, i+j+cont );
                
                StrValor strval = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i+caminhoJ, no, codigo );
                strval.setValor( new StringVar( caminho ) ); 
                
                caminhoExp = strval;
                
                j += cont;
            }
        }
               
        if ( caminhoExp == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ARQ_NOME_ESPERADO );
            return new InterResult( erro ) ;
        }
        
        ExecResult er = aplic.getExecutor().exec( caminhoExp );
        if ( er.getErro() != null )
            return new InterResult( er.getErro() );
        
        String caminho = ((StringVar)er.getVar()).getValor();
        
        try {
            if ( config.getDiretorioCorrente() != null ) {
                if ( !caminho.contains( ":" ) ) {                    
                    String barra = ( config.getDiretorioCorrente().endsWith( "/" ) ? "" : "/" );
                    caminho = config.getDiretorioCorrente() + barra + caminho;
                }
            }
                                    
            String cod = arquivoUtil.ler( caminho );

            int k = caminho.lastIndexOf( '/' );
            if ( k == -1 )
                k = 0;
            else k++;
            String arquivoNome = caminho.substring( k );
            
            ScriptInterVO scriptIVO = new ScriptInterVO( arquivoNome, cod );                                
            InterResult scriptExecResult = manager.getScriptInter().interpreta( no, base, aplic, codigo, scriptIVO, i+caminhoJ, codigo.getCodlen() );
            if ( scriptExecResult.getJ() == 0 )
                return new InterResult( scriptExecResult.getErro() );                        
                                                            
            return new InterResult( j );        
        } catch ( FileNotFoundException ex ) {
            CodigoErro erro =  new CodigoErro( this.getClass(), codigo, i+caminhoJ, ErroMSGs.ARQ_NAO_ENCONTRADO, caminho ); 
            return new InterResult( erro );
        } catch ( IOException ex ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+caminhoJ, ErroMSGs.ARQ_ERRO_LEITURA, caminho ); 
            return new InterResult( erro );
        }                                                                   
    }
                
}
