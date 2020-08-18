package italo.explab.arvore.exp.matriz.vetN1IncN2;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;
import italo.explab.var.mat.NumericaMatrizVar;
import italo.explab.var.num.RealVar;

public class VetN1IncN2Exec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        VetN1IncN2 vet = (VetN1IncN2)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult er1 = executor.exec( vet.getN1() );
        if ( er1.isErroOuEx() )
            return er1;
        
        if ( er1.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getN1().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
            return new ExecResult( erro );
        }

        if ( er1.getVar().getTipo() != Var.REAL ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getN1().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
            return new ExecResult( erro );
        }
        
        ExecResult er2 = executor.exec( vet.getInc() );
        if ( er2.isErroOuEx() )
            return er2;
        
        if ( er2.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getInc().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
            return new ExecResult( erro );
        }

        if ( er2.getVar().getTipo() != Var.REAL ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getInc().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
            return new ExecResult( erro );
        }
        
        ExecResult er3 = executor.exec( vet.getN2() );
        if ( er3.isErroOuEx() )
            return er3;
        
        if ( er3.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getN1().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
            return new ExecResult( erro );
        }

        if ( er3.getVar().getTipo() != Var.REAL ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getN1().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
            return new ExecResult( erro );
        }

        double n1 = ((RealVar)er1.getVar()).getValor();
        double inc = ((RealVar)er2.getVar()).getValor();
        double n2 = ((RealVar)er3.getVar()).getValor();
        
        if ( n1 > n2 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getN2().getI(), ErroMSGs.VET_N1_MAIOR_QUE_N2 );
            return new ExecResult( erro );
        } else if ( inc <= 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getInc().getI(), ErroMSGs.VET_INC_MENOR_OU_IGUAL_A_ZERO ); 
            return new ExecResult( erro );
        } else {
            double[][] vetor;
            if ( n1 == n2 ) {
                vetor = new double[ 1 ][ 1 ];
                vetor[0][0] = n1;
            } else {
                int quant = ( (int) ( ( n2 - n1 ) / inc ) ) + 1;                    

                vetor = new double[ 1 ][ quant ];
                for( int k = 0; k < quant; k++ )
                    vetor[0][k] = n1 + ( k * inc );                
            }
            
            return new ExecResult( new NumericaMatrizVar( vetor ) );
        }        
    }
    
}
