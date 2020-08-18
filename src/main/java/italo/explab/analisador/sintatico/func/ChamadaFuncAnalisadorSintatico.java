package italo.explab.analisador.sintatico.func;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class ChamadaFuncAnalisadorSintatico  implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public ChamadaFuncAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        
        int cont = manager.getContUtil().contaVarNomeTam( codigo, i+j );
        if ( cont > 0 ) {  
            String palavra = codigo.getCodigo().substring( i, i+cont );
            if ( palavra.equalsIgnoreCase( PalavrasReservadas.ESTE ) ) {
                j += cont;
                
                int k = j;
                
                j += manager.getContUtil().contaEsps( codigo, i+j );                
                if ( codigo.getSEGCH( i + j ) == '.' ) {
                    j++;  
                } else {
                    return new AnaliseResult( k );
                }
            } else if ( palavra.equalsIgnoreCase( PalavrasReservadas.SUPER ) ) {
                cont += manager.getContUtil().contaEsps( codigo, i+cont );
                if ( codigo.getSEGCH( i + cont ) == '.' )
                    j += cont + 1;                               
            }
        }        
        
        j += manager.getContUtil().contaEsps( codigo, i+j );                    
        cont = manager.getContUtil().contaVarNomeTam( codigo, i+j );
        if ( cont == 0 )
            return new AnaliseResult();        
            
        int fnomeJ = j;
        String fnome = codigo.getCodigo().substring( i+j, i+j+cont );
            
        j += cont;      
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        AnaliseResult result = manager.getParametrosAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;        
        
        j += result.getJ();
        
        for( String pr : PalavrasReservadas.PALAVRAS_RESERVADAS ) {
            if ( fnome.equalsIgnoreCase( pr ) ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+fnomeJ, ErroMSGs.EXISTE_COMO_PALAVRA_RESERVADA );
                return new AnaliseResult( erro );
            }
        }
                        
        return new AnaliseResult( j ); 
    }
    
}
