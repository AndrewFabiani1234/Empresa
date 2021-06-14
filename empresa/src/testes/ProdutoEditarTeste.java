package testes;

import model.Produto;
import model.ProdutoDAO;

public class ProdutoEditarTeste {

	public static void main(String[] args) {
		
		ProdutoDAO dao = new ProdutoDAO();
		Produto p = new Produto();

		System.out.println(dao.editar(1, 4.50, "Escova"));
		
		

	}

}
