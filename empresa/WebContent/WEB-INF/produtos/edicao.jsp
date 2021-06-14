<%@page import="model.Produto"%>
<%@page import="model.ProdutoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%

ProdutoDAO dao = new ProdutoDAO();
Produto p = new Produto();
p.setCodigo(Integer.parseInt(request.getParameter("p_codigo")));
p.setDescricao(request.getParameter("p_descricao"));
p.setPreco(Double.parseDouble(request.getParameter("p_preco")));
p.setFoto(request.getParameter("p_foto"));
session.setAttribute("men", dao.editar(p));
response.sendRedirect("../empresa/produtos");
%>




<!-- 
	<form action="../empresa/produtos" method="get">
	<input type="submit" value="OK">
	</form>
 -->





	

