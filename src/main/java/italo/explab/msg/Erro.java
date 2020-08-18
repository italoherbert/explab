package italo.explab.msg;

public class Erro {
    
    protected Class classe;
    protected String chave;
    protected String[] params;

    public Erro() {}

    public Erro( Class classe ) {
        this.classe = classe;
    }
        
    public Erro( Class classe, String chave, String... params ) {
        this.classe = classe;
        this.chave = chave;
        this.params = params;        
    }
        
    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

}
