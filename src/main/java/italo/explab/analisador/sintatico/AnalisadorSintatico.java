package italo.explab.analisador.sintatico;

import italo.explab.codigo.Codigo;


public interface AnalisadorSintatico {
    
    public AnaliseResult analisa( Codigo codigo, int i );
        
}
