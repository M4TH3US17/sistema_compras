package services;

import java.util.List;

import entities.Product;

public class PaymentService implements Payment {

	private List<Product> products;
	private Product product;
	private Double payment;
	private Double payment2;
	
	public PaymentService(List<Product> products, Double payment) {
		this.products = products;
		this.payment2 = payment;
	}
	
	public Double getPayment2() {
		return payment2;
	}

	public void setPayment2(Double payment2) {
		this.payment2 = payment2;
	}

	public PaymentService(Double payment) {
		this.payment = payment;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "Total: R$ " + String.format("%.2f", calcTotal())
				+"\nPagamento: R$ " + String.format("%.2f", payment2)
				+"\nTroco: R$ " + String.format("%.2f", calcChange());
	}

	@Override
	public Double calcTotal() {
		Double sum = 0.0;
		for(Product p: products) {
			sum += (p.getPrice() * p.getQuantity());
		}
		return sum;
	}

	@Override
	public Double calcChange() {
		Double total;
		Double change = 0.0;
		total = calcTotal();
		change = payment2 - total;
		
		if(change < 0) {
			return 0.0;
		} else {
			return change;	
		}
	}
	
	
}
