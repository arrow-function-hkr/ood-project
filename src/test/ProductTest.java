package test;

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

class ProductTest {

    private static final LocalDateTime MANUFACTURE = LocalDateTime.of(2020, 1, 1, 0, 0);
    private static final LocalDateTime END_DATE    = LocalDateTime.of(2025, 1, 1, 0, 0);

    private RecyclingGuidance guidance;
    private Material material;

    @BeforeEach
    void setUp() {
        guidance = new RecyclingGuidance("Place in recycling bin");
        material = new Material("Plastic", 0.8f, 3.5f, RecyclingCategory.RECYCLABLE, guidance);
    }

    private Product productWithOneMaterial() {
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material, 1.0f);
        return new Product("Bottle", materials, MANUFACTURE, END_DATE);
    }

    // --- setName ---

    @Test
    @DisplayName("setName should accept a valid name and return true")
    void shouldAcceptValidNameAndReturnTrue() {
        // Arrange
        Product p = productWithOneMaterial();

        // Act
        boolean result = p.setName("New Bottle");

        // Assert
        assertTrue(result);
        assertEquals("New Bottle", p.getName());
    }

    @Test
    @DisplayName("setName should reject null and return false")
    void shouldRejectNullNameAndReturnFalse() {
        // Arrange
        Product p = productWithOneMaterial();
        String originalName = p.getName();

        // Act
        boolean result = p.setName(null);

        // Assert
        assertFalse(result);
        assertEquals(originalName, p.getName()); // name unchanged
    }

    @Test
    @DisplayName("setName should reject a blank string and return false")
    void shouldRejectBlankStringNameAndReturnFalse() {
        // Arrange
        Product p = productWithOneMaterial();
        String originalName = p.getName();

        // Act
        boolean result = p.setName("   ");

        // Assert
        assertFalse(result);
        assertEquals(originalName, p.getName()); // name unchanged
    }

    @Test
    @DisplayName("setName should reject an empty string and return false")
    void shouldRejectEmptyStringNameAndReturnFalse() {
        // Arrange
        Product p = productWithOneMaterial();
        String originalName = p.getName();

        // Act
        boolean result = p.setName("");

        // Assert
        assertFalse(result);
        assertEquals(originalName, p.getName());
    }

    // --- getMaterials (defensive copy) ---

    @Test
    @DisplayName("getMaterials should return a copy - external mutations do not affect the product")
    void shouldReturnDefensiveCopyFromGetMaterials() {
        // Arrange
        Product p = productWithOneMaterial();
        int originalSize = p.getMaterials().size();

        // Act - mutate the returned map
        HashMap<Material, Float> returned = p.getMaterials();
        returned.clear();

        // Assert - internal state is unchanged
        assertEquals(originalSize, p.getMaterials().size());
    }

    // --- dates ---

    @Test
    @DisplayName("getManufactureDate should return the date passed at construction")
    void shouldReturnManufactureDatePassedAtConstruction() {
        // Arrange
        Product p = productWithOneMaterial();

        // Act & Assert
        assertEquals(MANUFACTURE, p.getManufactureDate());
    }

    @Test
    @DisplayName("getEndDate should return the date passed at construction")
    void shouldReturnEndDatePassedAtConstruction() {
        // Arrange
        Product p = productWithOneMaterial();

        // Act & Assert
        assertEquals(END_DATE, p.getEndDate());
    }
}
