package italo.explab.msg;

import italo.explab.codigo.Codigo;

public class LanceErro extends CodigoErro {

    public LanceErro( Class c, Codigo codigo, String chave, String... params ) {
        super( c, codigo, chave, params );
    }

    public LanceErro( Class c, Codigo codigo, int pos, String chave, String... params ) {
        super( c, codigo, pos, chave, params );
    }
    
}
