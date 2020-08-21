package italo.explab.func.numerica;

import italo.explab.ErroMSGs;
import italo.explab.var.num.NumericaVar;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.mat.NumericaMatrizVar;

public abstract class MalhaGradeNFunc implements NFunc {

    private final int quantParams = 2;

    protected abstract MatrizVar calcula( MatrizVar xvet, MatrizVar yvet );
    
    @Override
    public NFuncResult exec(NumericaVar... params) {
        NFuncErro erro = this.valida( params );
        if ( erro != null )
            return new NFuncResult( erro );
        
        MatrizVar xvet = (MatrizVar)params[0];
        MatrizVar yvet = (MatrizVar)params[1];
        MatrizVar valor = this.calcula( xvet, yvet );
        
        return new NFuncResult( new NumericaMatrizVar( valor ) );
    }        
    
    private NFuncErro valida( NumericaVar... params ) {   
        if ( params.length != quantParams )
            return new NFuncErro( this.getClass(), "func.quant.params.invalida", String.valueOf( quantParams ) );
        NumericaVar p1 = params[0];
        NumericaVar p2 = params[1];
        
        if ( p1.getTipo() != NumericaVar.MATRIZ ) {
            return new NFuncErro( this.getClass(), 1, ErroMSGs.PARAM_TIPO_MATRIZ_ESPERADO );
        } else if ( p2.getTipo() != NumericaVar.MATRIZ ) {
            return new NFuncErro( this.getClass(), 2, ErroMSGs.PARAM_TIPO_MATRIZ_ESPERADO );            
        }
        return null;        
    }

    @Override
    public int getQuantParams() {
        return quantParams;
    }
    
}
