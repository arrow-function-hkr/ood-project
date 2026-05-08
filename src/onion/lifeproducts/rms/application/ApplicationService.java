package onion.lifeproducts.rms.application;

import onion.lifeproducts.rms.domain.ImpactCalculationStrategyInterface;
import onion.lifeproducts.rms.domain.ImpactReport;
import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;
import onion.lifeproducts.rms.domain.SimpleImpactCalculationStrategy;
import onion.lifeproducts.rms.domain.WeightPlusLifespanImpactCalculationStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * ApplicationService is the bridge between the presentation layer
 * and the domain layer.
 *
 * The UI should call this class instead of calling domain objects directly.
 * This class coordinates use cases but does not contain business logic.
 */
public class ApplicationService {

	private final StoragePool storagePool;

	/**
	 * Creates an ApplicationService with an empty storage pool.
	 */
	public ApplicationService() {
		this.storagePool = new StoragePool();
	}

	/**
	 * Creates and stores a new product.
	 *
	 * @param name product name
	 * @param materials map where key = material id and value = material amount/weight
	 * @param lifespan product end date
	 * @return id of the created product
	 */
	public int addProduct(String name, HashMap<Integer, Float> materials, LocalDateTime lifespan) {
		Product product = new Product(name, materials, LocalDateTime.now(), lifespan);

		this.storagePool.addProduct(product);

		return product.getId();
	}

	/**
	 * Creates and stores a new material.
	 *
	 * @param name material name
	 * @param recycleRate recycle rate of the material
	 * @param emissionFactor emission factor of the material
	 * @param recyclingCategory index of RecyclingCategory enum
	 * @param recyclingGuidance guidance text
	 * @return id of the created material
	 */
	public int addMaterial(
			String name,
			float recycleRate,
			float emissionFactor,
			int recyclingCategory,
			String recyclingGuidance
	) {
		RecyclingCategory category = getRecyclingCategoryFromIndex(recyclingCategory);
		RecyclingGuidance guidance = new RecyclingGuidance(recyclingGuidance);

		this.storagePool.addRecyclingGuidance(guidance);

		Material material = new Material(
				name,
				recycleRate,
				emissionFactor,
				category,
				guidance
		);

		this.storagePool.addMaterial(material);

		return material.getId();
	}

	/**
	 * Adds recycling guidance if it does not already exist.
	 *
	 * @param type guidance text
	 * @return true if added, false if it already exists
	 */
	public boolean addRecyclingGuidance(String type) {
		for (RecyclingGuidance guidance : this.storagePool.getAllRecyclingGuidance()) {
			if (guidance.toString().equalsIgnoreCase(type)) {
				return false;
			}
		}

		this.storagePool.addRecyclingGuidance(new RecyclingGuidance(type));
		return true;
	}

	/**
	 * Returns all products.
	 *
	 * @return list of products
	 */
	public List<Product> getAllProducts() {
		return this.storagePool.getAllProducts();
	}

	/**
	 * Returns all product ids.
	 *
	 * @return list of product ids
	 */
	public List<Integer> getAllProductIds() {
		List<Integer> ids = new ArrayList<>();

		for (Product product : this.storagePool.getAllProducts()) {
			ids.add(product.getId());
		}

		return ids;
	}

	/**
	 * Returns all materials.
	 *
	 * @return list of materials
	 */
	public List<Material> getAllMaterials() {
		return this.storagePool.getAllMaterials();
	}

	/**
	 * Returns all material ids.
	 *
	 * @return list of material ids
	 */
	public List<Integer> getAllMaterialIds() {
		List<Integer> ids = new ArrayList<>();

		for (Material material : this.storagePool.getAllMaterials()) {
			ids.add(material.getId());
		}

		return ids;
	}

	/**
	 * Returns all recycling categories from the enum.
	 *
	 * @return list of recycling categories
	 */
	public List<RecyclingCategory> getAllRecyclingCategories() {
		return Arrays.asList(RecyclingCategory.values());
	}

	/**
	 * Returns descriptions of all products.
	 *
	 * @return product descriptions
	 */
	public List<String> getAllProductDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (Product product : this.storagePool.getAllProducts()) {
			descriptions.add(product.toString());
		}

		return descriptions;
	}

	/**
	 * Returns descriptions of all materials.
	 *
	 * @return material descriptions
	 */
	public List<String> getAllMaterialDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (Material material : this.storagePool.getAllMaterials()) {
			descriptions.add(material.toString());
		}

		return descriptions;
	}

	/**
	 * Returns descriptions of all recycling categories.
	 *
	 * @return recycling category descriptions
	 */
	public List<String> getAllRecyclingCategoryDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (RecyclingCategory category : RecyclingCategory.values()) {
			descriptions.add(category.toString());
		}

		return descriptions;
	}

	/**
	 * Returns description of one product by id.
	 *
	 * @param id product id
	 * @return list with product description, or empty list if not found
	 */
	public List<String> getProductDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		Product product = this.storagePool.getProductById(id);

		if (product != null) {
			result.add(product.toString());
		}

		return result;
	}

	/**
	 * Returns description of one material by id.
	 *
	 * @param id material id
	 * @return list with material description, or empty list if not found
	 */
	public List<String> getMaterialDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		Material material = this.storagePool.getMaterialById(id);

		if (material != null) {
			result.add(material.toString());
		}

		return result;
	}

	/**
	 * Returns description of one recycling category by index.
	 *
	 * @param id category index
	 * @return list with category description, or empty list if index is invalid
	 */
	public List<String> getRecyclingCategoryDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		RecyclingCategory[] categories = RecyclingCategory.values();

		if (id >= 0 && id < categories.length) {
			result.add(categories[id].toString());
		}

		return result;
	}

	/**
	 * Recycles a product by id using a selected impact calculation strategy.
	 *
	 * @param id product id
	 * @param impactCalculationStrategyId selected strategy id
	 * @return result lines for the UI
	 */
	public List<String> recycleProductById(int id, int impactCalculationStrategyId) {
		List<String> result = new ArrayList<>();
		Product product = this.storagePool.getProductById(id);

		if (product == null) {
			result.add("Product not found.");
			return result;
		}

		ImpactCalculationStrategyInterface strategy =
				createImpactCalculationStrategy(impactCalculationStrategyId);

		RecyclingService recyclingService = new RecyclingService(strategy);
		ImpactReport report = recyclingService.generateReport(product);

		result.add("Product recycled: " + product.getName());
		result.add("Impact value: " + report.getImpactValue());
		result.add("Generated at: " + report.getGeneratedAtDate());

		return result;
	}

	/**
	 * Converts an integer index into a RecyclingCategory enum value.
	 *
	 * @param index category index
	 * @return recycling category
	 */
	private RecyclingCategory getRecyclingCategoryFromIndex(int index) {
		RecyclingCategory[] categories = RecyclingCategory.values();

		if (index < 0 || index >= categories.length) {
			return RecyclingCategory.NON_RECYCLABLE;
		}

		return categories[index];
	}

	/**
	 * Creates the selected impact calculation strategy.
	 *
	 * @param strategyId strategy id
	 * @return selected impact calculation strategy
	 */
	private ImpactCalculationStrategyInterface createImpactCalculationStrategy(int strategyId) {
		if (strategyId == 2) {
			return new WeightPlusLifespanImpactCalculationStrategy();
		}

		return new SimpleImpactCalculationStrategy();
	}
}