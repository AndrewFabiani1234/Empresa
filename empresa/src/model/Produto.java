package model;

public class Produto {

	private int codigo;
	private String descricao;
	private double preco;
	private boolean ativo;
	private String foto;
	
	
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Produto(int codigo, String descricao, double preco, boolean ativo, String foto) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.preco = preco;
		this.ativo = ativo;
		this.foto = foto;
	}
	public Produto() {
		
	}
	
	public String toString() {
		return codigo+";"+descricao+";"+preco+";"+foto;
	}
	
	
	
}
