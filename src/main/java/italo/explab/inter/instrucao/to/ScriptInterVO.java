package italo.explab.inter.instrucao.to;

import italo.explab.inter.InterTO;

public class ScriptInterVO implements InterTO {
    
    private String codigo;
    private String arquivoNome;

    public ScriptInterVO( String arquivoNome, String codigo ) {
        this.arquivoNome = arquivoNome;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getArquivoNome() {
        return arquivoNome;
    }

    public void setArquivoNome(String nomeArquivo) {
        this.arquivoNome = nomeArquivo;
    }
    
    
    
}
