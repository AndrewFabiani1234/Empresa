<%@page import="services.Data"%>
<%@page import="services.Numero"%>
<%@page import="model.Pedido"%>
<%@page import="java.util.List"%>
<%@page import="model.PedidoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gerenciamento de Pedidos</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script>

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

function setItens(itens){
	
	var table = document.getElementById("tb_itens");
	var qtdeLinhas = table.rows.length;
	for(var i=1; i<qtdeLinhas; i++){
		table.deleteRow(1); // Deleta sempre a primeira linha da tabela
		
	}
	
	var tableBody = document.getElementById("id_body");
	
	item = itens.split("|") // vetor contendo os itens
	for(var i=0; i<item.length; i++){
		var tr = document.createElement("tr");
		tableBody.appendChild(tr);
		item_dados = item[i].split(";");
		for(var j=0; j<item_dados.length; j++){
			var td = document.createElement("td");
			td.appendChild(document.createTextNode(item_dados[j]));
			tr.appendChild(td);
		}
		
	}
}



</script>
<%
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
	<h3>Gerenciamento de Pedidos</h3>
	<button data-toggle="modal" data-target="#modal_inclusao" class="btn btn-primary">Novo</button>
	<table class="table table-stripped table-sm" style="display:block; height:300px; overflow:auto">
	<tr>
		<th style="width:8%">Numero</th>
		<th style="width:30%">Cliente</th>
		<th style="width:5%">Data</th>
		<th style="width:4%">Total</th>
		<th style="width:4%"></th>
		<th style="width:4%"></th>
	</tr>
<%
	PedidoDAO dao = new PedidoDAO();
	String sql = "select * from pedido";
	List<Pedido> lista = dao.get(sql);
	for(Pedido p:lista){
		
		String editar = "", excluir = "";
		editar = "<img data-toggle='modal' data-target='#modal_edicao' style='cursor:pointer' width='22' height='22' src='../empresa/img/editar.png'>"; 
		excluir = "<img data-toggle='modal' data-target='#modal_exclusao' style='cursor:pointer' width='22' height='22' src='../empresa/img/lixeira.png'>";
		
		out.println("<tr><td><button style='cursor:pointer' onclick='setItens(\""+p.getItensToString()+"\")'>"+p.getNumero()+"</button></td>"+
				"<td>"+p.getCliente()+"</td>"+
				"<td>"+Data.dataToStr(p.getData())+"</td>"+
				"<td align='left'>"+Numero.formatar(p.getTotal(), 2)+"</td>"+
				"<td>"+ editar +"</td>"+
				"<td>"+ excluir +"</td><tr>");
		
		 
		
		}
		
	
	
%>
</table>
</div>

<div class="container" style="margin-left:3px" id="principal">
	<h3>Itens de Pedido</h3>
	<table id="tb_itens" class="table table-stripped table-sm" style="display:block; overflow:auto">
		<tr>
			<th style="width:8%">Código</th>
			<th style="width:30%">Descrição</th>
			<th style="width:5%">Quantidade</th>
			<th style="width:4%">Preço Unitario</th>
			<th style="width:4%">SubTotal</th>
		</tr>
		<tbody id="id_body">
			
		</tbody>
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