package italo.explab.inter.exp;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.bool.node.BoolValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.ContadorUtil;
import italo.explab.var.BooleanVar;

public class NaoStringExpValorInter extends AbstractValorInter {

    @Override
    protected InterResult interpretaEXPs( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        
        AnaliseResult boolAResult = null;
        if ( codigo.getSEGCH( i ) == '(' ) {
            boolAResult = asManager.getBoolExpAnalisador().analisa( codigo, i );                                                    
        } else {            
            int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.VERDADE );
            if ( cont > 0 ) {
                BoolValor valor = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoBoolValor( i, no, codigo );
                valor.setValor( new BooleanVar( true ) );
                return new InterResult( valor, cont );
            }

            cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.FALSO );
            if ( cont > 0 ) {
                BoolValor valor = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoBoolValor( i, no, codigo );
                valor.setValor( new BooleanVar( false ) );
                return new InterResult( valor, cont );
            }
        }                

        AnaliseResult numExpAResult = asManager.getNumericaExpAnalisador().analisa( codigo, i );                
        AnaliseResult varEntreParentesesAResult = asManager.getVarOuChamadaFuncAnalisador().analisa( codigo, i );
        
        InterResult result = null;
        if ( boolAResult != null ) {
            if ( boolAResult.getJ() == numExpAResult.getJ() && boolAResult.getJ() == varEntreParentesesAResult.getJ() ) {
                result = manager.getVarTalvezComParentesesInter().interpreta( no, base, aplic, codigo, i, i2 );
            } else {
                if ( boolAResult.getJ()+numExpAResult.getJ() > 0 ) {
                    if ( boolAResult.getJ() > numExpAResult.getJ() ) {
                        result = manager.getBoolExpInter().interpreta( no, base, aplic, codigo, i, i2 );                                        
                    } else if ( boolAResult.getJ() < numExpAResult.getJ() ) {
                        result = manager.getNumExpInter().interpreta( no, base, aplic, codigo, i, i2 );    
                    }         
                } 
            }
        } else {
            if ( numExpAResult.getJ() == varEntreParentesesAResult.getJ() ) {
                result = manager.getVarTalvezComParentesesInter().interpreta( no, base, aplic, codigo, i, i2 );
            } else {
                result = manager.getNumExpInter().interpreta( no, base, aplic, codigo, i, i2 );    
            }
        }
        
        if ( result != null )
            if ( result.getJ() > 0 || result.getErro() != null )
                return result;                    
                        
        return manager.getStringInter().interpreta( no, base, aplic, codigo, i, i2 );
    }

    @Override
    public AnalisadorSintatico getValorAnalisador(AnalisadorSintaticoManager asManager) {
        return asManager.getValorNaoStringExpAnalisador();
    }
    
}

