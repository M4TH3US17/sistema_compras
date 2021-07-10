package model.dao;

import java.util.List;

import model.entities.Employee;

public interface EmployeeDao {

	void insert(List<Employee> emp, Employee emp2);
	void update(Integer ID, Double newSalary);
	void deleteById(Integer id);
	Employee findByAccount(String email, String password);
	List<Employee> findAll();
}