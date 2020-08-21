package italo.explab.analisador.sintatico.construtor;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public abstract class AbstractChamadaConstrutorAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public AbstractChamadaConstrutorAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
    
    protected abstract String getPalavraReservada();
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, this.getPalavraReservada() );
        if ( cont == 0 )
            return new AnaliseResult();        
        
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        AnaliseResult result = manager.getParametrosAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        
        return new AnaliseResult( j );
    }
    
}
