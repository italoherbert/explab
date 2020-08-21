package italo.explab.func.plot.build.g2d;

import italo.explab.func.plot.build.LinhaConfigBuilder;
import italo.explab.func.plot.build.PontoConfigBuilder;
import italo.explab.func.plot.obj.g2d.ExpLabDados2D;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class Dados2DBuilder {
    
    private final PontoConfigBuilder pontoCFGBuilder = new PontoConfigBuilder();
    private final LinhaConfigBuilder linhaCFGBuilder = new LinhaConfigBuilder();
    
    public ExpLabDados2D build( Objeto dadosObj, VariavelNome dadosVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        ExpLabDados2D dados2D = new ExpLabDados2D();
                        
        RecursoManager dadosObjRecursos = dadosObj.getRecursos();

        VariavelNome xvetorVN = new VariavelNome( "xvetor", dadosVN );
        VariavelNome yvetorVN = new VariavelNome( "yvetor", dadosVN );
        VariavelNome arestasVN = new VariavelNome( "arestas", dadosVN );

        StringVar legenda = classeUtil.stringValor( dadosObjRecursos, new VariavelNome( "legenda", dadosVN ) );
        if ( legenda != null )
            dados2D.setLegenda( legenda.getValor() );        

        MatrizVar xvetor = classeUtil.arrayValor( dadosObjRecursos, xvetorVN );
        MatrizVar yvetor = classeUtil.arrayValor( dadosObjRecursos, yvetorVN );
        MatrizVar arestasMat = null;
        try {
            arestasMat = classeUtil.arrayValor( dadosObjRecursos, arestasVN );
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

        double[] vx = new double[ xvetor.getNC() ];
        double[] vy = new double[ yvetor.getNC() ];
        for( int j = 0; j < vx.length; j++ ) {
            Var xel = xvetor.getElemento( 0, j );
            Var yel = yvetor.getElemento( 0, j );                                                        

            vx[ j ] = classeUtil.realValor( xel, new VariavelNome( "("+j+")", xvetorVN ) ).getValor();
            vy[ j ] = classeUtil.realValor( yel, new VariavelNome( "("+j+")", yvetorVN ) ).getValor();                        
        }
                 
        dados2D.setDadosX( vx );
        dados2D.setDadosY( vy );
        
        if ( arestasMat != null ) {
            int[][] arestas = new int[ arestasMat.getNL() ][ arestasMat.getNC() ];
            for( int j = 0; j < arestas.length; j++ ) {
                for( int k = 0; k < arestas[j].length; k++ ) {
                    Var el = arestasMat.getElemento( j, k );

                    arestas[j][k] = (int)classeUtil.realValor( el, new VariavelNome( "("+j+")", arestasVN ) ).getValor();
                }
            }
            dados2D.setArestas( arestas ); 
        }

        dados2D.setPontoConfig( pontoCFGBuilder.build( dadosObjRecursos, dadosVN, classeUtil ) );
        dados2D.setLinhaConfig( linhaCFGBuilder.build( dadosObjRecursos, dadosVN, classeUtil ) );
        
        return dados2D;
    }
    
}
