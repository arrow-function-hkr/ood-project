package onion.lifeproducts.rms.domain;

/**
 * Represents a material used inside products.
 *
 * A material has a recycle rate, emission factor,
 * recycling category, and recycling guidance.
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
     * Returns material id.
     *
     * @return material id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns material name.
     *
     * @return material name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns recycle rate.
     *
     * @return recycle rate
     */
    public float getRecycleRate() {
        return this.recycleRate;
    }

    /**
     * Returns emission factor.
     *
     * @return emission factor
     */
    public float getEmissionFactor() {
        return this.emissionFactor;
    }

    /**
     * Returns recycling category.
     *
     * @return recycling category
     */
    public RecyclingCategory getRecyclingCategory() {
        return this.recyclingCategory;
    }

    /**
     * Returns recycling guidance.
     *
     * @return recycling guidance
     */
    public RecyclingGuidance getRecyclingGuidance() {
        return this.recyclingGuidance;
    }

    /**
     * Changes material name.
     *
     * @param name new material name
     * @return true if name was changed, false if name was invalid
     */
    public boolean setName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        this.name = name;
        return true;
    }

    /**
     * Returns a readable material description.
     *
     * @return material description
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