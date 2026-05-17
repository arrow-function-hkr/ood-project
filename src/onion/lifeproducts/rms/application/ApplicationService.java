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
import java.util.Map;

/**
 * ApplicationService is the static bridge between the presentation layer
 * and the domain/application logic.
 *
 * This class is designed with static methods because ConsoleUIEntry callbacks
 * are created outside of ConsoleUI and cannot access a shared ApplicationService
 * instance through ConsoleUI composition or aggregation.
 *
 * The presentation layer should call this class instead of calling domain
 * objects directly. This keeps the presentation layer separated from the
 * internal domain model.
 *
 * ApplicationService coordinates use cases such as creating products,
 * creating materials, listing stored objects, and recycling products.
 * It does not contain the impact calculation business logic itself.
 */
public final class ApplicationService {

	private static final StoragePool storagePool = new StoragePool();

	/**
	 * Private constructor prevents creating instances of this utility/service class.
	 */
	private ApplicationService() {
	}
	/**
	 * Creates and stores a new product.
	 *
	 * The presentation layer sends material IDs with ratios or amounts.
	 * This method resolves those IDs into real Material domain objects before
	 * creating the Product object.
	 *
	 * @param name product name
	 * @param materialRatios map where key = material id and value = material ratio/amount
	 * @param lifespan product end date
	 * @return id of the created product
	 */
	public static int addProduct(String name, HashMap<Integer, Float> materialRatios, LocalDateTime lifespan) {
		HashMap<Material, Float> materials = (HashMap<Material, Float>)resolveMaterials(materialRatios);
		Product product = new Product(name, materials, LocalDateTime.now(), lifespan);

		storagePool.addProduct(product);

		return product.getId();
	}
	/**
	 * Creates and stores a new material.
	 *
	 * @param name material name
	 * @param recycleRate recycle rate of the material
	 * @param emissionFactor emission factor of the material
	 * @param recyclingCategory index of the RecyclingCategory enum
	 * @param recyclingGuidance recycling guidance text
	 * @return id of the created material
	 */
	public static int addMaterial(
			String name,
			float recycleRate,
			float emissionFactor,
			int recyclingCategory,
			String recyclingGuidance
	) {
		RecyclingCategory category = getRecyclingCategoryFromIndex(recyclingCategory);
		RecyclingGuidance guidance = new RecyclingGuidance(recyclingGuidance);

		storagePool.addRecyclingGuidance(guidance);

		Material material = new Material(
				name,
				recycleRate,
				emissionFactor,
				category,
				guidance
		);

		storagePool.addMaterial(material);

		return material.getId();
	}

	/**
	 * Adds a recycling guidance object if it does not already exist.
	 *
	 * @param type recycling guidance text/type
	 * @return true if the guidance was added, false if it already existed
	 */
	public static boolean addRecyclingGuidance(String type) {
		for (RecyclingGuidance guidance : storagePool.getAllRecyclingGuidance()) {
			if (guidance.toString().equalsIgnoreCase(type)) {
				return false;
			}
		}

		storagePool.addRecyclingGuidance(new RecyclingGuidance(type));
		return true;
	}

	/**
	 * Returns all stored products.
	 *
	 * @return list of all products
	 */
	public static List<Product> getAllProducts() {
		return storagePool.getAllProducts();
	}

	/**
	 * Returns the IDs of all stored products.
	 *
	 * @return list of product IDs
	 */
	public static List<Integer> getAllProductIds() {
		List<Integer> ids = new ArrayList<>();

		for (Product product : storagePool.getAllProducts()) {
			ids.add(product.getId());
		}

		return ids;
	}

	/**
	 * Returns all stored materials.
	 *
	 * @return list of all materials
	 */
	public static List<Material> getAllMaterials() {
		return storagePool.getAllMaterials();
	}

	/**
	 * Returns the IDs of all stored materials.
	 *
	 * @return list of material IDs
	 */
	public static List<Integer> getAllMaterialIds() {
		List<Integer> ids = new ArrayList<>();

		for (Material material : storagePool.getAllMaterials()) {
			ids.add(material.getId());
		}

		return ids;
	}

	/**
	 * Returns all available recycling categories.
	 *
	 * @return list of recycling categories
	 */
	public static List<RecyclingCategory> getAllRecyclingCategories() {
		return Arrays.asList(RecyclingCategory.values());
	}

	/**
	 * Returns text descriptions of all stored products.
	 *
	 * @return list of product descriptions
	 */
	public static List<String> getAllProductDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (Product product : storagePool.getAllProducts()) {
			descriptions.add(product.toString());
		}

		return descriptions;
	}

	/**
	 * Returns text descriptions of all stored materials.
	 *
	 * @return list of material descriptions
	 */
	public static List<String> getAllMaterialDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (Material material : storagePool.getAllMaterials()) {
			descriptions.add(material.toString());
		}

		return descriptions;
	}

	/**
	 * Returns text descriptions of all recycling categories.
	 *
	 * @return list of recycling category descriptions
	 */
	public static List<String> getAllRecyclingCategoryDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (RecyclingCategory category : RecyclingCategory.values()) {
			descriptions.add(category.toString());
		}

		return descriptions;
	}

	/**
	 * Returns the description of one product by its ID.
	 *
	 * @param id product ID
	 * @return list containing the product description, or an empty list if not found
	 */
	public static List<String> getProductDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		Product product = storagePool.getProductById(id);

		if (product != null) {
			result.add(product.toString());
		}

		return result;
	}

	/**
	 * Returns the description of one material by its ID.
	 *
	 * @param id material ID
	 * @return list containing the material description, or an empty list if not found
	 */
	public static List<String> getMaterialDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		Material material = storagePool.getMaterialById(id);

		if (material != null) {
			result.add(material.toString());
		}

		return result;
	}

	/**
	 * Returns the description of one recycling category by its enum index.
	 *
	 * @param id recycling category index
	 * @return list containing the category description, or an empty list if index is invalid
	 */
	public static List<String> getRecyclingCategoryDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		RecyclingCategory[] categories = RecyclingCategory.values();

		if (id >= 0 && id < categories.length) {
			result.add(categories[id].toString());
		}

		return result;
	}

	/**
	 * Recycles a product by ID and generates a text-based impact result.
	 *
	 * @param id product ID
	 * @param impactCalculationStrategyId selected impact calculation strategy ID
	 * @return list of text lines describing the recycling result
	 */
	public static List<String> recycleProductById(int id, int impactCalculationStrategyId) {
		List<String> result = new ArrayList<>();
		Product product = storagePool.getProductById(id);

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
	 * Converts an integer index from the presentation layer into a RecyclingCategory.
	 *
	 * If the index is invalid, NON_RECYCLABLE is returned as a safe fallback.
	 *
	 * @param index recycling category index
	 * @return matching RecyclingCategory, or NON_RECYCLABLE if the index is invalid
	 */
	private static RecyclingCategory getRecyclingCategoryFromIndex(int index) {
		RecyclingCategory[] categories = RecyclingCategory.values();

		if (index < 0 || index >= categories.length) {
			return RecyclingCategory.NON_RECYCLABLE;
		}

		return categories[index];
	}

	/**
	 * Creates the selected impact calculation strategy.
	 *
	 * @param strategyId strategy ID selected by the presentation layer
	 * @return selected impact calculation strategy
	 */
	private static ImpactCalculationStrategyInterface createImpactCalculationStrategy(int strategyId) {
		if (strategyId == 2) {
			return new WeightPlusLifespanImpactCalculationStrategy();
		}

		return new SimpleImpactCalculationStrategy();
	}

	/**
	 * Resolves material IDs from the presentation layer into Material domain objects.
	 *
	 * This allows Product to store real Material objects instead of only material IDs.
	 * Because of this, impact calculation strategies can access full material data,
	 * such as emission factor, recycle rate, recycling category, and guidance.
	 *
	 * @param materialRatios map where key = material ID and value = material ratio/amount
	 * @return map where key = Material object and value = material ratio/amount
	 */
	private static Map<Material, Float> resolveMaterials(Map<Integer, Float> materialRatios) {
		Map<Material, Float> materials = new HashMap<>();

		for (Map.Entry<Integer, Float> entry : materialRatios.entrySet()) {
			Material material = storagePool.getMaterialById(entry.getKey());

			if (material != null) {
				materials.put(material, entry.getValue());
			}
		}

		return materials;
	}
}

