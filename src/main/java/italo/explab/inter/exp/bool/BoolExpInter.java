package italo.explab.inter.exp.bool;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.bool.node.BoolExp;
import italo.explab.arvore.exp.bool.node.BooleanExp;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.exp.bool.to.BoolExpInterVO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class BoolExpInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        InterManager manager = aplic.getInterManager();
        
        AnaliseResult aresult = asManager.getBoolExpAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
        
        AnaliseResult aresult2 = asManager.getVarTalvezComParentesesAnalisador().analisa( codigo, i );
        if ( aresult2.getJ() > 0 && aresult.getJ() == aresult2.getJ() )
            return manager.getVarTalvezComParentesesInter().interpreta( no, base, aplic, codigo, i, i2 );
                                       
        return this.interpreta2( no, base, aplic, codigo, to, i, i2 );
    }
    
    public InterResult interpreta2( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
    
        List<BExp> exps = new ArrayList();
        
        boolean fechaParentesesEsperado = false;
        if ( to != null )
            fechaParentesesEsperado = ((BoolExpInterVO)to).isFechaParentesesEsperado();        
        
        int j = 0;
        boolean fim = false;
        while( !fim && i+j < i2 ) {
            j += contUtil.contaEsps( codigo, i+j );
            
            boolean not = false;
            if ( codigo.getSEGCH( i+j ) == '!' ) {
                not = true;
                j++;
            }
                        
            InterResult ir = manager.getBoolCompInter().interpreta( no, base, aplic, codigo, to, i+j, i2 );
            if ( ir.getErro() != null )
                return ir;

            if ( ir.getJ() == 0 ) {
                ir = manager.getBoolValorInter().interpreta( no, base, aplic, codigo, i+j, i2 );
                if ( ir.getErro() != null )
                    return ir;
            }
            
            if ( ir.getJ() == 0 ) {
                ir = manager.getBoolNaoExpValorInter().interpreta( no, base, aplic, codigo, i+j, i2 );
                if ( ir.getErro() != null )
                    return ir;                            
            }

            if ( ir.getJ() == 0 ) {
                switch ( codigo.getCH( i+j ) ) {
                case '(':
                    j++;
                    BoolExpInterVO vo = new BoolExpInterVO( true );
                    ir = this.interpreta2( no, base, aplic, codigo, vo, i+j, i2 );
                    if ( ir.getErro() != null )
                        return ir;                              
                    break;
                case ')':
                    if ( fechaParentesesEsperado ) {
                        fechaParentesesEsperado = false;
                        j++;
                    }
                    fim = true;
                    continue;
                }
            }

            if ( ir.getJ() == 0 ) {
                if ( not ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
                    return new InterResult( erro );
                }

                return new InterResult();
            }             

            j += ir.getJ();            
                                                 
            int op = BoolExp.VALOR_UNICO;
            
            int k = j + contUtil.contaEsps( codigo, i+j );
            char ch = codigo.getSEGCH( i+k );
            switch( ch ) {
                case '&':
                    op = BoolExp.AND;
                    j = k+1;
                    break;
                case '|':
                    op = BoolExp.OR;
                    j = k+1;
                    break;
                case '#':
                    op = BoolExp.XOR;
                    j = k+1;
                    break;
                case ')':
                    if ( fechaParentesesEsperado ) {
                        fechaParentesesEsperado = false;
                        j = k+1;
                    }
                    fim = true;
                    break;
                default:
                    fim = true;
                    break;
            }           
                        
            BooleanExp bexp = (BooleanExp)ir.getInstrucaoOuExp();
            if ( not )
                bexp.setNot( !bexp.isNot() ); 
            
            BExp exp = new BExp();
            exp.exp = bexp;
            exp.op = op;
            exps.add( exp );
        }    
        
        if ( fechaParentesesEsperado ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
            return new InterResult( erro );
        }
                
        if ( exps.isEmpty() )
            return new InterResult();
            
        BooleanExp exp = this.geraArvore( no, aplic, codigo, exps );        
        return new InterResult( exp, j );        
    }
    
    private BooleanExp geraArvore( ExecNo no, InterAplic aplic, Codigo codigo, List<BExp> exps ) {
        int size = exps.size();
        for( int i = 0; i <= 2; i++ ) {
            for( int j = 0; j < size-1; j++ ) {
                BExp exp1 = exps.get( j );
                BExp exp2 = exps.get( j+1 );
                
                boolean juntar = false;
                
                switch( i ) {
                    case 0:
                        juntar = exp1.op == BoolExp.XOR;                                                    
                        break;
                    case 1:
                        juntar = exp1.op == BoolExp.AND;
                        break;
                    case 2:
                        juntar = exp1.op == BoolExp.OR;
                        break;                    
                }
                
                if ( juntar ) {
                    BoolExp novaExp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoBoolExp( exp1.exp.getI(), no, codigo );
                    novaExp.setExp1( exp1.exp );
                    novaExp.setExp2( exp2.exp );
                    novaExp.setOperador( exp1.op ); 
                    
                    exp1.exp.setParente( novaExp );
                    exp2.exp.setParente( novaExp ); 
                    
                    exp1.exp = novaExp;
                    exp1.op = exp2.op;

                    exps.remove( exp2 );
                    size--;
                    j--;
                }
            }
        }
        return exps.get( 0 ).exp;
    }
    
    class BExp {
        BooleanExp exp;
        int op;
    }
    
}
