package italo.explab_ide.logica.format.el;

import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
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
    
        String nome = null;
        int cont = 0;
        for( Variavel v : vars ) {
            int c = aplic.getContadorUtil().contaTextoValor( codigo, i, v.getNome() );
            if ( c > 0 ) {
                if ( nome == null ) {
                    nome = v.getNome();
                    cont = c;
                } else if ( v.getNome().length() > nome.length() ) {
                    nome = v.getNome();
                    cont = c;
                }                    
            }
        }
        
        if ( nome != null ) {
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
        
        return new AnaliseResult();
    }
    
}
