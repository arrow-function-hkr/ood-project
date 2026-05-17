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

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getRecycleRate() {
        return this.recycleRate;
    }

    public float getEmissionFactor() {
        return this.emissionFactor;
    }

    public RecyclingCategory getRecyclingCategory() {
        return this.recyclingCategory;
    }

    public RecyclingGuidance getRecyclingGuidance() {
        return this.recyclingGuidance;
    }

    public boolean setName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        this.name = name;
        return true;
    }

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