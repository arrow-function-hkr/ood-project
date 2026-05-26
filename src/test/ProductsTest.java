package test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import onion.lifeproducts.rms.domain.*;

public class ProductsTest {

	static LocalDateTime defaultStartDate = LocalDateTime.now();
	static LocalDateTime defaultEndDate = defaultStartDate.withYear(defaultStartDate.getYear() + 1);
	static HashMap<Material, Float> defaultMaterials = new HashMap<>(Map.of(
		new Material(
			"",
			0.0f,
			0.0f,
			RecyclingCategory.PARTIALLY_RECYCLABLE,
			new RecyclingGuidance("")
		),
		1.0f
	));
	static String defaultProductName = "";

	static Product getNewDefaultProduct() {
		return new Product(defaultProductName, defaultMaterials, defaultStartDate, defaultEndDate);
	}

	static Product getNewDefaultProduct(String name) {
		return new Product(name, defaultMaterials, defaultStartDate, defaultEndDate);
	}

	static Product getNewDefaultProduct(HashMap<Material, Float> materials) {
		return new Product(defaultProductName, materials, defaultStartDate, defaultEndDate);
	}

	@Test
	@DisplayName("Product should be not null")
	void productCreation() {
		// arrange & act
		Product product = getNewDefaultProduct();
		
		// assert
		assertNotNull(product);
	}

	@Test
	@DisplayName("Product should have a name")
	void productShouldHaveName() {
		// arrange
		String productName = "product 1";
		Product product = getNewDefaultProduct(productName);

		// act
		String actualProductName = product.getName();
		
		// assert
		assertEquals(productName, actualProductName);
	}

	@Test
	@DisplayName("Product should have a non-empty list of materials")
	void productShouldHaveNonEmptyListOfMaterials() {
		// arrange
		Product product = getNewDefaultProduct();

		// act
		int productMaterialsSize = product.getMaterials().size();

		// assert
		assertNotEquals(0, productMaterialsSize);
	}

	@Test
	@DisplayName("Products should have unique IDs in a sequence [1, ∞)")
	void productsShouldHaveUniqueIDsInASequence() throws Exception {
		// arrange
	
		// Access to the private static field is only used for testing purposes.
		// Needed to reliably test the generation of product ID numbers sequentially,
		// independantly in which order testing methods are invoked
		Field nextId = Product.class.getDeclaredField("nextId");
		nextId.setAccessible(true);
		int originalNextId = nextId.getInt(null);
		nextId.set(null, 1);

		Product
			p1 = getNewDefaultProduct(),
			p2 = getNewDefaultProduct(),
			p3 = getNewDefaultProduct(),
			p4 = getNewDefaultProduct(),
			p5 = getNewDefaultProduct(),
			p6 = getNewDefaultProduct();

		// act
		int
			p1ID = p1.getId(),
			p2ID = p2.getId(),
			p3ID = p3.getId(),
			p4ID = p4.getId(),
			p5ID = p5.getId(),
			p6ID = p6.getId();


		// assert
		assertEquals(1, p1ID);
		assertEquals(2, p2ID);
		assertEquals(3, p3ID);
		assertEquals(4, p4ID);
		assertEquals(5, p5ID);
		assertEquals(6, p6ID);

		// restore original nextId value for future use
		nextId.set(null, originalNextId);
		nextId.setAccessible(false);
	}

	@Test
	@DisplayName("Product should be properly formated")
	void productShouldBeProperlyFormated() {
		// arrange
		String productName = "ProductName";
		Product product = getNewDefaultProduct(productName);

		// act
		String productAsString = product.toString();
		String expected = String.format(
			"Product ID: %d, Name: %s, Materials: %s, Manufacture date: %s, End date: %s",
			product.getId(),
			productName,
			defaultMaterials,
			defaultStartDate,
			defaultEndDate
		);

		// assert
		assertEquals(expected, productAsString);
	}

	@Test
	@DisplayName("Product should be able to have new name")
	void productShouldBeAbleToHaveNewName() {
		// arrange
		Product product = getNewDefaultProduct();

		// act
		boolean newNameSetSuccessfully = product.setName("new name");
		
		// assert
		assertTrue(newNameSetSuccessfully);
	}

	@Test
	@DisplayName("Product should not be able to have new empty/null name")
	void productShouldNotBeAbleToHaveNewEmptyOrNullName() {
		// arrange
		Product product = getNewDefaultProduct();

		// act
		boolean newNameSetSuccessfullyForEmpty = product.setName("");
		boolean newNameSetSuccessfullyForNull = product.setName(null);
		
		// assert
		assertFalse(newNameSetSuccessfullyForEmpty);
		assertFalse(newNameSetSuccessfullyForNull);
	}

}
