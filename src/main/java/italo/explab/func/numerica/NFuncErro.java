package italo.explab.func.numerica;

import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class NFuncErro {
   
    private final Class classe;
    private final String chave;
    private final String[] params;
    private final int paramIndice;

    public NFuncErro( Class classe, int paramIndice, String chave, String... params ) {
        this.classe = classe;
        this.paramIndice = paramIndice;
        this.chave = chave;
        this.params = params;
    }
    
    public NFuncErro( Class classe, String chave, String... params ) {
        this( classe, -1, chave, params );
    }
    
    public CodigoErro criaErro( Codigo codigo, MetodoParam[] metodoParams, int i ) {
        if ( paramIndice != -1 && paramIndice < metodoParams.length ) {
            int pos = metodoParams[ paramIndice ].getPos();
            return new CodigoErro( classe, codigo, pos, chave, params );
        }
        return new CodigoErro( classe, codigo, i, chave, params );
    }

    public int getParamIndice() {
        return paramIndice;
    }

    public Class getClasse() {
        return classe;
    }

    public String getChave() {
        return chave;
    }

    public String[] getParams() {
        return params;
    }
    
}
