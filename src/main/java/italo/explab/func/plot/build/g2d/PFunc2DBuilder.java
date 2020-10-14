package italo.explab.func.plot.build.g2d;

import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.FuncManager;
import italo.explab.func.FuncResult;
import italo.explab.func.plot.build.LinhaConfigBuilder;
import italo.explab.func.plot.build.PoligonoConfigBuilder;
import italo.explab.func.plot.build.PontoConfigBuilder;
import italo.explab.func.plot.obj.g2d.ExpLabPFunc2D;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import italo.explab.var.FuncVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.mat.NumericaMatrizVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;
import italo.iplot.funcs.g2d.Func2D;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncParametrica2DOpers;

public class PFunc2DBuilder  {
    
    private final PontoConfigBuilder pontoCFGBuilder = new PontoConfigBuilder();
    private final LinhaConfigBuilder linhaCFGBuilder = new LinhaConfigBuilder();
    private final PoligonoConfigBuilder poligonoCFGBuilder = new PoligonoConfigBuilder();
    
    public ExpLabPFunc2D build( FuncExp fno, Executor executor, Codigo codigo, Objeto funcObj, VariavelNome pfuncVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        FuncManager funcManager = executor.getAplic().getFuncManager();
        
        ExpLabPFunc2D pfunc2D = new ExpLabPFunc2D();

        RecursoManager funcObjRecursos = funcObj.getRecursos();
        
        RealVar minX = classeUtil.realValor( funcObjRecursos, new VariavelNome( "x1", pfuncVN ) );
        if ( minX != null )
            pfunc2D.setMinX( minX.getValor() );

        RealVar maxX = classeUtil.realValor( funcObjRecursos, new VariavelNome( "x2", pfuncVN ) );
        if ( maxX != null )
            pfunc2D.setMaxX( maxX.getValor() );
        
        RealVar minY = classeUtil.realValor( funcObjRecursos, new VariavelNome( "y1", pfuncVN ) );
        if ( minY != null )
            pfunc2D.setMinY( minY.getValor() );
                
        RealVar maxY = classeUtil.realValor( funcObjRecursos, new VariavelNome( "y2", pfuncVN ) );
        if ( maxY != null )
            pfunc2D.setMaxY( maxY.getValor() );
                
        RealVar t1 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "t1", pfuncVN ) );
        if ( t1 != null )
            pfunc2D.setT1( t1.getValor() );
        
        RealVar t2 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "t2", pfuncVN ) );
        if ( t2 != null )
            pfunc2D.setT2( t2.getValor() );
                
        RealVar npt = classeUtil.realValor( funcObjRecursos, new VariavelNome( "npt", pfuncVN ) );
        if ( npt != null )
            pfunc2D.setNPT( (int)npt.getValor() );
                       
        BooleanVar limitarX = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "limitarX", pfuncVN ) );
        if ( limitarX != null )
            pfunc2D.setLimitarX( limitarX.getValor() ); 
        
        BooleanVar limitarY = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "limitarY", pfuncVN ) );
        if ( limitarY != null )
            pfunc2D.setLimitarY( limitarY.getValor() );                 
        
        StringVar legenda = classeUtil.stringValor( funcObjRecursos, new VariavelNome( "legenda", pfuncVN ) );
        if ( legenda != null )
            pfunc2D.setLegenda( legenda.getValor() ); 
        
        FuncVar funX = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "funX", pfuncVN ), false );
        if ( funX != null ) {   
            Func2D f = (double x) -> {
                MetodoParam[] pars = { 
                    new MetodoParam( funX ),
                    new MetodoParam( new NumeroRealVar( x ) ),
                }; 
                FuncResult result = funcManager.getFExecFunc().exec( fno, executor, codigo, pars );                
                Var retornada =  result.getRetornada();
                if ( retornada == null )
                    return Double.NaN;
                if ( retornada.getTipo() != Var.REAL )
                    return Double.NaN;

                return ((RealVar)retornada).getValor();                    
            };
            pfunc2D.setFuncX( f );
        }
                        
        FuncVar funY = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "funY", pfuncVN ), false );
        if ( funY != null ) {   
            Func2D f = (double x) -> {
                MetodoParam[] pars = { 
                    new MetodoParam( funY ),
                    new MetodoParam( new NumeroRealVar( x ) ),
                }; 
                FuncResult result = funcManager.getFExecFunc().exec( fno, executor, codigo, pars );                
                Var retornada =  result.getRetornada();
                if ( retornada == null )
                    return Double.NaN;
                if ( retornada.getTipo() != Var.REAL )
                    return Double.NaN;

                return ((RealVar)retornada).getValor();                    
            };
            pfunc2D.setFuncY( f );
        }
                
        FuncVar funOpers = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "funOpers", pfuncVN ), false );
        if ( funOpers != null ) {   
            PCFuncParametrica2DOpers f = ( double[][] mat, int k ) -> {
                MetodoParam[] pars = { 
                    new MetodoParam( funOpers ),
                    new MetodoParam( new NumericaMatrizVar( mat ) ),
                    new MetodoParam( new NumeroRealVar( k ) )
                }; 
                FuncResult result = funcManager.getFExecFunc().exec( fno, executor, codigo, pars );  
                if ( result.getRetornada() == null )
                    return mat;                
                if ( result.getRetornada().getTipo() != Var.MATRIZ )
                    return mat;               
                
                MatrizVar retornada =  (MatrizVar)result.getRetornada();                                
                if ( retornada.getTipoCompativel() != Var.TC_NUMERICO )
                    return mat;
                
                double[][] matvet = new double[ retornada.getNL() ][ retornada.getNC() ];
                for( int i = 0; i < retornada.getNL(); i++ )
                    for( int j = 0; j < retornada.getNC(); j++ )
                        matvet[ i ][ j ] = ( (NumeroRealVar)retornada.getElemento( i, j ) ).getValor();                                                                    
                return matvet;                    
            };
            pfunc2D.setFuncOpers( f );
        }                  
        
        pfunc2D.setPontoConfig( pontoCFGBuilder.build( funcObjRecursos, pfuncVN, classeUtil ) );
        pfunc2D.setLinhaConfig( linhaCFGBuilder.build( funcObjRecursos, pfuncVN, classeUtil ) );
        pfunc2D.setPoligonoConfig( poligonoCFGBuilder.build( funcObjRecursos, pfuncVN, classeUtil ) );
        
        return pfunc2D;
    }

}


