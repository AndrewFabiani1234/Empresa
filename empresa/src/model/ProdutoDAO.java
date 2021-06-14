package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import services.BD;
import services.Numero;

public class ProdutoDAO {

	private BD bd;
	private Produto produto;
	private String men,sql;
	
	public ProdutoDAO() {
		bd = new BD();
	}
	
	/**
	 * Retorna uma lista de produtos a partir de uma instrução em SQL
	 * @param sql - a instrução a ser executada
	 * @param descricao - Um parametro usado como filtro na descrição do produto
	 * @return - a lista contendo todos os produtos
	 */
	public List<Produto> get(String sql, String descricao){
		List<Produto> lista = new ArrayList<Produto>();
		//Preencher a lista a partir do BD
		bd.getConnection();
		try {
			bd.st = bd.con.prepareStatement(sql);//select * from produtos where descricao like ?
			bd.st.setNString(1, descricao + "%");
			bd.rs = bd.st.executeQuery();
			while(bd.rs.next()) {//Enquanto existir proximo
				//Add os produtos a lista
				produto = new Produto();
				produto.setCodigo(bd.rs.getInt("codigo"));
				produto.setDescricao(bd.rs.getString("descricao"));
				produto.setPreco(bd.rs.getDouble("preco_unitario"));
				produto.setAtivo(bd.rs.getBoolean("ativo"));
				produto.setFoto(bd.rs.getString("foto"));
				lista.add(produto);
			}
		}
		catch (SQLException e) {
			System.out.println(e);
			lista = null;
		}
		finally {
			bd.close();
		}
		return lista;
	}
	/***
	 * Metodo para alterar o status de um produto (ativo ou não)
	 * @param codigo - codigo do produto a alterar
	 * @param ativo - 0 para inativar, 1 para ativar
	 * @return - uma mensagem informando o resultado da operacao
	 */
	public String ativar(int codigo, int ativo) {
		sql = "update produtos set ativo=? where codigo = ?";
		bd.getConnection();
		try {
			bd.st = bd.con.prepareStatement(sql);//select * from produtos where descricao like ?
			bd.st.setInt(1, ativo==0?0:1);
			bd.st.setInt(2, codigo);
			int n = bd.st.executeUpdate();
			if(n==1) {
				men = "Status do Produto alterado com sucesso!";
			}
			else {
				men = "Produto não localizado!";
			}
		
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			bd.close();
		}
		return men;
	}
	/**
	 * Metodo para incluir um produto
	 * @param p 
	 * @return 
	 */
	public String incluir(Produto p) {
		sql = "insert into produtos(descicao, preco_unitario, ativo, foto values (?,?,?,?)";
		bd.getConnection();
		try {
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, p.getDescricao());
			bd.st.setDouble(2, p.getPreco());
			bd.st.setBoolean(3, true);
			bd.st.setString(4, p.getFoto());
			men = "Produto inserido com sucesso!";
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Metodo para edição de um produto
	 * @param preco - preco que vai ser editado
	 * @param descricao - descricao que vai ser editada
	 * @return - mensagem com o resultado da edição
	 */
	public String editar(Produto p) {
		sql = "update produtos set preco_unitario=?, descricao=?, foto=? where codigo = ?";
		bd.getConnection();
		try {
			bd.st = bd.con.prepareStatement(sql);//select * from produtos where descricao like ?
			System.out.println(p.getDescricao());
			bd.st.setDouble(1, p.getPreco());
			bd.st.setString(2, p.getDescricao());
			bd.st.setString(3, p.getFoto());
			bd.st.setInt(4, p.getCodigo());
			int n = bd.st.executeUpdate();
			if(n==1) {
				men = "Status do Produto alterado com sucesso!";
			}
			else {
				men = "Produto não localizado!";
			}
		
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			bd.close();
		}
		return men;
	}
	
	/**
	 * Lê os dados de um arquivo .csv
	 * @return - Mensagem de sucesso ou falha
	 */
	public String exportarCSV() {
		bd.getConnection();
		try {
			PrintWriter pw = new PrintWriter("C:/Users/Andy/Documents/Trabalhos/FIEC/3º Modulo/JAVA");
			pw.println("codigo;descricao;preco_unitario;ativo;foto");
			sql = "select * from produtos";
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();
			while(bd.rs.next()) {//Enquanto existir proximo
				//Add os produtos a lista
				pw.print(bd.rs.getInt(1)+";");
				pw.print(bd.rs.getString(2)+";");
				pw.print(bd.rs.getDouble(3)+";");
				pw.print(bd.rs.getInt(4)+";");
				pw.print(bd.rs.getString(5)+"\n");
			}
			pw.close();
			return "Arquivo gerado com sucesso!";
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}
		finally {
			bd.close();
		}
		return "Falha na geração de arquivo";
	}
	
	/**
	 * Cria um aquivo .csv a partir dos dados do banco
	 * @return - Mensagem de sucesso ou falha
	 */
	public String importarCSV() {
		bd.getConnection();
		try {
			sql = "insert into produto_bk (descricao, preco_unitario, ativo, foto) values (?, ?, ?, ?, ?)";
			bd.st = bd.con.prepareStatement(sql);
			BufferedReader br = new BufferedReader(new FileReader ("C:/Users/Andy/Documents/Trabalhos/FIEC/3º Modulo/JAVA"));
			String linha = "";
			br.readLine(); // Pula a linha de cabeçalho do arquivo
			while((linha=br.readLine())!=null) { // Enquanto existirem linhas no arquivo .csv
				String[] dados = linha.split(";"); // dados[0] --> codigo
			//	bd.st.setString(1, dados[0]);
				bd.st.setString(1, dados[1]);
				bd.st.setString(2, dados[2]);
				bd.st.setString(3, dados[3]);
				bd.st.setString(4, dados[4]);
				bd.st.executeUpdate();
			}
			br.close();
			return "Dados armazendos com sucesso!";
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}
		finally {
			bd.close();
		}
		return "Falha no armazenamento dos dados";
	}
	
	/**
	 * Cria um arquivo .pdf a partir do dos dados dos BD usando o metodo get
	 * @return - Mensagem contendo o resultado da criação
	 */
	public String gerarPDF() {
		try {
			String file = "C:/Users/Andy/Documents/Trabalhos/FIEC/3º Modulo/JAVA/produto.pdf";
			PdfWriter writer = new PdfWriter(file);
			PdfDocument pdfdoc = new PdfDocument(writer);
			pdfdoc.addNewPage();
			Document doc = new Document(pdfdoc);
			
			PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
			
			String img = "C:/Users/Andy/Documents/Trabalhos/FIEC/3º Modulo/JAVA/rockisdead.jpg";
			ImageData data = ImageDataFactory.create(img);
			Image imagem = new Image(data);
			imagem.setHorizontalAlignment(HorizontalAlignment.CENTER);
			doc.add(imagem);
			
			Text titulo = new Text("Relatorio de Produtos");
			titulo.setFont(font);
			titulo.setFontSize(22);
			titulo.setFontColor(Color.BLUE);
			
			Paragraph p = new Paragraph();
			p.setTextAlignment(TextAlignment.CENTER);
			p.add(titulo);
			doc.add(p);
			
			float[] pontos = {50f, 300f, 100f};
			Table t = new Table(pontos);
			Cell cCodigo = new Cell().add("Codigo").setBackgroundColor(Color.LIGHT_GRAY);
			Cell cDescricao = new Cell().add("Descricao").setBackgroundColor(Color.LIGHT_GRAY);
			Cell cPreco = new Cell().add("Preco").setBackgroundColor(Color.LIGHT_GRAY);
			t.addCell(cCodigo);
			t.addCell(cDescricao);
			t.addCell(cPreco);
			
			
			List<Produto> lista = get("select * from produtos where descricao like ?", "");
			for(Produto prod:lista) {
				
				t.addCell(new Cell().add(""+prod.getCodigo()));
				t.addCell(new Cell().add(prod.getDescricao()));
				t.addCell(new Cell().add(""+Numero.formatar(prod.getPreco(), 2)).setTextAlignment(TextAlignment.RIGHT));
				
			}
			t.setHorizontalAlignment(HorizontalAlignment.CENTER);
			doc.add(t);
			doc.close();
			return "Arquivo gerado com sucesso";
			
		} 
		catch (IOException e) {
			return "Falha na geração do arquivo! "+ e;
		}
		
	}
	
}