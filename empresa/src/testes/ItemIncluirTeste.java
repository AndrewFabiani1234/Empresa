package testes;

import java.util.ArrayList;
import java.util.List;

import model.Item;

import model.Pedido;
import model.PedidoDAO;
import services.Data;

public class ItemIncluirTeste {

	public static void main(String[] args) {
		
		Item i1 = new Item(0, 1,10, 50);
		Item i2 = new Item(0, 2,10, 50);
		Item i3 = new Item(0, 3,10, 50);
		
		List<Item> lista = new ArrayList<Item>();
		lista.add(i1);
		lista.add(i2);
		lista.add(i3);
		
		Pedido p = new Pedido();
		p.setCliente("Alex");
		p.setData(Data.strToDate("26/05/2021"));
		//p.setTotal(100);
		p.setItens(lista); 
		PedidoDAO dao = new PedidoDAO();
		System.out.println(dao.incluir(p));
		
	}
}
