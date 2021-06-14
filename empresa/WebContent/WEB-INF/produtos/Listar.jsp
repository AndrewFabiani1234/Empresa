<%@page import="model.Produto"%>
<%@page import="java.util.List"%>
<%@page import="model.ProdutoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gerenciamento de Produtos</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script>
function buscar(){
	$.ajax({
		url:"../empresa/produtos",
		method:"POST",
		data:{
			pagina:"Listar.jsp",
			p_descricao:document.getElementById("p_descricao").value
		}
	})
	.done(function(data){ //data corresponde a pagina de retorno
		console.log("sucesso! ", data);
		$('#principal').html(data);
		document.getElementById("p_descricao").focus();
		document.getElementById("p_descricao").value = texto; //atualiza a DIV principal com os dados
		
	})
	.fail(function (erro){
			console.log("Ocorreu um erro ", erro);
		});
}

function setEdicao(produto){ //no formato String (.csv)
	dados = produto.split(";") //1;Sabonete;3.53;true;foto
	document.getElementById("codigo_edicao").value =  dados[0];
	document.getElementById("descricao_edicao").value =  dados[1];
	document.getElementById("preco_edicao").value =  dados[2];
	document.getElementById("foto_edicao").src = "../empresa/fotos/"+dados[4];
	
	document.getElementById("codigo_imagem").value =  dados[0];
	document.getElementById("descricao_imagem").value =  dados[1];
	document.getElementById("preco_imagem").value =  dados[2];
	
}
function setExclusao(produto){ //no formato String (.csv)
	dados = produto.split(";") //dados é um vetor contendo os dados do produto
	document.getElementById("descricao_exclusao").value =  dados[0] + " - " + dados[1];
	document.getElementById("codigo_exclusao").value =  dados[0];
}




</script>
<%
	String desc = request.getParameter("p_descricao");
	String men = (String)session.getAttribute("men");
	if(men!=null){	
%>
<div class="modal" id="modal_mensagem"> 
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<h3>Informação!</h3>
			</div>
			<div class="modal-body">
				<input type="button" style="width:100%;background-color:orange" value="<%=men%>">
			</div>
			<div class="modal-footer">
				<button onclick="document.getElementById('modal_mensagem').style.display = 'none'" class="btn btn-secondary">OK</button>
			</div>
		</div>
	</div>
</div>
<script>document.getElementById("modal_mensagem").style.display = 'inline'</script>
<% 		
		session.removeAttribute("men"); //remover da sessão
	}
%>
</head>
<body>

<div class="container" style="margin-left:3px" id="principal">
	<h3>Gerenciamento de Produtos</h3>
	<button data-toggle="modal" data-target="#modal_inclusao" class="btn btn-primary">Novo</button>
	<input type="text" name="p_descricao" id="p_descricao" onfocus="this.setSelectionRange(this.value.length, this.value.length);" onkeyup="buscar()">
	<table class="table table-stripped table-sn">
	<tr><th style="width:8%">Código</th>
		<th style="width:30%">Descrição</th>
		<th style="width:5%">Preço</th>
		<th style="width:4%">Ativo</th>
		<th style="width:4%"></th>
		<th style="width:4%"></th>
	</tr>
<%
	
	ProdutoDAO dao = new ProdutoDAO();
	if(desc == null) desc = ""; //retornar todos os produtos
	String sql = "select * from produtos where descricao like ?";
	List<Produto> lista = dao.get(sql, desc);
	
	for(Produto p:lista){
		//String ativo = p.isAtivo()?"checked":"";
		String ativo = "", editar = "", excluir = "";
		if(p.isAtivo()){
			ativo = "checked";
			editar = "<img data-toggle='modal' data-target='#modal_edicao' onclick='setEdicao(\""+p+"\")' style='cursor:pointer' width='22' height='22' src='../empresa/img/editar.png'>"; 
			excluir = "<img data-toggle='modal' data-target='#modal_exclusao' onclick='setExclusao(\""+p+"\")' style='cursor:pointer' width='22' height='22' src='../empresa/img/lixeira.png'>";
		}
		out.println("<tr><td>"+p.getCodigo()+"</td><td>"+p.getDescricao()+"</td><td>"+
			p.getPreco() + "</td><td><input onclick='return false' type=checkbox "+ ativo +
				"></td><td>"+ editar +"</td><td>"+ excluir +"</td><tr>");
		
		 
		
	}
	
	
%>
</table>
</div>

<div class="modal" id="modal_exclusao">
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<h3 class="modal-title">Confirma a exclusão do produto?</h3>
			</div>
			<div class="modal-body">
				<input type="button" style="width:100%" id="descricao_exclusao">
			</div>
			<div class="modal-footer">
				<form name="form_exclusao" action="../empresa/produtos" method="post">
					<input type="hidden" name="pagina" value="excluir.jsp">
					<input type="hidden" name="p_codigo" id="codigo_exclusao">
					<button onclick="form_exclusao.submit()" data-dismiss="modal" class="btn btn-danger">Excluir</button>
				</form>
				<button class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>

<div class="modal" id="modal_inclusao">
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<h3>Inclusão de produto</h3>
			</div>
			<div class="modal-body">
				<form name="form_inclusao" action="../empresa/produtos" method="post">
					<input type="hidden" name="pagina" value="incluir.jsp">
					<table>
						<tr>
							<td>Descrição</td>
							<td><input type="text"  step="0.1" required style="width300px" name="p_descricao" id="descricao_inclusao" ></td>
						</tr>
						<tr>
							<td>Preço</td>
							<td><input type="text" step="0.1" required style="width80px" name="p_preco" id="preco_inclusao"></td>
						</tr>
					</table>
						<input type="file" name="p_foto" size="30" accept="image/png,image/jpeg">
						<br>
						<input type="submit" value="Salvar" class="btn btn-primary">
						<input type="reset" value="Cancelar" class="btn btn-secondary" data-dismiss="modal">Cancelar
				</form>
			</div>
		</div>
	</div>
</div>


<div class="modal" id="modal_edicao">
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<h3>Edição de produto</h3>
			</div>
			<div class="modal-body">
				<form name="form_edicao" enctype="multipart/form-data" action="../empresa/produtos" method="post">
					<input type="hidden" name="pagina" value="edicao.jsp">
					<input type="hidden" name="p_codigo" id="codigo_edicao">
					<img name="p_foto" id="foto_edicao" width=150 height="150" data-dismiss="modal" data-toggle="modal" data-target="#modal-imagem-alterar">
					<table>
						<tr>
							<td>Descrição</td>
							<td><input type="text" required style="width300px" name="p_descricao" id="descricao_edicao"></td>
						</tr>
						<tr>
							<td>Preço</td>
							<td><input type="number" step="0.01" required style="width80px" name="p_preco" id="preco_edicao"></td>
						</tr>
					</table>
						<input type="file" name="p_foto" size="30" accept="image/png,image/jpeg">
						<br>
						<input type="submit" value="Salvar" class="btn btn-primary">
						<input type="reset" value="Cancelar" class="btn btn-secondary" data-dismiss="modal">Cancelar
				</form>
			</div>
		</div>
	</div>
</div>


<div class="modal" id="modal_imagem_alterar">
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<h3>Selecione o arquivo da imagem e pressione o botão atualizar</h3>
			</div>
			<div class="modal-body">
				<form name="form_imagem" enctype="multipart/form-data" action="../empresa/produtos" method="post">
					<input type="hidden" name="pagina" value="incluir.jsp">
					<input type="hidden" name="p_acao" id="alterar">
					<input type="hidden" name="p_codigo" id="codigo_imagem">
					<input type="hidden" name="p_descricao" id="descricao_imagem">
					<input type="hidden" name="p_preco" id="preco_imagem">
					<input type="file" name="p_foto" size="30" accept="image/png,image/jpeg">
					<br>
					<input type="submit" value="Atualizar" class="btn btn-primary">
					<input type="reset" value="Cancelar" class="btn btn-secondary" data-dismiss="modal">Cancelar
				</form>
			</div>
		</div>
	</div>
</div>



<div class="modal" id="modal_temp">
	<div class="modal-dialog">
		<div class="modal-content">		
			<div class="modal-header">
				<h3>Confirma a exclusão do produto?</h3>
			</div>
			<div class="modal-body">
				
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
	</div>
</div>

</body>
</html>