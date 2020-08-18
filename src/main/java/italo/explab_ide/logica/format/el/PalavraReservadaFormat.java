package italo.explab_ide.logica.format.el;

import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
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
        for( String palavra : PalavrasReservadas.PALAVRAS_RESERVADAS ) {
            int cont = aplic.getContadorUtil().contaTextoValor( codigo, i, palavra );
            if ( cont > 0 ) {
                boolean ok = ( i == 0 ) ;
                if ( !ok ) {
                    char ch = codigo.getSEGCH( i-1 );
                    switch( ch ) {
                        case '.':
                        case ';':
                        case ' ':
                        case '\n':
                        case '\r':
                        case '\t':                            
                            ok = true;
                            break;
                    }
                }
                
                if ( ok ) {
                    char ch = codigo.getSEGCH( i+cont );
                    switch ( ch ) {
                        case '.':
                        case ';':
                        case '(':
                        case ' ':
                        case '\n':
                        case '\r':
                        case '\t':
                        case '<':
                            return new AnaliseResult( cont );                                           
                    }
                }
            }
        }
        return new AnaliseResult();
    }
    
}
