package explab;

import italo.explab.PalavrasReservadas;

public interface InterConsts {                
    
    public final String[] NUM_REAL_VALIDOS = {
        "1",
        "-1.02",
        "-1",
        "1.02",
        "1.0",
        "0.000001",
        "-0.000001"            
    };
            
    public final String[] NUM_REAL_INVALIDOS = {
        ".1",
        "a1",      
        "--2",
    }; 
    
    public final Double[] NUM_REAL_ESPERADOS = {
        1.0,
        -1.02,
        -1.0,
        1.02,
        1.0,
        0.000001,
        -0.000001
    };
    
    public final String[] STRING_VALIDOS = {
        "\"XXXX\"",
        "\"\\\\ \t \n a b c\"",
        "\"///    \\\\\\\\    abc\""
    };
    
    public final String[] STRING_INVALIDOS = {
        "\"\\\\",
        "\""
    };
        
    public final String[] STRING_ESPERADOS = {
        "XXXX",
        "\\ \t \n a b c",
        "///    \\\\    abc"
    };
        
    public final String[] N1_INC_N2_VETS_VALIDOS = {
        "[1: 10:  50 ]",
        "[-50 :10  :-1]",
        "[ -1 ^2*(rquad(64) -7) : (5*(10^-1)):2*sen( pi/2) ]",
    };
    
    public final Double[][][] N1_INC_N2_VETS_VALIDOS_ESPERADO = {
        { { 1.0, 11.0, 21.0, 31.0, 41.0 } },
        { { -50.0, -40.0, -30.0, -20.0, -10.0 } },
        { { -1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0 } }
    };
    
    public final String[] N1_INC_N2_VETS_INVALIDOS = {
        "[1,10:100]",
        "1:10:100]",
        "[1:10:100",
        "[1:10,100]"        
    };
    
    public final String[] NUM_EXPS_VALIDAS = {
        "(xx-3)^2 - (yy-2)^2",
        "3",
        "-2^4",
        "(-2)^4",
        "-(4 +3 )",
        "rquad( 3 * (pi^0 +pot( 2, (3^(0))) ) )",
        "1",
        "-1",        
        "2^ 2",
        "(2 +3 )",
        "rquad( 4 )", 
        "pi",        
        "pi/2",
        "-pi",
        "-(4 +rquad(3 * (pi^0 +pot( 2, (3^(0))) ))- 1 )",
        "pi^0*(rquad(4^2*4)-1)^2",
        "1 + -2",
        "1--2",
        "((rquad( 4)^2)-1)*2",
        "2+3^(-1*2*(2/1)*0)",
        "2^-1",
        "2^(-1)",
        "pi^2+a.b(2,3, [ x 1 2] )(2, 3)",
        "pi = 2",
        "i++^2+( 2* (pi = e+=2^3)^4)",
        "a(1,:)'",
        "a(1,:)^2"
    };
    
    public final Double[] NUM_EXPS_VALIDAS_ESPERADO = {
        null,
        3.0,
        -16.0,
        16.0,
        -7.0,
        3.0,
        1.0,
        -1.0,        
        4.0,
        5.0,
        2.0d,
        Math.PI,
        Math.PI / 2.0,
        -Math.PI,
        -6.0,
        49.0,
        -1.0,
        3.0,        
        6.0,
        3.0,
        0.5, 
        0.5,
        null,
        null,
        null,
        null,
        null
    };
    
    public final String[] NUM_EXPS_INVALIDAS = {
        "sen(,)",
        "( 2^2*4 ;)",
        "(( 2^2*4 )",
        "( 3+2)^(4",
        "(((x-2))",
        "1-+-2",
        "1---2",
        "2+2-"            
    };
    
    public final String[] BOOL_EXPS_VALIDAS = {
        "(!falso)",
        "((!verdade))",
        "(!div_por_zero)",
        "( (div_por_zero ) )",
        "((( verdade) ) )",
        "(( falso ))",
        "!div_por_zero & pi > 3",
        "verdade",
        "falso",
        "!verdade",
        "!falso",
        "!verdade & pi > 3",
        "falso & falso | verdade",
        "verdade & verdade | falso",
        "falso & verdade | falso",
        "novo RGB() instanciade RGB",
        "(1<pi & \"xxx\"== \"xxy\")",
        "( ( ( pi<=3 ) | !( pi >= inf ) ) # div_por_zero ) | !div_por_zero",
        "((rquad( 4)^2)-1)*2 > pi",
        "\"\"+pi != \"\"+e",
        "( falso & verdade )",
        "1 > 2 & ((rquad( 4)^2)-1)*2 > pi",
        "( 1 > 2 & ((rquad( 4)^2)-1)*2 > pi | \"\"+pi != \"\"+e )",
        "pi < 1 & !(a.fc(2,1)(1,2).b != e) | (a.fc(2,1)(1,2).b != c) # (3 > f)",
        "( 3>pi & pi^2+a.b(2,3, [ x 1 2] )(2, 3) == 10 )",
        "!eh_maior()"
    };
    
    public final Boolean[] BOOL_EXPS_VALIDAS_ESPERADO = {
        true,
        false,
        true,
        false, 
        true,
        false,
        true,
        true,
        false,
        false,
        true,
        false,
        true,
        true,
        false,
        null,//true,
        false,
        true,
        true,
        true,
        false,
        false,
        true,
        null,
        null,
        null        
    };

    public final String[] BOOL_EXPS_INVALIDAS = {
        "!&verdade",
        "falso !verdade",
        "(2 > 3) & 3",
        "((2>3) & (3 > 2)",
        "verdade | (!2 > 4)",
        "\"1 2 3\""            
    };
    
    public final String[] MATRIZES_REAIS_VALIDAS = {
        "[ 1 2 3, 4]",
        "[ 1; 2]",
        "[ pi, 2 ]",        
        "[ pi^0*(rquad(4^2)-1)^2, -inf, inf; NaN, -pi, NaN]",
        "[ [ 1 2 ]; [ 3 4 ] ]",
        "[ [ 1 2; 3 4]; [1 2; 3 4 ] ]",
        "[ [1 2 3] [1 2 3] ]",
        "[ 1 2; [3 4] ]",
        "[ [1 2;3 4] [ 5 6 ; 7 pi]; [ pi, -inf; NaN 8] [ [9;10] [ NaN; 0 ] ] ]",
        "[1;2;3;4;[5]]",
        "[ [ 1 2 3 4 ]; [5 [6 7] 8] ]",
        "[[1;2;3;4] [5;6;7;8];[1;2;3;4] [5;6;7;8]]",
        "[ 1 2 3 4; 5 [6 7] 8; 9 10 [11 12]; [13 14 15 16] ]",
    };
    
    public final Double[][][] MATRIZES_REAIS_VALIDAS_ESPERADAS = {
        { { 1.0, 2.0, 3.0, 4.0} },
        { { 1.0 }, { 2.0 } },
        { { Math.PI, 2.0 } },
        { { 9.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY }, { Double.NaN, -Math.PI, Double.NaN } },
        { { 1.0, 2.0 }, { 3.0, 4.0 } },
        { { 1.0, 2.0 }, { 3.0, 4.0 }, { 1.0, 2.0 }, { 3.0, 4.0 } },
        { { 1.0, 2.0, 3.0, 1.0, 2.0, 3.0 } },
        { { 1.0, 2.0 }, { 3.0, 4.0 } },
        { { 1.0, 2.0, 5.0, 6.0 }, { 3.0, 4.0, 7.0, Math.PI }, { Math.PI, Double.NEGATIVE_INFINITY, 9.0, Double.NaN }, { Double.NaN, 8.0, 10.0, 0.0 } },
        { { 1.0 }, { 2.0 }, { 3.0 }, { 4.0 } ,{ 5.0 } },
        { { 1.0, 2.0, 3.0, 4.0 }, { 5.0, 6.0, 7.0, 8.0 } },
        { { 1.0, 5.0}, { 2.0, 6.0 }, { 3.0, 7.0 }, { 4.0, 8.0 }, { 1.0, 5.0 }, { 2.0, 6.0 }, { 3.0, 7.0 }, { 4.0, 8.0 } },
        { { 1.0, 2.0, 3.0, 4.0 }, { 5.0, 6.0, 7.0, 8.0 }, { 9.0, 10.0, 11.0, 12.0 }, { 13.0, 14.0, 15.0, 16.0 } },
    };
    
    public final String[] MATRIZES_REAIS_INVALIDAS = {
        "[ 1 2 ;, 4]",
        "[ 1; 2,]",
        "[ pi^2*(rquad(4^2)-1)^2. x, yy; a b c]",
        "[ 1 2; 3",
        "[ 1; [2 3]"
    };
    
    public final String[] MATRIZES_VALIDAS = {
        "[ verdade null NaN; [ rquad(4)^3+(1^(2+2)), pi, \"xxx=\"+pi ] ; (1<pi & \"xxx\"== \"xxy\") [ 1 2 ] ]",
        "[ new RGB() instanceof RGB, [ 1 \"a\" ] verdade; [ verdade falso ] falso ]"
    };
        
    public final String[] MATRIZES_INVALIDAS = {
        "[ verdade null NaN; [ rquad(4)^3+(1^(2+2)), pi, \"xxx=\"+pi ; (1<pi & \"xxx\"== \"xxy\") [ 1 2 ] ]",
        "[ new RGB() instanceof RGB, [ 1 \"a\" ] func; [ verdade falso ] falso ]"
    };
    
    public final String[] CHAMADA_FUNCS_VALIDAS = {
        "rquad( 4)",
        "pot( 2 , 4 )",
        "rquad  ( 4 )",
        "sen( pi/2 )",
        "f()"            
    };
    
    public final Double[] CHAMADA_FUNCS_VALIDAS_ESPERADAS = {
        2.0,
        16.0,
        2.0,
        1.0,
        null            
    };
    
    public final String[] CHAMADA_FUNCS_INVALIDAS = {
        "rquad(4,)",
        "pot(2 ,4",
        "-(2,4)",
        ".POT(2,3)",
        "pot.(2,3)",  
        "()"
    };
        
    public final String[] VARS_VALIDAS = {
        PalavrasReservadas.ESTE,
        "pi",
        "a.vetesp( 2, 3, 11 )( 0, pi ).x(i)",
        "a.b",
        "a.vetesp( 2, 3, 11 )( i, j).x(i)",        
        PalavrasReservadas.ESTE + ".fc( 2,3 )( i, j).x(i).a"
    };
    
    public final Double[] VARS_VALIDAS_ESPERADAS = {
        null,
        Math.PI,
        null,
        null,
        null,
        null
    };
    
    public final String[] VARS_INVALIDAS = {
        "-pi",
        "a.b.()",
        "a.fc( 2, 3)( i, j x(i)",
        "."+PalavrasReservadas.ESTE + ",a"
    };
    
    public final String[] INC_DEC_VALIDOS = {
        "pi++",
        "++pi",
        "pi--",
        "--pi"        
    };
        
    public final String[] INC_DEC_INVALIDOS = {
        "+i",
        "-i",
        "i+",
        "i-",
        "+++i",
        "-+i",
        "+-i",
        "---i"
    };
    
    public final String[] LEITURA_VAR_VALIDAS = {
        "x += rquad(4)",
        "x -=(rquad(4)^3)",
        "este.fun = func( x ) { zxcvdfs; }",
        "fun=func( xx, y){}",
        "fun=func(){}"
    };
    
    public final String[] LEITURA_VAR_INVALIDAS = {
        "fun = func() {",
        "= func() {}",
        "fun func() {}",
        "fun = func() { {xx; }",
        "enquanto = func() {}"
    };
            
    public final String[] STRING_EXP_VALIDAS = {
        "\"1 2 3\"",
        "\"\"+e",
        "\"\"+1+2+\" \"+(2>3)+(\"2*2\"+ \"x\")",
        "\"\"+verdade+\" \"+(2>3 & 3<5)+\"XXX\"",
        "\"xx\"+\" \"+(2^3*(((rquad(4)%2))^4) -1)",
        "\"Instancia de RGB= \"+(novo RGB() instanciade RGB)",
        "a+\" xx\"+(1>2)+\" \"+(2+3)",
        "\"\"+a.b(2,3)(3,4).a(2).x+\" \"+(a instanciade RGB)"
    };
    
    public final String[] STRING_EXP_VALIDAS_ESPERADAS = {
        "1 2 3",
        null,
        "3 falso2*2x",
        "verdade falsoXXX",
        "xx -1",
        null,//"Instancia de RGB= verdade",
        null,
        null
    };
    
    public final String[] STRING_EXP_INVALIDAS = {
        "(1+2+\" \"+2&>3+(\"2*2\"+ \"x\"))",
        "verdade+\" \"+(2>3 && 3<5)+\"XXX\"",
        "+\" \"(2^3*(((rquad(4)%2))^4) -1)",
        "1+2+\" \"+(2>3)+(\"2*2+ \"x\")",
        "verdade+\" +(2>3 & 3<5)+XXX",
        "\"xx+2^3*(((rquad(4)%2))^4) -1)"
    };
    
    public final String[] NOVO_OBJ_VALIDAS = {
        "novo RGB()",
        "novo RGB( pi, 1, 2 )",
        "novo RGB( pi^2+5, 1<2 )",
        "novo RGB( a.r(1, 2^3)(1, 2).a.b^2+5, 1<2 )"
    };
    
    public final String[] NOVO_OBJ_INVALIDAS = {
        "novo RGB",
        "novo RGB( pi 1, 2 )",
        "novo RGB( pi^2+5, 1<2 ",
        "novo RGB( a.r(1, 2^3(1, 2).a.b^2+5, 1<2 )"
    };
    
    public final String[] VAR_ENTRE_PARENTESES_VALIDAS = {
        "( div_por_zero )",
        "((( a)))",
        "((  ( b) ) )"
    };
    
    public final String[] VAR_ENTRE_PARENTESES_INVALIDAS = {
        "( div_por_zero",
        "(( a )",
        "((( b ))"
    };
    
    public final String[] SE_VALIDOS = {
        "se ( 1 > 2 & ((rquad( 4)^2)-1)*2 > pi | \"\"+pi != \"\"+e ){ lajfçalsj; se( 1>2 ){} adfhkh; afsdf }", 
        
        "se verdade exibaln \"xxx\";",
        
        "se ( 1 < 2 & ((rquad( 4)^2)-1)*2 > pi | \"\"+pi != \"\"+e ) exibaln \"XXX\" "
            + "senao se pi > 3 "
                + "se pi > 4 exiba \"XXX\"; "
                + "senao se ( 3 > 2 ) exibaln \"YY\"; "
                + "senao exibaln; "
            + "senao exibaln;",
    
        "se ( 1 > 2 & ((rquad( 4)^2)-1)*2 > pi | \"\"+pi != \"\"+e ){ lajfçalsj; se( 1>2 ){} adfhkh; afsdf }"
            + " senao se ( 1> 2 & 3<2 ) { alskjf; enquanto( pi < a ) { xxxx; adfaf; enquanto( a > b ) { xxxx } } ww; }"
            + " senao se ( 3>pi & pi^2+a.b(2,3, [ x 1 2] )(2, 3) == 10 ) { alskjf; enquanto( pi < a ) { yyyyy; dddd; enquanto( a > b ) { aaaa } } ww; }"
            + " senao { alskjf; enquanto( pi < a ) { zzzzz; fffff; enquanto( a > b ) { xxxxx } } ttttt; }"
    };
    
    public final String[] SE_INVALIDOS = {
        "se ( 1 > 2 & ((rquad( 4)^2)-1)*2 > pi | \"\"+pi != \"\"+e ){ lajfçalsj; se{ 1>2 }{} adfhkh; afsdf }"
            + " senao se ( 1> 2 && 3<2 ) { alskjf; enquanto( pi < a ) { xxxx; adfaf; enquanto( a > b ) { xxxx } } ww; }"
            + " senao se ( 3>pi & pi^2+a.b(2,3, [ x 1 2] )(2, 3) == 10 ) { alskjf; enquanto( pi < a ) { yyyyy; dddd; enquanto( a > b ) { aaaa } } ww; }"
            + " senao { alskjf; enquanto( pi < a ) { zzzzz; fffff; enquanto( a > b ) { xxxxx } } ttttt; }",
        
        "se ( 1 > 2 & ((rquad( 4)^2)-1)*2 > pi | \"\"+pi != \"\"+e ){ lajfçalsj; se{ 1>2 }{} adfhkh; afsdf }"
            + " senao se ( 1> 2 & 3<2 ) { alskjf; enquanto( pi < a ) { xxxx; adfaf; enquanto( a > b ) { xxxx } } ww; }"
            + " senao se ( 3>pi & pi^2+a.b(2,3, [ x 1 2] )(2, 3) == 10 ) { alskjf; enquanto( pi < a ) { { yyyyy; dddd; enquanto( a > b ) { aaaa } } ww; }"
            + " senao { alskjf; enquanto( pi < a ) { zzzzz; fffff; enquanto( a > b ) { xxxxx } } ttttt; }",
        
        "se ( 1 > 2 & ((rquad( 4)^2)-1)*2 > pi | \"\"+pi != \"\"+e ){ lajfçalsj; se{ 1>2 }{} adfhkh; afsdf }"
            + " senao se ( 1> 2 & 3<2 ) { alskjf; enquanto( pi %< a ) { xxxx; adfaf; enquanto( a > b ) { xxxx } } ww; }"
            + " senao se ( 3>pi & pi^2+a.b(2,3, [ x 1 2] )(2, 3) == 10 ) { alskjf; enquanto( pi < a ) { { yyyyy; dddd; enquanto( a > b ) { aaaa } } ww; }"
            + " senao { alskjf; enquanto( pi < a ) { zzzzz; fffff; enquanto( a > b ) { xxxxx } } ttttt; }",
        
        "se ( 1 > 2 & ((rquad( 4)^2)-1)*2 > pi | \"\"+pi != \"\"+e ){ lajfçalsj; se{ 1>2 }{} adfhkh; afsdf }"
            + " senao se ( 1> 2 & 3<2 ) { alskjf; enquanto( pi %< a ) { xxxx; adfaf; enquanto( a > b ) { xxxx } } ww; }"
            + " senao se ( !3>pi & pi^2+a.b(2,3, [ x 1 2] )(2, 3) == 10 ) { alskjf; enquanto( pi < a ) { { yyyyy; dddd; enquanto( a > b ) { aaaa } } ww; }"
            + " senao { alskjf; enquanto( pi < a ) { zzzzz; fffff; enquanto( a > b ) { xxxxx } } ttttt; }"
    };
    
    public final String[] CASO_VALIDOS = {
        "compare (2^3) { "
            + "caso 4 : xxx; enquanto( 2>4) { enquanto( 4<3 ) { xxxx; } } yyy; enquanto( pi > 4 ) { aaaa; se (a > b ) { aaa; } } pare; "
            + " caso 4+4 : aaa; pare;"
            + " caso [ 2>3 1 ] : pare; "
            + "padrao : xxx; yyy;"
            + "}",
        
        "compare (2^3) { "
            + "caso 4 : exibaln \"xxx\"; pare;"
            + " caso 4+4 : exibaln \"yyy\" pare;"
            + " caso [ 2>3 1 ] : exibaln \"zzz\"; pare;"
            + "padrao : exibaln; pare;"
            + "}",
        
        "compare [ 1^2+rquad( 2^3-pi ) false \"xx\"+pi; a b c ] { "
            + " caso x : xxx; enquanto( 2>4) { enquanto( 4<3 ) { xxxx; } } yyy; enquanto( pi > 4 ) { aaaa; se (a > b ) { aaa; } } "
            + " caso a.f( rquad( 2^e-( 4-2) ), 2, false )(1).x.y.f( x ) : aaa; pare;"
            + " caso [ 2>3 1 ] : pare;"
            + "}"
    };
    
    public final String[] CASO_INVALIDOS = {
        "compare 2^3 { "
            + "caso 4 : xxx; enquanto( 2>4) { enquanto( 4<3 ) { xxxx; } } yyy; enquanto( pi > 4 ) { aaaa; se (a > b ) { aaa; } } "
            + " caso 4+4 : aaa; pare;"
            + " caso [ 2>3 1 ] :  {"
            + "padrao : xxx; yyy;"
            + "}",
        
        "compare [ 1^2+rquad( 2^3-pi ) false \"xx\"+pi; a b c ] { "
            + " caso x  : xxx; enquanto( 2>4) { enquanto( 4<3 ) { xxxx; } } yyy; enquanto( pi > 4 ) { aaaa; se (a > b ) { aaa; } } pare;"
            + " caso a.f( rquad( 2^e-( 4-2) ), 2, false )(1).x.y.f( x ) : aaa; pare; "
            + " caso [ 2>3 1 ] : pare;"
            + "",
        
        "compare (2^3) { "
            + "caso 4 : xxx; enquanto( 2>4) { enquanto( 4<3 ) { xxxx; }  yyy; enquanto( pi > 4 ) { aaaa; se (a > b ) { aaa; } }, "
            + " caso 4+4 : aaa; pare;"
            + " caso [ 2>3 1 ] : pare;"
            + "padrao : xxx; yyy; pare;"
            + "}",
        
        "compare (2^3) {"
            + "padrao : xxx; yyy;"
            + "}",
        
        "compare [ 1^2+rquad( 2^3-pi ) false \"xx\"+pi; a b c ] { "
            + " caso x : xxx; enquanto( 2>4) { enquanto( 4<3 ) { xxxx; } } yyy; enquanto( pi > 4 ) { aaaa; se (a > b ) { aaa; } } "
            + "caso a.f( rquad( 2^e-( 4-2) ), 2, false )(1).x.y.f( x ) : aaa; pare;, "
            + " caso [ 2>3 1 ] : pare;"
            + "}"
    };
    
    public final String[] ENQUANTO_VALIDOS = {
        "enquanto 2+3^(-1*2%(2/1)) > i { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "enquanto ( [1 2 false \"\"+pi ] == 2*[1 2 3] ) { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "enquanto 2+3^(-1*2-(2/1)) > i += 2^3 {}",
        "enquanto 2+3^(-1*2/(2/1)) > i++ { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }"        
    };
    
    public final String[] ENQUANTO_INVALIDOS = {
        "enquanto 2+3^(-1*2/(2/1)) > i { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } }",
        "enquanto ( [1 2 false \"\"+pi ] == 2*[1 2 3 ) { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "enquanto 2+3^(-1*2%(2/1)) > i += 2^3",
        "enquanto 2+3^(-1*2-(2/1)) > i++ { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } }"        
    };
   
    public final String[] FACA_ENQUANTO_VALIDOS = {
        "faca { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } } enquanto 2+3^(-1*2%(2/1)) > i",
        "faca { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }enquanto ( [1 2 false \"\"+pi ] == 2*[1 2 3] )",
        "faca {} enquanto 2+3^(-1*2-(2/1)) > i += 2^3",
        "faca { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } } enquanto 2+3^(-1*2/(2/1)) > i++"        
    };
    
    public final String[] FACA_ENQUANTO_INVALIDOS = {
        "faca { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } enquanto 2+3^(-1*2/(2/1)) > i",
        "faca { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }enquanto ( [1 2 false \"\"+pi ] == 2*[1 2 3 )",
        "faca { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } } enquant 2+3^(-1*2%(2/1)) > i += 2^3",
        "faca { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } enquanto 2+3^(-1*2-(2/1)) > i++",        
        "faca { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } enquanto 2+3^(-1*2-(2/1)) > i+"        
    };
    
    public final String[] PARA_VALIDOS = {
        "para( a = \"\"+pi, b=1, c += 2; 2+3^(-1*2%(2/1)) > i; b++, c+=pi ) { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "para ( a = false & 2> 3, b=1^rquad((2*4)^3)-1 , c += 2 ; ( [1 2 false \"\"+pi ] == 2*[1 2 3] ) ; b++, c+=pi )  { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "para( a = false & 2> 3; 2+3^(-1*2-(2/1)) > i += 2^3; i++){}"
    };
    
    public final String[] PARA_INVALIDOS = {
        "para( a = \"\"+pi, b=1, c += 2; 2+3^(-1*2%(2/1)) > i b++, c+=pi ) { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "para( a = \"\"+pi, b=1 c += 2; 2+3^(-1*2%(2/1)) > i; b++, c+=pi ) { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "para( a = \"\"+pi, b=1, c += 2 2+3^(-1*2%(2/1)) > i; b++, c+=pi ) { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "para( a = \"\"+pi, b=1a, c += 2; 2+3^(-1*2%(2/1)) > i; b++, c+=pi ) { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",        
        "para( a = false & 2> 3, b=1^rquad((2*4)^3)-1 , c += 2 ; ( [1 2 false \"\"+pi  == 2*[1 2 3] ) ; b++, c+=pi )  { enquanto( 2>3 ) { se( 3>3 ) { compare 2 { caso 2 : pare; } } } }",
        "para( a = false & 2> 3; 2+3^(-1*2-(2/1)) > i += 2^3; i){}"
    };
    
    public final String[] CMDS_VALIDOS = {
        "ajuda xxx",
        "liste func z",
        "liste var",
        "liste func",
        "liste var z",
        "liste RGB construtor",
        "liste RGB func",
        "liste classe pc",
        "liste cmd l",
        "sair",
        "continue",
        "pare",
        "retorne 0",
        "cd \"xxx\"",
        "cd ../aa/aa",
        "ls",
        "exiba a+\"xxx\"+verdade+(2^rquad(3))+(a instanciade RGB)",
        "exibaln a+\"xxx\"+verdade+(2^rquad(3))+(a instanciade RGB)",
        "leialn a",
        "leialn a:string",
        "use a.b.Area",
        "use Area",
        "script teste.exp",
        "script ../a/b/teste.exp",
        "lance novo Exception()",
        "lance novo RuntimeException()",
        "lance novo E1()",
    };
    
    public final String[] INSTRUCOES_VALIDAS = {
        "func x( z, b ) { retorn 0; }",
        "classe x {}",
        "classe A { a=0; b=1; construtor() {} construtor( a, b) { este.a = 0; } func x() {} este.fun = func() { retorne 0; } }",
        "func x(){}",
        "func y(a){}",        
    };
    
    public final String[] TENTE_CAPTURE_VALIDAS = {
        "tente { exibaln; exiba \"a\"; } capture ( Exception | RuntimeException e ) {} capture ( Exception e ) { exibaln \"XXX\"; } finalize { writeln \"YYYY\"; }",
        "tente { exibaln; exiba \"a\"; } capture ( Exception | RuntimeException e ) {} capture ( Exception e ) { exibaln \"XXX\"; }",
        "tente { exibaln; exiba \"a\"; } capture ( Exception | RuntimeException e ) {} finalize { writeln\"XXX\"; }",
        "tente{}capture(Exception e){}finalize{ writeln\"XXX\"; }"
    };
    
    public final String[] TENTE_CAPTURE_INVALIDAS = {
        "tente { exibaln; exiba \"a\"; } capture ( Exception | RuntimeException ) {} capture ( Exception e ) { exibaln \"XXX\"; } finalize { writeln \"YYYY\"; }",
        "tente { exibaln; exiba \"a\"; } capture ( Exception  RuntimeException e ) {} capture ( Exception e ) { exibaln \"XXX\"; }",
        "tene { exibaln; exiba \"a\"; } capture ( Exception | RuntimeException e ) {} finnalize { writeln\"XXX\"; }",
        "tente { exibaln; exiba \"a\";  capture ( Exception | RuntimeException ) {} capture ( Exception e ) { exibaln \"XXX\"; } finalize { writeln \"YYYY\"; }",
    };
    
}
