package italo.explab_ide.logica.format.el;

import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.recursos.var.Variavel;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.format.CodigoFonteFormatConfig;
import java.util.List;
import javax.swing.text.AttributeSet;

public class VarNativaFormat  implements ValorFormat {

    private final ExpLabIDEAplic aplic;

    public VarNativaFormat(ExpLabIDEAplic aplic) {
        this.aplic = aplic;
    }
    
    @Override
    public AttributeSet getStyle(CodigoFonteFormatConfig cfg) {
        return cfg.getVarNativaStyle();
    }

    @Override
    public AnaliseResult processa( Codigo codigo, int i) {
        List<Variavel> vars = aplic.getGlobalRecursoManager().getGlobalVarLista().getVariaveis();
    
        for( Variavel var : vars ) {
            int cont = aplic.getContadorUtil().contaTextoValor( codigo, i, var.getNome() );
            if ( cont > 0 ) {
                if ( i > 0 ) {
                    char ch = codigo.getSEGCH( i-1 );                
                    if ( Character.isLetterOrDigit( ch ) || ch == '_' )
                        return new AnaliseResult();                    
                }
                char ch = codigo.getSEGCH( i+cont );
                if ( Character.isLetterOrDigit( ch ) || ch == '_' )
                    return new AnaliseResult();
                
                return new AnaliseResult( cont );
            }
        }
        
        return new AnaliseResult();
    }
    
}
