package model.entities;

import java.util.List;

public class Employee {

	private Integer ID;
	private String name;
	private Double salary;
	private String cargo;
	
	private Account account;
	
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

    public static Double payroll(List<Employee> employees) {
		Double total = 0.0;
		for(Employee emp: employees) {
			total += emp.getSalary();
		}
		return total;
	}

	@Override
	public String toString() {
		return "Employee [ID=" + ID + ", name=" + name + ", salary=" + salary + ", cargo=" + cargo + ", account="
				+ account + "]";
	}
}
