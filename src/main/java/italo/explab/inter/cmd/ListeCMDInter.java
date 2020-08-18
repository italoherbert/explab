package italo.explab.inter.cmd;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.cmd.node.Liste;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import italo.explab.var.StringVar;

public class ListeCMDInter extends Inter {
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        ContadorUtil contUtil = aplic.getContUtil();        
        
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.LISTE );
        if ( cont == 0 )
            return new InterResult();                

        Liste liste = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoListe( i, no, codigo );
                  
        String classeNome = null;
        String tipo = null;
        String termoBusca = null;
        
        int j = cont;
        
        int jj = contUtil.contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < i2 )
            return new InterResult();
                
        j += jj;
        
        int classeJ = j;
        
        cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC );
        if ( cont == 0 )
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.VAR );
        if ( cont == 0 )
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CLASSE );
        if ( cont == 0 )
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CMD );
                
        if ( cont > 0 ) {            
            tipo = codigo.getCodigo().substring( i+j, i+j+cont );
        } else {
            cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0  ) {
                String erroParam = PalavrasReservadas.FUNC + ", " + 
                        PalavrasReservadas.VAR + ", " + 
                        PalavrasReservadas.CLASSE + ", " +
                        PalavrasReservadas.CMD + " ou nome de classe";                
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRAS_RESERVADAS_ESPERADAS, erroParam );
                return new InterResult( erro );
            }
            classeNome = codigo.getCodigo().substring( i+j, i+j+cont );
        }
                    
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
                
        int classeTipoListagemJ = j;
        
        if ( tipo == null ) {            
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC );
            if ( cont == 0 )
                cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CONSTRUTOR );
            
            if ( cont > 0 ) {
                tipo = codigo.getCodigo().substring( i+j, i+j+cont );
                j += cont;  
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CMD_TIPO_LISTAGEM_INVALIDO ); 
                return new InterResult( erro );
            }
        }
        
        j += contUtil.contaEsps( codigo, i+j );
        cont = contUtil.contaVarNomeTam( codigo, i+j );
        if ( cont == 0 )
            cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
        
        if ( cont > 0 ) {                         
            termoBusca = codigo.getCodigo().substring( i+j, i+j+cont ); 
            j += cont;
        }
        
        StrValor classeNomeExp = null;
        if ( classeNome != null ) {
            classeNomeExp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i+classeJ, liste, codigo );            
            classeNomeExp.setValor( new StringVar( classeNome ) ); 
        }
        
        StrValor tipoExp = null;
        if ( tipo != null ) {
            tipoExp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i+classeTipoListagemJ, liste, codigo );            
            tipoExp.setValor( new StringVar( tipo ) ); 
        }
        
        StrValor termoBuscaExp = null;
        if ( termoBusca != null ) {            
            termoBuscaExp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i+classeJ, liste, codigo );            
            termoBuscaExp.setValor( new StringVar( termoBusca ) );
        }
        
        liste.setTipoExp( tipoExp );
        liste.setClasseNomeExp( classeNomeExp );
        liste.setTermoExp( termoBuscaExp );         
        
        return new InterResult( liste, j );
    }
    
}
