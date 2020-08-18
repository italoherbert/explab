package italo.explab.arvore.estruturas;

import italo.explab.arvore.ExecManager;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.estruturas.node.Capture;
import italo.explab.arvore.estruturas.node.CompareCaso;
import italo.explab.arvore.estruturas.node.CompareCasoGrupo;
import italo.explab.arvore.estruturas.node.Enquanto;
import italo.explab.arvore.estruturas.node.FacaEnquanto;
import italo.explab.arvore.estruturas.node.Finalize;
import italo.explab.arvore.estruturas.node.Para;
import italo.explab.arvore.estruturas.node.Se;
import italo.explab.arvore.estruturas.node.TenteCapture;
import italo.explab.codigo.Codigo;

public class EstruturaFactory {
    
    private final EstruturaExecManager estruturaExecManager;
    private final ExecManager manager;
    
    public EstruturaFactory( ExecManager manager ) {
        this.manager = manager;
        this.estruturaExecManager = manager.getEstruturaExecManager();
    }
    
    public Se novoSe( int i, ExecNo parente, Codigo codigo ) {
        Se se = new Se();
        se.setI( i );
        se.setParente( parente );
        se.setCodigo( codigo );
        se.setEntaoGrupo( manager.getExecNoFactory().novoGrupo( i, se, codigo ) );
        se.setSenaoGrupo( manager.getExecNoFactory().novoGrupo( i, se, codigo ) );
        se.setExec( estruturaExecManager.getSeExec() );
        return se;
    }
    
    public CompareCaso novoCompareCaso( int i, ExecNo parente, Codigo codigo ) {
        CompareCaso cc = new CompareCaso();
        cc.setI( i );
        cc.setParente( parente );
        cc.setCodigo( codigo );
        cc.setExec( estruturaExecManager.getCompareCasoExec() );
        return cc;
    }
    
    public CompareCasoGrupo novoCompareCasoGrupo( int i, ExecNo parente, Codigo codigo ) {
        CompareCasoGrupo ccg = new CompareCasoGrupo();
        ccg.setI( i );
        ccg.setParente( parente );
        ccg.setCodigo( codigo );
        ccg.setBloco( manager.getExecNoFactory().novoBloco( i, ccg, codigo ) ); 
        ccg.setExec( manager.getGrupoExec() );
        return ccg;
    }
    
    public Para novoPara( int i, ExecNo parente, Codigo codigo ) {
        Para para = new Para();                                 
        para.setI( i );
        para.setParente( parente );
        para.setCodigo( codigo );
        para.setBloco( manager.getExecNoFactory().novoBloco( i, para, codigo ) ); 
        para.setIniInstsGrupo( manager.getExecNoFactory().novoGrupo( i, para, codigo ) );
        para.setAposFimITInstsGrupo( manager.getExecNoFactory().novoGrupo( i, para, codigo ) ); 
        para.setExec( estruturaExecManager.getParaExec() );
        
        para.getIniInstsGrupo().getBloco().setBlocoRecursos( para ); 
        para.getAposFimITInstsGrupo().getBloco().setBlocoRecursos( para ); 
        
        return para;
    }
        
    public Enquanto novoEnquanto( int i, ExecNo parente, Codigo codigo ) {
        Enquanto enquanto = new Enquanto();
        enquanto.setI( i );
        enquanto.setParente( parente );
        enquanto.setCodigo( codigo );
        enquanto.setBloco( manager.getExecNoFactory().novoBloco( i, enquanto, codigo ) ); 
        enquanto.setExec( estruturaExecManager.getEnquantoExec() ); 
        return enquanto;
    }
    
    public FacaEnquanto novoFacaEnquanto( int i, ExecNo parente, Codigo codigo ) {
        FacaEnquanto facaEnquanto = new FacaEnquanto();
        facaEnquanto.setI( i );
        facaEnquanto.setParente( parente );
        facaEnquanto.setCodigo( codigo );
        facaEnquanto.setBloco( manager.getExecNoFactory().novoBloco( i, facaEnquanto, codigo ) ); 
        facaEnquanto.setExec( estruturaExecManager.getFacaEnquantoExec() );
        return facaEnquanto;
    }
    
    public TenteCapture novoTenteCapture( int i, ExecNo parente, Codigo codigo ) {
        TenteCapture tenteCapture = new TenteCapture();
        tenteCapture.setI( i );
        tenteCapture.setParente( parente );
        tenteCapture.setCodigo( codigo );        
        
        tenteCapture.setBloco( manager.getExecNoFactory().novoBloco( i, tenteCapture, codigo ) ); 
        tenteCapture.setFinalize( this.novoFinalize( i, tenteCapture, codigo ) );
        
        tenteCapture.setExec( estruturaExecManager.getTenteCaptureExec() );
        return tenteCapture;        
    }
    
    public Capture novoCapture( int i, ExecNo parente, Codigo codigo ) {
        Capture cap = new Capture();
        cap.setI( i );
        cap.setParente( parente );
        cap.setCodigo( codigo );
        cap.setBloco( manager.getExecNoFactory().novoBloco( i, cap, codigo ) );        
        cap.setExec( manager.getGrupoExec() );
        return cap;
    }
    
    public Finalize novoFinalize( int i, ExecNo parente, Codigo codigo ) {
        Finalize finalize = new Finalize();
        finalize.setI( i );
        finalize.setParente( parente );
        finalize.setCodigo( codigo );
        finalize.setBloco( manager.getExecNoFactory().novoBloco( i, finalize, codigo ) );
        finalize.setExec( manager.getGrupoExec() );
        return finalize;
    }
    
}
