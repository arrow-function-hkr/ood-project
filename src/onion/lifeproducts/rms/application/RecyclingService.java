package onion.lifeproducts.rms.application;

import onion.lifeproducts.rms.domain.ImpactCalculationStrategyInterface;
import onion.lifeproducts.rms.domain.ImpactReport;
import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * RecyclingService handles recycling-related application use cases.
 *
 * This service acts as a coordinator between the application layer
 * and the domain layer during recycling operations.
 *
 * RecyclingService uses the Strategy Pattern through
 * ImpactCalculationStrategyInterface. This allows the system
 * to switch between different impact calculation algorithms
 * without changing the recycling workflow itself.
 *
 * The service is responsible for:
 * <ul>
 *     <li>Recycling products</li>
 *     <li>Recycling collections of products</li>
 *     <li>Generating impact reports</li>
 *     <li>Calculating material-related impact values</li>
 * </ul>
 *
 * Business calculation logic itself is delegated to strategy classes
 * located in the domain layer.
 */
public class RecyclingService {

    private final ImpactCalculationStrategyInterface impactCalculationStrategy;

    /**
     * Creates a RecyclingService with a selected impact calculation strategy.
     *
     * @param impactCalculationStrategy strategy used to calculate environmental impact
     */
    public RecyclingService(ImpactCalculationStrategyInterface impactCalculationStrategy) {
        this.impactCalculationStrategy = impactCalculationStrategy;
    }

    /**
     * Recycles a single product using the selected impact calculation strategy.
     *
     * @param product product to recycle
     * @return calculated environmental impact value
     */
    public float recycle(Product product) {
        return this.impactCalculationStrategy.calculateImpact(product);
    }

    /**
     * Recycles multiple products using the selected impact calculation strategy.
     *
     * @param products array of products to recycle
     * @return total calculated environmental impact value
     */
    public float recycleAll(Product[] products) {
        return this.impactCalculationStrategy.calculateImpact(products);
    }

    /**
     * Recycles a single material.
     *
     * Currently, the material emission factor is used directly
     * as the material impact value.
     *
     * @param material material to recycle
     * @return material emission factor
     */
    public float recycle(Material material) {
        return material.getEmissionFactor();
    }

    /**
     * Recycles multiple materials and sums their impact values.
     *
     * @param materials array of materials to recycle
     * @return total impact value for all materials
     */
    public float recycleAll(Material[] materials) {
        float totalImpact = 0;

        for (Material material : materials) {
            totalImpact += recycle(material);
        }

        return totalImpact;
    }

    /**
     * Generates an impact report for a single recycled product.
     *
     * @param product recycled product
     * @return generated impact report
     */
    public ImpactReport generateReport(Product product) {
        float impactValue = recycle(product);

        return new ImpactReport(
                LocalDateTime.now(),
                impactValue,
                1,
                product.getMaterials().size(),
                product.getMaterials().size()
        );
    }

    /**
     * Generates a combined impact report for multiple recycled products.
     *
     * The report includes:
     * <ul>
     *     <li>Total impact value</li>
     *     <li>Total amount of products</li>
     *     <li>Total material amount</li>
     *     <li>Amount of unique materials used</li>
     * </ul>
     *
     * @param products products to recycle
     * @return generated impact report
     */
    public ImpactReport generateReportForAll(Product[] products) {
        float impactValue = recycleAll(products);

        Set<Integer> uniqueMaterialIds = new HashSet<>();
        int materialAmount = 0;

        for (Product product : products) {
            for (Material material : product.getMaterials().keySet()) {
                uniqueMaterialIds.add(material.getId());
            }

            materialAmount += product.getMaterials().size();
        }

        return new ImpactReport(
                LocalDateTime.now(),
                impactValue,
                products.length,
                materialAmount,
                uniqueMaterialIds.size()
        );
    }

    /**
     * Generates separate impact reports for each product in the array.
     *
     * @param products products to recycle
     * @return array of generated impact reports
     */
    public ImpactReport[] generateReportForEach(Product[] products) {
        return Arrays.stream(products)
                .map(this::generateReport)
                .toArray(ImpactReport[]::new);
    }
}

