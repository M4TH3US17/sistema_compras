package model.dao;

import db.DB;
import model.dao.impl.EmployeeDaoJDBC;
import model.dao.impl.ProductDaoJDBC;

public class DaoFactory {
		
	public static EmployeeDao createEmployeeDao() {
		return new EmployeeDaoJDBC(DB.getConnection());
	}
	
	public static ProductDao createProductDao() {
		return new ProductDaoJDBC(DB.getConnection());
	}
	
}
