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
    
    public void salvaNosParaExpandir( List<String> nosCaminhos ) throws IOException {
        String nosParaExpandirArqCaminho = aplic.getConfig().getProjNosParaExpandirConfigCaminho();
        
        try (PrintStream out = new PrintStream( new FileOutputStream( nosParaExpandirArqCaminho ) )) {
            for( String caminho : nosCaminhos )
                out.println( caminho );
        }        
    }
    
    public void salvaNoSelecionado( String noSelecionado ) throws IOException {
        String noSelecionadoArqCaminho = aplic.getConfig().getNoSelecionadoConfigCaminho();
        
        try (PrintStream out = new PrintStream( new FileOutputStream( noSelecionadoArqCaminho ) )) {
            out.println( noSelecionado );
        }
    }
    
    public List<String> carregaNosParaExpandirCaminho() throws IOException {
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
    
    public String carregaNoSelecionadoCaminho() throws IOException {
        String arqCaminho = aplic.getConfig().getNoSelecionadoConfigCaminho();
        
        String noCaminho = null;

        File file = new File( arqCaminho );        
        if ( !file.exists() ) 
            file.createNewFile();
        
        try ( BufferedReader in = new BufferedReader( new InputStreamReader( new FileInputStream( arqCaminho ) ) ) ) {
            noCaminho = in.readLine();
        }             
        
        return noCaminho;
    }
    
}
