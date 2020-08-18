package italo.explab.analisador.sintatico.func;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class LeituraFuncAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public LeituraFuncAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {        
        int j = 0;        
        j+= manager.getContUtil().contaEsps( codigo, i+j );
        
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        cont = manager.getContUtil().contaVarNomeTam( codigo, i+j );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );

        AnaliseResult result = manager.getParametrosAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;

        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        result = manager.getTrateEXAnalisador().analisa( codigo, i+j );
        
        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );

        if ( codigo.getSEGCH( i+j ) != '{' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO );
            return new AnaliseResult( erro );
        }

        j++;

        cont = manager.getContUtil().contaBlocoCodigoTam( codigo, i+j );
        if ( cont == -1 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, codigo.getCodlen(), ErroMSGs.FECHA_CHAVES_ESPERADO );
            return new AnaliseResult( erro );
        }
        j += cont + 1;

        return new AnaliseResult( j );        
    }
    
}
