<%@page import="model.ProdutoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mostra a rela��o de produtos na WEB</title>
</head>
<body>
	<table border="1">

		<tr>
			<td>Codigo</td>
			<td>Descri��o</td>
			<td>Pre�o</td>
			<td>Ativo</td>
			<td>Foto</td>
		</tr>
		<%	
			ProdutoDAO dao = new ProdutoDAO();
			dao.get("select * from produtos where descricao like ?", "");
			
		
		
		
			
			
			
			%>
	</table>
	
	
</body>
</html>