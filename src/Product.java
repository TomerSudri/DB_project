////Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
//public class Product {
//	private static int serialCounter = 0;
//
//	private int productId;
//	private String name;
//	private double price;
//	private Category category;
//	private boolean specialPackaging;
//	private double packagingCost;
//
//	public Product(String name, double price, String categoryName, boolean specialPackaging, double packagingCost) {
//		this.productId = ++serialCounter;
//		this.name = name;
//		this.price = price;
//		this.category = Category.fromString(categoryName); // ההמרה נעשית פה
//		this.specialPackaging = specialPackaging;
//		this.packagingCost = packagingCost;
//	}
//
//	public int getSerialNumber() {
//		return productId;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public double getPrice() {
//		return price;
//	}
//
//	public Category getCategory() {
//		return category;
//	}
//
//	public boolean hasSpecialPackaging() {
//		return specialPackaging;
//	}
//
//	public double getPackagingCost() {
//		return packagingCost;
//	}
//
//	public double getTotalPrice() {
//		double totalPrice = price;
//		if (specialPackaging) {
//			totalPrice += packagingCost;
//		}
//		return totalPrice;
//	}
//}
public class Product {
	private int productId;
	private String name;
	private double price;
	private Category category;
	private boolean specialPackaging;
	private double packagingCost;

	// בנאי למוצר חדש (לפני הכנסת ל־DB)
	public Product(String name, double price, String categoryName, boolean specialPackaging, double packagingCost) {
		this.name = name;
		this.price = price;
		this.category = Category.fromString(categoryName);
		this.specialPackaging = specialPackaging;
		this.packagingCost = packagingCost;
	}

	// בנאי למוצר קיים שנשלף מה־DB
	public Product(int productId, String name, double price, String categoryName, boolean specialPackaging, double packagingCost) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.category = Category.fromString(categoryName);
		this.specialPackaging = specialPackaging;
		this.packagingCost = packagingCost;
	}

	public int getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public Category getCategory() {
		return category;
	}

	public boolean hasSpecialPackaging() {
		return specialPackaging;
	}

	public double getPackagingCost() {
		return packagingCost;
	}

	public double getTotalPrice() {
		double totalPrice = price;
		if (specialPackaging) {
			totalPrice += packagingCost;
		}
		return totalPrice;
	}
}
