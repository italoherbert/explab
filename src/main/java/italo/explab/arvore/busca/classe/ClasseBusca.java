package italo.explab.arvore.busca.classe;

import italo.explab.arvore.BlocoNo;
import italo.explab.recursos.classe.Classe;

public class ClasseBusca {
    
    public Classe consideraPacoteBusca( BlocoNo no, String classeNome ) {
        Classe c = no.getBloco().getRecursos().getClasseLista().buscaLocal( classeNome );
        if ( c == null )
            c = no.getBloco().getRecursos().getClasseLista().ignorePacoteBuscaLocal( classeNome );
                
        if ( c == null && no.getParente() != null ) {
            BlocoNo pno = no.getParente().blocoNo();
            if ( pno != null )
                c = this.consideraPacoteBusca(pno, classeNome );
        }
        
        return c;
    }              
    
    public Classe busca( BlocoNo no, String classeNomeCompleto ) {
        Classe c = no.getBloco().getRecursos().getClasseLista().buscaLocal( classeNomeCompleto );
        
        if ( c == null && no.getParente() != null ) {
            BlocoNo pno = no.getParente().blocoNo();
            if ( pno != null )
                c = this.busca( pno, classeNomeCompleto );
        }
        return c;
    } 
    
    public Classe ignorePacoteBusca( BlocoNo bno, String classeNome ) {
        Classe c = bno.getBloco().getRecursos().getClasseLista().ignorePacoteBuscaLocal( classeNome );
        
        if ( c == null && bno.getParente() != null ) {
            BlocoNo pno = bno.getParente().blocoNo();
            if ( pno != null )
                c = this.ignorePacoteBusca( pno, classeNome );
        }
        
        return c;
    } 
    
}
