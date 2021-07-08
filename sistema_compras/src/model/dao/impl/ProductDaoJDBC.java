package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public void insert(Product obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Product obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
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
		return obj;
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
