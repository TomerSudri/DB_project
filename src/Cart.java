////Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//public class Cart {
//	private Set<Product> products;
//	//private int size;
//	private int cartId;
//	public Cart() {
//		this.products = new HashSet<>();
//		//this.products = new Product[10];
//		//this.size = 0;
//	}
//
//	public void addProduct(Product product) {
//		products.add(product);
//		//if (size == products.length) {
//		//	Product[] newArray = new Product[products.length * 2];
//		//	System.arraycopy(products, 0, newArray, 0, products.length);
//		//	products = newArray;
//		//}
//		//products[size++] = product;
//	}
//
//	//public Product[] getProducts() {
//	//	return Arrays.copyOf(products, size);
//	//}
//	public Set<Product> getProducts() {
//		return new HashSet<>(products); // מחזיר עותק כדי להגן על המידע
//	}
//
//	//	public double getTotalPrice() {
//	//		double totalPrice = 0.0;
//	//		for (int i = 0; i < size; i++) {
//	//			totalPrice += products[i].getTotalPrice();
//	//		}
//	//		return totalPrice;
//	//	}
//	public double getTotalPrice() {
//		double totalPrice = 0.0;
//		for (Product product : products) {
//			totalPrice += product.getTotalPrice();
//		}
//		return totalPrice;
//	}
//
//	//	public boolean isEmpty() {
//	//		return size == 0;
//	//	}
//	public boolean isEmpty() {
//		return products.isEmpty();
//	}
//
//	//	public void clear() {
//	//		products = new Product[10];
//	//		size = 0;
//	//	}
//
//	public void clear() {
//		products.clear();
//	}
//
//	public int getCartId() {
//		return cartId;
//	}
//
//	public void setCartId(int cartId) {
//		this.cartId = cartId;
//	}
//
//}

import java.util.HashSet;
import java.util.Set;

public class Cart {
	private Set<Product> products;
	private int cartId; // רלוונטי רק אם יש טבלת Carts בבסיס הנתונים

	public Cart() {
		this.products = new HashSet<>();
	}

	// הוספת מוצר לסל
	public void addProduct(Product product) {
		products.add(product);
	}

	// מחזיר עותק של רשימת המוצרים
	public Set<Product> getProducts() {
		return new HashSet<>(products);
	}

	// חישוב מחיר כולל של המוצרים בסל
	public double getTotalPrice() {
		double totalPrice = 0.0;
		for (Product product : products) {
			totalPrice += product.getTotalPrice();
		}
		return totalPrice;
	}

	// בדיקה אם הסל ריק
	public boolean isEmpty() {
		return products.isEmpty();
	}

	// ניקוי הסל
	public void clear() {
		products.clear();
	}

	// מזהה סל (אם רוצים להשתמש בטבלת Carts)
	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
}
