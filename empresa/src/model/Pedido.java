package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import services.Data;
import services.Numero;

public class Pedido {

	private int numero;
	private String cliente;
	private Date data;
	private double total;
	private List<Item> itens = new ArrayList<Item>();
	
	
	public Pedido() {
		
	}
	
	public Pedido(int numero, String cliente, Date data, double total, List<Item> itens) {
		this.numero = numero;
		this.cliente = cliente;
		this.data = data;
		this.total = total;
		this.itens = itens;
	}

	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public List<Item> getItens() {
		return itens;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
		
		total = 0;
		for(Item i:itens) {
			total += (i.getQuantidade() * i.getValor());
		}
	}
	/*
	public void addItem(Item i) {
		itens.add(i);
		total += (i.getQuantidade() * i.getValor());
	}
	*/
	public String toString() {
		// Futuramente a data será formatada
		return numero+";"+cliente+";"+Data.dataToStr(data)+";"+Numero.formatar(total, 2); // Dados do cabeçalho do pedido
	}
	
	public String getItensToString() {
		String s = "";
		
		for(Item i:itens) { // Iterator
			double subtotal = i.getValor() * i.getQuantidade();
			
			s += i.getCodigoProduto()+";"+"descricao do produto"+
								";"+i.getQuantidade()+";"+Numero.formatar(i.getValor(),2)+";"+Numero.formatar(subtotal, 2)+"|";
			
		}
		s = s.substring(0, s.length()-1); //retirar o ultimo |
		
		return s;
	}
	
}
