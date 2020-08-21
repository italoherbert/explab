package italo.explab.analisador.sintatico.valor;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public abstract class AbstractValorAnalisadorSintatico implements AnalisadorSintatico {

    protected final AnalisadorSintaticoManager manager;
    
    public AbstractValorAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
     
    protected abstract AnaliseResult analisaEXPs( Codigo codigo, int i );
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {        
        AnaliseResult result = manager.getLeituraVarAnalisador().analisa( codigo, i );                              
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
        
        result = this.analisaEXPs( codigo, i );
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
                
        result = manager.getMatAnalisador().analisa( codigo, i );                                     
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
     
        result = manager.getLeituraFuncValorAnalisador().analisa( codigo, i );
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
        
        result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i ); 
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;    
                 
        result = manager.getVarNovoObjetoAnalisador().analisa( codigo, i ); 
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
        
        int j = manager.getContUtil().contaEsps( codigo, i );
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.NULL );
        if ( cont > 0 )
            return new AnaliseResult( j+cont );                    
        
        cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.ESTE );
        if ( cont > 0 )
            if ( codigo.getSEGCH( i+j+cont ) != '.' )
                return new AnaliseResult( j+cont );                                                    
            
        return new AnaliseResult();        
    }
            
}
