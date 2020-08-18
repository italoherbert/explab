package italo.explab.arvore.cmd;

import italo.explab.arvore.ExecManager;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.cmd.node.Ajuda;
import italo.explab.arvore.cmd.node.CD;
import italo.explab.arvore.cmd.node.Continue;
import italo.explab.arvore.cmd.node.Exiba;
import italo.explab.arvore.cmd.node.LS;
import italo.explab.arvore.cmd.node.Pare;
import italo.explab.arvore.cmd.node.Retorne;
import italo.explab.arvore.cmd.node.Lance;
import italo.explab.arvore.cmd.node.Leia;
import italo.explab.arvore.cmd.node.LimpeTela;
import italo.explab.arvore.cmd.node.Liste;
import italo.explab.arvore.cmd.node.Sair;
import italo.explab.arvore.cmd.node.Use;
import italo.explab.codigo.Codigo;

public class CMDFactory {

    private final CMDExecManager cmdExecManager;
    
    public CMDFactory( ExecManager manager ) {
        cmdExecManager = manager.getCMDExecManager();
    }
    
    public Continue novoContinue( int i, ExecNo parente, Codigo codigo ) {
        Continue c = new Continue();
        c.setI( i );
        c.setParente( parente );
        c.setCodigo( codigo ); 
        c.setExec( cmdExecManager.getContinueExec() ); 
        return c;
    }
    
    public Pare novoPare( int i, ExecNo parente, Codigo codigo ) {
        Pare p = new Pare();
        p.setI( i );
        p.setParente( parente );
        p.setCodigo( codigo ); 
        p.setExec( cmdExecManager.getPareExec() ); 
        return p;
    }
    
    public Retorne novoRetorne( int i, ExecNo parente, Codigo codigo ) {
        Retorne r = new Retorne();
        r.setI( i );
        r.setParente( parente );
        r.setCodigo( codigo ); 
        r.setExec( cmdExecManager.getRetorneExec() );
        return r;
    }
    
    public Lance novoLance( int i, ExecNo parente, Codigo codigo ) {
        Lance lance = new Lance();
        lance.setI( i );
        lance.setParente( parente );
        lance.setCodigo( codigo ); 
        lance.setExec( cmdExecManager.getLanceExec() );
        return lance;
    }
    
    public Exiba novoExiba( int i, ExecNo parente, Codigo codigo ) {
        Exiba exiba = new Exiba();
        exiba.setI( i );
        exiba.setParente( parente );
        exiba.setCodigo( codigo ); 
        exiba.setExec( cmdExecManager.getExibaExec() ); 
        return exiba;
    }
    
    public Ajuda novoAjuda( int i, ExecNo parente, Codigo codigo ) {
        Ajuda ajuda = new Ajuda();
        ajuda.setI( i );
        ajuda.setParente( parente );
        ajuda.setCodigo( codigo ); 
        ajuda.setExec( cmdExecManager.getAjudaExec() ); 
        return ajuda;
    }
    
    public CD novoCD( int i, ExecNo parente, Codigo codigo ) {
        CD cd = new CD();
        cd.setI( i );
        cd.setParente( parente );
        cd.setCodigo( codigo ); 
        cd.setExec( cmdExecManager.getCDExec() ); 
        return cd;
    }
    
    public LS novoLS( int i, ExecNo parente, Codigo codigo ) {
        LS ls = new LS();
        ls.setI( i );
        ls.setParente( parente );
        ls.setCodigo( codigo ); 
        ls.setExec( cmdExecManager.getLSExec() ); 
        return ls;
    }
    
    public LimpeTela novoLimpeTela( int i, ExecNo parente, Codigo codigo ) {
        LimpeTela lt = new LimpeTela();
        lt.setI( i );
        lt.setParente( parente );
        lt.setCodigo( codigo ); 
        lt.setExec( cmdExecManager.getLimpeTelaExec() ); 
        return lt;
    }
    
    public Liste novoListe( int i, ExecNo parente, Codigo codigo ) {
        Liste liste = new Liste();
        liste.setI( i );
        liste.setParente( parente );
        liste.setCodigo( codigo ); 
        liste.setExec( cmdExecManager.getListeExec() ); 
        return liste;
    }
    
    public Leia novoLeia( int i, ExecNo parente, Codigo codigo ) {
        Leia leia = new Leia();
        leia.setI( i );
        leia.setParente( parente );
        leia.setCodigo( codigo ); 
        leia.setExec( cmdExecManager.getLeiaExec() ); 
        return leia;
    }
        
    public Use novoUse( int i, ExecNo parente, Codigo codigo ) {
        Use use = new Use();
        use.setI( i );
        use.setParente( parente );
        use.setCodigo( codigo ); 
        use.setExec( cmdExecManager.getUseExec() ); 
        return use;
    }
    
    public Sair novoSair( int i, ExecNo parente, Codigo codigo ) {
        Sair sair = new Sair();
        sair.setI( i );
        sair.setParente( parente );
        sair.setCodigo( codigo ); 
        sair.setExec( cmdExecManager.getSairExec() ); 
        return sair;
    }
    
}
