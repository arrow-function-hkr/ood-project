package onion.lifeproducts.rms.application;

import java.util.List;


public class StoragePool {
	private List<Product> productsPool;
	private List<Material> materialsPool;
	private List<ProductCategory> productCategoryPool;
	private List<RecyclingCategory> recyclingCategoryPool;
	private List<RecyclingGuidance> recyclingGuidancePool;

	public StoragePool() {
		productsPool = new List<Product>();
		materialsPool = new List<Material>();
		productCategoryPool = new List<ProductCategory>();
		recyclingCategoryPool = new List<RecyclingCategory>();
		recyclingGuidancePool = new List<RecyclingGuidance>();
	}

	public void addProduct(Product product) {
		this.productsPool.add(product);
	}
	public void addMaterial(Material material) {
		this.materialsPool.add(material);
	}
	public void addProductCategory(ProductCategory productCategory) {
		this.productCategory.add(productCategory);
	}
	public void addRecyclingCategory(RecyclingCategory recyclingCategory) {
		this.recyclingCategoryPool.add(recyclingCategory);
	}
	public void addRecyclingGuidance(RecyclingGuidance recyclingGuidance) {
		this.recyclingGuidance.add(recyclingGuidance);
	}

	public List<Product> getAllProducts() {
		return this.productsPool;
	}
	public List<Product> getAllMaterials() {
		return this.materialsPool;
	}
	public List<Product> getAllProductCategories() {
		return this.productCategory;
	}
	public List<Product> getAllRecyclingCategories() {
		return this.recyclingCategoryPool;
	}
	public List<Product> getAllRecyclingGuidances() {
		return this.recyclingGuidance;
	}
}
