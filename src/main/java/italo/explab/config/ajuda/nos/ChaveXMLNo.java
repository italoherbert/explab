package italo.explab.config.ajuda.nos;

public class ChaveXMLNo extends ElementoXMLNo {
    
    private final String nome;
    private final int peso;

    public ChaveXMLNo( ElementoXMLNo parente, String nome, int peso ) {
        super( parente );
        this.nome = nome;
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public int getTipo() {
        return CHAVE;
    }
    
}
