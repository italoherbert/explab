package italo.explab.analisador.sintatico.var;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class VarOuChamadaFuncAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;
    
    public VarOuChamadaFuncAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        
        boolean este = false;
              
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
        
        while( codigo.isCHValido( i+j ) ) {                        
            AnaliseResult result = manager.getChamadaFuncAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 ) {                
                cont = manager.getContUtil().contaVarNomeTam( codigo, i+j );
                if ( cont == 0 ) {
                    if ( este )
                        return new AnaliseResult( j );                    
                    return new AnaliseResult();
                } else {
                    String varnome = codigo.getCodigo().substring( i+j, i+j+cont );

                    for( String pr : PalavrasReservadas.PALAVRAS_RESERVADAS )
                        if ( varnome.equalsIgnoreCase( pr ) )
                            return new AnaliseResult();                                            
                    j += cont;
                }                
            } else {
                j += result.getJ();
            }
                        
            if ( codigo.getSEGCH( i+j ) == '(' ) {
                result = manager.getMatIndicesAnalisador().analisa( codigo, i+j );
                if ( result.getJ() == 0 )
                    return result;
                j += result.getJ();
            }
                        
            if( codigo.getSEGCH( i+j ) == '.' ) {
                j++;                                  
            } else {                
                return new AnaliseResult( j );
            }
        }
        return new AnaliseResult(); 
    }
    
}
