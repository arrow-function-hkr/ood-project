package onion.lifeproducts.rms.domain;

import java.util.List;

/**
 * Simple impact strategy.
 *
 * Uses only direct environmental impact values
 * from materials.
 */
public class SimpleImpactCalculationStrategy
        implements ImpactCalculationStrategyInterface {

    /**
     * Calculates impact for one product.
     */
    @Override
    public float calculateImpact(Product product) {

        float totalImpact = 0f;

        /*
         * Loop through all materials inside the product.
         */
        for (Material material : product.getMaterials()) {

            /*
             * Add all environmental impacts from the material.
             */
            totalImpact += material.getBurnAtmosphereImpact();

            totalImpact += material.getDecayAtmosphereImpact();

            totalImpact += material.getDecayGroundImpact();

            totalImpact += material.getBurnEnvironmentImpact();

            totalImpact += material.getDecayEnvironmentImpact();
        }

        return totalImpact;
    }

    /**
     * Calculates impact for array of products.
     */
    @Override
    public float calculateImpact(Product[] products) {

        float totalImpact = 0f;

        /*
         * Reuse single-product method.
         */
        for (Product product : products) {
            totalImpact += calculateImpact(product);
        }

        return totalImpact;
    }

    /**
     * Calculates impact for list of products.
     */
    @Override
    public float calculateImpact(List<Product> products) {

        float totalImpact = 0f;

        /*
         * Reuse single-product method.
         */
        for (Product product : products) {
            totalImpact += calculateImpact(product);
        }

        return totalImpact;
    }
}