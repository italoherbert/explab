package italo.explab.inter.cmd;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.cmd.node.Exiba;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import italo.explab.var.StringVar;

public abstract class AbstractExibaCMDInter extends Inter {

    protected abstract String getPalavraChave();
    
    protected abstract boolean isPularLinha();
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                                
        ContadorUtil contUtil = aplic.getContUtil();
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
                                 
        int cont = contUtil.contaTextoValor( codigo, i, this.getPalavraChave() );
        if ( cont == 0 )
            return new InterResult();
                                             
        InterManager manager = aplic.getInterManager();
                
        int j = cont;
        
        int jj = contUtil.contaEsps( codigo, i+j );        
        char ch = codigo.getSEGCH( i+j );
        if ( jj == 0 && i+j < i2 && ch != ';' )
            return new InterResult();
                
        j += jj;
                        
        Exiba exiba = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoExiba( i, no, codigo );
        
        if ( ch != ';' ) {
            AnaliseResult strAResult = asManager.getTalvezSemStrIniStringExpAnalisador().analisa( codigo, i+j );
            AnaliseResult valorResult = asManager.getValorAnalisador().analisa( codigo, i+j );            
            
            InterResult result;
            if ( strAResult.getJ() <= valorResult.getJ() ) {                
                result = manager.getValorInter().interpreta( exiba, no, aplic, codigo, i+j, i2 );
                if ( result.getJ() == 0 ) {
                    if ( result.getErro() != null )
                        return result;

                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
                    return new InterResult( erro );  
                }   
                j += result.getJ();
            } else {
                result = (InterResult)manager.getStringExpInter().interpreta( exiba, no, aplic, codigo, i+j, i2 );
                if ( result.getJ() == 0 ) {
                    if ( result.getErro() != null )
                            return result;
                    
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.EXP_STRING_TALVEZ_INI_STR_ESPERADA );
                    return new InterResult( erro ); 
                }
                                                    
                j += result.getJ();
            }
            
            exiba.setExp( (Exp)result.getInstrucaoOuExp() ); 
        } else {
            StrValor strValor = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i, exiba, codigo );
            strValor.setValor( new StringVar( "" ) ); 
            
            exiba.setExp( strValor ); 
        }
                
        exiba.setPularLinha( this.isPularLinha() );
        
        return new InterResult( exiba, j );
    }
        
}
