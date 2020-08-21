package italo.explab.inter.exp.string;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.string.node.StrExp;
import italo.explab.arvore.exp.string.node.StringExp;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class StringExpInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        InterManager manager = aplic.getInterManager();
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        int j = 0;
        j += contUtil.contaEsps( codigo, i+j );
                
        List<Exp> exps = new ArrayList();
        
        boolean valorEsperado = false;
                        
        boolean fim = false;
        while ( !fim && i+j < i2 ) {
            j += contUtil.contaEsps( codigo, i+j );
        
            int k = j;
            
            InterResult result;        
    
            AnaliseResult aresult = asManager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j ); 
            if ( aresult.getJ() > 0 ) {
                result = manager.getOOVarOuMatELOuFuncInter().interpreta( no, base, aplic, codigo, i+j, i2 );                                             
            } else {
                result = manager.getNaoStringExpValorInter().interpreta( no, base, aplic, codigo, i+j, i2 );
            }
            
            if ( result.getJ() == 0 ) {
                char ch = codigo.getSEGCH( i+j );            
                if ( ch == '(' ) {
                    j++;
                    result = this.interpreta( no, base, aplic, codigo, i+j, i2 );
                    if ( result.getJ() == 0 )
                        return result;                    
                    
                    j += result.getJ();                    
                    j += contUtil.contaEsps( codigo, i+j );
                    
                    if ( codigo.getSEGCH( i+j ) != ')' ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
                        return new InterResult( erro );
                    }
                    j++;
                    k = j;
                } else {            
                    if ( result.getErro() != null )
                        return result;                                        

                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
                    return new InterResult( erro );
                }                
            } else {
                j += result.getJ();                
                k = j;
                j += contUtil.contaEsps( codigo, i+j );
            }
                                     
            exps.add( (Exp)result.getInstrucaoOuExp() );
                                         
            valorEsperado = false;
            
            char ch = codigo.getSEGCH( i+j );
            switch ( ch ) {
                case '+':
                    valorEsperado = true;
                    j++;
                    break;                
                default:          
                    j = k;
                    fim = true;
                    break;
            }
        }
                
        if ( valorEsperado ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
            return new InterResult( erro );
        }
                        
        if ( exps.isEmpty() )
            return new InterResult();
                
        StringExp exp = this.geraArvore( no, base, aplic, codigo, exps );
        if ( exp != null )
            return new InterResult( exp, j );                                            
        
        return new InterResult();
    }
    
    public StringExp geraArvore( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, List<Exp> exps ) {
        int size = exps.size();
        for( int i = 0; i < size-1; i++ ) {
            Exp exp1 = exps.get( i );
            Exp exp2 = exps.get( i+1 );

            StrExp novaExp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrExp( exp1.getI(), no, codigo );
            novaExp.setExp1( exp1 );
            novaExp.setExp2( exp2 );                

            exps.set( i, novaExp );
            exps.remove( exp2 );
            size--;
            i--;                
        }
        
        if ( exps.get( 0 ) instanceof StringExp ) 
            return (StringExp)exps.get( 0 );
        
        return null;
    }
    
}
