package test;


import java.lang.reflect.Field;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import onion.lifeproducts.rms.domain.*;

public class MaterialTest {

	static Material getNewDefaultMaterial() {
		return new Material(
			"",
			0,
			0,
			RecyclingCategory.PARTIALLY_RECYCLABLE,
			new RecyclingGuidance("")
		);
	}

	static Material getNewDefaultMaterial(String name) {
		return new Material(
			name,
			0,
			0,
			RecyclingCategory.PARTIALLY_RECYCLABLE,
			new RecyclingGuidance("")
		);
	}

	static Material getNewDefaultMaterial(RecyclingCategory recyclingCategory) {
		return new Material(
			"",
			0,
			0,
			recyclingCategory,
			new RecyclingGuidance("")
		);
	}
	
	@Test
	@DisplayName("Material should be not null")
	void materialShoudBeNotNull() {
		// arrange & act
		Material material = getNewDefaultMaterial();

		// assert
		assertNotNull(material);
	}


	@Test
	@DisplayName("Materials should have unique IDs in a sequence [1, ∞)")
	void materialssShouldHaveUniqueIDsInASequence() throws Exception {
		// arrange
	
		// Access to the private static field is only used for testing purposes.
		// Needed to reliably test the generation of product ID numbers sequentially,
		// independantly in which order testing methods are invoked
		Field nextId = Material.class.getDeclaredField("nextId");
		nextId.setAccessible(true);
		int originalNextId = nextId.getInt(null);
		nextId.set(null, 1);

		Material
			m1 = getNewDefaultMaterial(),
			m2 = getNewDefaultMaterial(),
			m3 = getNewDefaultMaterial(),
			m4 = getNewDefaultMaterial(),
			m5 = getNewDefaultMaterial(),
			m6 = getNewDefaultMaterial();

		// act
		int
			m1ID = m1.getId(),
			m2ID = m2.getId(),
			m3ID = m3.getId(),
			m4ID = m4.getId(),
			m5ID = m5.getId(),
			m6ID = m6.getId();


		// assert
		assertEquals(1, m1ID);
		assertEquals(2, m2ID);
		assertEquals(3, m3ID);
		assertEquals(4, m4ID);
		assertEquals(5, m5ID);
		assertEquals(6, m6ID);

		// restore original nextId value for future use
		nextId.set(null, originalNextId);
		nextId.setAccessible(false);
	}
}
