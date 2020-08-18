
function mostra( el_id ) {
	var el = document.getElementById( el_id );
	el.style.display = "block";
	el.style.visibility = "visible";
}

function esconde( el_id ) {
	var el = document.getElementById( el_id );
	el.style.display = "none";
	el.style.visibility = "hidden";
}

function formataData( d ) {
	var dia = d.getDate();
	dia = ( dia < 10 ? "0"+dia : ""+dia );
	
	var mes = (d.getMonth()+1);
	mes = ( mes < 10 ? "0"+mes : ""+mes );
	
	return dia + "/" + mes + "/" + d.getFullYear() + " " +
			d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
}

function getURLParams( url ) {
	var params = new Array();
	
	var i = url.indexOf( "?" );
	if ( i != -1 ) {
		var params_str = url.substring( i+1 );
		var j = params_str.indexOf( "#" );
		if ( j != -1 )
			params_str = params_str.substring( 0, j );
		var pars = params_str.split( "&" );		
		for( var k = 0; k < pars.length; k++ ) {
			var par = pars[ k ];
			var p = par.split( "=" );
			if ( p.length == 2 )
				params[ p[0] ] = p[1];				 			
		}		
	}
	return params;
}

/**************** FUNÇÕES - XML ******************/

function stringParaXML( str ) {
	try {
		var xmlDoc = new ActiveXObject( "Microsoft.XMLDOM" );
		xmlDoc.async = false;				
		xmlDoc.loadXML( str );
		return xmlDoc;
	} catch ( e ) {
		return ( new DOMParser() ).parseFromString( str, "text/xml" );
	}

}

function xmlParaString( xmldoc ) {	
	if ( window.ActiveXObject ) {
		return xmldoc.xml;
	} else {	
		return new XMLSerializer().serializeToString( xmldoc );						
	}
	
}

function nodeValue( parent, node_name ) {
	var el = parent.getElementsByTagName( node_name )[0];	
	if( el == undefined || el == null )
		return undefined;
	
	var value = "";
	if( el.firstChild != undefined && el.firstChild != null ) {
		var nodeValue = el.firstChild.nodeValue;
		if( nodeValue != undefined && nodeValue != null )
			value = nodeValue;
	}	
	if( value == undefined || value == null || value == 'null' )
		return "";
	return value.replace( /\n/gi, "<br />" );	
}