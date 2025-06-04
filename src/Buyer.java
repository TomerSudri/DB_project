//Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
import java.util.Arrays;

public class Buyer extends User {
	private String address;
	private Cart cart;
	private Order[] orderHistory;
	private int orderHistorySize;

	public Buyer(String username, String password, String address) {
		super(username, password);
		this.address = address;
		this.cart = new Cart();
		this.orderHistory = new Order[10];
		this.orderHistorySize = 0;
	}

	public String getAddress() {
		return address;
	}

	public Cart getCart() {
		return cart;
	}

	public Order[] getOrderHistory() {
		return Arrays.copyOf(orderHistory, orderHistorySize);
	}

	public void placeOrder() throws EmptyCartException {
		if (cart.isEmpty()) {
			throw new EmptyCartException("Cannot place order. Cart is empty.");
		}
		Order order = new Order(cart.getProducts(), address);
		if (orderHistorySize == orderHistory.length) {
			Order[] newArray = new Order[orderHistory.length * 2];
			System.arraycopy(orderHistory, 0, newArray, 0, orderHistory.length);
			orderHistory = newArray;
		}
		orderHistory[orderHistorySize++] = order;
		cart.clear();
	}

	public void replaceCartWithHistory(int historyIndex) {
		if (historyIndex >= 0 && historyIndex < orderHistorySize) {
			this.cart = new Cart();
			for (Product product : orderHistory[historyIndex].getProducts()) {
				this.cart.addProduct(product);
			}
		}
	}
}
