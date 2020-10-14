package italo.explab.plotador;

import italo.explab.func.plot.obj.g2d.ExpLabDados2D;
import italo.explab.func.plot.obj.g2d.ExpLabFunc2D;
import italo.explab.func.plot.obj.g2d.ExpLabGrafico2D;
import italo.explab.func.plot.obj.g2d.ExpLabPFunc2D;
import italo.explab.func.plot.obj.g2d.ExpLabPlanoCartesiano2D;
import italo.explab.func.plot.obj.g3d.ExpLabFunc3D;
import italo.explab.func.plot.obj.g3d.ExpLabGrafico3D;
import italo.explab.func.plot.obj.g3d.ExpLabPFunc3D;
import italo.explab.func.plot.obj.g3d.ExpLabPlanoCartesiano3D;
import italo.explab.func.plot.obj.g3d.dados.ExpLabDados3D;
import italo.explab.func.plot.obj.g3d.dados.ExpLabPlot3Dados3D;
import italo.explab.func.plot.obj.g3d.dados.ExpLabSuperficieDados3D;
import italo.explab.gui.ExpLabGUIManager;
import italo.explab.gui.plot.Plot2DAplicGUI;
import italo.explab.gui.plot.Plot3DAplicGUI;
import italo.explab.gui.plot.PlotAplicGUIController;
import italo.iplot.funcs.g2d.Func2D;
import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.gui.plot.PlotGUI;
import italo.iplot.plot2d.Plot2D;
import italo.iplot.plot2d.g2d.ComponenteObjeto2D;
import italo.iplot.plot2d.g2d.GrafoObjeto2D;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2D;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCDadosObjeto2D;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncObjeto2D;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncParametrica2DOpers;
import italo.iplot.plot2d.planocartesiano.g2d.PlanoCartesianoObjeto2D;
import italo.iplot.plot3d.Plot3D;
import italo.iplot.plot3d.g3d.ComponenteObjeto3D;
import italo.iplot.plot3d.g3d.GrafoObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.SuperficieObjeto3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncParametricaObjeto3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Plotador {
    
    private final ExpLabGUIManager manager; 

    public Plotador( ExpLabGUIManager manager ) {
        this.manager = manager;                        
    }

    public void plot( ExpLabPlanoCartesiano2D pc2D ) {        
        PlotAplicGUIController plotAplicGUIController = manager.getPlotAplicGUIController();
        
        PlanoCartesianoPlot2D plot2D = new PlanoCartesianoPlot2D();
                        
        PlanoCartesianoPlot2DDriver drv = ( plot2d, pc ) -> {                  
            pc.setTitulo( pc2D.getTitulo() );
            pc.setYEixoRotulo( pc2D.getYRotulo() );
            pc.setXEixoRotulo( pc2D.getXRotulo() );
            pc.setPintarGrade( pc2D.getGradeConfig().isGradeVisivel() );
            pc.setGradeCor( pc2D.getGradeConfig().getGradeCor() );  
            
            for( ExpLabGrafico2D grafico : pc2D.getGraficos() ) {
                switch ( grafico.getTipo() ) {
                    case ExpLabGrafico2D.DADOS:
                        this.addDados2D( plot2d, pc, grafico );
                        break;
                    case ExpLabDados2D.FUNC:
                        this.addFunc2D( plot2d, pc, grafico );
                        break;
                    case ExpLabDados2D.PFUNC:
                        this.addPFunc2D( plot2d, pc, grafico );                        
                        break;                    
                }
            }
        };
                
        Plot2DGUI pgui = plot2D.novaPlot2DGUI();

        Plot2DAplicGUI janelaPlot2D = plotAplicGUIController.getPlot2DAplicGUIDisponivel();
        janelaPlot2D.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
        janelaPlot2D.setTitle( pc2D.getJanelaConfig().getTitulo() ); 

        plotAplicGUIController.setSizeELocation( janelaPlot2D, pc2D.getJanelaConfig().getLargura(), pc2D.getJanelaConfig().getAltura() );

        janelaPlot2D.setDesenhoComponent( pgui );
        
        SwingUtilities.invokeLater( () -> {
            janelaPlot2D.setVisible( true );
            
            int w = plot2D.getDesenhoComponent().getWidth();
            int h = plot2D.getDesenhoComponent().getHeight();        
            plot2D.constroi( drv, w, h );        
        } );
    }
    
    public void plot3d( ExpLabPlanoCartesiano3D pc3D ) {
        PlotAplicGUIController plotAplicGUIController = manager.getPlotAplicGUIController();
        
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
                        
        PlanoCartesianoPlot3DDriver drv = ( pc ) -> {                  
            pc.setTitulo( pc3D.getTitulo() );
            pc.setXEixoRotulo( pc3D.getXRotulo() );
            pc.setYEixoRotulo( pc3D.getZRotulo() );
            pc.setZEixoRotulo( pc3D.getYRotulo() );
            if ( pc3D.getDX() != -1 )
                pc.setDX( pc3D.getDX() );
            if ( pc3D.getDY() != -1 )
                pc.setDY( pc3D.getDY() );
            if ( pc3D.getDZ() != -1 )
                pc.setDZ( pc3D.getDZ() );
            
            pc.setPintarGrade( pc3D.getGradeConfig().isGradeVisivel() );
            pc.setGradeCor( pc3D.getGradeConfig().getGradeCor() );
                        
            for( ExpLabGrafico3D grafico : pc3D.getGraficos() ) {                
                switch ( grafico.getTipo() ) {
                    case ExpLabGrafico3D.DADOS:
                        this.addDados3D( plot3D, pc, grafico );
                        break;
                    case ExpLabGrafico3D.FUNC:
                        this.addFunc3D( plot3D, pc, grafico ); 
                        break;
                    default:
                        this.addPFunc3D( plot3D, pc, grafico );
                        break;
                }
            }
        };

        plot3D.setGrafico( plot3D.novoAlocaImagemGrafico() ); 
        
        PlotGUI pgui = plot3D.novoPlotGUI();

        Plot3DAplicGUI janelaPlot3D = plotAplicGUIController.getPlot3DAplicGUIDisponivel();
        janelaPlot3D.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
        janelaPlot3D.setTitle( pc3D.getJanelaConfig().getTitulo() ); 

        plotAplicGUIController.setSizeELocation( janelaPlot3D, pc3D.getJanelaConfig().getLargura(), pc3D.getJanelaConfig().getAltura() );

        janelaPlot3D.setDesenhoComponent( pgui );
        
        SwingUtilities.invokeLater( () -> {
            janelaPlot3D.setVisible( true );

            int w = plot3D.getDesenhoComponent().getWidth();
            int h = plot3D.getDesenhoComponent().getHeight();        
            plot3D.constroi( drv, w, h );
        } );        
    }
    
    public void addDados2D( Plot2D plot2d, PlanoCartesianoObjeto2D pc, ExpLabGrafico2D grafico ) {
        ExpLabDados2D dados2d = (ExpLabDados2D)grafico;

        double[] vx = dados2d.getDadosX();
        double[] vy = dados2d.getDadosY();
        double vraio = plot2d.getMath2D().verticeUnidade( dados2d.getPontoConfig().getRaioPX(), plot2d.getTela() );

        Objeto2D dadosObj2d;
        if ( dados2d.getArestas() == null ) {
            dadosObj2d = new PCDadosObjeto2D( vx, vy );
            ((PCDadosObjeto2D)dadosObj2d).setLegenda( dados2d.getLegenda() );
            ((PCDadosObjeto2D)dadosObj2d).setVerticeRaio( vraio );
        } else {
            dadosObj2d = new GrafoObjeto2D();

            int tam = vx.length;
            double[][] nos = new double[ tam ][];
            int[][] arestas = dados2d.getArestas();
            for( int i = 0; i < tam; i++ ) {
                double x = vx[i];
                double y = vy[i];
                nos[ i ] = new double[]{ x, y };
            }
            ((GrafoObjeto2D)dadosObj2d).setGrafoNos( nos );
            ((GrafoObjeto2D)dadosObj2d).setGrafoArestas( arestas );
            ((GrafoObjeto2D)dadosObj2d).setVerticeRaio( vraio );
        }   

        dadosObj2d.setPintarArestas( dados2d.getLinhaConfig().isVisivel() );
        dadosObj2d.setPintarVertices( dados2d.getPontoConfig().isVisivel() );
        dadosObj2d.setArestasCor( dados2d.getLinhaConfig().getCor() );
        dadosObj2d.setVerticesCor( dados2d.getPontoConfig().getCor() );

        pc.addComponenteObj2D( (ComponenteObjeto2D)dadosObj2d );
    }
    
    private void addFunc2D( Plot2D plot2d, PlanoCartesianoObjeto2D pc, ExpLabGrafico2D grafico ) {
        ExpLabFunc2D func2d = (ExpLabFunc2D)grafico;
        PCFuncObjeto2D funcObj2d = new PCFuncObjeto2D();

        funcObj2d.setLegenda( func2d.getLegenda() );
        funcObj2d.setXIntervalo( func2d.getX1(), func2d.getX2() );
        funcObj2d.setYIntervalo( func2d.getY1(), func2d.getY2() );
        funcObj2d.setYIntervaloAtivado( func2d.isLimitarY() );
        funcObj2d.setXIntervaloCompleto( func2d.isXCompleto() );
        funcObj2d.setFunc2D( func2d.getFunc() );
        funcObj2d.setPintarArestas( func2d.getLinhaConfig().isVisivel() );
        funcObj2d.setArestasCor( func2d.getLinhaConfig().getCor() );

        pc.addComponenteObj2D( funcObj2d );
    }
    
    private void addPFunc2D( Plot2D plot2d, PlanoCartesianoObjeto2D pc, ExpLabGrafico2D grafico ) {
        ExpLabPFunc2D pfunc2d = (ExpLabPFunc2D)grafico;
        PCDadosObjeto2D pfuncObj2d = new PCDadosObjeto2D();

        double t1 = pfunc2d.getT1();
        double t2 = pfunc2d.getT2();
        int npt = pfunc2d.getNPT();
        Func2D fx = pfunc2d.getFuncX();
        Func2D fy = pfunc2d.getFuncY();
        PCFuncParametrica2DOpers opers = pfunc2d.getFuncOpers();

        pfuncObj2d.setFuncsParametricas( t1, t2, npt, fx, fy );
        pfuncObj2d.setFuncParametricaOpers( opers );
        
        double vraio = plot2d.getMath2D().verticeUnidade( pfunc2d.getPontoConfig().getRaioPX(), plot2d.getTela() );

        pfuncObj2d.setLegenda( pfunc2d.getLegenda() );
        pfuncObj2d.setVerticeRaio( vraio );
        
        pfuncObj2d.setPintarArestas( pfunc2d.getLinhaConfig().isVisivel() );
        pfuncObj2d.setPintarVertices( pfunc2d.getPontoConfig().isVisivel() );
        pfuncObj2d.setArestasCor( pfunc2d.getLinhaConfig().getCor() );
        pfuncObj2d.setVerticesCor( pfunc2d.getPontoConfig().getCor() );

        pc.addComponenteObj2D( (ComponenteObjeto2D)pfuncObj2d );
    }
    
    private void addDados3D( Plot3D plot3d, PlanoCartesianoObjeto3D pc, ExpLabGrafico3D grafico ) {
        ExpLabDados3D dados3d = (ExpLabDados3D)grafico;
                
        double[] vx = dados3d.getVetorX();
        double[] vy = dados3d.getVetorY();

        double vraio = plot3d.getMath3D().verticeUnidade( dados3d.getPontoConfig().getRaioPX(), plot3d.getTela() );

        Objeto3D obj;
        if ( dados3d.isEhSuperficie() ) {
            obj = new SuperficieObjeto3D();
        } else {
            obj = new GrafoObjeto3D();
        }

        obj.setVerticeRaio3D( () -> vraio );
        obj.setPintarVertices( dados3d.getPontoConfig().isVisivel() );                        
        obj.setPintarArestas( dados3d.getLinhaConfig().isVisivel() ); 
        obj.setPintarFaces( dados3d.getPoligonoConfig().isVisivel() );
        obj.setDesenharFaces( dados3d.getLinhaConfig().isVisivel() ); 
        obj.setVerticesCor( dados3d.getPontoConfig().getCor() );
        obj.setArestasCor( dados3d.getLinhaConfig().getCor() ); 
        obj.setCor( dados3d.getPoligonoConfig().getCor() );
        obj.setFaceArestasCor( dados3d.getLinhaConfig().getCor() );
        obj.setAplicarIluminacaoAFace( dados3d.getPoligonoConfig().isIluminar() ); 
        obj.setAplicarIluminacaoAAresta( dados3d.getLinhaConfig().isIluminar() ); 

        if ( dados3d.getLinhaConfig().isPreenchimentoGradiente() ) {
            obj.setArestaPreenchimento( Objeto3D.Preenchimento.GRADIENTE );
        } else {
            obj.setArestaPreenchimento( Objeto3D.Preenchimento.NORMAL ); 
        }

        if ( dados3d.getPoligonoConfig().isPreenchimentoGradiente() ) {
            obj.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );
        } else {
            obj.setPreenchimento( Objeto3D.Preenchimento.NORMAL ); 
        }

        if ( dados3d.isEhSuperficie() ) {
            ExpLabSuperficieDados3D dados = (ExpLabSuperficieDados3D)dados3d;
            double[][] matz = dados.getMatrizZ();

            boolean somenteMalha = dados.isSomenteMalha();

            ((SuperficieObjeto3D)obj).setVetorX( vx ); 
            ((SuperficieObjeto3D)obj).setVetorZ( vy );
            ((SuperficieObjeto3D)obj).setMatrizY( matz );                                         

            if ( somenteMalha ) {
                obj.setPintarArestas( true );
                obj.setPintarFaces( false );
                obj.setDesenharFaces( true );                                                                         
            } else {                        
                obj.setPintarArestas( false );                               
            }                                        
        } else {
            ExpLabPlot3Dados3D dados = (ExpLabPlot3Dados3D)dados3d;
            double[] vz = dados.getVetorZ();

            int tam = vz.length;
            double[][] nos = new double[ tam ][];
            int[][] arestas = {};
            
            for( int i = 0; i < tam; i++ ) {
                double x = vx[i];
                double z = vy[i];
                double y = vz[i];
                nos[ i ] = new double[]{ x, y, z };            
            }
            
            if ( dados.getArestas() != null ) {
                arestas = dados.getArestas();
            } else if ( tam > 0 ) {
                arestas = new int[ tam-1][];
                for( int i = 0; i < tam; i++ ) {                    
                    if ( i > 0 )
                        arestas[ i-1 ] = new int[] { i-1, i };                    
                }
            }

            ((GrafoObjeto3D)obj).setGrafoNos( nos );
            ((GrafoObjeto3D)obj).setGrafoArestas( arestas );                    
        }   

        pc.addComponenteObj3D( (ComponenteObjeto3D)obj );
    }
    
    public void addFunc3D( Plot3D plot3d, PlanoCartesianoObjeto3D pc, ExpLabGrafico3D grafico ) {
        ExpLabFunc3D func3d = (ExpLabFunc3D)grafico;
                
        boolean somenteMalha = func3d.isSomenteMalha();
        double vraio = plot3d.getMath3D().verticeUnidade( func3d.getPontoConfig().getRaioPX(), plot3d.getTela() );                

        PCFuncObjeto3D funcObj3d = new PCFuncObjeto3D();                
        funcObj3d.setXIntervalo( func3d.getX1(), func3d.getX2() );
        funcObj3d.setZIntervalo( func3d.getY1(), func3d.getY2() ); 
        funcObj3d.setMinY( func3d.getZ1() );
        funcObj3d.setMaxY( funcObj3d.getZ2() ); 
        funcObj3d.setYIntervaloAtivado( func3d.isLimitarZ() ); 
        funcObj3d.setNDivs( func3d.getXNDivs(), func3d.getYNDivs() );
        funcObj3d.setFunc3D( func3d.getFunc() ); 

        funcObj3d.setVerticeRaio3D( () -> vraio );
        funcObj3d.setPintarVertices( func3d.getPontoConfig().isVisivel() );                        
        funcObj3d.setPintarFaces( func3d.getPoligonoConfig().isVisivel() );
        funcObj3d.setDesenharFaces( func3d.getLinhaConfig().isVisivel() ); 
        funcObj3d.setFaceArestasCor( func3d.getLinhaConfig().getCor() );
        funcObj3d.setCor( func3d.getPoligonoConfig().getCor() );
        funcObj3d.setVerticesCor( func3d.getPontoConfig().getCor() );
        funcObj3d.setAplicarIluminacaoAFace( func3d.getPoligonoConfig().isIluminar() ); 
        funcObj3d.setAplicarIluminacaoAAresta( func3d.getLinhaConfig().isIluminar() ); 

        if ( func3d.getLinhaConfig().isPreenchimentoGradiente() ) {
            funcObj3d.setArestaPreenchimento( Objeto3D.Preenchimento.GRADIENTE );
        } else {
            funcObj3d.setArestaPreenchimento( Objeto3D.Preenchimento.NORMAL ); 
        }

        if ( func3d.getPoligonoConfig().isPreenchimentoGradiente() ) {
            funcObj3d.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );
        } else {
            funcObj3d.setPreenchimento( Objeto3D.Preenchimento.NORMAL ); 
        }

        if ( somenteMalha ) {
            funcObj3d.setPintarArestas( true );
            funcObj3d.setPintarFaces( false );
            funcObj3d.setDesenharFaces( false );                                         
        } else {
            funcObj3d.setPintarArestas( false );                           
        }

        pc.addComponenteObj3D( funcObj3d );
    }    
    
    private void addPFunc3D( Plot3D plot3d, PlanoCartesianoObjeto3D pc, ExpLabGrafico3D grafico ) {
        ExpLabPFunc3D pfunc3d = (ExpLabPFunc3D)grafico;
                
        boolean somenteMalha = pfunc3d.isSomenteMalha();
        double vraio = plot3d.getMath3D().verticeUnidade( pfunc3d.getPontoConfig().getRaioPX(), plot3d.getTela() );                

        PCFuncParametricaObjeto3D pfuncObj3d = new PCFuncParametricaObjeto3D();                
        pfuncObj3d.setMinX( pfunc3d.getMinX() );
        pfuncObj3d.setMinY( pfunc3d.getMinZ() );
        pfuncObj3d.setMinZ( pfunc3d.getMinY() );
        pfuncObj3d.setMaxX( pfunc3d.getMaxX() );
        pfuncObj3d.setMaxY( pfunc3d.getMaxZ() );
        pfuncObj3d.setMaxZ( pfunc3d.getMaxY() );
        pfuncObj3d.setXIntervaloAtivado( pfunc3d.isLimitarX() );
        pfuncObj3d.setYIntervaloAtivado( pfunc3d.isLimitarZ() ); 
        pfuncObj3d.setZIntervaloAtivado( pfunc3d.isLimitarY() );
        pfuncObj3d.setFuncParametricaOpers( pfunc3d.getFuncOpers() ); 

        pfuncObj3d.setVetoresUeV( pfunc3d.getU1(), pfunc3d.getU2(), pfunc3d.getNPU(), pfunc3d.getV1(), pfunc3d.getV2(), pfunc3d.getNPV() );

        pfuncObj3d.setFuncX( pfunc3d.getFuncX() );
        pfuncObj3d.setFuncY( pfunc3d.getFuncY() );
        pfuncObj3d.setFuncZ( pfunc3d.getFuncZ() );

        pfuncObj3d.setVerticeRaio3D( () -> vraio );
        pfuncObj3d.setPintarVertices( pfunc3d.getPontoConfig().isVisivel() );                        
        pfuncObj3d.setPintarFaces( pfunc3d.getPoligonoConfig().isVisivel() );
        pfuncObj3d.setDesenharFaces( pfunc3d.getLinhaConfig().isVisivel() ); 
        pfuncObj3d.setFaceArestasCor( pfunc3d.getLinhaConfig().getCor() );
        pfuncObj3d.setCor( pfunc3d.getPoligonoConfig().getCor() );
        pfuncObj3d.setVerticesCor( pfunc3d.getPontoConfig().getCor() );
        pfuncObj3d.setAplicarIluminacaoAFace( pfunc3d.getPoligonoConfig().isIluminar() ); 
        pfuncObj3d.setAplicarIluminacaoAAresta( pfunc3d.getLinhaConfig().isIluminar() ); 

        if ( pfunc3d.getLinhaConfig().isPreenchimentoGradiente() ) {
            pfuncObj3d.setArestaPreenchimento( Objeto3D.Preenchimento.GRADIENTE );
        } else {
            pfuncObj3d.setArestaPreenchimento( Objeto3D.Preenchimento.NORMAL ); 
        }

        if ( pfunc3d.getPoligonoConfig().isPreenchimentoGradiente() ) {
            pfuncObj3d.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );
        } else {
            pfuncObj3d.setPreenchimento( Objeto3D.Preenchimento.NORMAL ); 
        }

        if ( somenteMalha ) {
            pfuncObj3d.setPintarArestas( true );
            pfuncObj3d.setPintarFaces( false );
            pfuncObj3d.setDesenharFaces( false );                                         
        } else {
            pfuncObj3d.setPintarArestas( false );                           
        }
        
        pc.addComponenteObj3D( pfuncObj3d );
    }    
    
}
