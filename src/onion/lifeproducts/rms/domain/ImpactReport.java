package onion.lifeproducts.rms.domain;

import java.time.LocalDateTime;

/**
 * Represents a report generated after impact calculation.
 *
 * The report stores the generated date, impact value,
 * number of products, number of materials, and number of unique materials.
 */
public class ImpactReport {

    private final LocalDateTime generatedAt;
    private final float impactValue;
    private final int productsAmountUsed;
    private final int materialsAmountUsed;
    private final int uniqueMaterialAmountUsed;

    /**
     * Creates an impact report.
     *
     * @param generatedAt time when report was generated
     * @param impactValue calculated impact value
     * @param productsAmountUsed number of products used
     * @param materialsAmountUsed total material references used
     * @param uniqueMaterialAmountUsed number of unique materials used
     */
    public ImpactReport(
            LocalDateTime generatedAt,
            float impactValue,
            int productsAmountUsed,
            int materialsAmountUsed,
            int uniqueMaterialAmountUsed
    ) {
        this.generatedAt = generatedAt;
        this.impactValue = impactValue;
        this.productsAmountUsed = productsAmountUsed;
        this.materialsAmountUsed = materialsAmountUsed;
        this.uniqueMaterialAmountUsed = uniqueMaterialAmountUsed;
    }

    /**
     * Returns report generation date.
     *
     * @return generated date
     */
    public LocalDateTime getGeneratedAtDate() {
        return this.generatedAt;
    }

    /**
     * Returns impact value.
     *
     * @return impact value
     */
    public float getImpactValue() {
        return this.impactValue;
    }

    /**
     * Returns number of products used.
     *
     * @return product amount
     */
    public int getProductsAmountUsed() {
        return this.productsAmountUsed;
    }

    /**
     * Returns number of material references used.
     *
     * @return material amount
     */
    public int getMaterialsAmountUsed() {
        return this.materialsAmountUsed;
    }

    /**
     * Returns number of unique materials used.
     *
     * @return unique material amount
     */
    public int getUniqueMaterialAmountUsed() {
        return this.uniqueMaterialAmountUsed;
    }
}