//Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
public enum Category {
	SHOES(1, "Shoes"),
	CLOTHING(2, "Clothing"),
	ELECTRONICS(3, "Electronics"),
	BOOKS(4, "Books");

	private final int id;
	private final String displayName;

	Category(int id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}

	public int getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static Category fromId(int id) {
		for (Category c : Category.values()) {
			if (c.id == id) return c;
		}
		throw new IllegalArgumentException("Invalid category ID: " + id);
	}

	@Override
	public String toString() {
		return displayName;
	}
}
