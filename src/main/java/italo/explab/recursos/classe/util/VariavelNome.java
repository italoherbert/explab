package italo.explab.recursos.classe.util;

public class VariavelNome {
    
    private final String nome;    
    private final VariavelNome parente;
    
    public VariavelNome( String atributo ) {
        this( atributo, null );
    }
    
    public VariavelNome( String nome, VariavelNome parente ) {
        this.nome = nome;
        this.parente = parente;
    }

    public String getNome() {
        return nome;
    }

    public VariavelNome getParente() {
        return parente;
    }
    
}
