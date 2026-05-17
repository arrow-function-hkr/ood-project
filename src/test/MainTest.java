package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import onion.lifeproducts.rms.application.ApplicationService;
import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;
import onion.lifeproducts.rms.domain.SimpleImpactCalculationStrategy;
import onion.lifeproducts.rms.domain.WeightPlusLifespanImpactCalculationStrategy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MainTest {

	/// example tests

	/// passing

	// @DisplayName("The friendly name of the test (5 < 7)")
	// @Test
	// void fiveLessThanSeven() {
	// 	assertTrue(5 < 7);
	// }
	
	/// failing

	// @DisplayName("The friendly name of the test (5 < 3)")
	// @Test
	// void fiveLessThanThree() {
	// 	assertTrue(5 < 3);
	// }

	@Test
	void simpleImpactUsesMaterialEmissionFactors() {
		Material steel = new Material(
				"Steel",
				0.7f,
				2.5f,
				RecyclingCategory.RECYCLABLE,
				new RecyclingGuidance("Recycle as metal.")
		);
		Material glass = new Material(
				"Glass",
				0.8f,
				1.2f,
				RecyclingCategory.RECYCLABLE,
				new RecyclingGuidance("Recycle as glass.")
		);

		Map<Material, Float> materials = new HashMap<>();
		materials.put(steel, 0.4f);
		materials.put(glass, 0.1f);

		Product product = new Product(
				"Cell phone",
				materials,
				LocalDateTime.of(2026, 1, 1, 0, 0),
				LocalDateTime.of(2026, 1, 11, 0, 0)
		);

		float impact = new SimpleImpactCalculationStrategy().calculateImpact(product);

		assertEquals(1.12f, impact, 0.0001f);
	}

	@Test
	void lifespanStrategyDividesEmissionImpactByProductLifespan() {
		Material copper = new Material(
				"Copper",
				0.6f,
				3.0f,
				RecyclingCategory.PARTIALLY_RECYCLABLE,
				new RecyclingGuidance("Recycle as electronics material.")
		);

		Map<Material, Float> materials = new HashMap<>();
		materials.put(copper, 0.5f);

		Product product = new Product(
				"Cable",
				materials,
				LocalDateTime.of(2026, 1, 1, 0, 0),
				LocalDateTime.of(2026, 1, 11, 0, 0)
		);

		float impact = new WeightPlusLifespanImpactCalculationStrategy().calculateImpact(product);

		assertEquals(0.15f, impact, 0.0001f);
	}

	@Test
	void applicationServiceResolvesMaterialIdsBeforeCreatingProduct() {
		ApplicationService applicationService = new ApplicationService();
		int steelId = applicationService.addMaterial(
				"Steel",
				0.7f,
				2.5f,
				0,
				"Recycle as metal."
		);

		HashMap<Integer, Float> materialRatios = new HashMap<>();
		materialRatios.put(steelId, 0.4f);
		materialRatios.put(-1, 0.6f);

		int productId = applicationService.addProduct(
				"Cell phone",
				materialRatios,
				LocalDateTime.of(2027, 1, 1, 0, 0)
		);

		Product product = applicationService.getAllProducts().stream()
				.filter(candidate -> candidate.getId() == productId)
				.findFirst()
				.orElseThrow();

		assertEquals(1, product.getMaterials().size());
		Material material = product.getMaterials().keySet().iterator().next();
		assertEquals("Steel", material.getName());
		assertEquals(0.4f, product.getMaterials().get(material), 0.0001f);
	}
}
