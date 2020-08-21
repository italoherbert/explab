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
import italo.explab.func.plot.build.g2d.PlanoCartesiano2DBuilder;
import italo.explab.func.plot.obj.ExpLabGradeConfig;
import italo.explab.func.plot.obj.ExpLabJanelaConfig;
import italo.explab.func.plot.obj.ExpLabLinhaConfig;
import italo.explab.func.plot.obj.ExpLabPontoConfig;
import italo.explab.func.plot.obj.g2d.ExpLabDados2D;
import italo.explab.func.plot.obj.g2d.ExpLabPlanoCartesiano2D;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
        
public class PlotFunc extends AbstractFunc {
    
    private final String NOME = FuncFactory.PLOT;
    private final int QUANT_PARAMS = -1;    
    
    private final PlanoCartesiano2DBuilder pc2DBuilder = new PlanoCartesiano2DBuilder();
    
    @Override
    public FuncResult exec( FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params ) {
        if ( params.length < 1 || params.length > 2 )
            return new FuncResult( new CodigoErro( this.getClass(), codigo, fno.getI(), ErroMSGs.PARAMS_QUANT_INVALIDA, "1 ou 2" ) );
                
        ClasseUtil classeUtil = executor.getAplic().getClasseUtil();
        try {
            if ( params.length == 1 ) {
                Var var = params[0].getVar();

                ExpLabPlanoCartesiano2D pc = pc2DBuilder.build( fno, executor, codigo, var, classeUtil );
                
                executor.getAplic().getPlotador().plot( pc ); 
            } else {
                Var v1 = params[0].getVar();
                Var v2 = params[1].getVar();
                
                MatrizVar xvetor = null;
                MatrizVar yvetor = null;
                if ( v1.getTipo() == Var.MATRIZ )
                    if ( ((MatrizVar)v1).getNL() == 1 )
                        xvetor = (MatrizVar)v1;
                
                if ( xvetor == null )
                    throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, "parametro 1", "array", v1.getStringTipo() );
                
                if ( v2.getTipo() == Var.MATRIZ )
                    if ( ((MatrizVar)v2).getNL() == 1 )
                        yvetor = (MatrizVar)v2;
                
                if ( yvetor == null )
                    throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, "parametro 2", "array", v2.getStringTipo() );
                
                if ( xvetor.getNC() != yvetor.getNC() )
                    throw new ClasseUtilException( ClasseUtilException.VETORES_TAMS_DIFERENTES, "parametro 1", "parametro 2" );
               
                double[] vx = new double[ xvetor.getNC() ];
                double[] vy = new double[ yvetor.getNC() ];
                for( int k = 0; k < vx.length; k++ ) {
                    Var x = xvetor.getElemento( 0, k );
                    Var y = yvetor.getElemento( 0, k );
                    vx[ k ] = classeUtil.realValor( x, new VariavelNome( "parametro 1 ("+k+")" ) ).getValor();
                    vy[ k ] = classeUtil.realValor( y, new VariavelNome( "parametro 2 ("+k+")" ) ).getValor();
                }
                
                ExpLabPlanoCartesiano2D pc = new ExpLabPlanoCartesiano2D();
                pc.setJanelaConfig( new ExpLabJanelaConfig() ); 
                pc.setGradeConfig( new ExpLabGradeConfig() ); 
                
                ExpLabDados2D dados2d = new ExpLabDados2D();
                dados2d.setPontoConfig( new ExpLabPontoConfig() );
                dados2d.setLinhaConfig( new ExpLabLinhaConfig() ); 
                dados2d.setDadosX( vx );
                dados2d.setDadosY( vy );
                                
                pc.addGrafico( dados2d );                
                
                executor.getAplic().getPlotador().plot( pc );
            }            
            return new FuncResult( this );
        } catch ( ClasseUtilException e ) {
            CodigoErro erro = classeUtil.geraErro( codigo, e );
            return new FuncResult( erro );
        }
    }   
        
    @Override
    public Func nova() {
        return new PlotFunc();
    }

    @Override
    public String getNome() {
        return NOME;
    }

    @Override
    public int getQuantParametros() {
        return QUANT_PARAMS;
    }
    
}

