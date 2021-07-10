package model.entities;

import java.util.List;
import java.util.stream.Collectors;

import model.dao.DaoFactory;
import model.dao.ProductDao;

public class Product {

	private String name;
	private Double price;
	private String type;
	private Integer quantity;
	private Integer ID;

	public Product() {
	}

	public Product(Integer ID, String name, Double price, String type, Integer quantity) {
		this.name = name;
		this.price = price;
		this.type = type;
		this.quantity = quantity;
		this.ID = ID;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	protected void readProducts() {
		System.out.println(name+", preço: R$ " + String.format("%.2f", price)+", quantidade: "+getQuantity()+";");
	}

	public static void filterMenuByType() {
		ProductDao productDao = DaoFactory.createProductDao();
		List<Product> products = productDao.findAll();
		System.out.println();
		System.out.println("-> BEBIDAS: ");
		List<Product> p1 = products.stream().filter(x -> x.getType().equalsIgnoreCase("bebida")).collect(Collectors.toList());
		p1.forEach(System.out::println);
		System.out.println("\n-> COMIDAS: ");
		List<Product> p2 = products.stream().filter(x -> x.getType().equalsIgnoreCase("comida")).collect(Collectors.toList());
		p2.forEach(System.out::println);
		System.out.println("\n-> FRUTAS: ");
		List<Product> p3 = products.stream().filter(x -> x.getType().equalsIgnoreCase("fruta")).collect(Collectors.toList());
		p3.forEach(System.out::println);
	}

	@Override
	public String toString() {
		return name+", preço: R$ " + String.format("%.2f", price);
	}
}
