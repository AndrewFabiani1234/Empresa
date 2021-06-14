package testes;

import java.util.List;

import model.Pedido;
import model.PedidoDAO;

public class PedidoGetListaTeste {

	public static void main(String[] args) {
		
		
		PedidoDAO dao = new PedidoDAO();
		List<Pedido> lista =  dao.get("select * from pedido");
		
		for(Pedido p:lista) {
			System.out.println(p);
			System.out.println(" ");
			System.out.println(p.getItensToString());
		}
		

	}

}
