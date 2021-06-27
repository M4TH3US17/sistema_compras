package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DB;

public class Employee {

	private Integer ID;
	private String name;
	private Double salary;
	private Account account;
	private String cargo;
	
	public Employee() {
	}
	
	public Employee(Integer ID, String name, Account account, Double salary, String cargo) {
		this.name = name;
		this.account = account;
		this.salary = salary;
		this.cargo = cargo;
		this.ID = ID;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public static void updateEmployee(int ID, Double newSalary) {
		Connection conn = null;
		PreparedStatement st = null;
				
		try {
			conn = DB.getConnection();
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
			DB.closeConnection();
		}
	}

	public static void deleteEmployee(int ID) {
		Connection conn = null;
		PreparedStatement st = null;
				
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement("DELETE FROM EMPLOYEE WHERE ID = ?;");
			st.setInt(1, ID);
			st.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

	public static Double payroll(List<Employee> employees) {
		Double total = 0.0;
		for(Employee emp: employees) {
			total += emp.getSalary();
		}
		return total;
	}
}
