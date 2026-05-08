package onion.lifeproducts.rms.domain;

import java.util.List;

/**
 * Defines the contract for impact calculation strategies.
 *
 * Different classes can implement this interface to calculate impact
 * in different ways.
 */
public interface ImpactCalculationStrategyInterface {

    /**
     * Calculates impact for one product.
     *
     * @param product product to calculate impact for
     * @return impact value
     */
    float calculateImpact(Product product);

    /**
     * Calculates impact for an array of products.
     *
     * @param products products to calculate impact for
     * @return total impact value
     */
    float calculateImpact(Product[] products);

    /**
     * Calculates impact for a list of products.
     *
     * @param products products to calculate impact for
     * @return total impact value
     */
    float calculateImpact(List<Product> products);
}