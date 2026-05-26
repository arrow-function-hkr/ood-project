package test;

import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;
import onion.lifeproducts.rms.domain.SimpleImpactCalculationStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleImpactCalculationStrategyTest {

    private static final float DELTA = 0.001f;
    private static final LocalDateTime MANUFACTURE = LocalDateTime.of(2020, 1, 1, 0, 0);
    private static final LocalDateTime END_DATE    = LocalDateTime.of(2025, 1, 1, 0, 0);

    private SimpleImpactCalculationStrategy strategy;
    private RecyclingGuidance guidance;

    @BeforeEach
    void setUp() {
        strategy = new SimpleImpactCalculationStrategy();
        guidance = new RecyclingGuidance("Place in recycling bin");
    }

    // --- helpers ---

    private Material material(String name, float emissionFactor) {
        return new Material(name, 0.8f, emissionFactor, RecyclingCategory.RECYCLABLE, guidance);
    }

    private Product product(HashMap<Material, Float> materials) {
        return new Product("Test Product", materials, MANUFACTURE, END_DATE);
    }

    // --- tests ---

    @Test
    @DisplayName("should return zero when product has no materials")
    void shouldReturnZeroWhenProductHasNoMaterials() {
        // Arrange
        Product p = product(new HashMap<>());

        // Act
        float result = strategy.calculateImpact(p);

        // Assert
        assertEquals(0.0f, result, DELTA);
    }

    @Test
    @DisplayName("should multiply material ratio by emission factor for a single material")
    void shouldMultiplyRatioByEmissionFactorForSingleMaterial() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 2.0f); // 2.0 * 3.5 = 7.0
        Product p = product(materials);

        // Act
        float result = strategy.calculateImpact(p);

        // Assert
        assertEquals(7.0f, result, DELTA);
    }

    @Test
    @DisplayName("should sum impact contributions from all materials")
    void shouldSumImpactContributionsFromAllMaterials() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f); // 1.0 * 3.5 = 3.5
        materials.put(material("Steel",   2.2f), 2.0f); // 2.0 * 2.2 = 4.4
        Product p = product(materials);

        // Act
        float result = strategy.calculateImpact(p);

        // Assert
        assertEquals(7.9f, result, DELTA); // 3.5 + 4.4
    }

    @Test
    @DisplayName("should sum impact across an array of products")
    void shouldSumImpactAcrossProductArray() {
        // Arrange
        HashMap<Material, Float> m1 = new HashMap<>();
        m1.put(material("Plastic", 3.5f), 1.0f); // 3.5

        HashMap<Material, Float> m2 = new HashMap<>();
        m2.put(material("Steel", 2.2f), 1.0f); // 2.2

        Product[] products = { product(m1), product(m2) };

        // Act
        float result = strategy.calculateImpact(products);

        // Assert
        assertEquals(5.7f, result, DELTA);
    }

    @Test
    @DisplayName("should sum impact across a list of products")
    void shouldSumImpactAcrossProductList() {
        // Arrange
        HashMap<Material, Float> m1 = new HashMap<>();
        m1.put(material("Plastic", 3.5f), 1.0f); // 3.5

        HashMap<Material, Float> m2 = new HashMap<>();
        m2.put(material("Steel", 2.2f), 1.0f); // 2.2

        List<Product> products = List.of(product(m1), product(m2));

        // Act
        float result = strategy.calculateImpact(products);

        // Assert
        assertEquals(5.7f, result, DELTA);
    }

    @Test
    @DisplayName("should return zero for an empty array of products")
    void shouldReturnZeroForEmptyProductArray() {
        // Arrange
        Product[] products = {};

        // Act
        float result = strategy.calculateImpact(products);

        // Assert
        assertEquals(0.0f, result, DELTA);
    }
}
