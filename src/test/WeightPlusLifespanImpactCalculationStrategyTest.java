package test;

import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;
import onion.lifeproducts.rms.domain.WeightPlusLifespanImpactCalculationStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightPlusLifespanImpactCalculationStrategyTest {

    private static final float DELTA = 0.001f;

    // Fixed 10-day lifespan keeps the expected values easy to verify manually.
    private static final LocalDateTime MANUFACTURE    = LocalDateTime.of(2020, 1, 1, 0, 0);
    private static final LocalDateTime END_10_DAYS    = LocalDateTime.of(2020, 1, 11, 0, 0);
    private static final LocalDateTime END_SAME_DAY   = LocalDateTime.of(2020, 1, 1, 0, 0);
    private static final LocalDateTime END_BEFORE_MFG = LocalDateTime.of(2019, 6, 1, 0, 0);

    private WeightPlusLifespanImpactCalculationStrategy strategy;
    private RecyclingGuidance guidance;

    @BeforeEach
    void setUp() {
        strategy = new WeightPlusLifespanImpactCalculationStrategy();
        guidance = new RecyclingGuidance("Place in recycling bin");
    }

    // --- helpers ---

    private Material material(String name, float emissionFactor) {
        return new Material(name, 0.8f, emissionFactor, RecyclingCategory.RECYCLABLE, guidance);
    }

    private Product product(HashMap<Material, Float> materials, LocalDateTime endDate) {
        return new Product("Test Product", materials, MANUFACTURE, endDate);
    }

    // --- tests ---

    @Test
    @DisplayName("should divide raw impact by lifespan in days")
    void shouldDivideRawImpactByLifespanDays() {
        // Arrange — ratio=2.0, emissionFactor=5.0 → raw=10.0, lifespan=10 days → 10.0/10=1.0
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 5.0f), 2.0f);
        Product p = product(materials, END_10_DAYS);

        // Act
        float result = strategy.calculateImpact(p);

        // Assert
        assertEquals(1.0f, result, DELTA);
    }

    @Test
    @DisplayName("should sum all materials before dividing by lifespan")
    void shouldSumAllMaterialsBeforeDividingByLifespan() {
        // Arrange — two materials: (1.0*4.0)+(1.0*6.0)=10.0, lifespan=10 days → 1.0
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 4.0f), 1.0f);
        materials.put(material("Steel",   6.0f), 1.0f);
        Product p = product(materials, END_10_DAYS);

        // Act
        float result = strategy.calculateImpact(p);

        // Assert
        assertEquals(1.0f, result, DELTA);
    }

    @Test
    @DisplayName("should return raw impact when lifespan is zero days")
    void shouldReturnRawImpactWhenLifespanIsZeroDays() {
        // Arrange — end date equals manufacture date → 0 days → fallback to raw sum
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 5.0f), 2.0f); // raw = 10.0
        Product p = product(materials, END_SAME_DAY);

        // Act
        float result = strategy.calculateImpact(p);

        // Assert — no division, returns 10.0
        assertEquals(10.0f, result, DELTA);
    }

    @Test
    @DisplayName("should return raw impact when end date is before manufacture date")
    void shouldReturnRawImpactWhenEndDateIsBeforeManufactureDate() {
        // Arrange — negative lifespan is treated the same as zero (guard: lifespanDays <= 0)
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 5.0f), 2.0f); // raw = 10.0
        Product p = product(materials, END_BEFORE_MFG);

        // Act
        float result = strategy.calculateImpact(p);

        // Assert — no division, returns 10.0
        assertEquals(10.0f, result, DELTA);
    }

    @Test
    @DisplayName("should return zero when product has no materials")
    void shouldReturnZeroWhenProductHasNoMaterials() {
        // Arrange
        Product p = product(new HashMap<>(), END_10_DAYS);

        // Act
        float result = strategy.calculateImpact(p);

        // Assert
        assertEquals(0.0f, result, DELTA);
    }

    @Test
    @DisplayName("should sum lifespan-adjusted impact across an array of products")
    void shouldSumAdjustedImpactAcrossProductArray() {
        // Arrange — each product: raw=10.0, lifespan=10 days → 1.0 per product
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 5.0f), 2.0f);

        Product[] products = {
            product(materials, END_10_DAYS), // 1.0
            product(materials, END_10_DAYS)  // 1.0
        };

        // Act
        float result = strategy.calculateImpact(products);

        // Assert
        assertEquals(2.0f, result, DELTA);
    }

    @Test
    @DisplayName("should sum lifespan-adjusted impact across a list of products")
    void shouldSumAdjustedImpactAcrossProductList() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 5.0f), 2.0f);

        List<Product> products = List.of(
            product(materials, END_10_DAYS), // 1.0
            product(materials, END_10_DAYS)  // 1.0
        );

        // Act
        float result = strategy.calculateImpact(products);

        // Assert
        assertEquals(2.0f, result, DELTA);
    }
}
