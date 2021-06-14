package model;

public class Item {

	private int numeroPedido, codigoProduto, quantidade;
	private double valor;
	
	
	public Item() {
		
	}
	
	public Item(int numeroPedido, int codigoProduto, int quantidade, double valor) {
		this.numeroPedido = numeroPedido;
		this.codigoProduto = codigoProduto;
		this.quantidade = quantidade;
		this.valor = valor;
	}
	
	public int getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public int getCodigoProduto() {
		return codigoProduto;
	}
	public void setCodigoProduto(int codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String toString() {
		return numeroPedido+";"+codigoProduto+";"+quantidade+";"+valor;
	}
	
}
