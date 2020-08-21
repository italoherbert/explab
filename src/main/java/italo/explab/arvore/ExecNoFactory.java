package italo.explab.arvore;

import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.arvore.bloco.Bloco;
import italo.explab.arvore.busca.construtor.ConstrutorBusca;
import italo.explab.arvore.busca.construtor.EsteConstrutorBusca;
import italo.explab.arvore.busca.construtor.SuperConstrutorBusca;
import italo.explab.arvore.classe.ClasseConstrutor;
import italo.explab.arvore.classe.EsteOuSuperConstrutor;
import italo.explab.arvore.exp.ExpFactory;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.exp.incdec.IncDec;
import italo.explab.arvore.cmd.CMDFactory;
import italo.explab.arvore.estruturas.EstruturaFactory;
import italo.explab.codigo.Codigo;
import italo.explab.recursos.GlobalRecursoManager;
import italo.explab.recursos.RecursoManager;

public class ExecNoFactory {
    
    private final ExpFactory expFactory;
    private final EstruturaFactory estruturaFactory;
    private final CMDFactory cmdFactory;
    private final ExecManager execManager;
    
    private final ConstrutorBusca esteConstrutorBusca = new EsteConstrutorBusca();
    private final ConstrutorBusca superConstrutorBusca = new SuperConstrutorBusca();
    
    public ExecNoFactory( ExecManager manager ) {
        this.execManager = manager;
        this.expFactory = new ExpFactory( manager );
        this.estruturaFactory = new EstruturaFactory( manager );
        this.cmdFactory = new CMDFactory( manager );
    }    
            
    public void setGrupoRaizCodigo( Grupo grupo, Codigo codigo ) {
        grupo.setCodigo( codigo );
        grupo.getBloco().setCodigo( codigo );        
    }

    public Grupo novoGrupoRaiz( int i, Codigo codigo ) {
        Grupo grupoRaiz = new Grupo();
        grupoRaiz.setI( i );
        grupoRaiz.setCodigo( codigo );
        grupoRaiz.setExec( execManager.getGrupoExec() ); 
        grupoRaiz.setBloco( this.novoBloco( i, grupoRaiz, codigo ) );        
        grupoRaiz.getBloco().setRecursos( new GlobalRecursoManager() );         
        return grupoRaiz;
    }
    
    public Grupo novoGrupo( int i, ExecNo parente, Codigo codigo ) {
        Grupo grupo = new Grupo();
        grupo.setI( i );
        grupo.setParente( parente );
        grupo.setCodigo( codigo );
        grupo.setExec( execManager.getGrupoExec() ); 
        grupo.setBloco( this.novoBloco( i, grupo, codigo ) );
        return grupo;
    }

    public Bloco novoBloco( int i, ExecNo parente, Codigo codigo ) {
        Bloco bloco = new Bloco();        
        bloco.setI( i );
        bloco.setParente( parente );
        bloco.setCodigo( codigo );
        bloco.setExec( execManager.getBlocoExec() ); 
        bloco.setRecursos( new RecursoManager() ); 
        return bloco;
    }
    
    public ClasseConstrutor novoConstrutor( int i, ExecNo parente, Codigo codigo ) {
        ClasseConstrutor classeConstrutor = new ClasseConstrutor();        
        classeConstrutor.setI( i );
        classeConstrutor.setParente( parente );
        classeConstrutor.setCodigo( codigo );
        classeConstrutor.setExec( execManager.getConstrutorExec() );
        classeConstrutor.setConstrutorBusca( esteConstrutorBusca );
        classeConstrutor.setBloco( this.novoBloco( i, classeConstrutor, codigo ) );  
        return classeConstrutor;
    }
    
    public EsteOuSuperConstrutor novoEsteConstrutor( int i, ExecNo parente, Codigo codigo ) {
        EsteOuSuperConstrutor esteConstrutor = new EsteOuSuperConstrutor();        
        esteConstrutor.setI( i );
        esteConstrutor.setParente( parente );
        esteConstrutor.setCodigo( codigo );
        esteConstrutor.setExec( execManager.getEsteOuSuperConstrutorExec() );
        esteConstrutor.setConstrutorBusca( esteConstrutorBusca );
        return esteConstrutor;
    }
    
    public EsteOuSuperConstrutor novoSuperConstrutor( int i, ExecNo parente, Codigo codigo ) {
        EsteOuSuperConstrutor superConstrutor = new EsteOuSuperConstrutor();        
        superConstrutor.setI( i );
        superConstrutor.setParente( parente );
        superConstrutor.setCodigo( codigo );
        superConstrutor.setExec( execManager.getEsteOuSuperConstrutorExec() );
        superConstrutor.setConstrutorBusca( superConstrutorBusca ); 
        return superConstrutor;
    }
    
    public IncDec novoIncDec( int i, ExecNo parente, Codigo codigo ) {
        IncDec incdec = new IncDec();        
        incdec.setI( i );
        incdec.setParente( parente );
        incdec.setCodigo( codigo );
        incdec.setExec( execManager.getIncDecExec() ); 
        return incdec;
    }
    
    public Atrib novoAtrib( int i, ExecNo parente, Codigo codigo ) {
        Atrib atrib = new Atrib();        
        atrib.setI( i );
        atrib.setParente( parente );
        atrib.setCodigo( codigo );
        atrib.setExec( execManager.getAtribExec() ); 
        return atrib;
    }
    
    public ExpFactory getExpFactory() {
        return expFactory;
    }

    public EstruturaFactory getEstruturaFactory() {
        return estruturaFactory;
    }

    public CMDFactory getCMDFactory() {
        return cmdFactory;
    }
    
}
