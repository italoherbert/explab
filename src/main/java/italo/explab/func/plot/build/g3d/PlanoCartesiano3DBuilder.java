package italo.explab.func.plot.build.g3d;

import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.plot.build.GradeConfigBuilder;
import italo.explab.func.plot.build.JanelaConfigBuilder;
import italo.explab.func.plot.obj.g3d.ExpLabPlanoCartesiano3D;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.RealVar;

public class PlanoCartesiano3DBuilder {
    
    private final JanelaConfigBuilder janelaCFGBuilder = new JanelaConfigBuilder();
    private final GradeConfigBuilder aparenciaCFGBuilder = new GradeConfigBuilder();
    private final Dados3DBuilder dados3DBuilder = new Dados3DBuilder(); 
    private final Func3DBuilder func3DBuilder = new Func3DBuilder();
    private final PFunc3DBuilder pfunc3DBuilder = new PFunc3DBuilder();
    
    public ExpLabPlanoCartesiano3D build( FuncExp fno, Executor executor, Codigo codigo, Var var, ClasseUtil classeUtil ) throws ClasseUtilException {
        ExpLabPlanoCartesiano3D pc = new ExpLabPlanoCartesiano3D();
                
        VariavelNome pc3dObjVN = new VariavelNome( "PC3D" );

        Objeto pc3DObj = classeUtil.buscaObjeto( var, "PC3D", pc3dObjVN );
        RecursoManager pcObjRecursos = pc3DObj.getRecursos();

        pc.setJanelaConfig( janelaCFGBuilder.build( pcObjRecursos, pc3dObjVN, classeUtil ) );
        pc.setGradeConfig( aparenciaCFGBuilder.build( pcObjRecursos, pc3dObjVN, classeUtil ) );

        StringVar tituloVar = classeUtil.stringValor( pcObjRecursos, new VariavelNome( "titulo", pc3dObjVN ) );
        StringVar xRotuloVar = classeUtil.stringValor( pcObjRecursos, new VariavelNome( "xrotulo", pc3dObjVN ) );
        StringVar yRotuloVar = classeUtil.stringValor( pcObjRecursos, new VariavelNome( "yrotulo", pc3dObjVN ) );
        StringVar zRotuloVar = classeUtil.stringValor( pcObjRecursos, new VariavelNome( "zrotulo", pc3dObjVN ) );
        RealVar dxVar = classeUtil.realValor( pcObjRecursos, new VariavelNome( "dx", pc3dObjVN ), false );
        RealVar dyVar = classeUtil.realValor( pcObjRecursos, new VariavelNome( "dy", pc3dObjVN ), false );
        RealVar dzVar = classeUtil.realValor( pcObjRecursos, new VariavelNome( "dz", pc3dObjVN ), false );
                
        if ( tituloVar != null )
            pc.setTitulo( tituloVar.getValor() );
        if ( xRotuloVar != null )
            pc.setXRotulo( xRotuloVar.getValor() );
        if ( yRotuloVar != null )
            pc.setYRotulo( yRotuloVar.getValor() );                
        if ( zRotuloVar != null )
            pc.setZRotulo( zRotuloVar.getValor() );  
        if ( dxVar != null )
            pc.setDX( dxVar.getValor() );  
        if ( dyVar != null )
            pc.setDY( dyVar.getValor() );  
        if ( dzVar != null )
            pc.setDZ( dzVar.getValor() );  

        VariavelNome graficosListaVN = new VariavelNome( "graficos", pc3dObjVN );
        MatrizVar graficosLista = classeUtil.arrayValor(pcObjRecursos, graficosListaVN );
        if ( graficosLista != null ) {
            for( int k = 0; k < graficosLista.getNC(); k++ ) {
                Var v = graficosLista.getElemento( 0, k );
                VariavelNome vn = new VariavelNome( "("+k+")", graficosListaVN );
                
                Objeto dadosObj = classeUtil.buscaObjeto( v, "Dados3D", vn, false );
                if ( dadosObj != null ) {
                    pc.addGrafico( dados3DBuilder.build( dadosObj, vn, false, classeUtil ) );
                } else {
                    Objeto superficieObj = classeUtil.buscaObjeto( v, "Superficie3D", vn, false );
                    if ( superficieObj != null ) {       
                        pc.addGrafico( dados3DBuilder.build( superficieObj, vn, true, classeUtil ) );
                    } else {
                        Objeto funcObj = classeUtil.buscaObjeto( v, "Func3D", vn, false );
                        if ( funcObj != null ) {
                            pc.addGrafico( func3DBuilder.build( fno, executor, codigo, funcObj, vn, classeUtil ) );
                        } else {
                            Objeto pfuncObj = classeUtil.buscaObjeto( v, "PFunc3D", vn, false );
                            if ( pfuncObj != null ) {
                                pc.addGrafico( pfunc3DBuilder.build( fno, executor, codigo, pfuncObj, vn, classeUtil ) );
                            } else {
                                String vc = classeUtil.variavelCompleta( vn );
                                String classeNome = "Dados3D, Superficie3D, Func3D ou PFunc3D";
                                throw new ClasseUtilException( ClasseUtilException.INSTANCIA_DE_UMA_DAS_CLASSES_ESPERADA, vc, classeNome );
                            }
                        }
                    }                
                }
            }
        }

        
        return pc;
    }
    
}
