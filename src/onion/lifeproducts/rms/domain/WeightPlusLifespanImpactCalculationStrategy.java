package onion.lifeproducts.rms.domain;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Impact strategy that includes material emission impact and product lifespan.
 *
 * This is a more advanced strategy than SimpleImpactCalculationStrategy.
 */
public class WeightPlusLifespanImpactCalculationStrategy implements ImpactCalculationStrategyInterface {

    @Override
    public float calculateImpact(Product product) {
        float totalImpact = 0;

        for (Map.Entry<Material, Float> materialEntry : product.getMaterials().entrySet()) {
            totalImpact += materialEntry.getValue() * materialEntry.getKey().getEmissionFactor();
        }

        long lifespanDays = Duration.between(
                product.getManufactureDate(),
                product.getEndDate()
        ).toDays();

        if (lifespanDays <= 0) {
            return totalImpact;
        }

        return totalImpact / lifespanDays;
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