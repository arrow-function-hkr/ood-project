package test;

import onion.lifeproducts.rms.application.RecyclingService;
import onion.lifeproducts.rms.domain.ImpactReport;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecyclingServiceTest {

    private static final float DELTA = 0.001f;
    private static final LocalDateTime MANUFACTURE = LocalDateTime.of(2020, 1, 1, 0, 0);
    private static final LocalDateTime END_DATE    = LocalDateTime.of(2025, 1, 1, 0, 0);

    private RecyclingService service;
    private RecyclingGuidance guidance;

    @BeforeEach
    void setUp() {
        // SimpleImpactCalculationStrategy is used as the injected strategy - its behaviour
        // is already verified in SimpleImpactCalculationStrategyTest, so using it here
        // keeps the focus on RecyclingService's own coordination logic.
        service = new RecyclingService(new SimpleImpactCalculationStrategy());
        guidance = new RecyclingGuidance("Place in recycling bin");
    }

    // --- helpers ---

    private Material material(String name, float emissionFactor) {
        return new Material(name, 0.8f, emissionFactor, RecyclingCategory.RECYCLABLE, guidance);
    }

    private Product product(HashMap<Material, Float> materials) {
        return new Product("Test Product", materials, MANUFACTURE, END_DATE);
    }

    // --- recycle(Product) ---

    @Test
    @DisplayName("recycle(Product) should delegate to the injected strategy")
    void recycleShouldDelegateToStrategy() {
        // Arrange - 1.0 * 3.5 = 3.5
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);
        Product p = product(materials);

        // Act
        float result = service.recycle(p);

        // Assert
        assertEquals(3.5f, result, DELTA);
    }

    // --- recycle(Material) ---

    @Test
    @DisplayName("recycle(Material) should return the material emission factor directly")
    void recycleMaterialShouldReturnEmissionFactor() {
        // Arrange
        Material m = material("Aluminum", 12.0f);

        // Act
        float result = service.recycle(m);

        // Assert
        assertEquals(12.0f, result, DELTA);
    }

    // --- recycleAll(Material[]) ---

    @Test
    @DisplayName("recycleAll(Material[]) should sum emission factors of all materials")
    void recycleAllMaterialsShouldSumEmissionFactors() {
        // Arrange
        Material[] materials = {
            material("Aluminum", 12.0f),
            material("Steel",     2.2f),
            material("Glass",     1.1f)
        };

        // Act
        float result = service.recycleAll(materials);

        // Assert - 12.0 + 2.2 + 1.1 = 15.3
        assertEquals(15.3f, result, DELTA);
    }

    // --- generateReport(Product) ---

    @Test
    @DisplayName("generateReport should return a non-null report")
    void generateReportShouldReturnNonNullReport() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);
        Product p = product(materials);

        // Act
        ImpactReport report = service.generateReport(p);

        // Assert
        assertNotNull(report);
    }

    @Test
    @DisplayName("generateReport should set impact value to the strategy result")
    void generateReportShouldSetCorrectImpactValue() {
        // Arrange - 2.0 * 5.0 = 10.0
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 5.0f), 2.0f);
        Product p = product(materials);

        // Act
        ImpactReport report = service.generateReport(p);

        // Assert
        assertEquals(10.0f, report.getImpactValue(), DELTA);
    }

    @Test
    @DisplayName("generateReport should record one product used")
    void generateReportShouldRecordOneProduct() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);
        Product p = product(materials);

        // Act
        ImpactReport report = service.generateReport(p);

        // Assert
        assertEquals(1, report.getProductsAmountUsed());
    }

    @Test
    @DisplayName("generateReport should record the correct number of materials")
    void generateReportShouldRecordMaterialCount() {
        // Arrange - product with two materials
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);
        materials.put(material("Steel",   2.2f), 1.0f);
        Product p = product(materials);

        // Act
        ImpactReport report = service.generateReport(p);

        // Assert
        assertEquals(2, report.getMaterialsAmountUsed());
    }

    // --- generateReportForAll(Product[]) ---

    @Test
    @DisplayName("generateReportForAll should sum impact across all products")
    void generateReportForAllShouldSumImpact() {
        // Arrange - each product: 1.0 * 3.5 = 3.5 → total 7.0
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);

        Product[] products = { product(materials), product(materials) };

        // Act
        ImpactReport report = service.generateReportForAll(products);

        // Assert
        assertEquals(7.0f, report.getImpactValue(), DELTA);
    }

    @Test
    @DisplayName("generateReportForAll should record the correct product count")
    void generateReportForAllShouldRecordProductCount() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);

        Product[] products = { product(materials), product(materials), product(materials) };

        // Act
        ImpactReport report = service.generateReportForAll(products);

        // Assert
        assertEquals(3, report.getProductsAmountUsed());
    }

    @Test
    @DisplayName("generateReportForAll should set generated-at timestamp")
    void generateReportForAllShouldSetGeneratedAtTimestamp() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);
        Product[] products = { product(materials) };

        // Act
        ImpactReport report = service.generateReportForAll(products);

        // Assert
        assertNotNull(report.getGeneratedAtDate());
    }
}
