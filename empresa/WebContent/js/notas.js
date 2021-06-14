function preencher() {
	
	var f = document.notas.falta;
	var i = 0;
	while(i < f.length){
		f[i].value=0;
		i++;
	}
	
}

function validar(){
	
	var f = document.notas.nota;
	var i = 0;
	while(i < f.length){
		if(f[i].value.length==0 || f[i].value < 0 || f[i].value>10){
			alert("Todas as notas devem estar entre 0 e 10")
			return;
		}
		i++;
	}
	document.notas.submit();
}	
