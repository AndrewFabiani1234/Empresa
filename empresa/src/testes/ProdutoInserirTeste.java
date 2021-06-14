package testes;

import model.Produto;
import model.ProdutoDAO;

public class ProdutoInserirTeste {

	public static void main(String[] args) {
	
		
		Produto p = new Produto();
		p.setDescricao("Escova de dente");
		p.setPreco(2.75);
		p.setFoto("");
		p.setAtivo(true);
		ProdutoDAO dao = new ProdutoDAO();
		System.out.println(dao.incluir(p));
		
		

	}

	

}
