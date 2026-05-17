package onion.lifeproducts.rms.application;

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

	public void addProduct(Product product) {
		this.productsPool.add(product);
	}

	public void addMaterial(Material material) {
		this.materialsPool.add(material);
	}

	public void addRecyclingGuidance(RecyclingGuidance recyclingGuidance) {
		this.recyclingGuidancePool.add(recyclingGuidance);
	}

	public List<Product> getAllProducts() {
		return this.productsPool;
	}

	public List<Material> getAllMaterials() {
		return this.materialsPool;
	}

	public List<RecyclingGuidance> getAllRecyclingGuidance() {
		return this.recyclingGuidancePool;
	}

	public Product getProductById(int id) {
		for (Product product : this.productsPool) {
			if (product.getId() == id) {
				return product;
			}
		}

		return null;
	}

	public Material getMaterialById(int id) {
		for (Material material : this.materialsPool) {
			if (material.getId() == id) {
				return material;
			}
		}

		return null;
	}

	public RecyclingGuidance getRecyclingGuidanceById(int id) {
		for (RecyclingGuidance guidance : this.recyclingGuidancePool) {
			if (guidance.getId() == id) {
				return guidance;
			}
		}

		return null;
	}

	public Product deleteProductById(int id) {
		Product product = getProductById(id);

		if (product != null) {
			this.productsPool.remove(product);
		}

		return product;
	}

	public Material deleteMaterialById(int id) {
		Material material = getMaterialById(id);

		if (material != null) {
			this.materialsPool.remove(material);
		}

		return material;
	}

	public RecyclingGuidance deleteRecyclingGuidanceById(int id) {
		RecyclingGuidance guidance = getRecyclingGuidanceById(id);

		if (guidance != null) {
			this.recyclingGuidancePool.remove(guidance);
		}

		return guidance;
	}
}