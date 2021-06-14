package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import services.BD;
import services.Data;

public class PedidoDAO {

	private BD bd, bdItem;
	private String sql;
	private Pedido pedido;
	
	
	public PedidoDAO() {
		bd = new BD(); // Gerenciar o pedido
		bdItem = new BD(); // gerenciar os itens
	}
	
	
	/**
	 * Retorna uma lista de pedidos a partir de uma instrução em SQL
	 * @param sql - a instrução a ser executada (deve conter todas as colunas)
	 * @return - a lista contendo todos os pedidos
	 */
	public List<Pedido> get(String sql){ //Acessar dados de duas tabelas
		List<Pedido> lista = new ArrayList<Pedido>();
		//Preencher a lista a partir do BD
		bd.getConnection();
		bdItem.getConnection();
		try {
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();
			while(bd.rs.next()) {
				pedido = new Pedido();
				pedido.setNumero(bd.rs.getInt("numero"));
				pedido.setCliente(bd.rs.getString("cliente"));
				Timestamp stamp = bd.rs.getTimestamp("data_pedido");
				pedido.setData(Data.timestampToDate(stamp));
				pedido.setTotal(bd.rs.getDouble("total"));
				
				
				sql = "select * from itens_pedido where numero_pedido = ?";
				bdItem.st = bdItem.con.prepareStatement(sql);
				bdItem.st.setInt(1, pedido.getNumero());
				bdItem.rs = bdItem.st.executeQuery();
				List<Item> itens = new ArrayList<Item>();
				while(bdItem.rs.next()) {
					itens.add(new Item(bdItem.rs.getInt(1), bdItem.rs.getInt(2), bdItem.rs.getInt(3), bdItem.rs.getDouble(4)));
				}
				pedido.setItens(itens);
				lista.add(pedido);
			}
		}
		catch (SQLException e) {
			System.out.println(e);
			lista = null;
		}
		finally {
			bd.close();
			bdItem.close();
		}
		return lista;
	}
	
	/**
	 * 
	 * @param pedido
	 * @return
	 */
	public String incluir(Pedido pedido) {	
		sql = "insert into pedido (cliente, data_pedido, total) values (?,?,?)";
		bd.getConnection();
		try {
			bd.con.setAutoCommit(false); // Todas as ações são gravadas automaticamente
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, pedido.getCliente());
			Timestamp sqlData = new Timestamp(pedido.getData().getTime());
			bd.st.setTimestamp(2, sqlData);
			bd.st.setDouble(3, pedido.getTotal());
			bd.st.executeUpdate();
			
			
			sql = "select numero from pedido order by numero desc limit 1";
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();
			bd.rs.next();
			int n = bd.rs.getInt(1); // Pego o numero do pedido que está sendo inserido
			
			ItemDAO itemDAO = new ItemDAO();
			//Preciso verificar se todos os itens foram inseridos...
			boolean chave = true;
			//Inclusão verificar todos os itens do pedido
			for(Item item:pedido.getItens()) {
				item.setNumeroPedido(n);
				if(itemDAO.incluir(item)==false) {
					chave = false;
					break;
				}
			}
			if(chave==true) {
				bd.con.commit(); //Todas as inclusões foram realizadas com sucesso
				return "Pedido contendo "+pedido.getItens().size()+" itens incluído com sucesso";
			}
			else {
				bd.con.rollback(); //Se alguma deu errado 
				return "Falha ao incluir o pedido";
			}
		}
		catch (SQLException e) {
			System.out.println(e);
			try {
				bd.con.rollback();
			} 
			catch (SQLException e1) {}
			return "Falha ao incluir o pedido";
		}
		finally {
			try {
				bd.con.setAutoCommit(true);
			} 
			catch (SQLException e) {}
			bd.close();
		}
		
	}
	
	
}
