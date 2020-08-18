package italo.explab.func.string;

import italo.explab.func.Func;

public class StringFuncFactory {
    
    public final static String MAIUSCULAS = "maiusculas";
    public final static String MINUSCULAS = "minusculas";
    public final static String CARACTERE = "caractere";
    public final static String INDICEDE = "indicede";
    public final static String SUBSTRING = "substring";
    public final static String STRTAM = "strtam";

    public final static String[] FUNCS = {
        MAIUSCULAS, MINUSCULAS, CARACTERE, INDICEDE, SUBSTRING, STRTAM
    };
    
    public Func criaNova( Func func ) {                                        
        String nome = func.getNome();                
        return this.criaNova( nome );
    }
    
    public Func criaNova( String nome ) {                                
        switch ( nome ) {
            case MAIUSCULAS:   
                return new MaiusculasStringFunc();
            case MINUSCULAS:
                return new MinusculasStringFunc();
            case CARACTERE:
                return new CaractereStringFunc();
            case INDICEDE:
                return new IndiceDEStringFunc();
            case SUBSTRING:
                return new SubstringStringFunc();
            case STRTAM:
                return new StrTamStringFunc();
            default:
                return null;
        }                
    }
    
}
