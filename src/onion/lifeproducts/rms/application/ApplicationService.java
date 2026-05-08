package onion.lifeproducts.rms.application;
import onion.lifeproducts.rms.domain.*;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;
=======
import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.ProductCategory;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;

>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
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

<<<<<<< HEAD
	// update (according to providen environmental impact formulae):
	//  remove ProductCategory productCategory
	//  replace Integer[] meterials with HashMap<Integer, Float> materials 
	//  replace LocalDateTime manufacture and LocalDateTime endDate with LocalDateTime lifespan  
	public Integer addProduct(String name, HashMap<Integer, Float> materials, LocalDateTime lifespan) {
		Product newProduct = new Product(name, materials, lifespan);
=======
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

>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
		this.storagePool.addProduct(newProduct);

		return newProduct.getId();
	}

<<<<<<< HEAD
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
	public Integer addMaterial(String name, float recycleRate, float emissionFactor, RecyclingCategory recyclingCategory, RecyclingGuidance recyclingGuidance) {
		Material newMaterial = new Material(name, recycleRate, emissionFactor, recyclingCategory, recyclingGuidance);
=======
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

>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
		this.storagePool.addMaterial(newMaterial);

		return newMaterial.getId();
	}

<<<<<<< HEAD
	public boolean addRecyclingCategory(String category) {
		RecyclingCategory newElement = new RecyclingCategory(category);
=======
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

>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
		for (RecyclingCategory existingElement : this.storagePool.getAllRecyclingCategories()) {
			if (newElement == existingElement) {
				return false;
			}
		}

		this.storagePool.addRecyclingCategory(newElement);
		return true;
	}
<<<<<<< HEAD
	public boolean addRecyclingGuidance(String guidance) {
		RecyclingGuidance newElement = new RecyclingGuidance(guidance);
=======

	/**
	 * Adds recycling guidance if it does not already exist.
	 */
	public boolean addRecyclingGuidance(String type) {
		RecyclingGuidance newElement = new RecyclingGuidance(type);

>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
		for (RecyclingGuidance existingElement : this.storagePool.getAllRecyclingGuidances()) {
			if (newElement == existingElement) {
				return false;
			}
		}

		this.storagePool.addRecyclingGuidance(newElement);
		return true;
	}
<<<<<<< HEAD
	public ArrayList<Product> getAllProducts() {
		return this.storagePool.getAllProducts();
	}
	public ArrayList<Integer> getAllProductIds() {
		ArrayList<Integer> productIds = new ArrayList<Integer>();
=======

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

>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
		for (Product product : this.storagePool.getAllProducts()) {
			productIds.add(product.getId());
		}

		return productIds;
	}
<<<<<<< HEAD
	public ArrayList<Material> getAllMaterials() {
		return this.storagePool.getAllMaterials();
	}
	public ArrayList<Integer> getAllMaterialIds() {
		ArrayList<Integer> materialIds = new ArrayList<Integer>();
=======

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

>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
		for (Material material : this.storagePool.getAllMaterials()) {
			materialIds.add(material.getId());
		}

		return materialIds;
	}
<<<<<<< HEAD
	//update: remove ProductCategory according to a new UML changes
	//public ArrayList<ProductCategory> getAllProductCategories() {
	//	return this.storagePool.getAllProductCategories();
	//}
	public ArrayList<RecyclingCategory> getAllRecyclingCategories() {
=======

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
>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
		return this.storagePool.getAllRecyclingCategories();
	}
}