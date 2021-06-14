package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BD {
	
	//3 objetos diferentes
	
	//conecta
	public Connection con = null; //realiza a conexão, usando o driver
	public PreparedStatement st = null; //sabe executar instruções em SQL
	public ResultSet rs = null; //recebe o resultado de uma consulta em SQL
	//para MySQL5 --> com.mysql.jdbc.Driver
	//para MySQL8 --> com.mysql.cj.jdbc.Driver
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String DATABASENAME = "lpiii";
	private final String URL = "jdbc:mysql://localhost:3306/"+DATABASENAME;//"?useTimezone=true&serverTimezone=UTC";
	private final String LOGIN = "root";
	private final String PASSWORD = "andreW2402!";
	
	
	public static void main(String[] args) {
		BD bd = new BD();
		bd.getConnection();
		bd.close();
		
	}
	
	/**
	 * Realiza a conexão ao banco de dados por meio do driver
	 * @return - true em caso de sucesso, ou false caso contrário
	 */
	public boolean getConnection() {
		try {
			Class.forName(DRIVER);
			//System.out.println("Driver carregado com sucesso!");
			con = DriverManager.getConnection(URL,LOGIN,PASSWORD);
			System.out.println("Conectou...");
			return true;
		}
		catch(ClassNotFoundException erro) {
			System.out.println("Driver não encontrado!!");
		}
		catch(SQLException erro) {
			System.out.println("Falha na conexão ao banco de dados!! "+
		                                                    erro.toString());
		}
		return false;
	}
	

	/**
	 * Encerra a conexão ao banco de dados
	 */
	public void close() {
		try {
			if(rs!=null) rs.close();
		} 
		catch (Exception e) {}

		try {
			if(st!=null) st.close();
		} 
		catch (Exception e) {}

		try {
			if(con!=null) {
				con.close();
				System.out.println("Desconectou...");
			}
		} 
		catch (Exception e) {}
	}

	
	
	//realizar a operação desejada (consulta, insert...)
	
	
	
	//desconectar
}
