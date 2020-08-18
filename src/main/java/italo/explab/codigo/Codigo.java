package italo.explab.codigo;

public class Codigo {
    
    public final static String PRINCIPAL_ARQUIVO = "principal";
    
    private final String codigo;
    private final int codlen;
    private final String arquivo;
        
    private Codigo codigoCompleto;
    private CodigoDriver drv;
        
    public Codigo( CodigoDriver drv, String codigo ) {
        this( drv, codigo, PRINCIPAL_ARQUIVO );
    }

    public Codigo( CodigoDriver drv, String codigo, String arquivo ) {
        this( drv, codigo, null, arquivo );
    }
    
    public Codigo( CodigoDriver drv, String codigo, Codigo codigoCompleto, String arquivo ) {
        this.drv = drv;
        this.codigo = codigo;
        this.codlen = codigo.length();
        
        this.arquivo = arquivo;
        
        this.codigoCompleto = codigoCompleto;
    }
      
    public Codigo codigoSemComentarios() {        
        StringBuilder codigoSB = new StringBuilder();
        
        int k = 0;
        while ( this.isCHValido( k ) ) {
            k += drv.getContUtil().contaComentariosTam( this, k );
            codigoSB.append( this.getCH( k ) );
            k++;                       
        }
        
        String cod = codigoSB.toString();
        
        return new Codigo( drv, cod, this, arquivo );
    }
    
    public String palavra( int posic ) {
        int i = ( posic >= codlen ? codlen-1 : posic );
                                    
        boolean ehEsp = true;
        while( i < codlen && ehEsp ) {
            switch( codigo.charAt( i ) ) {
                case ' ':
                case '\n':
                case '\r':
                case '\t':
                    i++;
                    break;
                default:
                    ehEsp = false;
                    break;
            }
        }
        if ( !Character.isLetterOrDigit( codigo.charAt( i ) ) )
            return String.valueOf( codigo.charAt( i ) );        
                        
        int j = i;
        boolean achouFim = false;
        while( !achouFim && j < codlen ) {
            if( !Character.isLetterOrDigit( codigo.charAt( j ) ) ) {                
                achouFim = true;
            } else {
                j++;
            }
        }
        if ( j > i )
            return codigo.substring( i, j );                
        return ""+codigo.charAt( i );
    }
        
    public String getCodigo() {
        return codigo;
    }
    
    public int getCodlen() {
        return codlen;
    }

    public char getCH( int pos ) {
        return codigo.charAt( pos );
    }    
    
    public char getSEGCH( int pos ) {
        if ( this.isCHValido( pos ) ) 
            return codigo.charAt( pos );
        return '\0';
    }
        
    public boolean isCHValido( int j ) {
        return j < codlen;
    }
              
    public int codigoCompletoPos( int pos ) {                        
        if ( codigoCompleto == null )
            return pos;
        
        int i = 0;
        int j = 0;
        int len = this.getCodlen();        
        while( j < pos && i < len ) {                
            int tam = drv.getContUtil().contaComentariosTam( codigoCompleto, i );                 
            i += tam;                           
            i++;
            j++;                    
        }
        return i;
    }
        
    public String getArquivo() {
        return arquivo;
    }
    
    public Codigo getCodigoCompleto() {
        return codigoCompleto;
    }
                
}
