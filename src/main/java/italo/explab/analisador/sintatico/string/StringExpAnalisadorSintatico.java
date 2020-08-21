package italo.explab.analisador.sintatico.string;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public class StringExpAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager manager;

    public StringExpAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }

    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = manager.getContUtil().contaEsps( codigo, i );
        
        boolean temIniStr = false;
        char ch = codigo.getSEGCH( i+j );
        if ( ch == '\"' || ch == '\'' ) {
            AnaliseResult result = manager.getStringAnalisador().analisa( codigo, i+j );
            if ( result.getJ() != 0 ) {                
                temIniStr = true;
                j += result.getJ();
            }            
        } else {
            AnaliseResult result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j );
            if ( result.getJ() != 0 ) {
                temIniStr = true;
                j += result.getJ();
            }
        }
        
        if ( temIniStr ) {
            int k = j;
                        
            j += manager.getContUtil().contaEsps( codigo, i+j );
            if ( codigo.getSEGCH( i+j ) == '+' ) {
                j++;
                j += manager.getContUtil().contaEsps( codigo, i+j );

                AnaliseResult result = manager.getTalvezSemStrIniStringExpAnalisador().analisa( codigo, i+j );
                if ( result.getJ() == 0 )
                    return result;
                
                j += result.getJ();
                
                return new AnaliseResult( j );
            }
            
            return new AnaliseResult( k );
        }           

        return new AnaliseResult();        
    }
    
}
