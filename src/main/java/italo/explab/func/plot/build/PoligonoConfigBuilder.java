package italo.explab.func.plot.build;

import italo.explab.func.plot.obj.ExpLabPoligonoConfig;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import java.awt.Color;

public class PoligonoConfigBuilder {
    
    public ExpLabPoligonoConfig build( RecursoManager dadosObjRecursos, VariavelNome dadosVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        ExpLabPoligonoConfig poligonoConfig = new ExpLabPoligonoConfig();
        
        VariavelNome poligonoConfigVN = new VariavelNome( "poligono", dadosVN );
        Objeto pontoObj = classeUtil.buscaObjeto(dadosObjRecursos, "PoligonoConfig", poligonoConfigVN );
        if ( pontoObj != null ) {
            RecursoManager poligonoObjRecursos = pontoObj.getRecursos();
            
            BooleanVar visivel = classeUtil.booleanValor( poligonoObjRecursos, new VariavelNome( "visivel", poligonoConfigVN ) );
            BooleanVar gradiente = classeUtil.booleanValor( poligonoObjRecursos, new VariavelNome( "gradiente", poligonoConfigVN ) );
            BooleanVar iluminar = classeUtil.booleanValor( poligonoObjRecursos, new VariavelNome( "iluminar", poligonoConfigVN ) );
            Color cor = classeUtil.corValor( poligonoObjRecursos, new VariavelNome( "cor", poligonoConfigVN ) );

            if ( visivel != null )
                poligonoConfig.setVisivel( visivel.getValor() );
            if ( gradiente != null )
                poligonoConfig.setPreenchimentoGradiente( gradiente.getValor() );
            if ( iluminar != null )
                poligonoConfig.setIluminar( iluminar.getValor() );
            
            poligonoConfig.setCor( cor );
        }

        return poligonoConfig;
    }
    
}
