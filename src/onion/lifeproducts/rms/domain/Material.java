package onion.lifeproducts.rms.domain;

/**
 * Represents a material used inside products.
 *
 * A material has:
 * - a recycle rate
 * - an emission factor
 * - a recycling category
 * - recycling guidance information
 */
public class Material {

    private static int nextId = 1;

    private final int id;
    private String name;
    private final float recycleRate;
    private final float emissionFactor;
    private final RecyclingCategory recyclingCategory;
    private final RecyclingGuidance recyclingGuidance;

    /**
     * Creates a new material.
     *
     * @param name material name
     * @param recycleRate how recyclable the material is
     * @param emissionFactor environmental impact/emission factor
     * @param recyclingCategory category of recyclability
     * @param recyclingGuidance guidance for recycling this material
     */
    public Material(
            String name,
            float recycleRate,
            float emissionFactor,
            RecyclingCategory recyclingCategory,
            RecyclingGuidance recyclingGuidance
    ) {
        this.id = nextId++;
        this.name = name;
        this.recycleRate = recycleRate;
        this.emissionFactor = emissionFactor;
        this.recyclingCategory = recyclingCategory;
        this.recyclingGuidance = recyclingGuidance;
    }

    /**
     * Returns the material ID.
     *
     * @return material ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the material name.
     *
     * @return material name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the recycle rate.
     *
     * @return recycle rate
     */
    public float getRecycleRate() {
        return this.recycleRate;
    }

    /**
     * Returns the emission factor.
     *
     * @return emission factor
     */
    public float getEmissionFactor() {
        return this.emissionFactor;
    }

    /**
     * Returns the recycling category.
     *
     * @return recycling category
     */
    public RecyclingCategory getRecyclingCategory() {
        return this.recyclingCategory;
    }

    /**
     * Returns the recycling guidance.
     *
     * @return recycling guidance
     */
    public RecyclingGuidance getRecyclingGuidance() {
        return this.recyclingGuidance;
    }

    /**
     * Updates the material name.
     *
     * The name cannot be null or blank.
     *
     * @param name new material name
     * @return true if updated successfully, otherwise false
     */
    public boolean setName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        this.name = name;
        return true;
    }

    /**
     * Returns a readable string representation of the material.
     *
     * @return formatted material information
     */
    @Override
    public String toString() {
        return "Material ID: " + this.id
                + ", Name: " + this.name
                + ", Recycle rate: " + this.recycleRate
                + ", Emission factor: " + this.emissionFactor
                + ", Category: " + this.recyclingCategory
                + ", Guidance: " + this.recyclingGuidance;
    }
}

