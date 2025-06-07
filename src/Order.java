////Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//public class Order {
//	//private Product[] products;
//	private Set<Product> products;
//	private String shippingAddress;
//	private double totalPrice;
//	private LocalDate orderDate;
//
//	private int orderId;
//
//
//	//	public Order(Product[] products, String shippingAddress) {
//	//		this.products = new Product[products.length];
//	//		System.arraycopy(products, 0, this.products, 0, products.length);
//	//		this.shippingAddress = shippingAddress;
//	//		calculateTotalPrice();
//	//		this.orderDate = LocalDate.now();
//	//	}
//	public Order(Set<Product> products, String shippingAddress) {
//		this.products = new HashSet<>(products);
//		this.shippingAddress = shippingAddress;
//		calculateTotalPrice();
//		this.orderDate = LocalDate.now();
//	}
//
//
//	//	public Product[] getProducts() {
//	//		return products;
//	//	}
//	public Set<Product> getProducts() {
//		return new HashSet<>(products);
//	}
//
//
//	public String getShippingAddress() {
//		return shippingAddress;
//	}
//
//	public double getTotalPrice() {
//		return totalPrice;
//	}
//
//	public LocalDate getOrderDate() {
//		return orderDate;
//	}
//
//	private void calculateTotalPrice() {
//		totalPrice = 0;
//		for (Product product : products) {
//			totalPrice += product.getTotalPrice();
//		}
//	}
//
//	public int getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(int orderId) {
//		this.orderId = orderId;
//	}
//
//}

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Order {
	private int orderId;
	private Set<Product> products;
	private String shippingAddress;
	private double totalPrice;
	private LocalDate orderDate;

	// בנאי להזמנה חדשה
	public Order(Set<Product> products, String shippingAddress) {
		this.products = new HashSet<>(products);
		this.shippingAddress = shippingAddress;
		this.orderDate = LocalDate.now();
		calculateTotalPrice();
	}

	// בנאי להזמנה קיימת שנשלפת מה־DB
	public Order(int orderId, List<Product> products, String shippingAddress, LocalDate orderDate) {
		this.orderId = orderId;
		this.products = new HashSet<>(products);
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		calculateTotalPrice();
	}

	public Set<Product> getProducts() {
		return new HashSet<>(products);
	}

	public List<Product> getProductList() {
		return new ArrayList<>(products);
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	private void calculateTotalPrice() {
		totalPrice = 0;
		for (Product product : products) {
			totalPrice += product.getTotalPrice();
		}
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
