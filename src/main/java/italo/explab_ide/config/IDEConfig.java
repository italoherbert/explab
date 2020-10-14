package italo.explab_ide.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import libs.comparador.Comparador;
import libs.comparador.IgnoreCaseComparador;
import libs.comparador.IgualComparador;
import libs.gui.arv.ArvConfig;

public class IDEConfig implements ArvConfig {
    
    private String scriptELExt;
    
    private String projetosXMLConfigCaminho;
    private String arquivosAbertosConfigCaminho;
    private String projNosParaExpandirConfigCaminho;
    private String noSelecionadoConfigCaminho;
    
    private String projetoBaseDirPadrao;
    private String projetoConfigArqPadrao;
    private String projetoExecScriptPadrao;
    private String projetoCharsetPadrao;
    
    private String sistema;
    private String docurl;
    
    private final Comparador ignoreCaseComparador = new IgnoreCaseComparador();
    private final Comparador igualComparador = new IgualComparador();
    
    public void carregaIDEConfig( String arq ) throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.load( new FileInputStream( arq ) );
        sistema = p.getProperty( "sistema" );
        docurl = p.getProperty( "ajuda.docurl" );
    }
    
    @Override
    public boolean isSomentePastas() {
        return false;
    }

    @Override
    public boolean isBarraRaizDir() {
        return false;
    }
    
    @Override
    public Comparador getComparador() {
        if ( sistema.equals( "linux" ) )
            return igualComparador;
        return ignoreCaseComparador;
    }

    public String getDocURL() {
        return docurl;
    }

    public String getScriptELExt() {
        return scriptELExt;
    }

    public void setScriptELExt(String scriptELExt) {
        this.scriptELExt = scriptELExt;
    }
    
    public String getProjetosXMLConfigCaminho() {
        return projetosXMLConfigCaminho;
    }

    public void setProjetosXMLConfigCaminho(String projetosXMLConfigCaminho) {
        this.projetosXMLConfigCaminho = projetosXMLConfigCaminho;
    }

    public String getArquivosAbertosConfigCaminho() {
        return arquivosAbertosConfigCaminho;
    }

    public void setArquivosAbertosConfigCaminho(String arquivosAbertosConfigCaminho) {
        this.arquivosAbertosConfigCaminho = arquivosAbertosConfigCaminho;
    }

    public String getNoSelecionadoConfigCaminho() {
        return noSelecionadoConfigCaminho;
    }

    public void setNoSelecionadoConfigCaminho(String noSelecionadoConfigCaminho) {
        this.noSelecionadoConfigCaminho = noSelecionadoConfigCaminho;
    }

    public String getProjNosParaExpandirConfigCaminho() {
        return projNosParaExpandirConfigCaminho;
    }

    public void setProjNosParaExpandirConfigCaminho(String arvNosParaExpandirConfigCaminho) {
        this.projNosParaExpandirConfigCaminho = arvNosParaExpandirConfigCaminho;
    }

    public String getProjetoBaseDirPadrao() {
        return projetoBaseDirPadrao;
    }

    public void setProjetoBaseDirPadrao(String projetoBaseDirPadrao) {
        this.projetoBaseDirPadrao = projetoBaseDirPadrao;
    }

    public String getProjetoConfigArqPadrao() {
        return projetoConfigArqPadrao;
    }

    public void setProjetoConfigArqPadrao(String projetoConfigArqPadrao) {
        this.projetoConfigArqPadrao = projetoConfigArqPadrao;
    }

    public String getProjetoExecScriptPadrao() {
        return projetoExecScriptPadrao;
    }

    public void setProjetoExecScriptPadrao(String projetoExecScriptPadrao) {
        this.projetoExecScriptPadrao = projetoExecScriptPadrao;
    }

    public String getProjetoCharsetPadrao() {
        return projetoCharsetPadrao;
    }

    public void setProjetoCharsetPadrao(String projetoCharsetPadrao) {
        this.projetoCharsetPadrao = projetoCharsetPadrao;
    }
    
}
