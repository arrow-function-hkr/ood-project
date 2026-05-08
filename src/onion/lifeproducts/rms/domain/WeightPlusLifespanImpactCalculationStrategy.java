package onion.lifeproducts.rms.domain;

import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Advanced impact strategy.
 * Uses raw material impact and adjusts it by product lifespan.
 */
public class WeightPlusLifespanImpactCalculationStrategy
        implements ImpactCalculationStrategyInterface {

    /**
     * Calculate impact for one product.
     */
    @Override
    public float calculateImpact(Product product) {

        float rawImpact = 0f;

        /*
         * Step 1 + Step 2:
         * Calculate raw product impact by summing all material impacts.
         */
        for (Material material : product.getMaterials()) {

            float materialImpact = 0f;

            materialImpact += material.getBurnAtmosphereImpact();
            materialImpact += material.getDecayAtmosphereImpact();
            materialImpact += material.getDecayGroundImpact();
            materialImpact += material.getBurnEnvironmentImpact();
            materialImpact += material.getDecayEnvironmentImpact();

            rawImpact += materialImpact;
        }

        /*
         * Step 3:
         * Calculate product lifespan in years.
         */
        LocalDateTime manufactureDate = product.getManufactureDate();
        LocalDateTime endDate = product.getEndDate();

        long lifespanDays = ChronoUnit.DAYS.between(manufactureDate, endDate);
        float lifespanYears = lifespanDays / 365.0f;

        if (lifespanYears <= 0f) {
            lifespanYears = 1f;
        }

        /*
         * Step 4:
         * Annual impact = raw impact divided by lifespan.
         */
        return rawImpact / lifespanYears;
    }

    /**
     * Calculate total impact for an array of products.
     */
    @Override
    public float calculateImpact(Product[] products) {

        float totalImpact = 0f;

        for (Product product : products) {
            totalImpact += calculateImpact(product);
        }

        return totalImpact;
    }

    /**
     * Calculate total impact for a list of products.
     */
    @Override
    public float calculateImpact(List<Product> products) {

        float totalImpact = 0f;

        for (Product product : products) {
            totalImpact += calculateImpact(product);
        }

        return totalImpact;
    }
}