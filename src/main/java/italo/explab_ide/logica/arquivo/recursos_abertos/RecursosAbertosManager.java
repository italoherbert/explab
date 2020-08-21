package italo.explab_ide.logica.arquivo.recursos_abertos;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte.ArquivosAbertos;
import italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte.ArquivosAbertosManager;
import italo.explab_ide.logica.arquivo.recursos_abertos.projarv.ProjArvoreManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RecursosAbertosManager {
    
    private final ArquivosAbertosManager arqsAbertosManager;
    private final ProjArvoreManager projArvoreManager;
    
    private final ExpLabIDEAplic aplic;

    public RecursosAbertosManager( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
        arqsAbertosManager = new ArquivosAbertosManager( aplic );
        projArvoreManager = new ProjArvoreManager( aplic );        
    }
    
    public void salvaNosCaminhosParaExpandir( List<String> nosCaminhos ) throws IOException {
        projArvoreManager.salva( nosCaminhos );
    }
    
    public List<String> recuperaNosCaminhosParaExpandir() throws IOException {
        return projArvoreManager.carrega();
    }
    
    public void salvaDadosArquivosAbertos( ArquivosAbertos arqAbertos ) throws IOException {
        arqsAbertosManager.salva( arqAbertos );
    }
    
    public ArquivosAbertos recuperaDadosArquivosAbertos() throws IOException {
        return arqsAbertosManager.carrega();
    }
    
}
