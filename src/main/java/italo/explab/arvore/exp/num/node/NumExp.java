package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.incdec.IncDec;
import italo.explab.arvore.instrucao.Instrucao;

public class NumExp extends NumericaExp {
    
    public final static int SOMA = 1;
    public final static int SUB = 2;
    public final static int MULT = 3;
    public final static int DIV = 4;
    public final static int MOD = 5;    
    public final static int POT = 6;
    public final static int NAO_ESC_MULT_MATS = 7;        
    
    private Exp exp1;
    private Exp exp2;
    private int operador = SOMA;
    
    private IncDec[] incsOuDecs = null;

    @Override
    public Instrucao novo( ExecNo parent ) {
        NumExp nexp = new NumExp();
        super.carrega( nexp, parent );
        
        nexp.setExp1( (Exp)exp1.novo( nexp ) );
        nexp.setExp2( (Exp)exp2.novo( nexp ) );
        nexp.setOperador( operador );
        
        if ( incsOuDecs != null ) {
            IncDec[] incdecs = new IncDec[ incsOuDecs.length ];
            for( int k = 0; k < incdecs.length; k++ )
                incdecs[ k ] = (IncDec)incsOuDecs[ k ].novo( nexp );

            nexp.setIncsOuDecs( incdecs ); 
        }
        
        return nexp;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        exp1.setBaseParamsParente( parent );
        exp2.setBaseParamsParente( parent );
        
        if( incsOuDecs != null )
            for( IncDec incdec : incsOuDecs )
                incdec.setBaseParamsParente( parent ); 
    }
    
    public Exp getExp1() {
        return exp1;
    }

    public void setExp1(Exp exp) {
        this.exp1 = exp;
    }

    public Exp getExp2() {
        return exp2;
    }

    public void setExp2(Exp exp) {
        this.exp2 = exp;
    }

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operador) {
        this.operador = operador;
    }

    public IncDec[] getIncsOuDecs() {
        return incsOuDecs;
    }

    public void setIncsOuDecs(IncDec[] incsOuDecs) {
        this.incsOuDecs = incsOuDecs;
    }
            
}
