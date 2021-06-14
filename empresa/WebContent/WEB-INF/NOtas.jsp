<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Recimento de notas e faltas</title>
</head>
<body>

<% 
	String[] notas = request.getParameterValues("nota");
	for(int i=0; i<notas.length; i++){
		out.println("Nota: "+(i+1)+" - "+notas[i]+"<br>");
	}
	
	out.println("<br><br>");
	String[] faltas = request.getParameterValues("faltas");
	for(int i=0; i<notas.length; i++){
		out.println("Nota: "+(i+1)+" - "+faltas[i]+"<br>");
	}
	
	
%>

</body>
</html>