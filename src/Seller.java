//Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
public class Seller extends User {
	private Product[] products;
	private int productCount;

	public Seller(String username, String password) {
		super(username, password);
		this.products = new Product[10];
		this.productCount = 0;
	}

	public Product[] getProducts() {
		Product[] actualProducts = new Product[productCount];
		System.arraycopy(products, 0, actualProducts, 0, productCount);
		return actualProducts;

	}

	public void addProduct(Product product) {
		if (productCount == products.length) {
			resizeArray();
		}
		products[productCount++] = product;
	}

	public int getProductCount() {
		return productCount;
	}

	private void resizeArray() {
		Product[] newProducts = new Product[products.length * 2];
		System.arraycopy(products, 0, newProducts, 0, products.length);
		products = newProducts;
	}
}
    