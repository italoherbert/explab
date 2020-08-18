package italo.explab.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ArquivoUtil {
        
    public String ler( String caminho ) throws FileNotFoundException, IOException {
        return this.ler( caminho, "UTF-8" );
    }
        
    public String ler( InputStream in ) throws FileNotFoundException, IOException {
        return this.ler( in, "UTF-8" );
    }
    
    public String ler( String caminho, String charset ) throws FileNotFoundException, IOException {
        return this.ler( new FileInputStream( caminho ), charset );
    }
    
    public String ler( InputStream in, String charset ) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader( in, charset ) );
        
        StringBuilder codigo = new StringBuilder();
        String linha;
        while( (linha = reader.readLine()) != null ) {
            codigo.append( linha );        
            codigo.append( "\n" );
        }
                
        return codigo.toString();
    }
    
}
