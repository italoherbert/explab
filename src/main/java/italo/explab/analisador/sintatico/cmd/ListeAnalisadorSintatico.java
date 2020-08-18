package italo.explab.analisador.sintatico.cmd;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class ListeAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public ListeAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;        
            
        j += manager.getContUtil().contaEsps( codigo, i+j );
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.LISTE );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        int jj = manager.getContUtil().contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < codigo.getCodlen() )
            return new AnaliseResult();
                
        j += jj;                
        
        cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC );
        if ( cont == 0 )
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.VAR );
        if ( cont == 0 )
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CLASSE );                
        if ( cont == 0 )
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CMD );                
        
        boolean tipoInformado = cont > 0;
        
        if ( cont == 0 )
            cont = manager.getContUtil().contaClasseOuPacoteNomeTam( codigo, i+j );                               
        
        if ( cont == 0  ) {
            String erroParam = PalavrasReservadas.FUNC + ", " + 
                        PalavrasReservadas.VAR + ", " + 
                        PalavrasReservadas.CLASSE + ", " +
                        PalavrasReservadas.CMD + " ou nome de classe";                                
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRAS_RESERVADAS_ESPERADAS, erroParam );
            return new AnaliseResult( erro );
        }
            
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
                        
        if ( !tipoInformado ) {            
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC );
            if ( cont == 0 )
                cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CONSTRUTOR );
            
            if ( cont > 0 ) {
                tipoInformado = true;
                j += cont;  
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CMD_TIPO_LISTAGEM_INVALIDO ); 
                return new AnaliseResult( erro );
            }
        } 
        
        if ( !tipoInformado ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CMD_TIPO_LISTAGEM_ESPERADO );
            return new AnaliseResult( erro );
        }
        
        j += manager.getContUtil().contaEsps( codigo, i+j );
        cont = manager.getContUtil().contaVarNomeTam( codigo, i+j );
        if ( cont == 0 )
            cont = manager.getContUtil().contaClasseOuPacoteNomeTam( codigo, i+j );
        
        if ( cont > 0 )
            j += cont;                
                            
        return new AnaliseResult( j );
    }
    
}
