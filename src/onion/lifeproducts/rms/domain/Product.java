package onion.lifeproducts.rms.domain;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Represents a product in the recycling management system.
 *
 * A product has an id, name, materials, manufacture date, and end date.
 * The materials are stored as a map:
 *
 * key   = material id
 * value = amount or weight of that material in the product
 */
public class Product {

    private static int nextId = 1;

    private final int id;
    private String name;
    private final HashMap<Integer, Float> materials;
    private final LocalDateTime manufactureDate;
    private final LocalDateTime endDate;

    /**
     * Creates a new product.
     *
     * @param name product name
     * @param materials map of material ids and their amounts/weights
     * @param manufactureDate date when the product was manufactured
     * @param endDate date when the product reaches end of life
     */
    public Product(
            String name,
            HashMap<Integer, Float> materials,
            LocalDateTime manufactureDate,
            LocalDateTime endDate
    ) {
        this.id = nextId++;
        this.name = name;
        this.materials = materials;
        this.manufactureDate = manufactureDate;
        this.endDate = endDate;
    }

    /**
     * Returns product id.
     *
     * @return product id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns product name.
     *
     * @return product name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns manufacture date.
     *
     * @return manufacture date
     */
    public LocalDateTime getManufactureDate() {
        return this.manufactureDate;
    }

    /**
     * Returns end date.
     *
     * @return end date
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    /**
     * Returns product materials.
     *
     * @return map of material ids and amounts/weights
     */
    public HashMap<Integer, Float> getMaterials() {
        return this.materials;
    }

    /**
     * Changes product name.
     *
     * @param name new product name
     * @return true if name was changed, false if name was invalid
     */
    public boolean setName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        this.name = name;
        return true;
    }

    /**
     * Returns a readable product description.
     *
     * @return product description
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