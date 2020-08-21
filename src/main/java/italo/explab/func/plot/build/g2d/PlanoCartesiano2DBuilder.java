package italo.explab.func.plot.build.g2d;

import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.plot.build.GradeConfigBuilder;
import italo.explab.func.plot.build.JanelaConfigBuilder;
import italo.explab.func.plot.obj.g2d.ExpLabPlanoCartesiano2D;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class PlanoCartesiano2DBuilder {
        
    private final JanelaConfigBuilder janelaCFGBuilder = new JanelaConfigBuilder();
    private final GradeConfigBuilder gradeCFGBuilder = new GradeConfigBuilder();
    private final Dados2DBuilder dados2DCFGBuilder = new Dados2DBuilder();
    private final Func2DBuilder func2DCFGBuilder = new Func2DBuilder();
    
    public ExpLabPlanoCartesiano2D build( FuncExp fno, Executor executor, Codigo codigo, Var var, ClasseUtil classeUtil ) throws ClasseUtilException {
        ExpLabPlanoCartesiano2D pc = new ExpLabPlanoCartesiano2D();
                
        VariavelNome pc2dObjVN = new VariavelNome( "PC2D" );

        Objeto pc2DObj = classeUtil.buscaObjeto( var, "PC2D", pc2dObjVN );
        RecursoManager pcObjRecursos = pc2DObj.getRecursos();

        pc.setJanelaConfig( janelaCFGBuilder.build( pcObjRecursos, pc2dObjVN, classeUtil ) );
        pc.setGradeConfig( gradeCFGBuilder.build( pcObjRecursos, pc2dObjVN, classeUtil ) );

        StringVar tituloVar = classeUtil.stringValor( pcObjRecursos, new VariavelNome( "titulo", pc2dObjVN ) );
        StringVar xRotuloVar = classeUtil.stringValor( pcObjRecursos, new VariavelNome( "xrotulo", pc2dObjVN ) );
        StringVar yRotuloVar = classeUtil.stringValor( pcObjRecursos, new VariavelNome( "yrotulo", pc2dObjVN ) );

        if ( tituloVar != null )
            pc.setTitulo( tituloVar.getValor() );
        if ( xRotuloVar != null )
            pc.setXRotulo( xRotuloVar.getValor() );
        if ( yRotuloVar != null )
            pc.setYRotulo( yRotuloVar.getValor() );                

        VariavelNome graficosListaVN = new VariavelNome( "graficos", pc2dObjVN );
        MatrizVar graficosLista = classeUtil.arrayValor( pcObjRecursos, graficosListaVN );
        if ( graficosLista != null ) {
            for( int k = 0; k < graficosLista.getNC(); k++ ) {
                Var v = graficosLista.getElemento( 0, k );
                VariavelNome vn = new VariavelNome( "("+k+")", graficosListaVN );

                Objeto dadosObj = classeUtil.buscaObjeto( v, "Dados2D", vn, false );
                if ( dadosObj != null ) {
                    pc.addGrafico( dados2DCFGBuilder.build( dadosObj, vn, classeUtil ) );                    
                } else {
                    Objeto funcObj = classeUtil.buscaObjeto( v, "Func2D", vn, false );
                    if ( funcObj != null ) {
                        pc.addGrafico( func2DCFGBuilder.build( fno, executor, codigo, funcObj, vn, classeUtil ) ); 
                    } else {
                        String vc = classeUtil.variavelCompleta( vn );
                        String classeNome = "Dados2D ou Func2D";
                        throw new ClasseUtilException( ClasseUtilException.INSTANCIA_DE_UMA_DAS_CLASSES_ESPERADA, vc, classeNome );
                    }
                }                
            }
        }

        return pc;
                
    }
    
}
