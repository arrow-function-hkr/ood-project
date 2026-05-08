package onion.lifeproducts.rms.application;

import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.ProductCategory;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Application service works as a bridge between the UI and the domain layer.
 * It coordinates actions but should not contain business logic.
 */
public class ApplicationService {

	private StoragePool storagePool;

	/**
	 * Creates the application service with its storage pool.
	 */
	public ApplicationService() {
		this.storagePool = new StoragePool();
	}

	/**
	 * Creates a new product and stores it in the storage pool.
	 *
	 * @return the id of the created product
	 */
	public Integer addProduct(
			String name,
			List<Material> materials,
			LocalDateTime manufactureDate,
			LocalDateTime endDate
	) {
		Product newProduct = new Product(
				name,
				materials,
				manufactureDate,
				endDate
		);

		this.storagePool.addProduct(newProduct);

		return newProduct.getId();
	}

	/**
	 * Creates a new material and stores it in the storage pool.
	 *
	 * @return the id of the created material
	 */
	public Integer addMaterial(
			String name,
			float recycleRate,
			float burnAtmosphereImpact,
			float decayAtmosphereImpact,
			float decayGroundImpact,
			float burnEnvironmentImpact,
			float decayEnvironmentImpact,
			LocalDateTime burnTime,
			LocalDateTime decayTime
	) {
		Material newMaterial = new Material(
				name,
				recycleRate,
				burnAtmosphereImpact,
				decayAtmosphereImpact,
				decayGroundImpact,
				burnEnvironmentImpact,
				decayEnvironmentImpact,
				burnTime,
				decayTime
		);

		this.storagePool.addMaterial(newMaterial);

		return newMaterial.getId();
	}

	/**
	 * Adds a product category if it does not already exist.
	 */
	public boolean addProductCategory(String type) {
		ProductCategory newElement = new ProductCategory(type);

		for (ProductCategory existingElement : this.storagePool.getAllProductCategories()) {
			if (newElement == existingElement) {
				return false;
			}
		}

		this.storagePool.addProductCategory(newElement);
		return true;
	}

	/**
	 * Adds a recycling category if it does not already exist.
	 */
	public boolean addRecyclingCategory(String type) {
		RecyclingCategory newElement = new RecyclingCategory(type);

		for (RecyclingCategory existingElement : this.storagePool.getAllRecyclingCategories()) {
			if (newElement == existingElement) {
				return false;
			}
		}

		this.storagePool.addRecyclingCategory(newElement);
		return true;
	}

	/**
	 * Adds recycling guidance if it does not already exist.
	 */
	public boolean addRecyclingGuidance(String type) {
		RecyclingGuidance newElement = new RecyclingGuidance(type);

		for (RecyclingGuidance existingElement : this.storagePool.getAllRecyclingGuidances()) {
			if (newElement == existingElement) {
				return false;
			}
		}

		this.storagePool.addRecyclingGuidance(newElement);
		return true;
	}

	/**
	 * Returns all products.
	 */
	public List<Product> getAllProducts() {
		return this.storagePool.getAllProducts();
	}

	/**
	 * Returns ids of all products.
	 */
	public List<Integer> getAllProductIds() {
		List<Integer> productIds = new ArrayList<>();

		for (Product product : this.storagePool.getAllProducts()) {
			productIds.add(product.getId());
		}

		return productIds;
	}

	/**
	 * Returns all materials.
	 */
	public List<Material> getAllMaterials() {
		return this.storagePool.getAllMaterials();
	}

	/**
	 * Returns ids of all materials.
	 */
	public List<Integer> getAllMaterialIds() {
		List<Integer> materialIds = new ArrayList<>();

		for (Material material : this.storagePool.getAllMaterials()) {
			materialIds.add(material.getId());
		}

		return materialIds;
	}

	/**
	 * Returns all product categories.
	 */
	public List<ProductCategory> getAllProductCategories() {
		return this.storagePool.getAllProductCategories();
	}

	/**
	 * Returns all recycling categories.
	 */
	public List<RecyclingCategory> getAllRecyclingCategories() {
		return this.storagePool.getAllRecyclingCategories();
	}
}