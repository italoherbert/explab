package italo.explab.arvore;

import italo.explab.arvore.estruturas.node.TenteCapture;
import italo.explab.codigo.Codigo;
import italo.explab.recursos.classe.Objeto;

public interface ExecNo {
                  
    public void impPilha( Executor executor );
        
    public Objeto objeto( Executor executor );
    
    public BlocoNo blocoNo();
    
    public TenteCapture tenteCaptureBlocoNo();
    
    public ExecNo funcOuConstrutorNo();

    public ExecNo grupoRaiz();
    
    public boolean isParenteAtrib();
    
    public ExecNo getParente();
    
    public Exec getExec();
    
    public Codigo getCodigo();

    public int getI();        
    
}
