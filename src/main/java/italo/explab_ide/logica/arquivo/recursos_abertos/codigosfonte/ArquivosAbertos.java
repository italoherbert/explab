package italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte;

import java.util.List;

public class ArquivosAbertos {
    
    private final List<ArquivoAberto> arquivos;
    private final int arquivoFocadoI;
    
    public ArquivosAbertos( List<ArquivoAberto> arquivos, int arquivoFocadoI ) {
        this.arquivos = arquivos;
        this.arquivoFocadoI = arquivoFocadoI;
    }

    public List<ArquivoAberto> getArquivos() {
        return arquivos;
    }

    public int getArquivoFocadoI() {
        return arquivoFocadoI;
    }
    
}
