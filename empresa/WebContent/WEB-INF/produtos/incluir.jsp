
<%@page import="java.io.File"%>
<%@page import="services.Diretorio"%>
<%@page import="java.util.Iterator"%>

<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="model.Produto"%>
<%@page import="model.ProdutoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%
String acao = "";
ProdutoDAO dao = new ProdutoDAO();
Produto p = new Produto();

//desempactar o multipart
FileItemFactory fif = new DiskFileItemFactory();
ServletFileUpload sfu = new ServletFileUpload(fif);

//Copiar todos os itens do pacote e numa lista

try{
	String path = getServletContext().getRealPath("/")+"fotos/";
	System.out.println(path); //caminho onde as imagens do produto serão armazenadas
	List<FileItem> itens = sfu.parseRequest(request);
	Iterator<FileItem> iterator = itens.iterator();
	while(iterator.hasNext()){ //percorre todos os itens da lista FileItem
		FileItem iten = (FileItem) iterator.next();//pega o item atual
		if(iten.getFieldName().equals(("p_codigo"))){
			p.setCodigo(Integer.parseInt(iten.getString()));
		}
		else if(iten.getFieldName().equals(("p_acao"))){
			acao = iten.getString();
		}
		else if(iten.getFieldName().equals(("p_codigo"))){
			p.setDescricao(iten.getString());
		}
		else if(iten.getFieldName().equals(("p_preco"))){
			p.setPreco(Double.parseDouble(iten.getString()));
		}
		
		else if(iten.getFieldName().equals(("p_foto"))){
			//caminho + nome do arquivo
			String fileName = Diretorio.getFileNameFromPath(iten.getName());
			p.setFoto(fileName);
			//gravar a imagem na pasta do servidor
			File file = new File(path+fileName);
		}
		
	}
	if(acao.equals("editar")){//alterar a imagem do produto
		session.setAttribute("men", dao.editar(p));
	}
	else{
		session.setAttribute("men", dao.incluir(p));
	}
	
	
}
catch(Exception e){
	System.out.println("Erro: "+e);
}

//session.setAttribute("men", dao.incluir(p));
response.sendRedirect("../empresa/produtos");
%>




<!-- 
	<form action="../empresa/produtos" method="get">
	<input type="submit" value="OK">
	</form>
 -->





	

