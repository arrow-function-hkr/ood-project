package onion.lifeproducts.rms.domain;

<<<<<<< HEAD
public class RecyclingCategory {
	final private String category;
	
	public RecyclingCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return this.category;
	}
}
=======
/**
 * Represents a recycling category.
 */
public class RecyclingCategory {

    private String type;

    /**
     * Creates a recycling category with a type.
     */
    public RecyclingCategory(String type) {
        this.type = type;
    }

    /** Returns recycling category type. */
    public String getType() {
        return type;
    }
}
>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
