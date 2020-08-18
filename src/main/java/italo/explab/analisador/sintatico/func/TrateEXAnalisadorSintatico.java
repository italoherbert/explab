package italo.explab.analisador.sintatico.func;

import italo.explab.ErroMSGs;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class TrateEXAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public TrateEXAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.TRATAR );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        
        boolean fim = false;
        while( !fim && codigo.isCHValido( i+j ) ) {
            j += manager.getContUtil().contaEsps( codigo, i+j );                        
                                    
            cont = manager.getContUtil().contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_DE_EXCECAO_ESPERADA );
                return new AnaliseResult( erro );
            }
            
            j += cont;
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            char ch = codigo.getSEGCH( i+j );
            if ( ch == '{' || ch == ',' ) {                                 
                if ( ch == '{' ) {
                    fim = true;                
                } else {
                    j++; 
                }
            }            
        }
        
        if ( fim )
            return new AnaliseResult( j );
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO );
        return new AnaliseResult( erro );
    }
}
