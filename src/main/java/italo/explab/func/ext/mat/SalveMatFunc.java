package italo.explab.func.ext.mat;

import italo.explab.ErroMSGs;
import italo.explab.InfoMSGs;
import italo.explab.InterConfig;
import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.AbstractFunc;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.msg.InterImpr;
import italo.explab.msg.MSGManager;
import italo.explab.msg.CodigoErro;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.RealVar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SalveMatFunc extends AbstractFunc {

    private final String NOME = FuncFactory.SALVE;
    private final int QUANT_PARAMS = -1;
    
    private final int NUM_ESPS_PADRAO = 4;

    @Override
    public FuncResult exec(FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params) {        
        if ( params.length < 2 || params.length > 3 )
            return new FuncResult( new CodigoErro( this.getClass(), codigo, fno.getI(), ErroMSGs.PARAMS_QUANT_INVALIDA, "2 ou 3" ) );
                
        InterImpr impr = executor.getAplic().getImpr();
        MSGManager interMSGManager = executor.getAplic().getMSGManager();
        InterConfig config = executor.getAplic().getConfig();
        
        Var var1 = params[0].getVar();        
        Var var2 = params[1].getVar();              
        
        if ( var1.getTipo() != Var.STRING ) {
            int pos = params[0].getPos();
            return new FuncResult( new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_STRING_ESPERADO ) );
        }
        
        if ( var2.getTipo() != Var.MATRIZ || var2.getTipoCompativel() != Var.TC_NUMERICO ) {
            int pos = params[1].getPos();
            return new FuncResult( new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_MATRIZ_REAL_ESPERADO ) );
        }
        
        int numEsps = NUM_ESPS_PADRAO;
        if ( params.length == 3 ) {
            Var var3 = params[2].getVar();
            if ( var3.getTipo() != Var.REAL ) {
                int pos = params[2].getPos();
                return new FuncResult( new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_STRING_ESPERADO ) );
            }
            numEsps = (int)((RealVar)var3).getValor();
        }
        
        String arquivo = ((StringVar)var1).getValor();
        Var[][] matriz = ((MatrizVar)var2).getMatriz();
                
        if ( !new File( arquivo ).isAbsolute() ) {
            arquivo = arquivo.replaceAll( "\\\\", "/" );
            String barra = ( config.getDiretorioCorrente().endsWith( "/" ) ? "" : "/" );
            arquivo = config.getDiretorioCorrente() + barra + arquivo;
        }
        
        PrintStream out = null;
        try {
            out = new PrintStream( new FileOutputStream( arquivo ) );
            
            int maxlen = 0;
            for( int k = 0; k < matriz.length; k++ ) {
                for( int k2 = 0; k2 < matriz[k].length; k2++ ) {
                    String valor = impr.formataVar( matriz[k][k2] );
                    int len = valor.length();
                    if ( len > maxlen )
                        maxlen = len;
                }
            }
            
            interMSGManager.enviaInfo( InfoMSGs.ARQ_SALVANDO );

            for( int k = 0; k < matriz.length; k++ ) {                                              
                for( int k2 = 0; k2 < matriz[k].length; k2++ ) {
                    String valor = impr.formataVar( matriz[k][k2] );
                    int len = valor.length();
                    int dif = maxlen-len;
                    int nesp = numEsps + dif;
                    for( int k3 = 0; k3 < nesp; k3++ )
                        out.print( ' ' );
                    out.print( valor );
                }
                out.print( "\n" );
            }            
            
            interMSGManager.enviaInfo( InfoMSGs.ARQ_SALVO, arquivo );
            interMSGManager.envia( "\n" ); 
            
            return new FuncResult( this );
        } catch ( IOException ex ) {
            int pos = params[0].getPos();
            return new FuncResult( new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.ARQ_NAO_ENCONTRADO, arquivo ) );            
        } finally {
            if ( out != null )
                out.close();                           
        }
    }
    
    @Override
    public Func nova() {
        return new SalveMatFunc();
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
