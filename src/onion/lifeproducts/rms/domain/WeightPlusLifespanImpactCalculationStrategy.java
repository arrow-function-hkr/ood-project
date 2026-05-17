package onion.lifeproducts.rms.domain;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Impact strategy that includes material emission impact and product lifespan.
 *
 * This strategy first calculates material impact using:
 * material amount × emission factor
 *
 * Then it divides the result by the product lifespan in days.
 */
public class WeightPlusLifespanImpactCalculationStrategy implements ImpactCalculationStrategyInterface {

    /**
     * Calculates impact for one product.
     *
     * If the product has a valid lifespan, the material impact is divided
     * by the number of lifespan days.
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

        long lifespanDays = Duration.between(
                product.getManufactureDate(),
                product.getEndDate()
        ).toDays();

        if (lifespanDays <= 0) {
            return totalImpact;
        }

        return totalImpact / lifespanDays;
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