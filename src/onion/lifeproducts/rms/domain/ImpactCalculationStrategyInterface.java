package onion.lifeproducts.rms.domain;

import java.util.List;

/**
 * Defines the contract for impact calculation strategies.
 *
 * Different classes can implement this interface to calculate product impact
 * in different ways. Each strategy receives Product objects, and the Product
 * contains its full Material objects with their ratios/amounts.
 */
public interface ImpactCalculationStrategyInterface {

    /**
     * Calculates impact for one product.
     *
     * The strategy can access the product's materials through
     * product.getMaterials(), where each map entry contains:
     *
     * key   = Material
     * value = material ratio/amount in the product
     *
     * @param product product to calculate impact for
     * @return impact value
     */
    float calculateImpact(Product product);

    /**
     * Calculates total impact for an array of products.
     *
     * @param products products to calculate impact for
     * @return total impact value
     */
    float calculateImpact(Product[] products);

    /**
     * Calculates total impact for a list of products.
     *
     * @param products products to calculate impact for
     * @return total impact value
     */
    float calculateImpact(List<Product> products);
}