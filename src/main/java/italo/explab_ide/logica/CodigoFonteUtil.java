package italo.explab_ide.logica;

public class CodigoFonteUtil {
    
    public int palavraIni( String texto, int pos ) {
        int i = pos-1;
        boolean fim = false;
        while( !fim && i >= 0 ) {
            char ch = texto.charAt( i );
            if ( Character.isLetterOrDigit( ch ) || ch == '_' )
                i--;
            else fim = true;
        }
        return i+1;
    }
    
}
