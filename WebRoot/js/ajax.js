function ajaxFunction(){
	var xmlHttp;
	try {
		xmlHttp=new XMLHttpRequest();
	} catch (e) {
		//IE6
		try{//Internet Explorer
			xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			alert("xxx");
		}
	}
	return xmlHttp;
}