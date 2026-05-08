package onion.lifeproducts.rms.application;

import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.ProductCategory;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;

import java.util.ArrayList;
import java.util.List;

/**
 * StoragePool works as an in-memory storage container.
 *
 * This class stores all domain objects while
 * the application is running.
 *
 * It acts like a very small temporary database.
 */
public class StoragePool {

	/**
	 * Stores all products.
	 */
	private final List<Product> productsPool;

	/**
	 * Stores all materials.
	 */
	private final List<Material> materialsPool;

	/**
	 * Stores all product categories.
	 */
	private final List<ProductCategory> productCategoryPool;

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

		productsPool = new ArrayList<>();
		materialsPool = new ArrayList<>();

		productCategoryPool = new ArrayList<>();
		recyclingCategoryPool = new ArrayList<>();

		recyclingGuidancePool = new ArrayList<>();
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

	/**
	 * Adds a product category to storage.
	 */
	public void addProductCategory(ProductCategory productCategory) {
		this.productCategoryPool.add(productCategory);
	}

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
	 * Returns all stored product categories.
	 */
	public List<ProductCategory> getAllProductCategories() {
		return this.productCategoryPool;
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
		return this.recyclingGuidancePool;
	}
}