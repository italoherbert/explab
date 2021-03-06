package italo.explab_ide.logica.arquivo.projeto;

public class ProjetoConfig {
    
    private final ProjetoXMLNo projeto;
    private final String charset;
    private final String execScript;

    public ProjetoConfig(ProjetoXMLNo projeto, String charset, String execScript) {
        this.projeto = projeto;
        this.charset = charset;
        this.execScript = execScript;
    }

    public ProjetoXMLNo getProjeto() {
        return projeto;
    }

    public String getCharset() {
        return charset;
    }

    public String getExecScript() {
        return execScript;
    }
    
}
