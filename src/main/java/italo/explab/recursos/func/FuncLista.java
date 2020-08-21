package italo.explab.recursos.func;

import italo.explab.func.Func;
import java.util.ArrayList;
import java.util.List;

public class FuncLista {
        
    protected final List<Func> funcs = new ArrayList();
        
    public void criaOuAltera( Func func ) {                
        String nome = func.getNome();
        Func f = this.buscaLocal( nome, func.getQuantParametros() );
        if ( f != null )
            funcs.remove( f );
        
        funcs.add( func );
    }
                    
    public Func buscaLocal( String nome, int quantParametros ) {
        for( Func f : funcs )
            if ( f.verifica( nome, quantParametros ) )
                return f;                               
        return null;
    }         
    
    public List<String> buscaLocalPorIniTermo( String initexto ) {
        List<String> lista = new ArrayList();
        for( Func f : funcs ) {
            if ( f.getNome().toLowerCase().startsWith( initexto.toLowerCase() ) ) {
                String prototipo = f.getNome() + "(";
                if ( f.getQuantParametros() <= 0 ) {
                    prototipo += ")";
                } else {
                    prototipo += " ";
                    for( int i = 0; i < f.getQuantParametros(); i++ ) {
                        prototipo += (i+1);
                        if ( i < f.getQuantParametros()-1 )
                            prototipo += ", ";
                    }
                    prototipo += " )";
                }
                lista.add( prototipo );
            }
        }
        return lista;
    }
    
    public void removeSeExiste( String nome, int quantParametros ) {
        Func f = this.buscaLocal( nome, quantParametros );
        if ( f != null )
            funcs.remove( f );
    }
    
    public List<Func> getFuncs() {
        return funcs;
    }
    
}
