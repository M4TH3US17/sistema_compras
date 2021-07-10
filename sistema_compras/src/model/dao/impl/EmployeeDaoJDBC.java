package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.EmployeeDao;
import model.entities.Account;
import model.entities.Employee;
import model.entities.LoginException;

public class EmployeeDaoJDBC implements EmployeeDao {

	private Connection conn;
	
	public EmployeeDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(List<Employee> emp, Employee emp2) {
		PreparedStatement st = null;
		try {
			for(Employee u: emp) {
				if(u.getAccount().getEmail().equalsIgnoreCase(emp2.getAccount().getEmail())) {
					throw new LoginException("Email existente!");
				} 
			}
			conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO EMPLOYEE (NOME, EMAIL, SENHA, SALARY, CARGO) VALUES (?, ?, ?, ?, ?);");

			st.setString(1, emp2.getName());
			st.setString(2, emp2.getAccount().getEmail());
			st.setString(3, emp2.getAccount().getPassword());
            st.setDouble(4, emp2.getSalary());
            st.setString(5, emp2.getCargo());
			
			st.executeUpdate();
			System.out.println("CADASTRADO!");
		} 
		catch(LoginException e) {
			System.err.println(e.getMessage());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Integer ID, Double newSalary) {
		PreparedStatement st = null;
				
		try {
			st = conn.prepareStatement("UPDATE EMPLOYEE SET SALARY = ? WHERE ID = ?;");
			st.setDouble(1, newSalary);
			st.setInt(2, ID);
			st.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM EMPLOYEE WHERE ID = ?;");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
		}
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
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Employee> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM EMPLOYEE ORDER BY NOME;");
			rs = st.executeQuery();
			List<Employee> employee = new ArrayList<>();
			
			while(rs.next()) {
				Account obj1 = instantiateAccount(rs);
				Employee obj2 = instantiateEmployee(rs, obj1);
				employee.add(obj2);
			}
			return employee;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
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
}
