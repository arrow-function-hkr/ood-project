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
	 * The presentation layer sends material IDs with ratios/amounts.
	 * This method resolves those IDs into real Material objects before
	 * creating the Product domain object.
	 *
	 * @param name product name
	 * @param materialRatios map where key = material id and value = material ratio/amount
	 * @param lifespan product end date
	 * @return id of the created product
	 */
	public int addProduct(String name, Map<Integer, Float> materialRatios, LocalDateTime lifespan) {
		Map<Material, Float> materials = resolveMaterials(materialRatios);
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

	public boolean addRecyclingGuidance(String type) {
		for (RecyclingGuidance guidance : this.storagePool.getAllRecyclingGuidance()) {
			if (guidance.toString().equalsIgnoreCase(type)) {
				return false;
			}
		}

		this.storagePool.addRecyclingGuidance(new RecyclingGuidance(type));
		return true;
	}

	public List<Product> getAllProducts() {
		return this.storagePool.getAllProducts();
	}

	public List<Integer> getAllProductIds() {
		List<Integer> ids = new ArrayList<>();

		for (Product product : this.storagePool.getAllProducts()) {
			ids.add(product.getId());
		}

		return ids;
	}

	public List<Material> getAllMaterials() {
		return this.storagePool.getAllMaterials();
	}

	public List<Integer> getAllMaterialIds() {
		List<Integer> ids = new ArrayList<>();

		for (Material material : this.storagePool.getAllMaterials()) {
			ids.add(material.getId());
		}

		return ids;
	}

	public List<RecyclingCategory> getAllRecyclingCategories() {
		return Arrays.asList(RecyclingCategory.values());
	}

	public List<String> getAllProductDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (Product product : this.storagePool.getAllProducts()) {
			descriptions.add(product.toString());
		}

		return descriptions;
	}

	public List<String> getAllMaterialDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (Material material : this.storagePool.getAllMaterials()) {
			descriptions.add(material.toString());
		}

		return descriptions;
	}

	public List<String> getAllRecyclingCategoryDescriptions() {
		List<String> descriptions = new ArrayList<>();

		for (RecyclingCategory category : RecyclingCategory.values()) {
			descriptions.add(category.toString());
		}

		return descriptions;
	}

	public List<String> getProductDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		Product product = this.storagePool.getProductById(id);

		if (product != null) {
			result.add(product.toString());
		}

		return result;
	}

	public List<String> getMaterialDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		Material material = this.storagePool.getMaterialById(id);

		if (material != null) {
			result.add(material.toString());
		}

		return result;
	}

	public List<String> getRecyclingCategoryDescriptionById(int id) {
		List<String> result = new ArrayList<>();
		RecyclingCategory[] categories = RecyclingCategory.values();

		if (id >= 0 && id < categories.length) {
			result.add(categories[id].toString());
		}

		return result;
	}

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

	private RecyclingCategory getRecyclingCategoryFromIndex(int index) {
		RecyclingCategory[] categories = RecyclingCategory.values();

		if (index < 0 || index >= categories.length) {
			return RecyclingCategory.NON_RECYCLABLE;
		}

		return categories[index];
	}

	private ImpactCalculationStrategyInterface createImpactCalculationStrategy(int strategyId) {
		if (strategyId == 2) {
			return new WeightPlusLifespanImpactCalculationStrategy();
		}

		return new SimpleImpactCalculationStrategy();
	}

	/**
	 * Resolves material IDs from the presentation layer into Material domain objects.
	 *
	 * @param materialRatios map where key = material id and value = material ratio/amount
	 * @return map where key = Material object and value = material ratio/amount
	 */
	private Map<Material, Float> resolveMaterials(Map<Integer, Float> materialRatios) {
		Map<Material, Float> materials = new HashMap<>();

		for (Map.Entry<Integer, Float> entry : materialRatios.entrySet()) {
			Material material = this.storagePool.getMaterialById(entry.getKey());

			if (material != null) {
				materials.put(material, entry.getValue());
			}
		}

		return materials;
	}
}