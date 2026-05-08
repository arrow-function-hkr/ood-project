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
 * Represents guidance for how a product or material should be recycled.
 */
public class RecyclingGuidance {

    private static int nextId = 1;

    private int id;
    private String type;

    /**
     * Creates recycling guidance with a type or description.
     */
    public RecyclingGuidance(String type) {
        this.id = nextId++;
        this.type = type;
    }

    /** Returns guidance id. */
    public int getId() {
        return id;
    }

    /** Returns guidance type or description. */
    public String getType() {
        return type;
    }
}
>>>>>>> 02aaf39 (feat(strategy): implement impact calculation strategies)
