
var LAYOUT_DIR = "layout/";

var PAG_NORTE = LAYOUT_DIR + "norte.html";
var PAG_MENU = LAYOUT_DIR + "menu.html";
var PAG_SUL = LAYOUT_DIR + "sul.html";

var PAG_DIR = "paginas/";

var PAG_INICIO_RAPIDO = PAG_DIR + "inicio-rapido.html";

var PAG_TIPOS_DE_DADOS = PAG_DIR + "tipos-de-dados.html";
var PAG_EXPS_ARITMETICAS = PAG_DIR + "expressoes-aritmeticas.html";
var PAG_EXPS_BOOLEANAS = PAG_DIR + "expressoes-booleanas.html";
var PAG_INCS_DECS = PAG_DIR + "incrementos-e-decrementos.html";
var PAG_VETORES_E_MATRIZES = PAG_DIR + "vetores-e-matrizes.html";
var PAG_LEITURA_E_ESCRITA = PAG_DIR + "leitura-e-escrita.html";
var PAG_EST_DECISAO = PAG_DIR + "estruturas-de-decisao.html";
var PAG_EST_REPETICAO = PAG_DIR + "estruturas-de-repeticao.html";
var PAG_FUNCOES = PAG_DIR + "funcoes.html";
var PAG_OO = PAG_DIR + "orientacao-a-objetos.html";
var PAG_TRATAMENTO_DE_EXCECOES = PAG_DIR + "tratamento-de-excecoes.html";
var PAG_GRAFICOS_E_PLOTAGEM = PAG_DIR + "graficos-e-plotagem.html";


var PAG_COMANDOS = PAG_DIR + "comandos.html";
var PAG_VARS_NATIVAS = PAG_DIR + "vars-nativas.html";
var PAG_FUNCS_NATIVAS = PAG_DIR + "funcs-nativas.html";

var CLASSES_DIR = PAG_DIR + "classes/";
var PLOT2D_CLASSES_DIR = CLASSES_DIR + "plot2d/";
var PLOT3D_CLASSES_DIR = CLASSES_DIR + "plot3d/";

var PAG_CLASSE_RGB = CLASSES_DIR + "RGB.html";
var PAG_CLASSE_JANELA_CONFIG = CLASSES_DIR + "JanelaConfig.html";
var PAG_CLASSE_GRADE_CONFIG = CLASSES_DIR + "GradeConfig.html";
var PAG_CLASSE_PONTO_CONFIG = CLASSES_DIR + "PontoConfig.html";
var PAG_CLASSE_LINHA_CONFIG = CLASSES_DIR + "LinhaConfig.html";
var PAG_CLASSE_POLIGONO_CONFIG = CLASSES_DIR + "PoligonoConfig.html";

var PAG_CLASSE_PC2D = PLOT2D_CLASSES_DIR + "PC2D.html";
var PAG_CLASSE_DADOS2D = PLOT2D_CLASSES_DIR + "Dados2D.html";
var PAG_CLASSE_FUNC2D = PLOT2D_CLASSES_DIR + "Func2D.html";

var PAG_CLASSE_PC3D = PLOT3D_CLASSES_DIR + "PC3D.html";
var PAG_CLASSE_DADOS3D = PLOT3D_CLASSES_DIR + "Dados3D.html";
var PAG_CLASSE_FUNC3D = PLOT3D_CLASSES_DIR + "Func3D.html";
var PAG_CLASSE_SUPERFICIE3D = PLOT3D_CLASSES_DIR + "Superficie3D.html";



var NORTE_SPAN_ID = "norte-span";
var MENU_SPAN_ID = "menu-span";
var SUL_SPAN_ID = "sul-span";
var PAG_SPAN_ID = "pagina-span";	

var COMANDOS_OPCOES_ID = "comandos-opcoes-id";
	
window.onload = function() {
	carrega_recurso( NORTE_SPAN_ID, PAG_NORTE );
	carrega_recurso( MENU_SPAN_ID, PAG_MENU, {
		sucesso : function( xmlhttp ) {
			//menu_op_clique( COMANDOS_OPCOES_ID );
		}
	} );
	carrega_recurso( SUL_SPAN_ID, PAG_SUL );
	
	var params = getURLParams( window.location.href );
	var pagina = params[ 'pag' ];
	var ancora = params[ 'aid' ];
				
	if ( pagina != undefined && pagina != null ) {
        carrega_pagina( pagina, ancora );
	} else {
		carrega_pagina( PAG_INICIO_RAPIDO );
	}	
}

function menu_op_clique( op_id ) {
	var el = document.getElementById( op_id );
	var display = el.style.display;
	if ( display == 'block' ) {
		el.style.display = 'none';
		el.style.visibility = 'hidden';
	} else {
		el.style.display = 'block';
		el.style.visibility = 'visible';
	}
}

function carrega_pagina( pagina, ancora_nome_id ) {
	carrega_recurso( PAG_SPAN_ID, pagina, {
		sucesso : function( xmlhttp ) {
			if ( ancora_nome_id != undefined && ancora_nome_id != null ) {
				var top = document.getElementById( ancora_nome_id ).offsetTop;
				window.scrollTo( 0, top );
			}
		},
		erro : function( xmlhttp ) {
			alerta( 'Página não encontrada: '+pagina );
		}
	} );
}