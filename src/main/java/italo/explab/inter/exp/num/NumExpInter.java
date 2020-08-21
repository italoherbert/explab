package italo.explab.inter.exp.num;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.arvore.exp.funcoumat.FuncOuMatELExp;
import italo.explab.arvore.exp.matriz.MatrizExp;
import italo.explab.arvore.exp.matriz.vetN1IncN2.VetN1IncN2;
import italo.explab.arvore.exp.incdec.IncDec;
import italo.explab.arvore.exp.num.node.NumExp;
import italo.explab.arvore.exp.num.node.NumFunc;
import italo.explab.arvore.exp.num.node.NumMat;
import italo.explab.arvore.exp.num.node.NumRealValor;
import italo.explab.arvore.exp.num.node.NumAtrib;
import italo.explab.arvore.exp.num.node.NumIncDec;
import italo.explab.arvore.exp.num.node.NumVariavel;
import italo.explab.arvore.exp.num.node.NumVetN1IncN2;
import italo.explab.arvore.exp.num.node.NumericaExp;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.exp.num.to.NumExpInterVO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import italo.explab.var.num.NumeroRealVar;
import java.util.ArrayList;
import java.util.List;

public class NumExpInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        InterManager manager = aplic.getInterManager();
                
        AnaliseResult aresult = asManager.getNumericaExpAnalisador().analisa( codigo, i );
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
        
        boolean fechaParentesesEsperado = false;
        if ( to != null )
            fechaParentesesEsperado = ((NumExpInterVO)to).isFechaParentesesEsperado();
        
        List<NExp> exps = new ArrayList();
        
        NumRealValor zero = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumRealValor( i, no, codigo );
        zero.setValor( new NumeroRealVar( 0 ) );
        
        NExp zeroNExp = new NExp();
        zeroNExp.exp = zero;
        zeroNExp.op = NumExp.SOMA;        
            
        int j = 0;
        
        int k = j;
        
        j += contUtil.contaEsps( codigo, i+j );
        
        if ( codigo.getSEGCH( i+j ) == '-' && codigo.getSEGCH( i+j+1 ) != '-' ) {
            zeroNExp.op = NumExp.SUB;        
            j++;
        }
                
        boolean fim = false;
        while( !fim && i+j < i2 ) {
            j += contUtil.contaEsps( codigo, i+j );
            
            char ch = codigo.getSEGCH( i+j );
                                    
            InterResult ir;
            if ( ch == '(' ) {
                j++;
                NumExpInterVO vo = new NumExpInterVO( true );
                ir = this.interpreta2( no, base, aplic, codigo, vo, i+j, i2 );
                if ( ir.getJ() == 0 )
                    return ir;                                                           
            } else {
                ir = manager.getAtribVarInter().interpreta( no, base, aplic, codigo, i+j, i2 );
                if ( ir.getErro() != null )
                    return ir;

                if ( ir.getJ() == 0 ) {
                    ir = manager.getIncDecInter().interpreta( no, base, aplic, codigo, i+j, i2 );
                    if ( ir.getErro() != null )
                        return ir;
                }

                if ( ir.getJ() == 0 ) {
                    ir = manager.getRealInter().interpreta( no, base, aplic, codigo, i+j, i2 );                                    
                    if ( ir.getErro() != null )
                       return ir;
                }

                if ( ir.getJ() == 0 ) {                    
                    ir = manager.getRealMatInter().interpreta( no, base, aplic, codigo, i+j, i2 );
                    if ( ir.getErro() != null )
                        return ir;
                }

                if ( ir.getJ() == 0 ) {
                    ir = manager.getVetN1IncN2Inter().interpreta( no, base, aplic, codigo, i+j, i2 );
                    if ( ir.getErro() != null )
                        return ir;
                }

                if ( ir.getJ() == 0 ) {
                    ir = manager.getOOVarOuMatELOuFuncInter().interpreta( no, base, aplic, codigo, i+j, i2 );
                    if ( ir.getErro() != null )
                        return ir;                    
                }
            }
            
            if ( ir.getJ() == 0 ) {
                j = k;
                fim = true;
                continue;
            }
                        
            j += ir.getJ();
            
            k = j;

            j += contUtil.contaEsps( codigo, i+j );
                                    
            int op = NumExp.SOMA;
            boolean transposta = false;
            
            ch = codigo.getSEGCH( i+j );
            if ( ch == '\'' ) {
                transposta = true;
                j++;
                
                k = j;
                
                j += contUtil.contaEsps( codigo, i+j );
            }
            
            ch = codigo.getSEGCH( i+j );
            switch( ch ) {
                case '+':
                    op = NumExp.SOMA;
                    j++;
                    break;
                case '-':
                    op = NumExp.SUB;
                    j++;
                    break;
                case '*':
                    j++;
                    if ( codigo.getSEGCH( i+j ) == '*' ) {
                        op = NumExp.NAO_ESC_MULT_MATS;
                        j++;
                    } else {
                        op = NumExp.MULT;
                    }
                    break;
                case '/':
                    op = NumExp.DIV;
                    j++;
                    break;
                case '%':
                    op = NumExp.MOD;
                    j++;
                    break;
                case '^':
                    op = NumExp.POT;
                    j++;
                    break;
                case ')':                        
                    if ( fechaParentesesEsperado ) {
                        fechaParentesesEsperado = false;
                        j++;
                    }
                    fim = true;
                    break;
                default:
                    j = k;
                    fim = true;                    
                    break;
            }
            
            NumericaExp exp;
            if ( ir.getInstrucaoOuExp() instanceof VariavelExp ) {
                exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumVariavel( i+j, no, codigo );
                ((NumVariavel)exp).setVariavelExp( (VariavelExp)ir.getInstrucaoOuExp() ); 
            } else if ( ir.getInstrucaoOuExp() instanceof FuncOuMatELExp ) {
                exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumFunc( i+j, no, codigo );
                ((NumFunc)exp).setFuncExp((FuncOuMatELExp)ir.getInstrucaoOuExp() ); 
            } else if ( ir.getInstrucaoOuExp() instanceof MatrizExp ) {
                exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumMat( i+j, no, codigo );
                ((NumMat)exp).setMatrizExp( (MatrizExp)ir.getInstrucaoOuExp() ); 
            } else if ( ir.getInstrucaoOuExp() instanceof VetN1IncN2 ) {
                exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumVetN1IncN2( i+j, no, codigo );
                ((NumVetN1IncN2)exp).setVetN1IncN2( (VetN1IncN2)ir.getInstrucaoOuExp() ); 
            } else if ( ir.getInstrucaoOuExp() instanceof Atrib ) {
                exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumAtrib( i+j, no, codigo );
                ((NumAtrib)exp).setAtrib( (Atrib)ir.getInstrucaoOuExp() ); 
            } else if ( ir.getInstrucaoOuExp() instanceof IncDec ) {                                                 
                exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumIncDec( i+j, no, codigo );
                ((NumIncDec)exp).setIncDec( (IncDec)ir.getInstrucaoOuExp() ); 
            } else if ( ir.getInstrucaoOuExp() instanceof NumericaExp ) {
                exp = (NumericaExp)ir.getInstrucaoOuExp();
            } else {
                fim = true;
                continue;
            }
             
            exp.setTransposta( transposta ); 
                    
            NExp nexp = new NExp();
            nexp.exp = exp;
            nexp.op = op;
            
            exps.add( nexp );
        }
        
        if ( fechaParentesesEsperado ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
            return new InterResult( erro );
        }
                        
        exps.add( 0, zeroNExp );                
        
        NumericaExp exp = this.geraArvore( no, aplic, codigo, exps );                
        return new InterResult( exp, j );        
    }
    
    private NumericaExp geraArvore( ExecNo no, InterAplic aplic, Codigo codigo, List<NExp> exps ) {
        int size = exps.size();
        for( int i = 0; i <= 4; i++ ) {
            for( int j = 0; j < size-1; j++ ) {
                NExp exp1 = exps.get( j );
                NExp exp2 = exps.get( j+1 );
                
                boolean juntar = false;
                
                switch( i ) {                    
                    case 0:
                        juntar = exp1.op == NumExp.MOD;
                        break;
                    case 1:
                        juntar = exp1.op == NumExp.POT;
                        break;                    
                    case 2:
                        juntar = exp1.op == NumExp.MULT || exp1.op == NumExp.DIV;
                        break;
                    case 3:
                        juntar = exp1.op == NumExp.SOMA || exp1.op == NumExp.SUB;
                        break;                        
                    case 4:
                        juntar = exp1.op == NumExp.NAO_ESC_MULT_MATS;                                                    
                        break;
                }
                
                if ( juntar ) {
                    NumExp novaExp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumExp( exp1.exp.getI(), no, codigo );
                    novaExp.setExp1( exp1.exp );
                    novaExp.setExp2( exp2.exp );
                    novaExp.setOperador( exp1.op );
                    
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
     
    
    class NExp {
        NumericaExp exp;
        int op;
    }
    
}
