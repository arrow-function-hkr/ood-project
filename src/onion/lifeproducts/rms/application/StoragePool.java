package onion.lifeproducts.rms.application;
import onion.lifeproducts.rms.domain.*;

<<<<<<< HEAD
import java.util.ArrayList;
=======
import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;

import java.util.ArrayList;
import java.util.List;
>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)

/**
 * StoragePool works as an in-memory storage container.
 *
 * This class stores all domain objects while
 * the application is running.
 *
 * It acts like a very small temporary database.
 */
public class StoragePool {
<<<<<<< HEAD
	private ArrayList<Product> productsPool;
	private ArrayList<Material> materialsPool;
	// update: remove ProductCategory according to a new UML changes
	//private ArrayList<ProductCategory> productCategoryPool;
	private ArrayList<RecyclingCategory> recyclingCategoryPool;
	private ArrayList<RecyclingGuidance> recyclingGuidancePool;
=======
>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)

	/**
	 * Stores all products.
	 */
	private final List<Product> productsPool;

	/**
	 * Stores all materials.
	 */
	private final List<Material> materialsPool;


	/**
	 * Stores all recycling categories.
	 */
	private final List<RecyclingCategory> recyclingCategoryPool;

	/**
	 * Stores all recycling guidance objects.
	 */
	private final List<RecyclingGuidance> recyclingGuidancePool;

	/**
	 * Creates empty storage lists.
	 *
	 * We use ArrayList because List is only an interface
	 * and cannot be created directly.
	 */
	public StoragePool() {
<<<<<<< HEAD
		productsPool = new ArrayList<Product>();
		materialsPool = new ArrayList<Material>();
		// update: remove ProductCategory according to a new UML changes
		//productCategoryPool = new ArrayList<ProductCategory>();
		recyclingCategoryPool = new ArrayList<RecyclingCategory>();
		recyclingGuidancePool = new ArrayList<RecyclingGuidance>();
=======

		productsPool = new ArrayList<>();
		materialsPool = new ArrayList<>();

		recyclingCategoryPool = new ArrayList<>();

		recyclingGuidancePool = new ArrayList<>();
>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
	}

	/**
	 * Adds a product to storage.
	 */
	public void addProduct(Product product) {
		this.productsPool.add(product);
	}

	/**
	 * Adds a material to storage.
	 */
	public void addMaterial(Material material) {
		this.materialsPool.add(material);
	}
<<<<<<< HEAD
	// update: remove ProductCategory according to a new UML changes
	//public void addProductCategory(ProductCategory productCategory) {
	//	this.productCategory.add(productCategory);
	//}
	public void addRecyclingCategory(RecyclingCategory recyclingCategory) {
		this.recyclingCategoryPool.add(recyclingCategory);
	}
	public void addRecyclingGuidance(RecyclingGuidance recyclingGuidance) {
		this.recyclingGuidancePool.add(recyclingGuidance);
	}

	public ArrayList<Product> getAllProducts() {
		return this.productsPool;
	}
	public ArrayList<Material> getAllMaterials() {
		return this.materialsPool;
	}
	// update: remove ProductCategory according to a new UML changes
	//public ArrayList<Product> getAllProductCategories() {
	//	return this.productCategory;
	//}
	public ArrayList<RecyclingCategory> getAllRecyclingCategories() {
		return this.recyclingCategoryPool;
	}
	public ArrayList<RecyclingGuidance> getAllRecyclingGuidances() {
=======


	/**
	 * Adds a recycling category to storage.
	 */
	public void addRecyclingCategory(RecyclingCategory recyclingCategory) {
		this.recyclingCategoryPool.add(recyclingCategory);
	}

	/**
	 * Adds recycling guidance to storage.
	 */
	public void addRecyclingGuidance(
			RecyclingGuidance recyclingGuidance
	) {
		this.recyclingGuidancePool.add(recyclingGuidance);
	}

	/**
	 * Returns all stored products.
	 */
	public List<Product> getAllProducts() {
		return this.productsPool;
	}

	/**
	 * Returns all stored materials.
	 */
	public List<Material> getAllMaterials() {
		return this.materialsPool;
	}


	/**
	 * Returns all stored recycling categories.
	 */
	public List<RecyclingCategory> getAllRecyclingCategories() {
		return this.recyclingCategoryPool;
	}

	/**
	 * Returns all stored recycling guidance objects.
	 */
	public List<RecyclingGuidance> getAllRecyclingGuidances() {
>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
		return this.recyclingGuidancePool;
	}
}