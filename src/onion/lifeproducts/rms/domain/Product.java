package onion.lifeproducts.rms.domain;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents one product in the system.
 */
public class Product {

    private static int nextId = 1;

    private int id;

    private String name;
    private List<Material> materials;

    private LocalDateTime manufactureDate;
    private LocalDateTime endDate;

    /**
     * Creates a product with materials and lifespan.
     */
    public Product(
            String name,
            List<Material> materials,
            LocalDateTime manufactureDate,
            LocalDateTime endDate
    ) {
        this.id = nextId++;

        this.name = name;
        this.materials = materials;
        this.manufactureDate = manufactureDate;
        this.endDate = endDate;
    }
    /** Returns product id */
    public int getId() {
        return id;
    }

    /** Returns product name */
    public String getName() {
        return name;
    }

    /** Returns all materials inside the product */
    public List<Material> getMaterials() {
        return materials;
    }

    /** Returns manufacture date */
    public LocalDateTime getManufactureDate() {
        return manufactureDate;
    }

    /** Returns end-of-life date */
    public LocalDateTime getEndDate() {
        return endDate;
    }
}