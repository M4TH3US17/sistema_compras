package model.dao;

import java.util.List;

import model.entities.Employee;

public interface EmployeeDao {

	void insert(Employee obj);
	void update(Employee obj);
	void deleteById(Integer id);
	Employee findByAccount(String email, String password);
	List<Employee> findAll();
}