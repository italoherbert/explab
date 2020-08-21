package explab.inter.scripts;

import java.io.InputStream;

public class Arquivo {
    
    private String nome;
    private InputStream inputStream;

    public Arquivo(String nome, InputStream inputStream) {
        this.nome = nome;
        this.inputStream = inputStream;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
}
