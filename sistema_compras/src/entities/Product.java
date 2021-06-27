package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import db.DB;
import db.DbException;

public class Product {

	private String name;
	private Double price;
	private String type;
	private Integer quantity;
	private Integer ID;
	
	public Product() {
	}
	
	public Product(Integer ID, String name, Double price, String type, Integer quantity) {
		this.name = name;
		this.price = price;
		this.type = type;
		this.quantity = quantity;
		this.ID = ID;
	}
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public void readProducts() {
		System.out.println(name+", preço: R$ " + String.format("%.2f", price)+", quantidade: "+getQuantity()+";");
	}
	
	public static void readTablePrices(List<Product> products) {
		System.out.println();
		System.out.println("-> BEBIDAS: ");
		List<Product> p1 = products.stream().filter(x -> x.getType().equalsIgnoreCase("bebida")).collect(Collectors.toList());
		p1.forEach(System.out::println);
		System.out.println("\n-> COMIDAS: ");
		List<Product> p2 = products.stream().filter(x -> x.getType().equalsIgnoreCase("comida")).collect(Collectors.toList());
		p2.forEach(System.out::println);
		System.out.println("\n-> FRUTAS: ");
		List<Product> p3 = products.stream().filter(x -> x.getType().equalsIgnoreCase("fruta")).collect(Collectors.toList());
		p3.forEach(System.out::println);
	}

	public static void addProduct(String name, Double price, String type) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO PRODUCT (NOME, PRICE, TYPE) VALUES (?, ?, ?);");
			st.setString(1, name);
			st.setDouble(2, price);
			st.setString(3, type);
			st.executeUpdate();
		}
		catch(SQLException e) {
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

	public static void updateNameProduct(int id, String newName) {
		Connection conn = null;
		PreparedStatement st = null;
         try {
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"UPDATE PRODUCT SET NOME = ? WHERE ID = ?;");
			st.setString(1, newName);
			st.setInt(2, id);
			st.executeUpdate();
			System.out.println("PRODUTO ATUALIZADO!");
		}
		catch(SQLException e) {
			throw new DbException("Error update!");
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

	public static void updatePriceProduct(int id, Double newPrice) {
	    Connection conn = null;
		PreparedStatement st = null;
         try {
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"UPDATE PRODUCT SET PRICE = ? WHERE ID = ?;");
			st.setDouble(1, newPrice);
			st.setInt(2, id);
			st.executeUpdate();
			System.out.println("PRODUTO ATUALIZADO!");
		}
		catch(SQLException e) {
			throw new DbException("Error update!");
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
	
	public static void deleteProduct(int id) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"DELETE FROM PRODUCT WHERE ID = ?;");
			st.setInt(1, id);
			st.executeUpdate();
			System.out.println("PRODUTO DELETADO!");
		}
		catch(SQLException e) {
			throw new DbException("Error update!");
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
	@Override
	public String toString() {
		return name+", preço: R$ " + String.format("%.2f", price);
	}
	
	
}
