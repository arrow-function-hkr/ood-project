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
 * RecyclingService handles recycling-related use cases.
 *
 * It uses the Strategy Pattern through ImpactCalculationStrategyInterface.
 * This means different impact calculation algorithms can be used
 * without changing this service.
 */
public class RecyclingService {

    private final ImpactCalculationStrategyInterface impactCalculationStrategy;

    /**
     * Creates a recycling service with a selected calculation strategy.
     *
     * @param impactCalculationStrategy strategy used to calculate impact
     */
    public RecyclingService(ImpactCalculationStrategyInterface impactCalculationStrategy) {
        this.impactCalculationStrategy = impactCalculationStrategy;
    }

    /**
     * Calculates recycling impact for one product.
     *
     * @param product product to recycle
     * @return calculated impact value
     */
    public float recycle(Product product) {
        return this.impactCalculationStrategy.calculateImpact(product);
    }

    /**
     * Calculates recycling impact for many products.
     *
     * @param products products to recycle
     * @return total calculated impact value
     */
    public float recycleAll(Product[] products) {
        return this.impactCalculationStrategy.calculateImpact(products);
    }

    /**
     * Calculates recycling impact for one material.
     *
     * @param material material to recycle
     * @return calculated material impact
     */
    public float recycle(Material material) {
        return material.getEmissionFactor();
    }

    /**
     * Calculates recycling impact for many materials.
     *
     * @param materials materials to recycle
     * @return total material impact
     */
    public float recycleAll(Material[] materials) {
        float totalImpact = 0;

        for (Material material : materials) {
            totalImpact += recycle(material);
        }

        return totalImpact;
    }

    /**
     * Generates an impact report for one product.
     *
     * @param product product used in the report
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
     * Generates one combined impact report for many products.
     *
     * @param products products used in the report
     * @return generated impact report
     */
    public ImpactReport generateReportForAll(Product[] products) {
        float impactValue = recycleAll(products);

        Set<Integer> uniqueMaterialIds = new HashSet<>();
        int materialAmount = 0;

        for (Product product : products) {
            uniqueMaterialIds.addAll(product.getMaterials().keySet());
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
     * Generates one impact report for each product.
     *
     * @param products products used in the reports
     * @return array of impact reports
     */
    public ImpactReport[] generateReportForEach(Product[] products) {
        return Arrays.stream(products)
                .map(this::generateReport)
                .toArray(ImpactReport[]::new);
    }
}