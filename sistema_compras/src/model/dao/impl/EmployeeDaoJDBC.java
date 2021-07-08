package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DbException;
import model.dao.EmployeeDao;
import model.entities.Account;
import model.entities.Employee;

public class EmployeeDaoJDBC implements EmployeeDao {

	private Connection conn;
	
	public EmployeeDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Employee obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Employee obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee findByAccount(String email, String password) {
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM EMPLOYEE WHERE EMAIL = ? AND SENHA = ?;");
			st.setString(1, email);
			st.setString(2, password);
			rs = st.executeQuery();
			if(rs.next()) {
				Account account = instantiateAccount(rs);
				Employee emp = instantiateEmployee(rs, account);
				return emp;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			
		}
	}

	private Account instantiateAccount(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setEmail(rs.getString("EMAIL"));
		account.setPassword(rs.getString("SENHA"));
		return account;
	}

	private Employee instantiateEmployee(ResultSet rs, Account account) throws SQLException {
		Employee emp = new Employee();
		emp.setName(rs.getString("NOME"));
		emp.setID(rs.getInt("ID"));
		emp.setCargo(rs.getString("CARGO"));
		emp.setSalary(rs.getDouble("SALARY"));
		emp.setAccount(account);
		return emp;
	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
