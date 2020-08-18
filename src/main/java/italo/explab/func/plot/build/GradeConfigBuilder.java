package italo.explab.func.plot.build;

import italo.explab.func.plot.obj.ExpLabGradeConfig;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import java.awt.Color;

public class GradeConfigBuilder {
    
    public ExpLabGradeConfig build( RecursoManager pcObjRecursos, VariavelNome pcObjVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        ExpLabGradeConfig gradeConfig = new ExpLabGradeConfig();
                
        VariavelNome gradeVN = new VariavelNome( "grade", pcObjVN );
                    
        Objeto gradeObj = classeUtil.buscaObjeto( pcObjRecursos, "GradeConfig", gradeVN );
        if ( gradeObj != null ) {
            RecursoManager gradeRecursos = gradeObj.getRecursos();

            BooleanVar gradeVisivelVar = classeUtil.booleanValor( gradeRecursos, new VariavelNome( "visivel", gradeVN ) );
            Color gradeCor = classeUtil.corValor( gradeRecursos, new VariavelNome( "cor", gradeVN ) );

            if ( gradeVisivelVar != null )
                gradeConfig.setGradeVisivel( gradeVisivelVar.getValor() );
            gradeConfig.setGradeCor( gradeCor );
        }
        
        return gradeConfig;
    }
    
}
