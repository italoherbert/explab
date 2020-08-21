package italo.explab.recursos.classe.util;

public class ClasseUtilException extends Exception {
    
    public final static int PERSONALIZADO = 0;
    public final static int RGB_FORA_DA_FAIXA = 1;
    public final static int TIPO_INVALIDO = 2;
    public final static int VALOR_NAO_NULO_ESPERADO = 3;
    public final static int INSTANCIA_DE_CLASSE_ESPERADA = 4;
    public final static int INSTANCIA_DE_UMA_DAS_CLASSES_ESPERADA = 5;
    public final static int VETORES_TAMS_DIFERENTES = 6;
        
    private String[] params;
    private String erroChave;
    private int tipo = PERSONALIZADO;

    public ClasseUtilException( String erroChave, String... params ) {
        this.erroChave = erroChave;
        this.params = params;
        this.tipo = PERSONALIZADO;
    }

    public ClasseUtilException( int tipo, String... params ) {
        this.tipo = tipo;
        this.params = params;
    }   

    public String[] getParams() {
        return params;
    }

    public int getTipo() {
        return tipo;
    }

    public String getErroChave() {
        return erroChave;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public void setErroChave(String erroChave) {
        this.erroChave = erroChave;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
}
