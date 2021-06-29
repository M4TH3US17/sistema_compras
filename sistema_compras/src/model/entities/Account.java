package model.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DB;

public class Account {

	private String email;
	private String password;

	public Account() {
	}

	public Account(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void createAccount(List<Employee> employees, String email, String password, String name_user, Double salary, String cargo) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			for(Employee u: employees) {
				if(u.getAccount().getEmail().equalsIgnoreCase(email)) {
					throw new LoginException("Email existente!");
				} 
			}
			conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO EMPLOYEE (NOME, EMAIL, SENHA, SALARY, CARGO) VALUES (?, ?, ?, ?, ?);");

			st.setString(1, name_user);
			st.setString(2, email);
			st.setString(3, password);
            st.setDouble(4, salary);
            st.setString(5, cargo);
			
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
			DB.closeConnection();
		}
	}

	@Override
	public String toString() {
		return "Account [email=" + email + ", password=" + password + "]";
	} 
}
