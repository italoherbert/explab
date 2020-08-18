package italo.explab_ide.logica.arquivo.recursos_abertos.projarv;

import italo.explab_ide.ExpLabIDEAplic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ProjArvoreManager {
    
    private final ExpLabIDEAplic aplic;
    
    public ProjArvoreManager( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
    
    public void salva( List<String> nosCaminhos ) throws IOException {
        String arqCaminho = aplic.getConfig().getProjNosParaExpandirConfigCaminho();
        
        try (PrintStream out = new PrintStream( new FileOutputStream( arqCaminho ) )) {
            for( String caminho : nosCaminhos )
                out.println( caminho );
        }
    }
    
    public List<String> carrega() throws IOException {
        String arqCaminho = aplic.getConfig().getProjNosParaExpandirConfigCaminho();
        
        List<String> nosCaminhos = new ArrayList();

        File file = new File( arqCaminho );        
        if ( !file.exists() ) 
            file.createNewFile();
        
        try ( BufferedReader in = new BufferedReader( new InputStreamReader( new FileInputStream( arqCaminho ) ) ) ) {
            String linha;
            while ( ( linha = in.readLine() ) != null )
                nosCaminhos.add( linha );
        }
        
        return nosCaminhos;
    }
    
}
