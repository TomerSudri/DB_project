//Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cart {
	private Set<Product> products;
	//private int size;

	public Cart() {
		this.products = new HashSet<>();
		//this.products = new Product[10];
		//this.size = 0;
	}

	public void addProduct(Product product) {
		products.add(product);
		//if (size == products.length) {
		//	Product[] newArray = new Product[products.length * 2];
		//	System.arraycopy(products, 0, newArray, 0, products.length);
		//	products = newArray;
		//}
		//products[size++] = product;
	}

	//public Product[] getProducts() {
	//	return Arrays.copyOf(products, size);
	//}
	public Set<Product> getProducts() {
		return new HashSet<>(products); // מחזיר עותק כדי להגן על המידע
	}

	//	public double getTotalPrice() {
	//		double totalPrice = 0.0;
	//		for (int i = 0; i < size; i++) {
	//			totalPrice += products[i].getTotalPrice();
	//		}
	//		return totalPrice;
	//	}
	public double getTotalPrice() {
		double totalPrice = 0.0;
		for (Product product : products) {
			totalPrice += product.getTotalPrice();
		}
		return totalPrice;
	}

	//	public boolean isEmpty() {
	//		return size == 0;
	//	}
	public boolean isEmpty() {
		return products.isEmpty();
	}

	//	public void clear() {
	//		products = new Product[10];
	//		size = 0;
	//	}

	public void clear() {
		products.clear();
	}
}
