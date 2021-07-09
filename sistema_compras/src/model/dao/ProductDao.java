package model.dao;

import java.util.List;

import model.entities.Product;

public interface ProductDao {

	void updateNameProduct(int id, String newName);
	void updatePriceProduct(int id, Double newPrice);
	void addProduct(String name, Double price, String type);
	void deleteById(Integer id);
	Product findById(Integer id);
	List<Product> findAll();
	
}
