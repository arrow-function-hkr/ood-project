package onion.lifeproducts.rms.domain;

import java.util.List;
import java.util.Map;

/**
 * Simple impact calculation strategy.
 *
 * This strategy calculates impact from each material's emission factor
 * and its ratio/amount inside a product.
 */
public class SimpleImpactCalculationStrategy implements ImpactCalculationStrategyInterface {

    @Override
    public float calculateImpact(Product product) {
        float totalImpact = 0;

        for (Map.Entry<Material, Float> materialEntry : product.getMaterials().entrySet()) {
            totalImpact += materialEntry.getValue() * materialEntry.getKey().getEmissionFactor();
        }

        return totalImpact;
    }

    @Override
    public float calculateImpact(Product[] products) {
        float totalImpact = 0;

        for (Product product : products) {
            totalImpact += calculateImpact(product);
        }

        return totalImpact;
    }

    @Override
    public float calculateImpact(List<Product> products) {
        float totalImpact = 0;

        for (Product product : products) {
            totalImpact += calculateImpact(product);
        }

        return totalImpact;
    }
}