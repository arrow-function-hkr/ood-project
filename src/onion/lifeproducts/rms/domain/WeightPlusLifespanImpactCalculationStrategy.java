package onion.lifeproducts.rms.domain;

import java.time.Duration;
import java.util.List;

/**
 * Impact strategy that includes both material weight/amount and product lifespan.
 *
 * This is a more advanced strategy than SimpleImpactCalculationStrategy.
 */
public class WeightPlusLifespanImpactCalculationStrategy implements ImpactCalculationStrategyInterface {

    /**
     * Calculates impact using material amount and lifespan.
     *
     * Simple idea:
     * - More material amount gives higher impact.
     * - Longer lifespan reduces impact because the product is used longer.
     *
     * @param product product to calculate impact for
     * @return calculated impact value
     */
    @Override
    public float calculateImpact(Product product) {
        float totalWeight = 0;

        for (float materialAmount : product.getMaterials().values()) {
            totalWeight += materialAmount;
        }

        long lifespanDays = Duration.between(
                product.getManufactureDate(),
                product.getEndDate()
        ).toDays();

        if (lifespanDays <= 0) {
            return totalWeight;
        }

        return totalWeight / lifespanDays;
    }

    /**
     * Calculates impact for an array of products.
     *
     * @param products products to calculate impact for
     * @return total impact value
     */
    @Override
    public float calculateImpact(Product[] products) {
        float totalImpact = 0;

        for (Product product : products) {
            totalImpact += calculateImpact(product);
        }

        return totalImpact;
    }

    /**
     * Calculates impact for a list of products.
     *
     * @param products products to calculate impact for
     * @return total impact value
     */
    @Override
    public float calculateImpact(List<Product> products) {
        float totalImpact = 0;

        for (Product product : products) {
            totalImpact += calculateImpact(product);
        }

        return totalImpact;
    }
}