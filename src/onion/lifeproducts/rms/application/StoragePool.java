package onion.lifeproducts.rms.application;

import java.util.ArrayList;


public class StoragePool {
	private ArrayList<Product> productsPool;
	private ArrayList<Material> materialsPool;
	private ArrayList<ProductCategory> productCategoryPool;
	// update: remove ProductCategory according to a new UML changes
	//private ArrayList<RecyclingCategory> recyclingCategoryPool;
	private ArrayList<RecyclingGuidance> recyclingGuidancePool;

	public StoragePool() {
		productsPool = new ArrayList<Product>();
		materialsPool = new ArrayList<Material>();
		// update: remove ProductCategory according to a new UML changes
		//productCategoryPool = new ArrayList<ProductCategory>();
		recyclingCategoryPool = new ArrayList<RecyclingCategory>();
		recyclingGuidancePool = new ArrayList<RecyclingGuidance>();
	}

	public void addProduct(Product product) {
		this.productsPool.add(product);
	}
	public void addMaterial(Material material) {
		this.materialsPool.add(material);
	}
	// update: remove ProductCategory according to a new UML changes
	//public void addProductCategory(ProductCategory productCategory) {
	//	this.productCategory.add(productCategory);
	//}
	public void addRecyclingCategory(RecyclingCategory recyclingCategory) {
		this.recyclingCategoryPool.add(recyclingCategory);
	}
	public void addRecyclingGuidance(RecyclingGuidance recyclingGuidance) {
		this.recyclingGuidance.add(recyclingGuidance);
	}

	public ArrayList<Product> getAllProducts() {
		return this.productsPool;
	}
	public ArrayList<Product> getAllMaterials() {
		return this.materialsPool;
	}
	// update: remove ProductCategory according to a new UML changes
	//public ArrayList<Product> getAllProductCategories() {
	//	return this.productCategory;
	//}
	public ArrayList<Product> getAllRecyclingCategories() {
		return this.recyclingCategoryPool;
	}
	public ArrayList<Product> getAllRecyclingGuidances() {
		return this.recyclingGuidance;
	}
}
