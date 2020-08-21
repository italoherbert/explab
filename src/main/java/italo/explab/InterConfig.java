package italo.explab;

public class InterConfig {
        
    private final String CLASSE_ARQ_EXT = ".exp";
    private final String SCRIPTS_PASTA = "scripts";

    private final String ARQ_CHARSET_PADRAO = "UTF-8";
    
    private final String CLASSE_EXCEPTION = "explab.ex.Exception";
    private final String CLASSE_RUNTIME_EXCEPTION = "explab.ex.RuntimeException";
    
    private final int QUANT_MAT_EL_ESP = 2;    
    
    private String diretorioCorrente;
    private String scriptDir;
    private String usuarioDir;
        
    public String getArqCharsetPadrao() {
        return ARQ_CHARSET_PADRAO;
    }
    
    public String getClasseArqEXT() {
        return CLASSE_ARQ_EXT;
    }
    
    public String getScriptsPasta() {
        return SCRIPTS_PASTA;
    }
    
    public int getQuantMatElEsp() {
        return QUANT_MAT_EL_ESP;
    }
    
    public String getClasseException() {
        return CLASSE_EXCEPTION;
    }
    
    public String getClasseRuntimeException() {
        return CLASSE_RUNTIME_EXCEPTION;
    }
    
    public String getDiretorioCorrente() {
        return diretorioCorrente;
    }

    public void setDiretorioCorrente(String diretorioCorrente) {
        this.diretorioCorrente = diretorioCorrente;
    }

    public String getScriptDir() {
        return scriptDir;
    }

    public void setScriptDir(String scriptDir) {
        this.scriptDir = scriptDir;
    }

    public String getUsuarioDir() {
        return usuarioDir;
    }

    public void setUsuarioDir(String usuarioDir) {
        this.usuarioDir = usuarioDir;
    }
    
}
