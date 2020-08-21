package italo.explab.inter.cmd;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.InterLeitura;
import italo.explab.InterSessaoManager;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.bool.node.BoolValor;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.cmd.node.Leia;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import italo.explab.arvore.cmd.node.leia.LeiaListener;
import italo.explab.arvore.cmd.node.leia.LeiaResult;
import italo.explab.var.BooleanVar;
import italo.explab.var.StringVar;

public class LeiaLNCMDInter extends Inter implements LeiaListener { 
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                                        
        ContadorUtil contUtil = aplic.getContUtil();
        
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.LEIALN );
        if ( cont == 0 )
            return new InterResult();
 
        Leia leia = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoLeia( i, no, codigo );
               
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();                 
       
        String varnome;
        boolean tipoString = false;
        
        int j = cont;
        
        int jj = contUtil.contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < i2 )
            return new InterResult();
                
        j += jj;
        
        int varJ = j;
                                
        AnaliseResult result = asManager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return new InterResult( result );        
        
        cont = result.getJ();
        
        varnome = codigo.getCodigo().substring( i+j, i+j+cont );
                        
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );                
                    
        int tipoStrJ = j;
        
        char ch = codigo.getSEGCH( i+j );
        if ( ch == ':' ) {
            j++;
            j += contUtil.contaEsps( codigo, i+j );
            
            tipoStrJ = j;
            
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.STRING );
            if ( cont > 0 ) {                                                            
                tipoString = true;                                
                j += cont;
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRA_RESERVADA_ESPERADA, PalavrasReservadas.STRING );
                return new InterResult( erro );
            }
        }        

        StrValor varnomeExp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i+varJ, leia, codigo );
        varnomeExp.setValor( new StringVar( varnome ) );  
        
        BoolValor tipoStringExp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoBoolValor( i+tipoStrJ, leia, codigo );
        tipoStringExp.setValor( new BooleanVar( tipoString ) ); 
        
        leia.setVarNomeExp( varnomeExp );
        leia.setEhTipoStringExp( tipoStringExp );
        leia.setLeiaListener( this );        
        
        return new InterResult( leia, j );                        
    }

    @Override
    public LeiaResult execLeitura( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, boolean tipoString ) {
        InterManager manager = aplic.getInterManager();
        InterLeitura interLeitura = aplic.getILeitura();
        InterSessaoManager sessaoManager = aplic.getSessaoManager();                       
                
        if ( !sessaoManager.isFim() ) {            
            String valorLido = interLeitura.getTextoLido();
            
            if ( tipoString ) {
                if ( valorLido.endsWith( "\n" ) )
                    valorLido = valorLido.substring( 0, valorLido.length()-1 );
                
                valorLido = "\"" + valorLido.replaceAll( "\"", "\\\\\"" ) + "\"";                                   
            }
            
            Codigo valorCodigo = new Codigo( aplic, valorLido );
            int len = valorLido.length();
                        
            InterResult valorResult = manager.getValorInter().interpreta( no, base, aplic, valorCodigo, 0, len );
            if ( valorResult.getJ() == 0 ) {
                if ( valorResult.getErro() != null ) {
                    return new LeiaResult( valorResult.getErro() );
                } else {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VALOR_INVALIDO );
                    return new LeiaResult( erro );
                }
            } else {                
                Exp exp = (Exp)valorResult.getInstrucaoOuExp();
                return new LeiaResult( exp );
            }
        }

        return new LeiaResult();
    }
          
}
