//Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
import java.util.*;

public class ECommerceSystem {
	//private Buyer[] buyers;
	//private Seller[] sellers;
	private Map<String, Buyer> buyerMap;
	private Map<String, Seller> sellerMap;
	private int buyerCount;
	private int sellerCount;

	public ECommerceSystem() {
		//this.buyers = new Buyer[10]; // Initial capacity for buyers
		//this.sellers = new Seller[10]; // Initial capacity for sellers
		this.buyerMap = new HashMap<>();
		this.sellerMap = new HashMap<>();
		this.buyerCount = 0;
		this.sellerCount = 0;
	}

	//public Buyer[] getBuyers() {
		//return Arrays.copyOf(buyers, buyerCount);
	//}

	public Collection<Buyer> getBuyers() {
		return buyerMap.values();
	}

	public Collection<Seller> getSellers() {
		return sellerMap.values();
	}


	//public Seller[] getSellers() {
		//return Arrays.copyOf(sellers, sellerCount);
	//}

	public boolean addBuyer(Buyer buyer) {
		if (buyerMap.containsKey(buyer.getUsername())) {
			return false; // קונה כבר קיים
		}
		buyerMap.put(buyer.getUsername(), buyer);
		return true;
	}

	public boolean addSeller(Seller seller) {
		if (sellerMap.containsKey(seller.getUsername())) {
			return false; // מוכר כבר קיים
		}
		sellerMap.put(seller.getUsername(), seller);
		return true;
	}

	public Buyer findBuyerByUsername(String username) {
		return buyerMap.get(username);
	}

	public Seller findSellerByUsername(String username) {
		return sellerMap.get(username);
	}


	public Product[] getProductsByCategory(Category category) {
		List<Product> productList = new ArrayList<>();

		for (Seller seller : sellerMap.values()) {
			Product[] sellerProducts = seller.getProducts();
			for (Product product : sellerProducts) {
				if (product != null && product.getCategory() == category) {
					productList.add(product);
				}
			}
		}

		return productList.toArray(new Product[0]);
	}

	private Product[] addToArray(Product[] array, Product productToAdd) {
		Product[] newArray = Arrays.copyOf(array, array.length + 1);
		newArray[array.length] = productToAdd;
		return newArray;
	}
}
