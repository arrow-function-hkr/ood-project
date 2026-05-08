package onion.lifeproducts.rms.domain;

/**
 * Represents a category for products.
 */
public class ProductCategory {

    private String type;

    /**
     * Creates a product category with a type.
     */
    public ProductCategory(String type) {
        this.type = type;
    }

    /** Returns the category type. */
    public String getType() {
        return type;
    }
}