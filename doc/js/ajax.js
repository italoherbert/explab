
function carrega_recurso( id, recurso, params ) {
	var xmlhttp = novoXMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if ( xmlhttp.readyState == 4 ) {
			if ( xmlhttp.status == 200 ) {		
				if ( id != null && id != undefined )
					document.getElementById( id ).innerHTML = xmlhttp.responseText;												
				
				if ( params )
					if ( typeof( params.sucesso ) == "function" )
						params.sucesso.call( this, xmlhttp );				
			} else {
				if ( params )
					if ( typeof( params.erro ) == "function" )
						params.erro.call( this, xmlhttp );
			}
		}
	}	
	xmlhttp.open( "GET", recurso, true );	
	xmlhttp.send( null );
}

function novoXMLHttpRequest() {
	if (window.ActiveXObject) {
		var versoes = ["Microsoft.XMLHttp", "MSXML2.XMLHttp",
						"MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp.4.0",
						"MSXML2.XMLHttp.5.0", "MSXML2.XMLHttp.6.0"];
		for (var i=0; i<versoes.length; i++) {
			try {
				var xmlHttp = new ActiveXObject(versoes[i]);
				return xmlHttp;
			} catch (ex) {}
		}
	} else if (window.XMLHttpRequest) {			
		return new XMLHttpRequest();
	} else {
		return null;
	}
}