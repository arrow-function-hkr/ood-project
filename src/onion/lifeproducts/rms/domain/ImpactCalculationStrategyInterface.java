package onion.lifeproducts.rms.domain;

import java.util.List;

/**
 * Strategy interface for environmental impact calculations.
 *
 * Different calculation strategies can implement
 * different environmental formulas.
 */
public interface ImpactCalculationStrategyInterface {

    /**
     * Calculates impact for one product.
     */
    float calculateImpact(Product product);

    /**
     * Calculates impact for array of products.
     */
    float calculateImpact(Product[] products);

    /**
     * Calculates impact for list of products.
     */
    float calculateImpact(List<Product> products);
}