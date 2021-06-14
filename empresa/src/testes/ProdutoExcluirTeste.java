package testes;

import model.ProdutoDAO;

public class ProdutoExcluirTeste {

	public static void main(String[] args) {
		
		ProdutoDAO dao =  new ProdutoDAO();
		System.out.println(dao.ativar(3, 1));

	}

}
