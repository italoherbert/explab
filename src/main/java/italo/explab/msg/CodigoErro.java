package italo.explab.msg;

import italo.explab.codigo.Codigo;

public class CodigoErro extends Erro {  
    
    protected Codigo codigo;
    protected int pos = -1;

    public CodigoErro() {}
          
    public CodigoErro( Class c, Codigo codigo, String chave, String... params ) {
        this( c, codigo, -1, chave, params );
    }
    
    public CodigoErro( Class c, Codigo codigo, int pos, String chave, String... params) {
        super( c, chave, params );
        this.codigo = codigo;
        this.pos = pos;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

}
