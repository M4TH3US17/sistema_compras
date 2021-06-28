package model.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.services.OrderService;

public class Order {

	private  Date date;
	private OrderService order;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private List<Product> products = new ArrayList<>();
	
	public Order() {
	}
	
	public Order(List<Product> product, Date date, OrderService order) {
		this.products = product;
		this.date = date;
		this.order = order;
	}
	
	public OrderService getOrder() {
		return order;
	}

	public void setOrder(OrderService order) {
		this.order = order;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void readOrder(List<Product> products){
		System.out.println("\nDATA: " + sdf.format(date)
			+"\nCOMPRA: ");
		products.forEach(x -> x.readProducts());
	    System.out.println(order.toString());
	}	
}
