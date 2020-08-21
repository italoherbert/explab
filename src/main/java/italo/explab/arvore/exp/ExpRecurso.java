package italo.explab.arvore.exp;

import italo.explab.arvore.busca.ArvBuscaManager;
import italo.explab.recursos.classe.Objeto;

public interface ExpRecurso {
           
    public final static int NORMAL = 0;
    public final static int ESTE = 1;
    public final static int SUPER = 2;
         
    public void runtimeConfigObjBusca( Objeto obj, ArvBuscaManager abmanager );
    
    public void setOOChamador( OOChamada ooChamada );
 
    public void setOOChamada( OOChamada ooChamada );
    
}
