package onion.lifeproducts.rms.domain;

import java.util.List;

/**
 * Simple impact calculation strategy.
 *
 * This strategy calculates impact by adding the material amounts/weights
 * stored inside a product.
 */
public class SimpleImpactCalculationStrategy implements ImpactCalculationStrategyInterface {

    /**
     * Calculates impact for one product.
     *
     * Current simple formula:
     * total impact = sum of all material amounts/weights
     *
     * @param product product to calculate impact for
     * @return calculated impact value
     */
    @Override
    public float calculateImpact(Product product) {
        float totalImpact = 0;

        for (float materialAmount : product.getMaterials().values()) {
            totalImpact += materialAmount;
        }

        return totalImpact;
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