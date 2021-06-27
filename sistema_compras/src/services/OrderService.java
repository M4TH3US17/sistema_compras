package services;

public class OrderService {

	private Payment payment;

	public OrderService() {
	}
	
	public OrderService(Payment payment) {
		this.payment = payment;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "TOTAL: R$ " + String.format("%.2f", payment.calcTotal())
		+"\nTROCO: R$ " + String.format("%.2f", payment.calcChange());
	}
}
