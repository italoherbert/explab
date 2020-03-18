
var MENSAGEM_FUNDO_ID = "layout-mensagem-fundo";
var MENSAGEM_INFO_ID = "mensagem-info";
var MENSAGEM_ERRO_ID = "mensagem-erro";

function mostrar_mensagem( info_msg, erro_msg, delay, apos_callback, apos_callback_params ) {	
	var mensagem_info = document.getElementById( MENSAGEM_INFO_ID );
	var mensagem_erro = document.getElementById( MENSAGEM_ERRO_ID );													
	mensagem_info.innerHTML = info_msg;
	mensagem_erro.innerHTML = erro_msg;
	
	mostra( MENSAGEM_FUNDO_ID );

	if ( delay != undefined && delay != null ) {	
		setTimeout( function() {	
			esconde( MENSAGEM_FUNDO_ID );
			
			if ( typeof( apos_callback ) == "function" )
				apos_callback.call( this, apos_callback_params );		
		}, delay );
	}
	
}

function esconder_mensagem() {	
	esconde( MENSAGEM_FUNDO_ID );	
}