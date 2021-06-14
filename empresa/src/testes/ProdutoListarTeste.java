package testes;

import java.util.List;

import model.Produto;
import model.ProdutoDAO;

public class ProdutoListarTeste {

	public static void main(String[] args) {

	ProdutoDAO dao = new ProdutoDAO();
	List<Produto> lista = dao.get("select * from produtos where descricao like %", "");
	
	for(Produto p:lista) {
		System.out.println(p.getDescricao());
	}
	
	}	
}
