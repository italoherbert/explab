package italo.explab.arvore.cmd.exec;

import italo.explab.ErroMSGs;
import italo.explab.InfoMSGs;
import italo.explab.InterConfig;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.msg.MSGManager;
import java.io.File;

public class LSExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {        
        MSGManager msgManager = executor.getAplic().getMSGManager();
        InterConfig config = executor.getAplic().getConfig();
                
        Codigo codigo = no.getCodigo();
        
        File file = new File( config.getDiretorioCorrente() );
        if ( file.exists() ) {
            msgManager.enviaInfo( InfoMSGs.LISTA_DE_ARQUIVOS ); 
            msgManager.envia( "=\n" ); 
            String[] arqs = file.list();
            for( String arqnome : arqs )
                msgManager.envia( "\t"+arqnome+"\n" );
            
            return new ExecResult();                        
        } else {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.CAMINHO_INVALIDO, config.getDiretorioCorrente() );
            return new ExecResult( erro );
        }            
    }
    
}
