package italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte;

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
import java.util.StringTokenizer;

public class ArquivosAbertosManager {
    
    private final ExpLabIDEAplic aplic;
    
    public ArquivosAbertosManager( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
    
    public void salva( ArquivosAbertos arquivosAbertos ) throws IOException {
        String arquivoConfigCaminho = aplic.getConfig().getArquivosAbertosConfigCaminho();
        
        try ( PrintStream out = new PrintStream( new FileOutputStream( arquivoConfigCaminho ) ) ) {
            List<ArquivoAberto> arquivos = arquivosAbertos.getArquivos();
            int arquivoFocadoI = arquivosAbertos.getArquivoFocadoI();
            int size = arquivos.size();
            for( int i = 0; i < size; i++ ) {
                ArquivoAberto arq = arquivos.get( i );
                out.print( "\"" + arq.getCaminho() + "\" "+arq.getCursorPos() );
                if ( i == arquivoFocadoI )
                    out.print( " focado" );
                out.println();
            }
        }
    }
    
    public ArquivosAbertos carrega() throws IOException {
        String arquivoConfigCaminho = aplic.getConfig().getArquivosAbertosConfigCaminho();
        
        List<ArquivoAberto> arquivos = new ArrayList();
        int arquivoFocadoI = -1;
        
        File file = new File( arquivoConfigCaminho );        
        if ( !file.exists() ) 
            file.createNewFile();
        
        try ( BufferedReader in = new BufferedReader( new InputStreamReader( new FileInputStream( arquivoConfigCaminho ) ) ) ) {
            String linha;
            while( (linha = in.readLine() ) != null ) {
                StringTokenizer tokenizer = new StringTokenizer( linha );
                String caminho = tokenizer.nextToken();
                caminho = caminho.substring( 1, caminho.length()-1 );
                
                int cursorPos = Integer.parseInt( tokenizer.nextToken() );
                
                if ( tokenizer.hasMoreTokens() )
                    arquivoFocadoI = arquivos.size();
                
                arquivos.add( new ArquivoAberto( caminho, cursorPos ) );
            }
        }
        
        return new ArquivosAbertos( arquivos, arquivoFocadoI );        
    }
    
}
