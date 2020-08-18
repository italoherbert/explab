package italo.explab.arvore.cmd.exec;

import italo.explab.ErroMSGs;
import italo.explab.InterConfig;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.string.node.StringExp;
import italo.explab.arvore.cmd.node.CD;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.msg.MSGManager;
import italo.explab.var.StringVar;
import java.io.File;

public class CDExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        CD cd = (CD)no;
        
        Codigo codigo = no.getCodigo();
        
        StringExp strexp = cd.getCaminhoExp();
                        
        ExecResult er = executor.exec( strexp );
        if ( er.isErroOuEx() )
            return er;
        
        MSGManager msgManager = executor.getAplic().getMSGManager();
        InterConfig config = executor.getAplic().getConfig();
        
        String caminho = ((StringVar)er.getVar()).getValor();
        if ( caminho == null ) {
            msgManager.envia( config.getDiretorioCorrente() ); 
        } else {        
            caminho = caminho.replaceAll( "\\\\", "/" );
            String[] partes = caminho.split( "/" );      

            String dirCorr;
            if ( new File( caminho ).isAbsolute() ) {
                dirCorr = caminho;
            } else {
                dirCorr = config.getDiretorioCorrente();
                for( String parte : partes ) {
                    if ( parte.equals( ".." ) ) {
                        int k = dirCorr.lastIndexOf( "/" );
                        if ( k != -1 )
                            if ( k == dirCorr.length()-1 )
                                k = dirCorr.lastIndexOf( "/", k-1 );                        

                        if ( k != -1 )
                            dirCorr = dirCorr.substring( 0, k );                        
                    } else {
                        if ( dirCorr.endsWith( "/" ) ) {
                            dirCorr += parte;
                        } else {
                            dirCorr += "/" + parte;
                        }
                    } 
                }
            }

            if ( new File( dirCorr ).exists() ) {
                config.setDiretorioCorrente( dirCorr ); 
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, strexp.getI(), ErroMSGs.CAMINHO_INVALIDO, dirCorr );                
                return new ExecResult( erro );
            }
        }
         
        return new ExecResult();
    }
    
}
