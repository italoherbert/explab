package italo.explab.func.plot.build.g3d;

import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.FuncManager;
import italo.explab.func.FuncResult;
import italo.explab.func.plot.build.LinhaConfigBuilder;
import italo.explab.func.plot.build.PoligonoConfigBuilder;
import italo.explab.func.plot.build.PontoConfigBuilder;
import italo.explab.func.plot.obj.g3d.ExpLabPFunc3D;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import italo.explab.var.FuncVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.mat.NumericaMatrizVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;
import italo.iplot.funcs.g3d.Func3D;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncParametrica3DOpers;

public class PFunc3DBuilder {
    
    private final PontoConfigBuilder pontoCFGBuilder = new PontoConfigBuilder();
    private final LinhaConfigBuilder linhaCFGBuilder = new LinhaConfigBuilder();
    private final PoligonoConfigBuilder poligonoCFGBuilder = new PoligonoConfigBuilder();
    
    public ExpLabPFunc3D build( FuncExp fno, Executor executor, Codigo codigo, Objeto funcObj, VariavelNome pfuncVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        FuncManager funcManager = executor.getAplic().getFuncManager();
        
        ExpLabPFunc3D pfunc3D = new ExpLabPFunc3D();

        RecursoManager funcObjRecursos = funcObj.getRecursos();
        
        RealVar minX = classeUtil.realValor( funcObjRecursos, new VariavelNome( "x1", pfuncVN ) );
        if ( minX != null )
            pfunc3D.setMinX( minX.getValor() );

        RealVar maxX = classeUtil.realValor( funcObjRecursos, new VariavelNome( "x2", pfuncVN ) );
        if ( maxX != null )
            pfunc3D.setMaxX( maxX.getValor() );
        
        RealVar minY = classeUtil.realValor( funcObjRecursos, new VariavelNome( "y1", pfuncVN ) );
        if ( minY != null )
            pfunc3D.setMinY( minY.getValor() );
                
        RealVar maxY = classeUtil.realValor( funcObjRecursos, new VariavelNome( "y2", pfuncVN ) );
        if ( maxY != null )
            pfunc3D.setMaxY( maxY.getValor() );
        
        RealVar minZ = classeUtil.realValor( funcObjRecursos, new VariavelNome( "z1", pfuncVN ) );
        if ( minZ != null )
            pfunc3D.setMinZ( minZ.getValor() );
                
        RealVar maxZ = classeUtil.realValor( funcObjRecursos, new VariavelNome( "z2", pfuncVN ) );
        if ( maxZ != null )
            pfunc3D.setMaxZ( maxZ.getValor() );
        
        RealVar u1 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "u1", pfuncVN ) );
        if ( u1 != null )
            pfunc3D.setU1( u1.getValor() );
        
        RealVar u2 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "u2", pfuncVN ) );
        if ( u2 != null )
            pfunc3D.setU2( u2.getValor() );
        
        RealVar v1 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "v1", pfuncVN ) );
        if ( v1 != null )
            pfunc3D.setV1( v1.getValor() );
        
        RealVar v2 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "v2", pfuncVN ) );
        if ( v2 != null )
            pfunc3D.setV2( v2.getValor() );
        
        RealVar npu = classeUtil.realValor( funcObjRecursos, new VariavelNome( "npu", pfuncVN ) );
        if ( npu != null )
            pfunc3D.setNPU( (int)npu.getValor() );
        
        RealVar npv = classeUtil.realValor( funcObjRecursos, new VariavelNome( "npv", pfuncVN ) );
        if ( npv != null )
            pfunc3D.setNPV( (int)npv.getValor() );
               
        BooleanVar limitarX = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "limitarX", pfuncVN ) );
        if ( limitarX != null )
            pfunc3D.setLimitarX( limitarX.getValor() ); 
        
        BooleanVar limitarY = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "limitarY", pfuncVN ) );
        if ( limitarY != null )
            pfunc3D.setLimitarZ( limitarY.getValor() ); 
        
        BooleanVar limitarZ = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "limitarZ", pfuncVN ) );
        if ( limitarZ != null )
            pfunc3D.setLimitarZ( limitarZ.getValor() ); 
        
        FuncVar funX = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "funX", pfuncVN ), false );
        if ( funX != null ) {   
            Func3D f = (double x, double z) -> {
                MetodoParam[] pars = { 
                    new MetodoParam( funX ),
                    new MetodoParam( new NumeroRealVar( x ) ),
                    new MetodoParam( new NumeroRealVar( z ) )
                }; 
                FuncResult result = funcManager.getFExecFunc().exec( fno, executor, codigo, pars );                
                Var retornada =  result.getRetornada();
                if ( retornada == null )
                    return Double.NaN;
                if ( retornada.getTipo() != Var.REAL )
                    return Double.NaN;

                return ((RealVar)retornada).getValor();                    
            };
            pfunc3D.setFuncX( f );
        }
                        
        FuncVar funZ = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "funZ", pfuncVN ), false );
        if ( funZ != null ) {   
            Func3D f = (double x, double z) -> {
                MetodoParam[] pars = { 
                    new MetodoParam( funZ ),
                    new MetodoParam( new NumeroRealVar( x ) ),
                    new MetodoParam( new NumeroRealVar( z ) )
                }; 
                FuncResult result = funcManager.getFExecFunc().exec( fno, executor, codigo, pars );                
                Var retornada =  result.getRetornada();
                if ( retornada == null )
                    return Double.NaN;
                if ( retornada.getTipo() != Var.REAL )
                    return Double.NaN;

                return ((RealVar)retornada).getValor();                    
            };
            pfunc3D.setFuncY( f );
        }
        
        FuncVar funY = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "funY", pfuncVN ), false );
        if ( funY != null ) {   
            Func3D f = (double x, double z) -> {
                MetodoParam[] pars = { 
                    new MetodoParam( funY ),
                    new MetodoParam( new NumeroRealVar( x ) ),
                    new MetodoParam( new NumeroRealVar( z ) )
                }; 
                FuncResult result = funcManager.getFExecFunc().exec( fno, executor, codigo, pars );                
                Var retornada =  result.getRetornada();
                if ( retornada == null )
                    return Double.NaN;
                if ( retornada.getTipo() != Var.REAL )
                    return Double.NaN;

                return ((RealVar)retornada).getValor();                    
            };
            pfunc3D.setFuncZ( f );
        }
        
        FuncVar funOpers = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "funOpers", pfuncVN ), false );
        if ( funOpers != null ) {   
            PCFuncParametrica3DOpers f = ( double[][] mat, int k ) -> {
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
            pfunc3D.setFuncOpers( f );
        }
        
        BooleanVar somenteMalhaVar = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "somenteMalha", pfuncVN ) );            
        if ( somenteMalhaVar != null )
            pfunc3D.setSomenteMalha( somenteMalhaVar.getValor() );             
        
        pfunc3D.setPontoConfig( pontoCFGBuilder.build( funcObjRecursos, pfuncVN, classeUtil ) );
        pfunc3D.setLinhaConfig( linhaCFGBuilder.build( funcObjRecursos, pfuncVN, classeUtil ) );
        pfunc3D.setPoligonoConfig( poligonoCFGBuilder.build( funcObjRecursos, pfuncVN, classeUtil ) );
        
        return pfunc3D;
    }

}

