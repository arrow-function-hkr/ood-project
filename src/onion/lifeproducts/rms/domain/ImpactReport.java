package onion.lifeproducts.rms.domain;

import java.time.LocalDateTime;

/**
 * Represents a report generated after impact calculation.
 * The report stores:
 * - when the report was generated
 * - the calculated environmental impact value
 * - how many products were used
 * - how many material references were used
 * - how many unique materials were involved
 */
public class ImpactReport {

    private final LocalDateTime generatedAt;
    private final float impactValue;
    private final int productsAmountUsed;
    private final int materialsAmountUsed;
    private final int uniqueMaterialAmountUsed;

    /**
     * Creates a new impact report.
     *
     * @param generatedAt time when the report was generated
     * @param impactValue calculated impact value
     * @param productsAmountUsed number of products used
     * @param materialsAmountUsed total number of material references used
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
     * Returns the date and time when the report was generated.
     *
     * @return report generation date and time
     */
    public LocalDateTime getGeneratedAtDate() {
        return this.generatedAt;
    }

    /**
     * Returns the calculated impact value.
     *
     * @return impact value
     */
    public float getImpactValue() {
        return this.impactValue;
    }

    /**
     * Returns how many products were used in the report.
     *
     * @return number of products used
     */
    public int getProductsAmountUsed() {
        return this.productsAmountUsed;
    }

    /**
     * Returns the total number of material references used.
     *
     * @return number of material references used
     */
    public int getMaterialsAmountUsed() {
        return this.materialsAmountUsed;
    }

    /**
     * Returns the number of unique materials used.
     *
     * @return number of unique materials used
     */
    public int getUniqueMaterialAmountUsed() {
        return this.uniqueMaterialAmountUsed;
    }

    /**
     * Returns a readable string representation of the impact report.
     *
     * @return formatted impact report information
     */
    @Override
    public String toString() {
        return "Impact Report\n"
                + "Generated At: " + this.generatedAt + "\n"
                + "Impact Value: " + this.impactValue + "\n"
                + "Products Used: " + this.productsAmountUsed + "\n"
                + "Materials Used: " + this.materialsAmountUsed + "\n"
                + "Unique Materials Used: " + this.uniqueMaterialAmountUsed;
    }
}