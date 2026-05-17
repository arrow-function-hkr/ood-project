package onion.lifeproducts.rms.domain;

/**
 * Represents recycling guidance for a material.
 *
 * Example:
 * "Place in plastic recycling bin."
 */
public class RecyclingGuidance {

    private static int nextId = 1;

    private final int id;
    private final String guide;

    /**
     * Creates recycling guidance.
     *
     * @param guide guidance text
     */
    public RecyclingGuidance(String guide) {
        this.id = nextId++;
        this.guide = guide;
    }

    public int getId() {
        return this.id;
    }

    public String getGuide() {
        return this.guide;
    }

    @Override
    public String toString() {
        return this.guide;
    }
}