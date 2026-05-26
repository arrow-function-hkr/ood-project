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
        // SimpleImpactCalculationStrategy is used as the injected strategy — its behaviour
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
    @DisplayName("should delegate to the injected strategy when recycling a product")
    void shouldDelegateToInjectedStrategyWhenRecyclingProduct() {
        // Arrange — 1.0 * 3.5 = 3.5
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
    @DisplayName("should return the material emission factor directly when recycling a material")
    void shouldReturnEmissionFactorDirectlyWhenRecyclingMaterial() {
        // Arrange
        Material m = material("Aluminum", 12.0f);

        // Act
        float result = service.recycle(m);

        // Assert
        assertEquals(12.0f, result, DELTA);
    }

    // --- recycleAll(Material[]) ---

    @Test
    @DisplayName("should sum emission factors of all materials when recycling an array")
    void shouldSumEmissionFactorsWhenRecyclingMaterialArray() {
        // Arrange
        Material[] materials = {
            material("Aluminum", 12.0f),
            material("Steel",     2.2f),
            material("Glass",     1.1f)
        };

        // Act
        float result = service.recycleAll(materials);

        // Assert — 12.0 + 2.2 + 1.1 = 15.3
        assertEquals(15.3f, result, DELTA);
    }

    // --- generateReport(Product) ---

    @Test
    @DisplayName("should return a non-null report from generateReport")
    void shouldReturnNonNullReportFromGenerateReport() {
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
    @DisplayName("should set the impact value to the strategy result in generateReport")
    void shouldSetImpactValueToStrategyResultInGenerateReport() {
        // Arrange — 2.0 * 5.0 = 10.0
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 5.0f), 2.0f);
        Product p = product(materials);

        // Act
        ImpactReport report = service.generateReport(p);

        // Assert
        assertEquals(10.0f, report.getImpactValue(), DELTA);
    }

    @Test
    @DisplayName("should record one product used in generateReport")
    void shouldRecordOneProductUsedInGenerateReport() {
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
    @DisplayName("should record the correct number of materials in generateReport")
    void shouldRecordCorrectMaterialCountInGenerateReport() {
        // Arrange — product with two materials
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
    @DisplayName("should sum impact across all products in generateReportForAll")
    void shouldSumImpactAcrossAllProductsInGenerateReportForAll() {
        // Arrange — each product: 1.0 * 3.5 = 3.5 → total 7.0
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);

        Product[] products = { product(materials), product(materials) };

        // Act
        ImpactReport report = service.generateReportForAll(products);

        // Assert
        assertEquals(7.0f, report.getImpactValue(), DELTA);
    }

    @Test
    @DisplayName("should record the correct product count in generateReportForAll")
    void shouldRecordCorrectProductCountInGenerateReportForAll() {
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
    @DisplayName("should count shared materials only once as unique in generateReportForAll")
    void shouldCountSharedMaterialsOnlyOnceAsUniqueInGenerateReportForAll() {
        // Arrange — both products share the exact same Material object
        Material sharedMaterial = material("Plastic", 3.5f);
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(sharedMaterial, 1.0f);

        Product[] products = { product(materials), product(materials) };
        // Total material references = 2, but unique materials = 1

        // Act
        ImpactReport report = service.generateReportForAll(products);

        // Assert
        assertEquals(2, report.getMaterialsAmountUsed());      // 2 references
        assertEquals(1, report.getUniqueMaterialAmountUsed()); // 1 unique
    }

    @Test
    @DisplayName("should set generated-at timestamp in generateReportForAll")
    void shouldSetGeneratedAtTimestampInGenerateReportForAll() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);
        Product[] products = { product(materials) };

        // Act
        ImpactReport report = service.generateReportForAll(products);

        // Assert
        assertNotNull(report.getGeneratedAtDate());
    }

    // --- generateReportForEach(Product[]) ---

    @Test
    @DisplayName("should return one report per product in generateReportForEach")
    void shouldReturnOneReportPerProductInGenerateReportForEach() {
        // Arrange
        HashMap<Material, Float> materials = new HashMap<>();
        materials.put(material("Plastic", 3.5f), 1.0f);

        Product[] products = { product(materials), product(materials), product(materials) };

        // Act
        ImpactReport[] reports = service.generateReportForEach(products);

        // Assert
        assertEquals(3, reports.length);
    }

    @Test
    @DisplayName("should calculate impact independently for each product in generateReportForEach")
    void shouldCalculateImpactIndependentlyForEachProductInGenerateReportForEach() {
        // Arrange — two products with different materials
        HashMap<Material, Float> m1 = new HashMap<>();
        m1.put(material("Plastic", 3.5f), 1.0f); // impact = 3.5

        HashMap<Material, Float> m2 = new HashMap<>();
        m2.put(material("Steel", 2.2f), 1.0f); // impact = 2.2

        Product[] products = { product(m1), product(m2) };

        // Act
        ImpactReport[] reports = service.generateReportForEach(products);

        // Assert — each report has only its own product's impact
        assertEquals(3.5f, reports[0].getImpactValue(), DELTA);
        assertEquals(2.2f, reports[1].getImpactValue(), DELTA);
    }
}
