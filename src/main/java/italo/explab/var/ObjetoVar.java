package italo.explab.var;

import italo.explab.recursos.classe.Objeto;

public class ObjetoVar implements Var {
        
    private final Objeto objeto;

    public ObjetoVar( Objeto objeto ) {
        this.objeto = objeto;
    }

    public Objeto getObjeto() {
        return objeto;
    }    
    
    public boolean isNull() {
        return objeto == null;
    }

    @Override
    public Var nova() {
        return new ObjetoVar( objeto );
    }

    @Override
    public int getTipo() {
        return OBJETO_REF;
    }
                    
    @Override
    public int getTipoCompativel() {
        return TC_OBJETO_REF;
    }

    @Override
    public String getStringTipo() {
        return TIPO_OBJETO_REF;
    }

    @Override
    public boolean iguais(Var var) {
        if ( var instanceof ObjetoVar ) {
            if ( var instanceof NullVar ) {
                return this.isNull();
            } else {
                return ((ObjetoVar)var).getObjeto().getReferencia() == this.getObjeto().getReferencia();
            }
        }
        return false;
    }
    
}
