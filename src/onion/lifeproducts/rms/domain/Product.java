package onion.lifeproducts.rms.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a product in the recycling management system.
 *
 * A product has an id, name, materials, manufacture date, and end date.
 * The materials are stored as a map:
 *
 * key   = material
 * value = ratio or amount of that material in the product
 */
public class Product {

    private static int nextId = 1;

    private final int id;
    private String name;
    private final Map<Material, Float> materials;
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
            Map<Material, Float> materials,
            LocalDateTime manufactureDate,
            LocalDateTime endDate
    ) {
        this.id = nextId++;
        this.name = name;
        this.materials = new HashMap<>(materials);
        this.manufactureDate = manufactureDate;
        this.endDate = endDate;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getManufactureDate() {
        return this.manufactureDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public Map<Material, Float> getMaterials() {
        return new HashMap<>(this.materials);
    }

    public boolean setName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        this.name = name;
        return true;
    }

    @Override
    public String toString() {
        return "Product ID: " + this.id
                + ", Name: " + this.name
                + ", Materials: " + this.materials
                + ", Manufacture date: " + this.manufactureDate
                + ", End date: " + this.endDate;
    }
}