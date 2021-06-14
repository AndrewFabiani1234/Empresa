package testes;

import model.Produto;

public class ProdutoTestar {

	public static void main(String[] args) {
		
		Produto p1 = new Produto();
		p1.setCodigo(1);
		p1.setDescricao("Sabonete");
		p1.setPreco(3.45);
		p1.setAtivo(true);
		p1.setFoto("sabonete.jpg");
		
		
		System.out.println(p1);
		
		Produto p2 = new Produto(2, "Detergente", 4.50, true, "detergente.jpg");
		
		System.out.println(p2);
		
	}

}



