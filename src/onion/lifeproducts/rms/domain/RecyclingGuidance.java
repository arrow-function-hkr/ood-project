package onion.lifeproducts.rms.domain;

<<<<<<< HEAD
public class RecyclingGuidance {
	final private String guidance;
	
	public RecyclingGuidance(String guidance) {
		this.guidance = guidance;
	}
	
	public String getGuidance() {
		return this.guidance;
	}
}
=======
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

    /**
     * Returns guidance id.
     *
     * @return guidance id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns guidance text.
     *
     * @return guidance text
     */
    public String getGuide() {
        return this.guide;
    }

    /**
     * Returns readable guidance text.
     *
     * @return guidance description
     */
    @Override
    public String toString() {
        return this.guide;
    }
}
>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
