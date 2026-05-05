package onion.lifeproducts.rms.application;
import onion.lifeproducts.rms.domain.*;

import java.util.ArrayList;
import java.time.LocalDateTime;


public class ApplicationService {
	// template methods that are defined in the current UML, that ConsoleUI will (most probably) use
	private StoragePool storagePool;
	
	public ApplicationService() {
		this.storagePool = new StoragePool();
	}

	// update (according to providen environmental impact formulae):
	//  remove ProductCategory productCategory
	//  replace Integer[] meterials with Pair<Integer, float>[] 
	//  replace LocalDateTime manufacture and LocalDateTime endDate with LocalDateTime lifespan  
	public Integer addProduct(String name, Pair<float, Integer>[] materials, LocalDateTime lifespan) {
		Product newProduct = new Product(name, materials, lifespan);
		this.storagePool.addProduct(newProduct);
		return newProduct.getId();
	}

	// update (according to providen environmental impact formulae):
	//  remove float emissionFactorurnEnvironmentImpact
	//  remove float burnAtmosphereImpact
	//  remove float decayAtmosphereImpact
	//  remove float decayGroundImpact
	//  remove float burnEnvironmentImpact
	//  remove float decayEnvironmenImpact
	//  remove float burnTime
	//  remove float decayTime
	//  add float emissionFactor
	public Integer addMaterial(String name, float recycleRate, float emissionFactor, RecycleCategory recycleCategory, RecycleGuidance recycleGuidance) {
		Material newMaterial = new Material(name, recycleRate, emissionFactor, recycleCategory, recycleGuidance);
		return newMaterial.getId();
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
	public ArrayList<Product> getAllProducts() {
		return this.storagePool.getAllProducts();
	}
	public ArrayList<Integer> getAllProductIds() {
		List<Integer> productIds = new List<Integer>();
		for (Product product : this.storagePool.getAllProducts()) {
			productIds.add(product.getId());
		}
		return productIds;
	}
	public ArrayList<Material> getAllMaterials() {
		return this.storagePool.getAllMaterials();
	}
	public ArrayList<Integer> getAllMaterialIds() {
		ArrayList<Integer> materialIds = new ArrayList<Integer>();
		for (Product material : this.storagePool.getAllMaterials()) {
			materialIds.add(material.getId());
		}
		return materialIds;
	}
	public ArrayList<ProductCategory> getAllProductCategories() {
		return this.storagePool.getAllProductCategories();
	}
	public ArrayList<RecyclingCategory> getAllRecyclingCategories() {
		return this.storagePool.getAllRecyclingCategories();
	}
}
