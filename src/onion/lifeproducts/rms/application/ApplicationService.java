package onion.lifeproducts.rms.application;

import java.util.List;
import java.time.LocalDateTime;


public class ApplicationService {
	// template methods that are defined in the current UML, that ConsoleUI will (most probably) use
	private StoragePool storagePool;
	
	public ApplicationService() {
		this.storagePool = new StoragePool();
	}

	public Integer addProduct(String name, ProductCategory category, Integer[] materials, LocalDateTime manufactureDate, LocalDateTime endDate) {
		Product newProduct = new Product(name, category, materials, manufactureDate, endDate);
		this.storagePool.addProduct(newProduct);
		return newProduct.getId();
	}

	public Integer addMaterial(String name, float recycleRate, float burnAtmosphereImpact, float decayAtmosphereImpact, float decayGroundImpact, float burnEnvironmentImpact, float decayEnvironmentImpact, LocalDateTime burnTime, LocalDateTime decayTime) {
		Material newMaterial = new Material(name, recycleRate, burnAtmosphereImpact, decayAtmosphereImpact, decayGroundImpact, burnEnvironmentImpact, decayEnvironmentImpact, burnTime, decayTime);
		this.storagePool.addMaterial(newMaterial);
		return newMaterial.getId();
	}
	public boolean addProductCategory(String type) {
		ProductCategory newElement = new ProductCategory(type);
		for (ProductCategory existingElement : this.storagePool.getAllProductCategories()) {
			if(newElement == existingElement) {
				return false;
			}
		}
		this.storagePool.addProductCategory(newElement);
		return true;
	}
	public boolean addRecyclingCategory(String type) {
		RecyclingCategory newElement = new RecyclingCategory(type);
		for (RecyclingCategory existingElement : this.storagePool.getAllRecyclingCategories()) {
			if(newElement == existingElement) {
				return false;
			}
		}
		this.storagePool.addRecyclingCategory(newElement);
		return true;
	}
	public boolean addRecyclingGuidance(String type) {
		RecyclingGuidance newElement = new RecyclingGuidance(type);
		for (RecyclingGuidance existingElement : this.storagePool.getAllRecyclingGuidances()) {
			if(newElement == existingElement) {
				return false;
			}
		}
		this.storagePool.addRecyclingGuidance(newElement);
		return true;
	}
	public List<Product> getAllProducts() {
		return this.storagePool.getAllProducts();
	}
	public List<Integer> getAllProductIds() {
		List<Integer> productIds = new List<Integer>();
		for (Product product : this.storagePool.getAllProducts()) {
			productIds.add(product.getId());
		}
		return productIds;
	}
	public List<Material> getAllMaterials() {
		return this.storagePool.getAllMaterials();
	}
	public List<Integer> getAllMaterialIds() {
		List<Integer> materialIds = new List<Integer>();
		for (Product material : this.storagePool.getAllMaterials()) {
			materialIds.add(material.getId());
		}
		return materialIds;
	}
	public List<ProductCategory> getAllProductCategories() {
		return this.storagePool.getAllProductCategories();
	}
	public List<RecyclingCategory> getAllRecyclingCategories() {
		return this.storagePool.getAllRecyclingCategories();
	}
}
