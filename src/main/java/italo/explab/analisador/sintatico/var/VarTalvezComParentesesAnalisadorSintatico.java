package italo.explab.analisador.sintatico.var;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class VarTalvezComParentesesAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public VarTalvezComParentesesAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        
        int k = 0;
        
        char ch = codigo.getSEGCH( i+j );        
        while( codigo.isCHValido( i+j ) && ch == '(' ) {
            k++;
            
            j++;
            j += manager.getContUtil().contaEsps( codigo, i+j );
            ch = codigo.getSEGCH( i+j );
        }
                
        if ( !codigo.isCHValido( i+j ) )
            return new AnaliseResult();
                
        AnaliseResult result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;

        j += result.getJ();

        while( codigo.isCHValido( i+j ) && k > 0 ) {
            j += manager.getContUtil().contaEsps( codigo, i+j );

            if ( codigo.getSEGCH( i+j ) == ')' ) {
                k--;                    
                j++;                    
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
                return new AnaliseResult( erro );
            }
        } 

        if ( k > 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
            return new AnaliseResult( erro );
        }

        return new AnaliseResult( j );    
    }
    
}
