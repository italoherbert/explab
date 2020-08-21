package italo.explab.func.plot.build;

import italo.explab.func.plot.obj.ExpLabLinhaConfig;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import java.awt.Color;

public class LinhaConfigBuilder {
    
    public ExpLabLinhaConfig build( RecursoManager dadosObjRecursos, VariavelNome dadosVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        ExpLabLinhaConfig linhaConfig = new ExpLabLinhaConfig();
        
        VariavelNome linhasConfigVN = new VariavelNome( "linha", dadosVN );
        Objeto linhaObj = classeUtil.buscaObjeto( dadosObjRecursos, "LinhaConfig", linhasConfigVN );
        if ( linhaObj != null ) {
            RecursoManager linhaObjRecursos = linhaObj.getRecursos();
            BooleanVar visivel = classeUtil.booleanValor( linhaObjRecursos, new VariavelNome( "visivel", linhasConfigVN ) );
            BooleanVar gradiente = classeUtil.booleanValor( linhaObjRecursos, new VariavelNome( "visivel", linhasConfigVN ) );
            BooleanVar iluminar = classeUtil.booleanValor( linhaObjRecursos, new VariavelNome( "visivel", linhasConfigVN ) );
            Color linhasCor = classeUtil.corValor( linhaObjRecursos, new VariavelNome( "cor", linhasConfigVN ) );

            if ( visivel != null )
                linhaConfig.setVisivel( visivel.getValor() );
            if ( gradiente != null )
                linhaConfig.setPreenchimentoGradiente( gradiente.getValor() );
            if ( iluminar != null )
                linhaConfig.setIluminar( iluminar.getValor() );
            linhaConfig.setCor( linhasCor );
        }  
        
        return linhaConfig;
    }
    
}
