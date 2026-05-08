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
 * Represents the recycling category of a material.
 */
public enum RecyclingCategory {

<<<<<<< HEAD
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
=======
    RECYCLABLE,
    PARTIALLY_RECYCLABLE,
    NON_RECYCLABLE
}
>>>>>>> c3fc143 (feat(application,domain): redesign application and domain layers based on updated UML architecture)
