package italo.explab.inter.exp.atrib;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.matriz.valor.GenericaMatrizValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;

public class AtribVarInter extends Inter {
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();                

        AnaliseResult aresult = asManager.getLeituraVarAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );                            
                
        InterManager manager = aplic.getInterManager();        
        ContadorUtil contUtil = aplic.getContUtil();              
                
        Atrib atrib = aplic.getExecutor().getExecManager().getExecNoFactory().novoAtrib( i, no, codigo );

        int j = 0;        
        j += contUtil.contaEsps( codigo, i+j );
                                          
        InterResult varResult = manager.getOOVarOuMatELOuFuncInter().interpreta( atrib, base, aplic, codigo, i+j, i2 );
        if ( varResult.getJ() == 0 ) 
            return varResult;        
                                     
        j += varResult.getJ();        
        j += contUtil.contaEsps( codigo, i+j );
                
        int op = Atrib.IGUAL;
        
        char ch = codigo.getSEGCH( i+j );
        if ( ch == '[' ) {
            ch = codigo.getSEGCH( i+j+1 );
            if ( ch != ']' ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_COLCHETES_ESPERADO );
                return new InterResult( erro );
            }
            
            op = Atrib.ADD_AO_FIM;
            j += 2;            
        } else {
            switch ( ch ) {
                case '+':
                    op = Atrib.MAIS_IGUAL;
                    j++;                    
                    break;
                case '-':
                    op = Atrib.MENOS_IGUAL;
                    j++;                    
                    break;
                case '*':
                    op = Atrib.MULT_IGUAL;
                    j++;                    
                    break;
                case '/':
                    op = Atrib.DIV_IGUAL;
                    j++;                    
                    break;
                case '%':
                    op = Atrib.MOD_IGUAL;
                    j++;                    
                    break;
            }
        }
        
        j += contUtil.contaEsps( codigo, i+j );                 
        
        ch = codigo.getSEGCH( i+j );                
        if ( ch != '=' ) {    
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.IGUAL_ESPERADO );
            return new InterResult( erro );
        }        
                                               
        j++;
        j += contUtil.contaEsps( codigo, i+j );
                 
        Exp valor = null;
        ch = codigo.getSEGCH( i+j );
        if ( op != Atrib.ADD_AO_FIM && ch == '[' ) {
            ch = codigo.getSEGCH( i+j+1 );
            if ( ch == ']' ) {
                GenericaMatrizValor mv = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoMatrizValor( i+j, atrib, codigo );
                mv.setMatriz( new Exp[1][0] ); 
                                
                j += 2;
                j += contUtil.contaEsps( codigo, i+j );
                
                valor = mv;                
            }
        }
        
        if ( valor == null ) {
            InterResult valorResult = manager.getValorInter().interpreta( atrib, base, aplic, codigo, i+j, i2 );        
            if ( valorResult.getJ() == 0 ) {                    
                if ( valorResult.getErro() != null )
                    return valorResult;            

                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
                return new InterResult( erro );
            }

            j += valorResult.getJ(); 
            
            valor = (Exp)valorResult.getInstrucaoOuExp();
        }

        atrib.setOperador( op );
        atrib.setVarExp( (Exp)varResult.getInstrucaoOuExp() ); 
        atrib.setValorExp( valor );
                                    
        return new InterResult( atrib, j );
    }
    
}
