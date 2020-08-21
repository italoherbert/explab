package italo.explab.analisador.sintatico.classe;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class NovoObjetoAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public NovoObjetoAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;                
        
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.NOVO );
        if ( cont == 0 ) {
            return new AnaliseResult();
        }
         
        j += cont;        
        j += manager.getContUtil().contaEsps( codigo, i+j );            
        
        cont = manager.getContUtil().contaClasseOuPacoteNomeTam( codigo, i+j );
        if ( cont == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NOME_ESPERADO );
            return new AnaliseResult( erro );
        }                
        
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        AnaliseResult result = manager.getParametrosAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 ) {
            if ( result.getErro() != null ) {
                return result;
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_PARENTESES_ESPERADO );
                return new AnaliseResult( erro );
            }
        }
        
        j += result.getJ();
        
        return new AnaliseResult( j );
    }
    
}
