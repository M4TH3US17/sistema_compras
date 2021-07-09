package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ProductDao;
import model.entities.Product;

public class ProductDaoJDBC implements ProductDao {

	private Connection conn;
	
	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
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
	public Product findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM PRODUCT WHERE ID = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Product obj = instantiateProduct(rs);
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Product instantiateProduct(ResultSet rs) throws SQLException {
		Product obj = new Product();
		obj.setID(rs.getInt("ID"));
		obj.setName(rs.getString("NOME"));
		obj.setPrice(rs.getDouble("PRICE"));
		obj.setType(rs.getString("TYPE"));
		obj.setQuantity(null);
		return obj;
	}

	@Override
	public List<Product> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM PRODUCT;");
			rs = st.executeQuery();
			List<Product> products = new ArrayList<>();
			while(rs.next()) {
				Product obj = instantiateProduct(rs);
				products.add(obj);
			}
			return products;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void addProduct(String name, Double price, String type) {
		PreparedStatement st = null;
		try {
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
		}
	}

	@Override
	public void updateNameProduct(int id, String newName) {
		PreparedStatement st = null;
         try {
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
		}
	}

	@Override
	public void updatePriceProduct(int id, Double newPrice) {
		PreparedStatement st = null;
        try {
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
		}
	}

}
