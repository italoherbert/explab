# explab

O ExpLab é um interpretador e linguagem de programação: interpretada, orientada a objetos e a matemática. Ela suporta as estruturas básicas de uma linguagem de programação OO como, por exemplo, os comandos: SE, COMPARE/CASO, ENQUANTO, PARA... inclusive, também suporta definição e chamada de funções, expressões aritméticas e booleans, classes, objetos como variáveis instâncias de classes, herança de classes, tratamento de exceções, organização das classes em pacotes, etc. Inclusive, como linguagem de programação orientada a matemática, a linguagem ExpLab tem suporte a operações com vetores e matrizes e plotagem e visualização de dados e funções matemáticas em 2D e 3D.

Trata-se de um software semelhante a outros já consagrados como: MatLab, SciLab e GNU Octave.

Ao utilizar os softwares já bem sucedidos e semelhantes ao ExpLab, pude perceber que, suas linguagens de script parecem não ser Orientadas a Objetos. Por isso, o suporte a orientação a objetos pelo ExpLab parece ser um diferencial do interpretador. Inclusive, considero o ExpLab como uma ótima escolha para o ensino de programação básica por suportar todos os recursos de linguagens como, por exemplo, a linguagem pascal e ter comandos e palavras chaves em português.

O ExpLab tem uma sintaxe muito parecida com a das linguagems C, C++ e Java. É uma linguagem case insensitive e as variáveis não precisam ser declaradas com tipos definidos. Os tipos das variáveis são detectados automaticamente pelo interpretador, conforme o valor, ou expressão atribuido a variável.

Por enquanto, essa é a descrição do projeto que já tem pronta uma versão funcional com todos os recursos descritos acima. Estou publicando através do GitHub, um software interpretador e linguagem de programação já finalizado. Claro, sempre há algo para melhorar ou acrescentar, então, pretendo dar continuidade ao projeto e, talvez conseguir colaboradores interessados no projeto.

# Exemplo 1

<p>Calcula o fatorial de um número de duas formas: recursiva (primeira função: "fatorial1") e imperativa (segunda função: "fatorial2")</p>
  
<pre>
func fat1( n ) {
    fat = 1;
    enquanto( n > 0 ) {
        fat *= n;
        n--;
    }
	retorne fat;
}

func fat2( n ) {
    fat = 1;
    enquanto ( n > 0 )
       fat &#42;= n--;
    retorne fat;
}

func fat3( n ) {
    fat = 1;
    para ( i=2; i <= n; i++ )
        fat &#42;= i;
    retorne fat;
}

func fat4( n ) {  // recursiva
    se n < 1 
        retorne 1;
    retorne n * fat4( n-1 );
}

exiba "Informe um número: ";
leialn num;

exibaln "Fat1="+fat1( num );
exibaln "Fat2="+fat2( num );
exibaln "Fat3="+fat3( num );
exibaln "Fat4="+fat4( num );
</pre>

# Exemplo 2

<p>Exemplo de operação com matrizes</p>

<pre>
a = [ 1 2 3; 4 5 6 ]^rquad(abs( 2-2*3 )) + (2* ((pot( 2, 4 )*PI^0)*1.0)*3)/4;  
b = a( 1,: );

exibaln a;   // exibe a matriz &#91; 25 28 33; 40 49 60 &#93;
exibaln b;   // exiba a matriz (vetor) &#91; 40 49 60 &#93;
</pre>

# Exemplo 3

<p>Lê um número e verifica se número é primo</p>

<pre>
terminar = verdade;
faca &#123;
    exiba "Informe um numero: ";
    leialn s&#58;string;

    tente &#123;        
        n = strparareal( s );

        se n % 2 == 0 
            exibaln "O número "+n+" é par!";
        senao exibaln "O número "+n+" é impar!";

        terminar = verdade;
    &#125; capture ( RealFormatoException e ) &#123;
        exibaln "Você informou um valor não real. Deseja informar outro número?";
        exibaln " (S/Sim)   - Sim";
        exibaln " (N/Outro) - Não";
        exiba ">> ";
        leialn resp:string;
        resp = maiusculas( resp );
        terminar = ( resp != 'S' &amp; resp != 'SIM' ); 		
    &#125;	
&#125; enquanto ( !terminar );
</pre>
