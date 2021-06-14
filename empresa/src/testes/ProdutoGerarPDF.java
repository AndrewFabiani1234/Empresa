package testes;

import model.ProdutoDAO;

public class ProdutoGerarPDF {

	public static void main(String[] args) {
		
		ProdutoDAO dao = new ProdutoDAO();
		System.out.println(dao.gerarPDF());

	}

}
