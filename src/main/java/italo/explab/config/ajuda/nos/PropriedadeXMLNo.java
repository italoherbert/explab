package italo.explab.config.ajuda.nos;

public class PropriedadeXMLNo extends ElementoXMLNo {
    
    private final String nome;
    private final String valor;

    public PropriedadeXMLNo( ElementoXMLNo parente, String nome, String valor ) {
        super( parente );
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public int getTipo() {
        return PROPRIEDADE;
    }
    
}
