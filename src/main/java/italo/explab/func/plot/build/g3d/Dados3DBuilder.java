package italo.explab.func.plot.build.g3d;

import italo.explab.func.plot.build.LinhaConfigBuilder;
import italo.explab.func.plot.build.PoligonoConfigBuilder;
import italo.explab.func.plot.build.PontoConfigBuilder;
import italo.explab.func.plot.obj.g3d.dados.ExpLabDados3D;
import italo.explab.func.plot.obj.g3d.dados.ExpLabPlot3Dados3D;
import italo.explab.func.plot.obj.g3d.dados.ExpLabSuperficieDados3D;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class Dados3DBuilder {
    
    private final PontoConfigBuilder pontoCFGBuilder = new PontoConfigBuilder();
    private final LinhaConfigBuilder linhaCFGBuilder = new LinhaConfigBuilder();    
    private final PoligonoConfigBuilder poligonoCFGBuilder = new PoligonoConfigBuilder();    
    private final SuperficieVetorZBuilder superficieVetorZBuilder = new SuperficieVetorZBuilder();
    
    public ExpLabDados3D build( Objeto dadosObj, VariavelNome dadosVN, boolean ehsuperficie, ClasseUtil classeUtil ) throws ClasseUtilException {                                                                    
        RecursoManager dadosObjRecursos = dadosObj.getRecursos();

        VariavelNome xvetorVN = new VariavelNome( "xvetor", dadosVN );
        VariavelNome yvetorVN = new VariavelNome( "yvetor", dadosVN );
        VariavelNome arestasVN = new VariavelNome( "arestas", dadosVN );                
        
        MatrizVar xvetor = classeUtil.arrayValor( dadosObjRecursos, xvetorVN );
        MatrizVar yvetor = classeUtil.arrayValor( dadosObjRecursos, yvetorVN );
        MatrizVar arestasMat = null;
        try {
            arestasMat = classeUtil.matrizValor( dadosObjRecursos, arestasVN );
        } catch ( ClasseUtilException e ) {
            
        }
                        
        if ( xvetor == null ) {
            String vc = classeUtil.variavelCompleta( xvetorVN );
            throw new ClasseUtilException( ClasseUtilException.VALOR_NAO_NULO_ESPERADO, vc );
        }

        if ( yvetor == null ) {
            String vc = classeUtil.variavelCompleta( yvetorVN );
            throw new ClasseUtilException( ClasseUtilException.VALOR_NAO_NULO_ESPERADO, vc );
        }
                
        if ( xvetor.getNC() != yvetor.getNC() ) {
            String xvetorVC = classeUtil.variavelCompleta( xvetorVN );
            String yvetorVC = classeUtil.variavelCompleta( yvetorVN );
            throw new ClasseUtilException( ClasseUtilException.VETORES_TAMS_DIFERENTES, xvetorVC, yvetorVC );
        }
                
        ExpLabDados3D dados3D;
        if ( ehsuperficie ) {                    
            VariavelNome zvetorVN = new VariavelNome( "zmat", dadosVN );
            double[][] matz = superficieVetorZBuilder.build( dadosObjRecursos, xvetor, yvetor, xvetorVN, yvetorVN, zvetorVN, classeUtil );
            
            BooleanVar somenteMalhaVar = classeUtil.booleanValor( dadosObjRecursos, new VariavelNome( "somenteMalha", dadosVN ) );
            
            dados3D = new ExpLabSuperficieDados3D();
            ((ExpLabSuperficieDados3D)dados3D).setMatrizZ( matz ); 
            
            if ( somenteMalhaVar != null )
                ((ExpLabSuperficieDados3D)dados3D).setSomenteMalha( somenteMalhaVar.getValor() );             
        } else {
            VariavelNome zvetorVN = new VariavelNome( "zvetor", dadosVN );
            MatrizVar zvetor = classeUtil.arrayValor( dadosObjRecursos, zvetorVN );              
            StringVar legendaVar = classeUtil.stringValor( dadosObjRecursos, new VariavelNome( "legenda", dadosVN ) );            
            
            if ( xvetor.getNC() != zvetor.getNC() ) {
                String xvetorVC = classeUtil.variavelCompleta( xvetorVN );
                String zvetorVC = classeUtil.variavelCompleta( zvetorVN );
                throw new ClasseUtilException( ClasseUtilException.VETORES_TAMS_DIFERENTES, xvetorVC, zvetorVC );
            }
                                    
            double[] vz = new double[ zvetor.getNC() ]; 
            for( int j = 0; j < vz.length; j++ ) {
                Var zel = zvetor.getElemento( 0, j );
                vz[ j ] = classeUtil.realValor( zel, new VariavelNome( "("+j+")", zvetorVN ) ).getValor();                        
            }
            
            dados3D = new ExpLabPlot3Dados3D();            
            ((ExpLabPlot3Dados3D)dados3D).setVetorZ( vz );             
            
            if ( legendaVar != null )
                ((ExpLabPlot3Dados3D)dados3D).setLegenda( legendaVar.getValor() ); 
        }
        
        dados3D.setPontoConfig( pontoCFGBuilder.build( dadosObjRecursos, dadosVN, classeUtil ) );
        dados3D.setLinhaConfig( linhaCFGBuilder.build( dadosObjRecursos, dadosVN, classeUtil ) );
        dados3D.setPoligonoConfig( poligonoCFGBuilder.build( dadosObjRecursos, dadosVN, classeUtil ) );
                   
        double[] vx = new double[ xvetor.getNC() ];
        double[] vy = new double[ yvetor.getNC() ];
        for( int j = 0; j < vx.length; j++ ) {
            Var xel = xvetor.getElemento( 0, j );
            Var yel = yvetor.getElemento( 0, j );                                                        

            vx[ j ] = classeUtil.realValor( xel, new VariavelNome( "("+j+")", xvetorVN ) ).getValor();
            vy[ j ] = classeUtil.realValor( yel, new VariavelNome( "("+j+")", yvetorVN ) ).getValor();                        
        }
        
        dados3D.setVetorX( vx );
        dados3D.setVetorY( vy );
        dados3D.setEhSuperficie( ehsuperficie ); 
        
        if ( arestasMat != null ) {
            int[][] arestas = new int[ arestasMat.getNL() ][ arestasMat.getNC() ];
            for( int j = 0; j < arestas.length; j++ ) {
                for( int k = 0; k < arestas[j].length; k++ ) {
                    Var el = arestasMat.getElemento( j, k );

                    arestas[j][k] = (int)classeUtil.realValor( el, new VariavelNome( "("+j+")", arestasVN ) ).getValor();
                }
            }
            dados3D.setArestas( arestas ); 
        }

        return dados3D;
    }
        
}
