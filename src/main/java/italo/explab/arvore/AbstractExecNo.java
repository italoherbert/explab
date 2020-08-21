package italo.explab.arvore;

import italo.explab.arvore.classe.ClasseConstrutor;
import italo.explab.arvore.estruturas.node.Capture;
import italo.explab.arvore.estruturas.node.Finalize;
import italo.explab.arvore.estruturas.node.TenteCapture;
import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.recursos.classe.Objeto;

public abstract class AbstractExecNo implements ExecNo {
    
    protected int i;
    protected Exec exec = null;
    protected ExecNo parente = null;
    protected Codigo codigo = null;
        
    protected void carrega( AbstractExecNo novo, ExecNo parent ) {
        novo.setI( i );
        novo.setCodigo( codigo );
        novo.setParente( parent );
        novo.setExec( exec ); 
    }
    
    @Override
    public void impPilha( Executor executor ) {
        this.impPilha( executor, 0 );
    }
    
    public void impPilha( Executor executor, int nivel ) {
        for( int j = 0; j < nivel; j++ )
            System.out.print( "    " );
        
        System.out.println( this+"  "+this.objeto( executor) );
        if ( parente != null )
            ((AbstractExecNo)parente).impPilha( executor, nivel+1 );
    }
    
    @Override
    public boolean isParenteAtrib() {
        ExecNo no = this;
        while( no != null ) {
            if ( no instanceof Atrib )
                return true;            
            no = no.getParente();
        }        
        return false;
    }

    @Override
    public ExecNo grupoRaiz() {
        ExecNo no = this;
        ExecNo grupoRaiz = null;
        while( no != null ) {           
            if ( no.getParente() == null )
                grupoRaiz = no;            
            no = no.getParente();        
        }
        return grupoRaiz;
    }
    
    @Override
    public Objeto objeto( Executor executor ) {
        ExecNo no = this;
        while( no != null ) {
            if ( no instanceof ObjetoRecursoNo ) {
                Objeto obj = ((ObjetoRecursoNo)no).getRuntimeObjeto();
                if ( obj != null )
                    return obj;
            }
            no = no.getParente();
        }        
        return null;
    }
    
    @Override
    public TenteCapture tenteCaptureBlocoNo() {
        ExecNo corrente = this;
        while( corrente != null ) {
            if ( corrente instanceof FuncExp || corrente instanceof ClasseConstrutor )
                return null;
            
            if ( corrente instanceof Capture || corrente instanceof Finalize ) {
                System.out.println( "XXXXXXXXXXXXXX" );
                ExecNo tc = null;
                while ( corrente != null && tc == null ) {
                    if ( corrente instanceof TenteCapture ) {
                        tc = corrente;
                    } else {
                        corrente = corrente.getParente();
                    }
                }
                if ( tc == null )
                    return null;
                
                corrente = tc.getParente();
            } else {            
                if ( corrente instanceof TenteCapture )
                    return (TenteCapture)corrente;
            
                corrente = corrente.getParente();
            }
        }        
        return null;
    }
    
    @Override
    public ExecNo funcOuConstrutorNo() {
        ExecNo corrente = this;
        while( corrente != null ) {
            if ( corrente instanceof FuncExp || corrente instanceof ClasseConstrutor )
                return corrente;
            corrente = corrente.getParente();
        }        
        return null;
    }
        
    @Override
    public BlocoNo blocoNo() {
        ExecNo corrente = this;
        while( corrente != null ) {
            if ( corrente instanceof BlocoNo )
                return (BlocoNo)corrente;
            corrente = corrente.getParente();
        }        
        return null;
    }
    
    @Override
    public ExecNo getParente() {
        return parente;
    }

    public void setParente(ExecNo parente) {
        this.parente = parente;
    }

    @Override
    public Exec getExec() {
        return exec;
    }

    public void setExec(Exec exec) {
        this.exec = exec;
    }

    @Override
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }
        
}
