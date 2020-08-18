package italo.explab.var;

import italo.explab.func.Func;
import italo.explab.func.UsuarioFunc;

public class FuncVar implements Var {
    
    private UsuarioFunc ufunc;
    
    public FuncVar( UsuarioFunc func ) {
        this.ufunc = func;
    }

    @Override
    public Var nova() {
        return new FuncVar( ufunc );
    }

    @Override
    public int getTipo() {
        return FUNC;
    }

    @Override
    public int getTipoCompativel() {
        return TC_FUNC;
    }

    @Override
    public String getStringTipo() {
        return TIPO_FUNC;
    }

    @Override
    public boolean iguais(Var var) {
        if ( var instanceof FuncVar ) {
            FuncVar fvar = (FuncVar)var;
            Func f = fvar.getUsuarioFunc();
            return f.verifica( ufunc.getNome(), ufunc.getQuantParametros() ); 
        }
        return false;
    }

    public UsuarioFunc getUsuarioFunc() {
        return ufunc;
    }

    public void setUsuarioFunc(UsuarioFunc func) {
        this.ufunc = func;
    }
    
}
