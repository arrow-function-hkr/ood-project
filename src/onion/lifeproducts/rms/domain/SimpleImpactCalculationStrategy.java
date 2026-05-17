package onion.lifeproducts.rms.domain;

import java.util.List;
import java.util.Map;

/**
 * Simple impact calculation strategy.
 *
 * This strategy calculates environmental impact using:
 *
 * material amount × emission factor
 *
 * for all materials inside a product.
 */
public class SimpleImpactCalculationStrategy implements ImpactCalculationStrategyInterface {

    /**
     * Calculates impact for one product.
     *
     * The impact is calculated from each material's:
     * - ratio/amount
     * - emission factor
     *
     * @param product product to calculate impact for
     * @return calculated impact value
     */
    @Override
    public float calculateImpact(Product product) {
        float totalImpact = 0;

        for (Map.Entry<Material, Float> materialEntry : product.getMaterials().entrySet()) {
            totalImpact += materialEntry.getValue()
                    * materialEntry.getKey().getEmissionFactor();
        }

        return totalImpact;
    }

    /**
     * Calculates total impact for multiple products stored in an array.
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
     * Calculates total impact for multiple products stored in a list.
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