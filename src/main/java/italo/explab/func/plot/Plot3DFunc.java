package italo.explab.func.plot;

import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.AbstractFunc;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.func.plot.build.g3d.PlanoCartesiano3DBuilder;
import italo.explab.func.plot.obj.ExpLabGradeConfig;
import italo.explab.func.plot.obj.ExpLabJanelaConfig;
import italo.explab.func.plot.obj.g3d.ExpLabPlanoCartesiano3D;
import italo.explab.func.plot.obj.g3d.dados.ExpLabSuperficieDados3D;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class Plot3DFunc extends AbstractFunc {

    private final String NOME = FuncFactory.PLOT3D;
    private final int QUANT_PARAMS = -1;    
    
    private final PlanoCartesiano3DBuilder pc3DBuilder = new PlanoCartesiano3DBuilder();
    
    @Override
    public FuncResult exec( FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params ) {                        
                        
        if ( params.length != 3 && ( params.length < 1 || params.length > 3 ) )
            return new FuncResult( new CodigoErro( this.getClass(), codigo, fno.getI(), ErroMSGs.PARAMS_QUANT_INVALIDA, "1 ou 3" ) );
                
        ClasseUtil classeUtil = executor.getAplic().getClasseUtil();
        try {
            if ( params.length == 1 ) {
                Var var = params[0].getVar();
                
                ExpLabPlanoCartesiano3D pc = pc3DBuilder.build( fno, executor, codigo, var, classeUtil );
                
                executor.getAplic().getPlotador().plot3d( pc ); 
            } else {
                Var v1 = params[0].getVar();
                Var v2 = params[1].getVar();
                Var v3 = params[2].getVar();
                                
                if ( v1.getTipo() != Var.MATRIZ )
                    throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, "parametro 1", "array", v1.getStringTipo() );
                if ( v2.getTipo() != Var.MATRIZ )
                    throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, "parametro 2", "array", v2.getStringTipo() );                                

                if ( v3.getTipo() == Var.MATRIZ ) {
                    if ( ((MatrizVar)v3).getNC() != ((MatrizVar)v1).getNC() ) {
                        throw new ClasseUtilException( ErroMSGs.PLOT3D_XVET_ZMAT_DIMS_INCOMPATIVEIS, "parametro 1", "parametro 3" );
                    } else if ( ((MatrizVar)v3).getNL() != ((MatrizVar)v2).getNC() ) {
                        throw new ClasseUtilException( ErroMSGs.PLOT3D_YVET_ZMAT_DIMS_INCOMPATIVEIS, "parametro 2", "parametro 3" );
                    }                       
                } else {
                    throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, "parametro 3", "matriz", v3.getStringTipo() );                                
                }                

                MatrizVar xvetor = (MatrizVar)v1;
                MatrizVar yvetor = (MatrizVar)v2;
                MatrizVar zmatriz = (MatrizVar)v3;

                double[] vx = new double[ xvetor.getNC() ];
                double[] vy = new double[ yvetor.getNC() ];
                for( int k = 0; k < vx.length; k++ ) {
                    Var x = xvetor.getElemento( 0, k );
                    Var y = yvetor.getElemento( 0, k );
                    vx[ k ] = classeUtil.realValor(x, new VariavelNome( "parametro 1 ("+k+")" ) ).getValor();
                    vy[ k ] = classeUtil.realValor(y, new VariavelNome( "parametro 2 ("+k+")" ) ).getValor();
                }

                double[][] matz = new double[ zmatriz.getNL() ][ zmatriz.getNC() ];
                for( int k = 0; k < matz.length; k++ ) {
                    for( int j = 0; j < matz[k].length; j++ ) {
                        Var z = zmatriz.getElemento( k, j );
                        matz[ k ][ j ] = classeUtil.realValor(z, new VariavelNome( "parametro 3 ("+k+","+j+")" ) ).getValor();
                    }
                }

                ExpLabPlanoCartesiano3D pc = new ExpLabPlanoCartesiano3D();
                pc.setJanelaConfig( new ExpLabJanelaConfig() );
                pc.setGradeConfig( new ExpLabGradeConfig() );                

                ExpLabSuperficieDados3D dados3d = new ExpLabSuperficieDados3D();
                dados3d.setVetorX( vx );
                dados3d.setVetorY( vy );
                dados3d.setMatrizZ( matz ); 

                pc.addGrafico( dados3d );          
                   
                executor.getAplic().getPlotador().plot3d( pc ); 
            }            
            return new FuncResult( this );
        } catch ( ClasseUtilException e ) {
            CodigoErro erro = classeUtil.geraErro( codigo, e );
            return new FuncResult( erro );
        }
    }   
    
    @Override
    public Func nova() {
        return new Plot3DFunc();
    }
    
    @Override
    public int getQuantParametros() {
        return QUANT_PARAMS;
    }
    
    @Override
    public String getNome() {
        return NOME;
    }
        
}


