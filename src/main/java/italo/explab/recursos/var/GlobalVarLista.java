package italo.explab.recursos.var;

import italo.explab.var.BooleanVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.Var;

public class GlobalVarLista extends VarLista {
    
    public final static String PI = "pi";
    public final static String E = "e";
    public final static String NAN = "nan";
    public final static String INF_POS = "inf";    
    public final static String SCRIPT_DIR = "scriptdir";
    public final static String USUARIO_DIR = "usuariodir";
    public final static String DIV_POR_ZERO = "div_por_zero";
    public final static String ARQ_CHARSET = "arq_charset";
    public final static String SUAVIZAR2D = "suavizar2d";
        
    private final Variavel divisaoPorZero;
    private final Variavel suavizar2d;
    
    public GlobalVarLista() {        
        Variavel pi = new Variavel( PI, new NumeroRealVar( Math.PI ) );
        pi.setConstante( true );

        Variavel e = new Variavel( E, new NumeroRealVar( Math.E  ) );        
        e.setConstante( true );
        
        Variavel nan = new Variavel( NAN, new NumeroRealVar( Double.NaN  ) );        
        nan.setConstante( true );
                
        Variavel infPos = new Variavel( INF_POS, new NumeroRealVar( Double.POSITIVE_INFINITY  ) );        
        infPos.setConstante( true );
                        
        divisaoPorZero = new Variavel( DIV_POR_ZERO, new BooleanVar( false ) );
        suavizar2d = new Variavel( SUAVIZAR2D, new BooleanVar( true ) );
        
        super.vars.add( pi );
        super.vars.add( e );  
        super.vars.add( nan );
        super.vars.add( infPos );
        super.vars.add( divisaoPorZero );
        super.vars.add( suavizar2d );
    }
            
    public boolean verificaSeVarConstante( String nome ) {
        for( Variavel v : super.vars )
            if ( v.getNome().equalsIgnoreCase( nome ) ) 
                return v.isConstante();
        return false;
    }
            
    public boolean isDivisaoPorZeroLigada() {
        if ( divisaoPorZero.getVar().getTipo() == Var.BOOLEAN )
            return ((BooleanVar)divisaoPorZero.getVar()).getValor();
        return false;
    }
    
}
