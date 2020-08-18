package italo.explab.func.ext.mat;

import italo.explab.InfoMSGs;
import italo.explab.ErroMSGs;
import italo.explab.InterConfig;
import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.AbstractFunc;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.msg.MSGManager;
import italo.explab.msg.CodigoErro;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumeroRealVar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CarregueMatFunc extends AbstractFunc {

    private final String NOME = FuncFactory.CARREGUE;
    private final int QUANT_PARAMS = 1;

    @Override
    public FuncResult exec( FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params ) {        
        if ( params.length != QUANT_PARAMS )
            return new FuncResult( new CodigoErro( this.getClass(), codigo, fno.getI(), ErroMSGs.PARAMS_QUANT_INVALIDA, "1" ) );
            
        MSGManager interMSGManager = executor.getAplic().getMSGManager();
        InterConfig config = executor.getAplic().getConfig();
        
        Var var = params[0].getVar();        
        
        if ( var.getTipo() != Var.STRING ) {
            int pos = params[0].getPos();
            return new FuncResult( new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_STRING_ESPERADO ) );
        }
        String arquivo = ((StringVar)var).getValor();
        
        if ( !new File( arquivo ).isAbsolute() ) {
            arquivo = arquivo.replaceAll( "\\\\", "/" );
            String barra = ( config.getDiretorioCorrente().endsWith( "/" ) ? "" : "/" );
            arquivo = config.getDiretorioCorrente() + barra + arquivo;
        }
  
        BufferedReader in = null;
        MatrizVar matriz;
        String elemento = null;
        try {
            List<List<Double>> dados = new ArrayList();
            int nl = 0;
            int nc = 0;
            
            interMSGManager.enviaInfo( InfoMSGs.ARQ_CARREGANDO, arquivo );
            interMSGManager.envia( "\n" );
            
            in = new BufferedReader( new InputStreamReader( new FileInputStream( arquivo ) ) );
            String linha;
            boolean prim = true;
            while ( ( linha = in.readLine() ) != null ) {
                StringTokenizer st = new StringTokenizer( linha );

                int len = st.countTokens();
                List<Double> lista = new ArrayList( len );
                while( st.hasMoreTokens() )
                    lista.add( Double.parseDouble( elemento = st.nextToken() ) );
                
                dados.add( lista ); 
                
                if ( prim )
                    nc = len;
                
                nl++;
                
                prim = false;
            }
            
            matriz = new MatrizVar( nl, nc );
            for( int k = 0; k < nl; k++ ) {
                for( int k2 = 0; k2 < nc; k2++ ) {
                    double valor = dados.get( k ).get( k2 );
                    matriz.setElemento( k, k2, new NumeroRealVar( valor ) );
                }
            }
            
            interMSGManager.enviaInfo( InfoMSGs.ARQ_CARREGADO );
            interMSGManager.envia( "\n" );
            
            return new FuncResult( matriz, this );
        } catch ( NumberFormatException ex ) {
            return new FuncResult( new CodigoErro( this.getClass(), codigo, fno.getI(), ErroMSGs.MATRIZ_LEITURA_ELEMENTO_REAL_ESPERADO, elemento ) );            
        } catch ( FileNotFoundException ex ) {
            int pos = params[0].getPos();
            return new FuncResult( new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.ARQ_NAO_ENCONTRADO, arquivo ) );            
        } catch ( IOException ex ) {
            int pos = params[0].getPos();
            return new FuncResult( new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.ARQ_ERRO_LEITURA, arquivo ) );            
        } finally {
            if ( in != null ) {
                try {
                    in.close();
                } catch ( IOException ex ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, fno.getI(), ErroMSGs.ARQ_ERRO_FECHAMENTO, arquivo );
                    return new FuncResult( erro );
                }
            }
        }        
    }
    
    @Override
    public Func nova() {
        return new CarregueMatFunc();
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
