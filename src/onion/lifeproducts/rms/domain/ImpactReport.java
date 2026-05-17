package onion.lifeproducts.rms.domain;

import java.time.LocalDateTime;

/**
 * Represents a report generated after impact calculation.
 *
 * The report stores the generated date, calculated impact value,
 * number of products included, total material references used,
 * and number of unique materials used.
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
     * @param productsAmountUsed number of products used in the report
     * @param materialsAmountUsed total material references used in the report
     * @param uniqueMaterialAmountUsed number of unique materials used in the report
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

    public LocalDateTime getGeneratedAtDate() {
        return this.generatedAt;
    }

    public float getImpactValue() {
        return this.impactValue;
    }

    public int getProductsAmountUsed() {
        return this.productsAmountUsed;
    }

    public int getMaterialsAmountUsed() {
        return this.materialsAmountUsed;
    }

    public int getUniqueMaterialAmountUsed() {
        return this.uniqueMaterialAmountUsed;
    }
}