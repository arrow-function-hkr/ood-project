package onion.lifeproducts.rms.domain;

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