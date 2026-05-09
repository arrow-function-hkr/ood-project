package onion.lifeproducts.rms.application;
import onion.lifeproducts.rms.domain.*;

import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.RecyclingGuidance;

import java.util.ArrayList;
import java.util.List;

/**
 * StoragePool works as an in-memory storage container.
 *
 * It stores products, materials, and recycling guidance objects
 * while the application is running.
 */
public class StoragePool {

	private final List<Product> productsPool;
	private final List<Material> materialsPool;
	private final List<RecyclingGuidance> recyclingGuidancePool;

	/**
	 * Creates empty storage lists.
	 */
	public StoragePool() {
		this.productsPool = new ArrayList<>();
		this.materialsPool = new ArrayList<>();
		this.recyclingGuidancePool = new ArrayList<>();
	}

	/**
	 * Adds a product to storage.
	 *
	 * @param product product to add
	 */
	public void addProduct(Product product) {
		this.productsPool.add(product);
	}

	/**
	 * Adds a material to storage.
	 *
	 * @param material material to add
	 */
	public void addMaterial(Material material) {
		this.materialsPool.add(material);
	}

	/**
	 * Adds recycling guidance to storage.
	 *
	 * @param recyclingGuidance guidance to add
	 */
	public void addRecyclingGuidance(RecyclingGuidance recyclingGuidance) {
		this.recyclingGuidancePool.add(recyclingGuidance);
	}

	/**
	 * Returns all products.
	 *
	 * @return list of products
	 */
	public List<Product> getAllProducts() {
		return this.productsPool;
	}

	/**
	 * Returns all materials.
	 *
	 * @return list of materials
	 */
	public List<Material> getAllMaterials() {
		return this.materialsPool;
	}

	/**
	 * Returns all recycling guidance objects.
	 *
	 * @return list of recycling guidance objects
	 */
	public List<RecyclingGuidance> getAllRecyclingGuidance() {
		return this.recyclingGuidancePool;
	}

	/**
	 * Finds a product by id.
	 *
	 * @param id product id
	 * @return product if found, otherwise null
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
	 * Finds a material by id.
	 *
	 * @param id material id
	 * @return material if found, otherwise null
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
	 * Finds recycling guidance by id.
	 *
	 * @param id recycling guidance id
	 * @return recycling guidance if found, otherwise null
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
	 * Deletes a product by id.
	 *
	 * @param id product id
	 * @return deleted product if found, otherwise null
	 */
	public Product deleteProductById(int id) {
		Product product = getProductById(id);

		if (product != null) {
			this.productsPool.remove(product);
		}

		return product;
	}

	/**
	 * Deletes a material by id.
	 *
	 * @param id material id
	 * @return deleted material if found, otherwise null
	 */
	public Material deleteMaterialById(int id) {
		Material material = getMaterialById(id);

		if (material != null) {
			this.materialsPool.remove(material);
		}

		return material;
	}

	/**
	 * Deletes recycling guidance by id.
	 *
	 * @param id recycling guidance id
	 * @return deleted guidance if found, otherwise null
	 */
	public RecyclingGuidance deleteRecyclingGuidanceById(int id) {
		RecyclingGuidance guidance = getRecyclingGuidanceById(id);

		if (guidance != null) {
			this.recyclingGuidancePool.remove(guidance);
		}

		return guidance;
	}
}