package italo.explab_ide.logica.arquivo.recursos_abertos;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte.ArquivosAbertos;
import italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte.ArquivosAbertosManager;
import italo.explab_ide.logica.arquivo.recursos_abertos.projarv.ProjArvoreManager;
import java.io.IOException;
import java.util.List;

public class RecursosAbertosManager {
    
    private final ArquivosAbertosManager arqsAbertosManager;
    private final ProjArvoreManager projArvoreManager;
    
    public RecursosAbertosManager( ExpLabIDEAplic aplic ) {
        arqsAbertosManager = new ArquivosAbertosManager( aplic );
        projArvoreManager = new ProjArvoreManager( aplic );        
    }
    
    public void salvaNosCaminhosParaExpandir( List<String> nosCaminhos ) throws IOException {
        projArvoreManager.salvaNosParaExpandir( nosCaminhos );
    }
    
    public List<String> recuperaNosCaminhosParaExpandir() throws IOException {
        return projArvoreManager.carregaNosParaExpandirCaminho();
    }
    
    public void salvaNoSelecionadoCaminho( String noSelecionadoCaminho ) throws IOException {
        projArvoreManager.salvaNoSelecionado( noSelecionadoCaminho ); 
    }
    
    public String recuperaNoSelecionadoCaminho() throws IOException {
        return projArvoreManager.carregaNoSelecionadoCaminho();
    }
    
    public void salvaDadosArquivosAbertos( ArquivosAbertos arqAbertos ) throws IOException {
        arqsAbertosManager.salva( arqAbertos );
    }
    
    public ArquivosAbertos recuperaDadosArquivosAbertos() throws IOException {
        return arqsAbertosManager.carrega();
    }
        
}
