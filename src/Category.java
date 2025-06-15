
/**
 * Enum representing product categories in the system.
 * Each category has a human-readable display name (e.g., "BOOKS", "CLOTHING").
 * Provides utility methods for conversion and display.
 */
public enum Category {
	BOOKS("BOOKS"),
	CLOTHING("CLOTHING"),
	ELECTRONICS("ELECTRONICS"),
	TOYS("TOYS");

	private final String displayName;

	/**
	 * Constructor for a category enum value.
	 * @param displayName the name to display for this category
	 */
	Category(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns the display name of the category.
	 * @return readable name of the category
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Converts a string to the matching Category enum value.
	 * Case-insensitive match based on the display name.
	 *
	 * @param name the string to convert
	 * @return matching Category enum value
	 * @throws IllegalArgumentException if no match is found
	 */
	public static Category fromString(String name) {
		for (Category c : Category.values()) {
			if (c.displayName.equalsIgnoreCase(name)) return c;
		}
		throw new IllegalArgumentException("Invalid category name: " + name);
	}

	/**
	 * Returns the display name when printing the enum.
	 * Overrides the default enum toString method.
	 * @return display name of the category
	 */
	@Override
	public String toString() {
		return displayName;
	}
}
