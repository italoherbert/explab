package italo.explab.inter.exp.bool;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.bool.node.BoolComp;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import italo.explab.var.StringVar;

public class BoolCompInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        int j = 0;

        BoolComp bc = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoBoolComp( i+j, no, codigo );
        
        boolean notExp1 = false;
        if ( codigo.getSEGCH( i+j ) == '!' ) {
            notExp1 = true;
            j++;
            j += contUtil.contaEsps( codigo, i+j );
        }
        
        InterResult ir1 = manager.getBoolValorInter().interpreta( bc, base, aplic, codigo, i+j, i2 );
        if ( ir1.getJ() == 0 )
            ir1 = manager.getNaoBoolExpValorInter().interpreta( bc, base, aplic, codigo, i+j, i2 );        
                
        if ( ir1.getJ() == 0 )
            return ir1;
        
        j += ir1.getJ();
        j += contUtil.contaEsps( codigo, i+j );
        
        int op;
        
        char ch = codigo.getSEGCH( i+j );
        switch( ch ) {
            case '=':
                if ( codigo.getSEGCH( i+j+1 ) != '=' ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.IGUAL_ESPERADO );
                    return new InterResult( erro );
                }
                op = BoolComp.IGUAL;                
                j += 2;
                break;
            case '!':
                if ( codigo.getSEGCH( i+j+1 ) != '=' ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.IGUAL_ESPERADO );
                    return new InterResult( erro );
                }
                op = BoolComp.DIFERENTE;  
                j += 2;
                break;
            case '>':
                if ( codigo.getSEGCH( i+j+1 ) == '=' ) {
                    op = BoolComp.MAIOR_OU_IGUAL;  
                    j++;
                } else {
                    op = BoolComp.MAIOR;
                }
                j++;
                break;
            case '<':
                if ( codigo.getSEGCH( i+j+1 ) == '=' ) {
                    op = BoolComp.MENOR_OU_IGUAL;
                    j++;
                } else {
                    op = BoolComp.MENOR;
                }
                j++;
                break;
            default:
                int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.INSTANCIADE );
                if ( cont == 0 )
                    return new InterResult();
                
                op = BoolComp.INSTANCIADE;
                j += cont;                
                break;
                
        }

        j += contUtil.contaEsps( codigo, i+j );

        boolean notExp2 = false;
        
        if ( codigo.getSEGCH( i+j ) == '!' ) {
            notExp2 = true;
            j++;            
            j += contUtil.contaEsps( codigo, i+j );
        }

        Exp exp1 = (Exp)ir1.getInstrucaoOuExp();
        Exp exp2;
        if ( op == BoolComp.INSTANCIADE ) {
            int cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NOME_ESPERADO );
                return new InterResult( erro );
            }

            String classeNome = codigo.getCodigo().substring( i+j, i+j+cont );
            StrValor exp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i, no, codigo );
            exp.setValor( new StringVar( classeNome ) );
            
            j += cont;
            
            exp2 = exp;
        } else {
            InterResult ir2 = manager.getBoolValorInter().interpreta( bc, base, aplic, codigo, i+j, i2 );
            if ( ir2.getJ() == 0 ) 
                ir2 = manager.getNaoBoolExpValorInter().interpreta( bc, base, aplic, codigo, i+j, i2 );
            
            if ( ir2.getJ() == 0 )
                return ir2;

            j += ir2.getJ();

            exp2 = (Exp)ir2.getInstrucaoOuExp();        
        }
                
        bc.setExp1( exp1 );
        bc.setExp2( exp2 );
        bc.setOperador( op ); 
        bc.setNotExp1( notExp1 );
        bc.setNotExp2( notExp2 ); 
                        
        return new InterResult( bc, j );
    }
        
}
