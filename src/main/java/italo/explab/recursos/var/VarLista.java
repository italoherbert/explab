package italo.explab.recursos.var;

import italo.explab.var.Var;
import java.util.ArrayList;
import java.util.List;

public class VarLista {
        
    protected final List<Variavel> vars = new ArrayList();
    
    public Variavel criaOuAltera( String nome, Var var ) throws ConstanteException {
        return this.criaOuAltera( new Variavel( nome, var ) );
    }
    
    public Variavel criaOuAltera( Variavel novaVar ) throws ConstanteException {
        boolean alterou = this.altera( novaVar );       
        if ( !alterou )
            return this.addVar( novaVar );                 
        
        return null;
    }
        
    public Variavel addVar( String nome, Var var ) {
        return this.addVar( new Variavel( nome, var ) );
    }
        
    public Variavel addVar( Variavel v ) {
        vars.add( v );
        return v;
    }
    
    public boolean altera( Variavel novaVar ) throws ConstanteException {
        boolean alterou = false;
        int size = vars.size();
        for( int i = 0; !alterou && i < size; i++ ) {
            Variavel v = vars.get( i );
            if ( v.getNome().equalsIgnoreCase( novaVar.getNome() ) ) {
                if ( v.isConstante() ) 
                    throw new ConstanteException( v.getNome() );
                
                v.setVar( novaVar.getVar() ); 
                alterou = true;
            } 
        }         
                   
        return alterou;
    }
            
    public Variavel remove( String nome ) throws VarReservadaException {
        int size = vars.size();
        Variavel var = null;
        for( int i = 0; var == null && i < size; i++ ) {
            Variavel v = vars.get( i );
            if ( v.isConstante() )
                throw new VarReservadaException();
                
            if ( v.getNome().equalsIgnoreCase( nome ) )
                var = vars.remove( i );                        
        }                        
        return var;
    }
    
    public Variavel buscaLocal( String nome ) {
        for( Variavel v : vars )
            if ( v.getNome().equalsIgnoreCase( nome ) )
                return v; 
        return null;
    }
    
    public List<String> buscaLocalPorIniTermo( String initermo ) {
        List<String> lista = new ArrayList();
        for( Variavel v : vars )
            if ( v.getNome().toLowerCase().startsWith(initermo.toLowerCase() ) )
                lista.add( v.getNome() );
        return lista;
    }
        
    public int tamanho() {
        return vars.size();
    }
    
    public boolean isVazia() {
        return vars.isEmpty();
    }
            
    public List<Variavel> getVariaveis() {
        return vars;
    }
    
}
