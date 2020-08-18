package italo.explab;

public class PalavrasReservadas {

    public final static String VERDADE = "verdade";
    public final static String FALSO = "falso";
    public final static String FUNC = "func";
    public final static String RETORNE = "retorne";
    public final static String CLASSE = "classe";
    public final static String PACOTE = "pacote";
    public final static String CONSTRUTOR = "construtor";
    public final static String EXTENDE = "extende";
    
    public final static String SE = "se";
    public final static String SENAO = "senao";
    public final static String COMPARE = "compare";
    public final static String CASO = "caso";
    public final static String PADRAO = "padrao";
    public final static String PARA = "para";
    public final static String ENQUANTO = "enquanto";
    public final static String FACA = "faca";
    public final static String ESTE = "este";
    public final static String SUPER = "super";
    public final static String BASE = "base";
    public final static String INSTANCIADE = "instanciade";
    public final static String TENTE = "tente";
    public final static String CAPTURE = "capture";
    public final static String FINALIZE = "finalize";
    public final static String TRATAR = "tratar";
    public final static String LANCE = "lance";
    
    public final static String USE = "use";
    public final static String SCRIPT = "script";
    public final static String LISTE = "liste";
    public final static String EXIBA = "exiba";
    public final static String EXIBALN = "exibaln";
    public final static String LEIALN = "leialn";
    public final static String CD = "cd";
    public final static String LS = "ls";
    public final static String AJUDA = "ajuda";
    public final static String PARE = "pare";
    public final static String CONTINUE = "continue";    
    public final static String SAIR = "sair";
    public final static String LIMPETELA = "limpetela";
    public final static String CMD = "cmd";
    
    public final static String VAR = "var";
    public final static String NOVO = "novo";
    public final static String STRING = "string";
    public final static String NULL = "null";
   
    public final static String[] PALAVRAS_RESERVADAS = {
        VERDADE, FALSO, RETORNE, VAR, CLASSE, PACOTE, SE, SENAO, COMPARE, CASO, PADRAO, PARA, ENQUANTO, FACA, 
        FUNC, NOVO, STRING, NULL, ESTE, SUPER, CONSTRUTOR, 
        LISTE, AJUDA, CD, LS, SCRIPT, USE, SAIR, LIMPETELA,  
        EXIBA, EXIBALN, LEIALN, LANCE, TRATAR, PARE, CONTINUE, EXTENDE, TENTE, CAPTURE, FINALIZE
    };
        
    public boolean verificaSePalavraReservada( String palavra ) {
        for( String p : PALAVRAS_RESERVADAS )
            if ( p.equalsIgnoreCase( palavra ) )
                return true;
        return false;
    }
        
}
