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
import italo.explab.func.plot.obj.g3d.ExpLabFunc3D;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import italo.explab.var.FuncVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;
import italo.explab.var.Var;
import italo.iplot.funcs.g3d.Func3D;

public class Func3DBuilder {
    
    private final PontoConfigBuilder pontoCFGBuilder = new PontoConfigBuilder();
    private final LinhaConfigBuilder linhaCFGBuilder = new LinhaConfigBuilder();
    private final PoligonoConfigBuilder poligonoCFGBuilder = new PoligonoConfigBuilder();
    
    public ExpLabFunc3D build( FuncExp fno, Executor executor, Codigo codigo, Objeto funcObj, VariavelNome funcVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        FuncManager funcManager = executor.getAplic().getFuncManager();
        
        ExpLabFunc3D func3D = new ExpLabFunc3D();

        RecursoManager funcObjRecursos = funcObj.getRecursos();
        
        RealVar x1 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "x1", funcVN ) );
        if ( x1 != null )
            func3D.setX1( x1.getValor() );

        RealVar x2 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "x2", funcVN ) );
        if ( x2 != null )
            func3D.setX2( x2.getValor() );
        
        RealVar y1 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "y1", funcVN ) );
        if ( y1 != null )
            func3D.setY1( y1.getValor() );
                
        RealVar y2 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "y2", funcVN ) );
        if ( y2 != null )
            func3D.setY2( y2.getValor() );
        
        RealVar z1 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "z1", funcVN ) );
        if ( z1 != null )
            func3D.setZ1( z1.getValor() );
                
        RealVar z2 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "z2", funcVN ) );
        if ( z2 != null )
            func3D.setZ2( z2.getValor() );
        
        RealVar xndivs = classeUtil.realValor( funcObjRecursos, new VariavelNome( "xndivs", funcVN ) );
        if ( xndivs != null )
            func3D.setXNDivs( (int)xndivs.getValor() );
        
        RealVar yndivs = classeUtil.realValor( funcObjRecursos, new VariavelNome( "yndivs", funcVN ) );
        if ( yndivs != null )
            func3D.setYNDivs( (int)yndivs.getValor() );
       
        BooleanVar limitarZ = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "limitarZ", funcVN ) );
        if ( limitarZ != null )
            func3D.setLimitarZ( limitarZ.getValor() );        
        
        FuncVar fun = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "fun", funcVN ) );
        if ( fun != null ) {   
            Func3D f = (double x, double z) -> {
                MetodoParam[] pars = { 
                    new MetodoParam( fun ),
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
            func3D.setFunc( f );
        }           
        
        BooleanVar somenteMalhaVar = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "somenteMalha", funcVN ) );            
        if ( somenteMalhaVar != null )
            func3D.setSomenteMalha( somenteMalhaVar.getValor() );             
        
        func3D.setPontoConfig( pontoCFGBuilder.build( funcObjRecursos, funcVN, classeUtil ) );
        func3D.setLinhaConfig( linhaCFGBuilder.build( funcObjRecursos, funcVN, classeUtil ) );
        func3D.setPoligonoConfig( poligonoCFGBuilder.build( funcObjRecursos, funcVN, classeUtil ) );
        
        return func3D;
    }

}
