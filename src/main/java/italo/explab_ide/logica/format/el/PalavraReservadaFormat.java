package italo.explab_ide.logica.format.el;

import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.recursos.var.Variavel;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.format.CodigoFonteFormatConfig;
import javax.swing.text.AttributeSet;

public class PalavraReservadaFormat implements ValorFormat {

    private final ExpLabIDEAplic aplic;

    public PalavraReservadaFormat(ExpLabIDEAplic aplic) {
        this.aplic = aplic;
    }
    
    @Override
    public AttributeSet getStyle( CodigoFonteFormatConfig cfg ) {
        return cfg.getPalavraReservadaStyle();
    }

    @Override
    public AnaliseResult processa( Codigo codigo, int i ) {
        String palavra = null;
        int cont = 0;
        for( String p : PalavrasReservadas.PALAVRAS_RESERVADAS ) {
            int c = aplic.getContadorUtil().contaTextoValor( codigo, i, p );
            if ( c > 0 ) {
                if ( palavra == null ) {
                    palavra = p;
                    cont = c;
                } else if ( p.length() > palavra.length() ) {
                    palavra = p;
                    cont = c;
                }                    
            }
        }
         
        if ( palavra != null ) {
            boolean ok = ( i == 0 ) ;
            if ( !ok ) {
                char ch = codigo.getSEGCH( i-1 );
                if ( !Character.isLetterOrDigit( ch ) )                                             
                    ok = true;                         
            }

            if ( ok ) {
                char ch = codigo.getSEGCH( i+cont );
                if ( !Character.isLetterOrDigit( ch ) )
                    return new AnaliseResult( cont );                        
            }            
        }
        return new AnaliseResult();
    }
    
}
