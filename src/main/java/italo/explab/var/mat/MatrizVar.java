package italo.explab.var.mat;

import italo.explab.var.BooleanVar;
import italo.explab.var.NullVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.Var;
import italo.explab.var.num.NumericaVar;

public class MatrizVar implements NumericaVar {
    
    public final static int CAPACIDADE_NL_PADRAO = 1;
    public final static int CAPACIDADE_NC_PADRAO = 10;
    
    protected Var[][] matriz;
    protected int tipo = MATRIZ;
    protected int tipoCompativel = TC_NUMERICO;
    
    protected int linhas = 0;
    protected int colunas = 0;

    protected boolean temMatEL = false;
            
    public MatrizVar( int nl, int nc ) {
        this( new Var[ nl ][ nc ], nl, nc );                
    }
    
    public MatrizVar( int nl, int nc, int capacidadeNL, int capacidadeNC ) {
        this( new Var[ capacidadeNL ][ capacidadeNC ], nl, nc );                
    }
    
    public MatrizVar( Var[][] matriz, int nl, int nc ) {
        this.matriz = matriz;        
        this.linhas = nl;
        this.colunas = nc;
    }

    @Override
    public Var nova() {
        Var[][] mat = new Var[ matriz.length ][];
        for( int i = 0; i < mat.length; i++ ) {
            Var[] vetor = matriz[ i ];
            Var[] vet = new Var[ vetor.length ];
            for( int j = 0; j < vet.length; j++ )
                vet[ j ] = vetor[ j ];
            mat[ i ] = vet;
        }
        return new MatrizVar( mat, linhas, colunas );
    }
        
    @Override
    public boolean iguais( Var var ) {
        if ( !(var instanceof MatrizVar ) )
            return false;
                
        int nl = this.getNL();
        int nc = this.getNC();
        
        MatrizVar mat = (MatrizVar)var;
        int nl2 = mat.getNL();
        int nc2 = mat.getNC();
        
        if ( nl == nl2 && nc == nc2 ) {        
            for( int i = 0; i < nl; i++ )
                for( int j = 0; j < nc; j++ )
                    if ( !this.getElemento( i, j ).iguais( mat.getElemento( i, j ) ) )
                        return false;
            return true;
        }
        return false;
    }

    public Var getElemento( int linha, int coluna ) {
        return matriz[ linha ][ coluna ];
    }

    public void setElemento( int linha, int coluna, Var valor ) {
        if ( tipoCompativel == TC_NUMERICO ) {
            if ( valor.getTipoCompativel() != TC_NUMERICO )
                tipoCompativel = TC_GENERICO;           
        } else if ( tipoCompativel == TC_BOOLEAN ) {
            if ( valor.getTipoCompativel() != TC_BOOLEAN )
                tipoCompativel = TC_GENERICO;
        }
                
        if ( linha >= linhas )
            linhas = linha+1;
        if ( coluna >= colunas )
            colunas = coluna+1;
                                
        matriz[ linha ][ coluna ] = valor;
    }

    public void setMatriz( Var[][] mat, int nl, int nc, int tc ) {        
        switch (tc) {
            case TC_NUMERICO:
                tipoCompativel = TC_NUMERICO;
                break;
            case TC_BOOLEAN:
                tipoCompativel = TC_BOOLEAN;
                break;
            default:
                tipoCompativel = TC_GENERICO;
                break;
        }
        this.matriz = mat;
                
        this.linhas = nl;
        this.colunas = nc;        
    }    
        
    public Var elementoPadraoPorTC() {
        switch ( tipoCompativel ) {
            case Var.TC_NUMERICO: return new NumeroRealVar( 0 );
            case Var.TC_BOOLEAN: return new BooleanVar( true );            
        }
        return new NullVar();
    }
    
    public int getNL() {
        return linhas;
    }
    
    public int getNC() {
        return colunas;
    }
    
    public int getCapacidadeNL() {
        if ( matriz == null )
            return 0;
        return matriz.length;
    }
    
    public int getCapacidadeNC() {
        if ( matriz == null )
            return 0;
        if ( matriz.length <= 0 )
            return 0;
        return matriz[0].length;
    }

    public boolean isTemMatEL() {
        return temMatEL;
    }

    public void setTemMatEL(boolean temMatEL) {
        this.temMatEL = temMatEL;
    }    

    @Override
    public int getTipo() {
        return tipo;
    }

    @Override
    public int getTipoCompativel() {
        return tipoCompativel;
    }
        
    public Var[][] getMatriz() {
        return matriz;
    }      

    @Override
    public String getStringTipo() {
        return TIPO_MATRIZ;
    }
    
}
