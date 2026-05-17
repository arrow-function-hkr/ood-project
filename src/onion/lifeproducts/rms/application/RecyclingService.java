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

    public RecyclingService(ImpactCalculationStrategyInterface impactCalculationStrategy) {
        this.impactCalculationStrategy = impactCalculationStrategy;
    }

    public float recycle(Product product) {
        return this.impactCalculationStrategy.calculateImpact(product);
    }

    public float recycleAll(Product[] products) {
        return this.impactCalculationStrategy.calculateImpact(products);
    }

    public float recycle(Material material) {
        return material.getEmissionFactor();
    }

    public float recycleAll(Material[] materials) {
        float totalImpact = 0;

        for (Material material : materials) {
            totalImpact += recycle(material);
        }

        return totalImpact;
    }

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

    public ImpactReport[] generateReportForEach(Product[] products) {
        return Arrays.stream(products)
                .map(this::generateReport)
                .toArray(ImpactReport[]::new);
    }
}