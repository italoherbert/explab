
package italo.explab.func;

import italo.explab.func.numerica.NFuncFactory;
import italo.explab.func.ext.mat.CarregueMatFunc;
import italo.explab.func.ext.FExecFunc;
import italo.explab.func.ext.GetArqCharsetFunc;
import italo.explab.func.ext.ParaStrFunc;
import italo.explab.func.ext.mat.SalveMatFunc;
import italo.explab.func.ext.SetArqCharsetFunc;
import italo.explab.func.ext.StrParaBoolFunc;
import italo.explab.func.ext.StrParaRealFunc;
import italo.explab.func.ext.TipoDeFunc;
import italo.explab.func.ext.mat.MatCapNCFunc;
import italo.explab.func.ext.mat.MatCapNLFunc;
import italo.explab.func.ext.mat.MatNCFunc;
import italo.explab.func.ext.mat.MatNLFunc;
import italo.explab.func.ext.mat.PreencheMatrizFunc;
import italo.explab.func.ext.mat.TranspostaFunc;
import italo.explab.func.ext.mat.UnsPreencheMatrizFunc;
import italo.explab.func.ext.mat.ZerosPreencheMatrizFunc;
import italo.explab.func.plot.Plot3DFunc;
import italo.explab.func.plot.PlotFunc;
import italo.explab.func.string.StringFuncFactory;

public class FuncFactory {
    
    public final static String FEXEC = "fexec";
    public final static String TIPODE = "tipode";    
    public final static String GETCHARSET = "getcharset";
    public final static String SETCHARSET = "setcharset";    
    public final static String SALVE = "salve";
    public final static String CARREGUE = "carregue";   
    public final static String PLOT = "plot";
    public final static String PLOT3D = "plot3d";
    
    public final static String TRANSPOSTA = "transposta";
    
    public final static String PREENCHE = "preenche";
    public final static String ZEROS = "zeros";
    public final static String UNS = "uns";
    
    public final static String STRPARAREAL = "strparareal";
    public final static String STRPARABOOL= "strparabool";
    public final static String PARASTR = "parastr";
        
    public final static String MAT_NL = "matnl";
    public final static String MAT_NC = "matnc";    
    public final static String MAT_CAP_NL = "matcapnl";
    public final static String MAT_CAP_NC = "matcapnc";        

    public final static String IMP_ERROS = "imprimeErros";

    public final static String[] FUNCS = {
        FEXEC, TIPODE, GETCHARSET, SETCHARSET, 
        STRPARAREAL, PARASTR, STRPARABOOL,     
        SALVE, CARREGUE, 
        MAT_NL, MAT_NC, MAT_CAP_NL, MAT_CAP_NC,
        PLOT, PLOT3D, PREENCHE, ZEROS, UNS
    };
    
    private final NFuncFactory numFuncFactory = new NFuncFactory();
    private final StringFuncFactory stringFuncFactory = new StringFuncFactory();
    
    public Func criaNova( Func func ) {                                        
        String nome = func.getNome();                
        return this.criaNova( nome );
    }
    
    public Func criaNova( String nome ) {                                
        switch ( nome ) {
            case FEXEC:   
                return new FExecFunc();
            case TIPODE:
                return new TipoDeFunc();
            case GETCHARSET:
                return new GetArqCharsetFunc();
            case SETCHARSET:
                return new SetArqCharsetFunc();
            case STRPARAREAL:
                return new StrParaRealFunc();
            case STRPARABOOL:
                return new StrParaBoolFunc();
            case PARASTR:
                return new ParaStrFunc();
            case SALVE:
                return new SalveMatFunc();
            case CARREGUE:
                return new CarregueMatFunc();                
            case PLOT:
                return new PlotFunc();
            case PLOT3D:
                return new Plot3DFunc();
            case PREENCHE:
                return new PreencheMatrizFunc();
            case ZEROS:
                return new ZerosPreencheMatrizFunc();
            case UNS:
                return new UnsPreencheMatrizFunc();
            case MAT_NL:
                return new MatNLFunc();
            case MAT_NC:   
                return new MatNCFunc();
            case MAT_CAP_NL:
                return new MatCapNLFunc();
            case MAT_CAP_NC:
                return new MatCapNCFunc();
            case TRANSPOSTA:
                return new TranspostaFunc();
            default:
                return null;
        }                
    }

    public NFuncFactory getNumFuncFactory() {
        return numFuncFactory;
    }

    public StringFuncFactory getStringFuncFactory() {
        return stringFuncFactory;
    }
    
}
