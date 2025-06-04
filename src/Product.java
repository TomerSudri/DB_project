//Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
public class Product {
	private static int serialCounter = 0;

	private int serialNumber;
	private String name;
	private double price;
	private Category category;
	private boolean specialPackaging;
	private double packagingCost;

	public Product(String name, double price, Category category, boolean specialPackaging, double packagingCost) {
		this.serialNumber = ++serialCounter;
		this.name = name;
		this.price = price;
		this.category = category;
		this.specialPackaging = specialPackaging;
		this.packagingCost = packagingCost;
	}

	public int getSerialNumber() {
		return serialNumber;
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
