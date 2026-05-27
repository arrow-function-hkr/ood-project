package onion.lifeproducts.rms.application;

import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.RecyclingGuidance;

import java.util.ArrayList;
import java.util.List;

/**
 * StoragePool works as an in-memory storage container
 * for the application layer.
 *
 * This class stores domain objects temporarily while
 * the application is running.
 * StoragePool is responsible for:
 * <ul>
 *     <li>Storing products</li>
 *     <li>Storing materials</li>
 *     <li>Storing recycling guidance objects</li>
 *     <li>Searching objects by ID</li>
 *     <li>Removing objects by ID</li>
 * </ul>
 *
 * The storage is not persistent. All data exists only
 * during the current application execution.
 */
public class StoragePool {

	private final List<Product> productsPool;
	private final List<Material> materialsPool;
	private final List<RecyclingGuidance> recyclingGuidancePool;

	/**
	 * Creates empty in-memory storage lists.
	 */
	public StoragePool() {
		this.productsPool = new ArrayList<>();
		this.materialsPool = new ArrayList<>();
		this.recyclingGuidancePool = new ArrayList<>();
	}

	/**
	 * Adds a product to the storage pool.
	 *
	 * @param product product to store
	 */
	public void addProduct(Product product) {
		this.productsPool.add(product);
	}

	/**
	 * Adds a material to the storage pool.
	 *
	 * @param material material to store
	 */
	public void addMaterial(Material material) {
		this.materialsPool.add(material);
	}

	/**
	 * Adds a recycling guidance object to the storage pool.
	 *
	 * @param recyclingGuidance recycling guidance to store
	 */
	public void addRecyclingGuidance(RecyclingGuidance recyclingGuidance) {
		this.recyclingGuidancePool.add(recyclingGuidance);
	}

	/**
	 * Returns a copy of all stored products.
	 *
	 * <p>The returned list is a defensive copy - callers may modify it freely
	 * without affecting the internal pool.</p>
	 *
	 * @return copy of the product list
	 */
	public List<Product> getAllProducts() {
		return new ArrayList<>(this.productsPool);
	}

	/**
	 * Returns a copy of all stored materials.
	 *
	 * <p>The returned list is a defensive copy - callers may modify it freely
	 * without affecting the internal pool.</p>
	 *
	 * @return copy of the material list
	 */
	public List<Material> getAllMaterials() {
		return new ArrayList<>(this.materialsPool);
	}

	/**
	 * Returns a copy of all stored recycling guidance objects.
	 *
	 * <p>The returned list is a defensive copy - callers may modify it freely
	 * without affecting the internal pool.</p>
	 *
	 * @return copy of the recycling guidance list
	 */
	public List<RecyclingGuidance> getAllRecyclingGuidance() {
		return new ArrayList<>(this.recyclingGuidancePool);
	}

	/**
	 * Searches for a product by its ID.
	 *
	 * @param id product ID
	 * @return matching product, or null if not found
	 */
	public Product getProductById(int id) {
		for (Product product : this.productsPool) {
			if (product.getId() == id) {
				return product;
			}
		}

		return null;
	}

	/**
	 * Searches for a material by its ID.
	 *
	 * @param id material ID
	 * @return matching material, or null if not found
	 */
	public Material getMaterialById(int id) {
		for (Material material : this.materialsPool) {
			if (material.getId() == id) {
				return material;
			}
		}

		return null;
	}

	/**
	 * Searches for a recycling guidance object by its ID.
	 *
	 * @param id recycling guidance ID
	 * @return matching recycling guidance, or null if not found
	 */
	public RecyclingGuidance getRecyclingGuidanceById(int id) {
		for (RecyclingGuidance guidance : this.recyclingGuidancePool) {
			if (guidance.getId() == id) {
				return guidance;
			}
		}

		return null;
	}

	/**
	 * Deletes a product by its ID.
	 *
	 * @param id product ID
	 * @return removed product, or null if not found
	 */
	public Product deleteProductById(int id) {
		Product product = getProductById(id);

		if (product != null) {
			this.productsPool.remove(product);
		}

		return product;
	}

	/**
	 * Deletes a material by its ID.
	 *
	 * @param id material ID
	 * @return removed material, or null if not found
	 */
	public Material deleteMaterialById(int id) {
		Material material = getMaterialById(id);

		if (material != null) {
			this.materialsPool.remove(material);
		}

		return material;
	}

	/**
	 * Deletes a recycling guidance object by its ID.
	 *
	 * @param id recycling guidance ID
	 * @return removed recycling guidance, or null if not found
	 */
	public RecyclingGuidance deleteRecyclingGuidanceById(int id) {
		RecyclingGuidance guidance = getRecyclingGuidanceById(id);

		if (guidance != null) {
			this.recyclingGuidancePool.remove(guidance);
		}

		return guidance;
	}
}
