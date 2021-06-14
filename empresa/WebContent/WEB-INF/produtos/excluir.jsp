<%@page import="model.ProdutoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%

	ProdutoDAO dao = new ProdutoDAO();
	int codigo = Integer.parseInt(request.getParameter("p_codigo"));
	//out.println(dao.ativar(codigo, 0));
	
	session.setAttribute("men", dao.ativar(codigo, 0));
	response.sendRedirect("../empresa/produtos");
%>




<!-- 
	<form action="../empresa/produtos" method="get">
	<input type="submit" value="OK">
	</form>
 -->
