        package onion.lifeproducts.rms.domain;import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Represents a product in the recycling management system.
 *
 * A product contains:
 * - a unique ID
 * - a product name
 * - materials with their ratios/amounts
 * - manufacture date
 * - end-of-life date
 *
 * The materials are stored as:
 * key   = Material
 * value = material ratio or amount in the product
 */
public class Product {

    private static int nextId = 1;

    private final int id;
    private String name;
    private final HashMap<Material, Float> materials;
    private final LocalDateTime manufactureDate;
    private final LocalDateTime endDate;

    /**
     * Creates a new product.
     *
     * @param name product name
     * @param materials map of materials and their ratios/amounts
     * @param manufactureDate date when the product was manufactured
     * @param endDate date when the product reaches end of life
     */
    public Product(
            String name,
            HashMap<Material, Float> materials,
            LocalDateTime manufactureDate,
            LocalDateTime endDate
    ) {
        this.id = nextId++;
        this.name = name;
        this.materials = new HashMap<>(materials);
        this.manufactureDate = manufactureDate;
        this.endDate = endDate;
    }

    /**
     * Returns the product ID.
     *
     * @return product ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the product name.
     *
     * @return product name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the manufacture date.
     *
     * @return manufacture date
     */
    public LocalDateTime getManufactureDate() {
        return this.manufactureDate;
    }

    /**
     * Returns the end-of-life date.
     *
     * @return end date
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    /**
     * Returns a copy of the product materials map.
     *
     * The returned map contains:
     * key   = Material
     * value = material ratio or amount
     *
     * A copy is returned to protect internal object data.
     *
     * @return copy of materials map
     */
    public HashMap<Material, Float> getMaterials() {
        return new HashMap<>(this.materials);
    }

    /**
     * Updates the product name.
     *
     * The name cannot be null or blank.
     *
     * @param name new product name
     * @return true if updated successfully, otherwise false
     */
    public boolean setName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        this.name = name;
        return true;
    }

    /**
     * Returns a readable string representation of the product.
     *
     * @return formatted product information
     */
    @Override
    public String toString() {
        return "Product ID: " + this.id
                + ", Name: " + this.name
                + ", Materials: " + this.materials
                + ", Manufacture date: " + this.manufactureDate
                + ", End date: " + this.endDate;
    }
}

