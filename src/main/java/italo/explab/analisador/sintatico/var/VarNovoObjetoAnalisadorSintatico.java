package italo.explab.analisador.sintatico.var;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class VarNovoObjetoAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public VarNovoObjetoAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0; 
        
        AnaliseResult result = manager.getNovoObjetoAnalisador().analisa( codigo, i ); 
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        
        if( codigo.getSEGCH( i+j ) == '.' ) {
            j++;
            result = manager.getChamadaFuncAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 ) {                
                int cont = manager.getContUtil().contaVarNomeTam( codigo, i+j );
                if ( cont == 0 ) {                    
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
        }
        
        return new AnaliseResult( j );
    }
}
