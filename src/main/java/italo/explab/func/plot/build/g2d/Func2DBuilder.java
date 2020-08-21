package italo.explab.func.plot.build.g2d;

import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.FuncManager;
import italo.explab.func.FuncResult;
import italo.explab.func.plot.build.LinhaConfigBuilder;
import italo.explab.func.plot.obj.g2d.ExpLabFunc2D;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import italo.explab.var.FuncVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.iplot.funcs.g2d.Func2D;

public class Func2DBuilder {
    
    private final LinhaConfigBuilder linhaCFGBuilder = new LinhaConfigBuilder();    
    
    public ExpLabFunc2D build( FuncExp fno, Executor executor, Codigo codigo, Objeto funcObj, VariavelNome funcVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        FuncManager funcManager = executor.getAplic().getFuncManager();
        
        ExpLabFunc2D func2D = new ExpLabFunc2D();

        RecursoManager funcObjRecursos = funcObj.getRecursos();
        
        RealVar x1 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "x1", funcVN ) );
        if ( x1 != null )
            func2D.setX1( x1.getValor() );

        RealVar x2 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "x2", funcVN ) );
        if ( x2 != null )
            func2D.setX2( x2.getValor() );
        
        RealVar y1 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "y1", funcVN ) );
        if ( y1 != null )
            func2D.setY1( y1.getValor() );
                
        RealVar y2 = classeUtil.realValor( funcObjRecursos, new VariavelNome( "y2", funcVN ) );
        if ( y2 != null )
            func2D.setY2( y2.getValor() );
       
        BooleanVar xcompleto = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "xcompleto", funcVN ) );
        if ( xcompleto != null )
            func2D.setXCompleto( xcompleto.getValor() );
        
        BooleanVar limitarY = classeUtil.booleanValor( funcObjRecursos, new VariavelNome( "limitarY", funcVN ) );
        if ( limitarY != null )
            func2D.setLimitarY( limitarY.getValor() );        
        
        StringVar legenda = classeUtil.stringValor( funcObjRecursos, new VariavelNome( "legenda", funcVN ) );
        if ( legenda != null )
            func2D.setLegenda( legenda.getValor() );
        
        FuncVar fun = classeUtil.funcValor( funcObjRecursos, new VariavelNome( "fun", funcVN ) );
        if ( fun != null ) {   
            Func2D f = (double x) -> {
                MetodoParam[] pars = { 
                    new MetodoParam( fun ),
                    new MetodoParam( new NumeroRealVar( x ) ) 
                }; 
                FuncResult result = funcManager.getFExecFunc().exec( fno, executor, codigo, pars );
                Var retornada =  result.getRetornada();
                if ( retornada == null )
                    return Double.NaN;
                if ( retornada.getTipo() != Var.REAL )
                    return Double.NaN;

                return ((RealVar)retornada).getValor();                    
            };
            func2D.setFunc( f );
        }           
        
        func2D.setLinhaConfig( linhaCFGBuilder.build( funcObjRecursos, funcVN, classeUtil ) );
        
        return func2D;
    }
    
}
