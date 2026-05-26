package test;

import onion.lifeproducts.rms.application.StoragePool;
import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class StoragePoolTest {

    private static final LocalDateTime MANUFACTURE = LocalDateTime.of(2020, 1, 1, 0, 0);
    private static final LocalDateTime END_DATE    = LocalDateTime.of(2025, 1, 1, 0, 0);

    private StoragePool pool;
    private RecyclingGuidance guidance;

    @BeforeEach
    void setUp() {
        pool = new StoragePool(); // fresh pool for every test — no shared state
        guidance = new RecyclingGuidance("Place in recycling bin");
    }

    // --- helpers ---

    private Material newMaterial(String name) {
        return new Material(name, 0.8f, 3.5f, RecyclingCategory.RECYCLABLE, guidance);
    }

    private Product newProduct(String name, Material mat) {
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(mat, 1.0f);
        return new Product(name, materials, MANUFACTURE, END_DATE);
    }

    // --- products ---

    @Test
    @DisplayName("addProduct then getProductById should return the same product")
    void shouldMakeProductRetrievableByIdAfterAdding() {
        // Arrange
        Material mat = newMaterial("Plastic");
        Product product = newProduct("Bottle", mat);

        // Act
        pool.addProduct(product);
        Product found = pool.getProductById(product.getId());

        // Assert
        assertNotNull(found);
        assertEquals(product.getId(), found.getId());
        assertEquals("Bottle", found.getName());
    }

    @Test
    @DisplayName("getProductById should return null when no product with that ID exists")
    void shouldReturnNullForUnknownProductId() {
        // Arrange — empty pool
        // Act
        Product found = pool.getProductById(99999);

        // Assert
        assertNull(found);
    }

    @Test
    @DisplayName("getAllProducts should return all added products")
    void shouldReturnAllAddedProducts() {
        // Arrange
        Material mat = newMaterial("Plastic");
        pool.addProduct(newProduct("Bottle",  mat));
        pool.addProduct(newProduct("Jar",     mat));
        pool.addProduct(newProduct("Can",     mat));

        // Act & Assert
        assertEquals(3, pool.getAllProducts().size());
    }

    @Test
    @DisplayName("deleteProductById should remove the product and return it")
    void shouldRemoveAndReturnProductOnDelete() {
        // Arrange
        Material mat = newMaterial("Plastic");
        Product product = newProduct("Bottle", mat);
        pool.addProduct(product);

        // Act
        Product deleted = pool.deleteProductById(product.getId());

        // Assert
        assertNotNull(deleted);
        assertEquals(product.getId(), deleted.getId());
        assertNull(pool.getProductById(product.getId())); // no longer in pool
    }

    @Test
    @DisplayName("deleteProductById should return null when the product does not exist")
    void shouldReturnNullWhenDeletingUnknownProductId() {
        // Arrange — empty pool
        // Act
        Product deleted = pool.deleteProductById(99999);

        // Assert
        assertNull(deleted);
    }

    // --- materials ---

    @Test
    @DisplayName("addMaterial then getMaterialById should return the same material")
    void shouldMakeMaterialRetrievableByIdAfterAdding() {
        // Arrange
        Material material = newMaterial("Aluminum");

        // Act
        pool.addMaterial(material);
        Material found = pool.getMaterialById(material.getId());

        // Assert
        assertNotNull(found);
        assertEquals(material.getId(), found.getId());
        assertEquals("Aluminum", found.getName());
    }

    @Test
    @DisplayName("getMaterialById should return null when no material with that ID exists")
    void shouldReturnNullForUnknownMaterialId() {
        // Act
        Material found = pool.getMaterialById(99999);

        // Assert
        assertNull(found);
    }

    @Test
    @DisplayName("deleteMaterialById should remove the material and return it")
    void shouldRemoveAndReturnMaterialOnDelete() {
        // Arrange
        Material material = newMaterial("Glass");
        pool.addMaterial(material);

        // Act
        Material deleted = pool.deleteMaterialById(material.getId());

        // Assert
        assertNotNull(deleted);
        assertEquals(material.getId(), deleted.getId());
        assertNull(pool.getMaterialById(material.getId())); // no longer in pool
    }

    // --- recycling guidance ---

    @Test
    @DisplayName("addRecyclingGuidance then getRecyclingGuidanceById should return the same guidance")
    void shouldMakeGuidanceRetrievableByIdAfterAdding() {
        // Arrange
        RecyclingGuidance rg = new RecyclingGuidance("Sort by colour");

        // Act
        pool.addRecyclingGuidance(rg);
        RecyclingGuidance found = pool.getRecyclingGuidanceById(rg.getId());

        // Assert
        assertNotNull(found);
        assertEquals(rg.getId(), found.getId());
    }

    @Test
    @DisplayName("getRecyclingGuidanceById should return null for an unknown ID")
    void shouldReturnNullForUnknownGuidanceId() {
        // Act
        RecyclingGuidance found = pool.getRecyclingGuidanceById(99999);

        // Assert
        assertNull(found);
    }

}
