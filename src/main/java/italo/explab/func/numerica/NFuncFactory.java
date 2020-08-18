package italo.explab.func.numerica;

public class NFuncFactory {
    
    public final static String SOMA = "soma";
    public final static String SUB = "sub";
    public final static String MULT = "mult";
    public final static String DIV = "div";
    public final static String POT = "pot";
    public final static String MOD = "mod";
    public final static String NAO_ESCALAR_MULT = "matmult";
    public final static String IDENTIDADE = "identidade";
    public final static String VET_ESP = "vetesp";    
    public final static String XMALHAGRADE = "xmalhagrade";
    public final static String YMALHAGRADE = "ymalhagrade";
    
    public final static String INTEIRO = "inteiro";
    public final static String FRACIONARIO = "fracionario";
    public final static String ARREDONDA = "arredonda";
    public final static String RANDOM = "random";
    
    public final static String SQRT = "sqrt";
    public final static String LOG = "log";
    public final static String LOG10 = "log10";
    public final static String LOGN = "logn";
    public final static String EXP = "exp";
    public final static String ABS = "abs";
    public final static String SEN = "sen";
    public final static String COS = "cos";
    public final static String TAN = "tan";
    public final static String ASEN = "asen";
    public final static String ACOS = "acos";
    public final static String ATAN = "atan";
    public final static String ATAN2 = "atan2";
    
    public final static String POW = "pow";
    public final static String RQUAD = "rquad";
    
    public final static String RADIANOS = "radianos";
    public final static String GRAUS = "graus";
    
    public final static String[] FUNCS = {
        SQRT, LOG, LOG10, LOGN, EXP, ABS, POT, SEN, COS, TAN, ASEN, ACOS, ATAN, ATAN2,  
        VET_ESP, IDENTIDADE, NAO_ESCALAR_MULT, XMALHAGRADE, YMALHAGRADE,
        INTEIRO, FRACIONARIO, ARREDONDA, RANDOM, RADIANOS, GRAUS,
        SOMA, SUB, MULT, DIV, MOD
    };
    
    public NFunc criaNova( NFunc nfunc ) {                                        
        String nome = nfunc.getNome();                
        return this.criaNova( nome );
    }
    
    public NFunc criaNova( String nome ) {                                
        switch ( nome ) {
            case POT:
            case POW:
                return new PotNFunc();
            case ABS:
                return new AbsNFunc();
            case SQRT:
            case RQUAD:
                return new RQuadNFunc();
            case LOG:
                return new LogNFunc();
            case LOG10:
                return new Log10NFunc();
            case LOGN:
                return new LogNNFunc();
            case EXP:
                return new ExpNFunc();
            case SEN:
                return new SenNFunc();
            case COS:
                return new CosNFunc();
            case TAN:
                return new TanNFunc();
            case ASEN:
                return new ASenNFunc();
            case ACOS:
                return new ACosNFunc();
            case ATAN:
                return new ATanNFunc();
            case ATAN2:
                return new ATan2NFunc();
            case INTEIRO:
                return new InteiroNFunc();
            case FRACIONARIO:
                return new FracionarioNFunc();
            case ARREDONDA:
                return new ArredondaNFunc();
            case RANDOM:
                return new RandomNFunc();
            case VET_ESP:
                return new VetEspNFunc();
            case IDENTIDADE:
                return new IdentidadeNFunc();
            case NAO_ESCALAR_MULT:
                return new NaoEscalarMultNFunc();
            case XMALHAGRADE:
                return new XMalhaGradeNFunc();
            case YMALHAGRADE:
                return new YMalhaGradeFunc();
            case SOMA:
                return new SomaNFunc();
            case SUB:
                return new SubNFunc();
            case MULT:
                return new MultNFunc();
            case DIV:
                return new DivNFunc();
            case MOD:
                return new ModNFunc();                            
            case RADIANOS:
                return new RadianosNFunc();
            case GRAUS:
                return new GrausNFunc();
            default:
                return null;
        }                
    }
    
}
