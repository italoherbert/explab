package italo.explab.arvore;

import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.var.Variavel;

public class ExecUtil {
    
    public static void imp( ExecNo no ) {
        imp( no, 0 );
    }
    
    public static void impPilha( ExecNo no ) {
        impPilha( no, 0 );
    }
    
    public static void imp( ExecNo no, int nivel ) {
        for( int i = 0; i < nivel; i++ )
            for( int j = 0; j < 4; j++ )
                System.out.print( " " );
        
        System.out.println( no );
        
        if ( no instanceof BlocoNo ) {
            Instrucao[] instrucoes = ((BlocoNo)no).getBloco().getInstrucoes();
            if ( instrucoes != null ) {
                for( Instrucao inst : instrucoes ) {
                    imp( inst, nivel+1 );
                }
            }
        }                
    }
    
    public static void impPilha( ExecNo no, int nivel ) {
        for( int i = 0; i < nivel; i++ )
            for( int j = 0; j < 4; j++ )
                System.out.print( " " );
                
        System.out.println( no ); 
        
        if ( no.getParente() != null ) 
            impPilha( no.getParente(), nivel + 1 );
    }
    
    public static void impVars( ExecNo no ) {        
        if ( no != null ) {
            if ( no instanceof BlocoNo )
                for( Variavel v : ((BlocoNo)no).getBloco().getRecursos().getVarLista().getVariaveis() ) 
                    System.out.println( v.getNome()+"  "+v.getVar().getStringTipo() );        
            impVars( no.getParente() );            
        }
    }
    
}
