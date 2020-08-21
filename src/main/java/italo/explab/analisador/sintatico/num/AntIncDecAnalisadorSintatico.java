package italo.explab.analisador.sintatico.num;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public class AntIncDecAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public AntIncDecAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        boolean incDecOpEncontrado = false;
        if ( codigo.getSEGCH( i+j ) == '-' && codigo.getSEGCH( i+j+1 ) == '-' ) {
            incDecOpEncontrado = true;
        } else if ( codigo.getSEGCH( i+j ) == '+' && codigo.getSEGCH( i+j+1 ) == '+' ) {
            incDecOpEncontrado = true;
        }
        
        if ( !incDecOpEncontrado )
            return new AnaliseResult();

        j += 2;
        AnaliseResult result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;

        j += result.getJ();
        return new AnaliseResult( j );               
    }
    
}
