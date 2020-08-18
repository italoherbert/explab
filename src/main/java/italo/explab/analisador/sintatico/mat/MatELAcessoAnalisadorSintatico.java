package italo.explab.analisador.sintatico.mat;

import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public class MatELAcessoAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager manager;

    public MatELAcessoAnalisadorSintatico(AnalisadorSintaticoManager manager) {
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
        
        cont = manager.getContUtil().contaVarNomeTam( codigo, i+j );
        if ( cont > 0 ) {
            j += cont;
            j += manager.getContUtil().contaEsps( codigo, i+j );
                        
            AnaliseResult aresult = manager.getMatIndicesAnalisador().analisa( codigo, i+j );
            if ( aresult.getJ() > 0 ) {
                j += aresult.getJ();            
                
                int k = j;
                k += manager.getContUtil().contaEsps( codigo, i+k );
                if ( codigo.getSEGCH( i+k ) == '\'' )
                    j = k+1;                
                
                return new AnaliseResult( j );
            }
        }
                
        return new AnaliseResult();
    }    
    
}
