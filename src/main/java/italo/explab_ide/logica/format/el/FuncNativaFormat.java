package italo.explab_ide.logica.format.el;

import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.format.CodigoFonteFormatConfig;
import java.util.List;
import javax.swing.text.AttributeSet;

public class FuncNativaFormat implements ValorFormat {

    private final ExpLabIDEAplic aplic;

    public FuncNativaFormat(ExpLabIDEAplic aplic) {
        this.aplic = aplic;
    }
    
    @Override
    public AttributeSet getStyle(CodigoFonteFormatConfig cfg) {
        return cfg.getFuncNativaStyle();
    }

    @Override
    public AnaliseResult processa( Codigo codigo, int i ) {        
        List<Func> funcs = aplic.getGlobalRecursoManager().getGlobalFuncLista().getFuncs();
    
        for( Func f : funcs ) {
            int cont = aplic.getContadorUtil().contaTextoValor( codigo, i, f.getNome() );
            if ( cont > 0 ) {
                if ( i > 0 ) {
                    char ch = codigo.getSEGCH( i-1 );                
                    if ( Character.isLetterOrDigit( ch ) || ch == '_' )
                        return new AnaliseResult();                    
                }
                char ch = codigo.getSEGCH( i+cont );
                if ( Character.isLetterOrDigit( ch ) || ch == '_' )
                    return new AnaliseResult();
                
                int cont2 = aplic.getContadorUtil().contaEsps( codigo, i+cont );
                ch = codigo.getSEGCH( i+cont+cont2 );
                if ( ch == '(' )
                    return new AnaliseResult( cont );                   

                return new AnaliseResult();
            }
        }
        
        return new AnaliseResult();
    }
    
}
