package italo.explab.arvore.cmd.exec;

import italo.explab.ErroMSGs;
import italo.explab.InterLeitura;
import italo.explab.InterSessaoManager;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.cmd.node.Leia;
import italo.explab.arvore.cmd.node.leia.LeiaResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.var.ConstanteException;
import italo.explab.recursos.var.GlobalVarLista;
import italo.explab.var.BooleanVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;

public class LeiaExec implements Exec {

    private boolean esperar = false;
    private boolean esperando = false; 
    
    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        InterLeitura interLeitura = executor.getAplic().getILeitura();
        GlobalVarLista gvarlista = executor.getAplic().getGlobalRecursoManager().getGlobalVarLista();
        InterSessaoManager sessaoManager = executor.getAplic().getSessaoManager();
        
        Leia leia = (Leia)no;            
        
        Codigo codigo = no.getCodigo();
                
        ExecResult er = executor.exec( leia.getVarNomeExp() );
        if ( er.isErroOuEx() )
            return er;
        
        String varnome = ((StringVar)er.getVar()).getValor();
        
        er = executor.exec( leia.getEhTipoStringExp() );
        if ( er.isErroOuEx() )
            return er;
        
        boolean tipoString = ((BooleanVar)er.getVar()).getValor();
                
        if ( gvarlista.verificaSeVarConstante( varnome ) ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_TENTATIVA_DE_ALTERAR_CONSTANTE, varnome );
            return new ExecResult( erro );
        }        
        
        interLeitura.iniciarLeitura();
                    
        esperar = true;
        while( esperar && !sessaoManager.isFim() ) {
            try {
                esperando = true;
                synchronized( this ) {
                    wait();
                }
                esperando = false;
            } catch ( InterruptedException e ) {

            }
        }
                        
        Var valor = null;
        if ( !sessaoManager.isFim() ) { 
            BlocoNo bno = no.blocoNo();
            if ( bno == null ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.ERRO_BLOCO_NO_ESPERADO );
                return new ExecResult( erro );
            }
            
            LeiaResult result = leia.getLeiaListener().execLeitura( bno, bno, executor.getAplic(), codigo, tipoString );
            if ( result.getErro() != null )
                return new ExecResult( result.getErro() );
            
            if ( result.getValorLidoExp() != null ) {
                ExecResult er2 = executor.exec( result.getValorLidoExp() );
                if ( er.isErroOuEx() )            
                    return er2;
                
                valor = er2.getVar();
                if ( valor != null ) {
                    try {                                                                     
                        bno.getBloco().getRecursos().getVarLista().criaOuAltera( varnome, valor );                            
                    } catch ( ConstanteException ex ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, result.getValorLidoExp().getI(), ErroMSGs.VAR_TENTATIVA_DE_ALTERAR_CONSTANTE, varnome );
                        return new ExecResult( erro );
                    }
                }                      
            }
        }            

        interLeitura.finalizarLeitura();
               
        if ( sessaoManager.isFim() )
            return new ExecResult();          
                                
        return new ExecResult( valor ); 
    }
    
    public synchronized void leituraConcluida() {
        esperar = false;
        if ( esperando )
            notifyAll();        
    }
    
}
