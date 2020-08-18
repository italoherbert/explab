package italo.explab.config.msg;

public class MSGErro {
        
    private String chave;
    private String valor;
    private String exceptionClasseNome;
        
    public MSGErro() {}

    public MSGErro( String chave, String valor ) {
        this.chave = chave;
        this.valor = valor;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getExceptionClasseNome() {
        return exceptionClasseNome;
    }

    public void setExceptionClasseNome(String exceptionClasseNome) {
        this.exceptionClasseNome = exceptionClasseNome;
    }


}
