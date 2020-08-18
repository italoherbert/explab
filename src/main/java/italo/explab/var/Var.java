package italo.explab.var;

public interface Var {
    
    public final static int VOID = 0;
    public final static int GENERICO = 1;
    public final static int REAL = 2;
    public final static int MATRIZ = 3;    
    public final static int STRING = 4;
    public final static int BOOLEAN = 5;
    public final static int OBJETO_REF = 6;
    public final static int FUNC = 7;
    public final static int EXCEPTION = 8;
    
    public final static int TC_GENERICO = 0;
    public final static int TC_NUMERICO = 1;
    public final static int TC_STRING = 2;
    public final static int TC_BOOLEAN = 3;  
    public final static int TC_OBJETO_REF = 5;
    public final static int TC_MATRIZ_GENERICA = 6;
    public final static int TC_FUNC = 7;
    public final static int TC_EXCEPTION = 8;
    
    public final static String TIPO_NULL = "null";
    public final static String TIPO_GENERICO = "generico";
    public final static String TIPO_REAL = "real";
    public final static String TIPO_MATRIZ = "matriz";
    public final static String TIPO_STRING = "string";
    public final static String TIPO_BOOLEAN = "boolean";
    public final static String TIPO_OBJETO_REF = "objeto";
    public final static String TIPO_FUNC = "func";
    public final static String TIPO_EXCEPTION = "exceÃƒÂ§ÃƒÂ£o";
        
    public Var nova();
    
    public int getTipo();
        
    public int getTipoCompativel();
    
    public String getStringTipo();
        
    public boolean iguais( Var var );
                
}
