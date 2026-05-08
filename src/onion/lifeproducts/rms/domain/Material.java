package onion.lifeproducts.rms.domain;

import java.time.LocalDateTime;

/**
 * Represents one material and its environmental properties.
 */
public class Material {

    /**
     * Static counter used to generate unique ids.
     */
    private static int nextId = 1;

    /**
     * Unique material id.
     */
    private int id;

    /**
     * Material name.
     */
    private String name;

    /**
     * Recyclability score between 0 and 1.
     */
    private float recycleRate;

    /**
     * Environmental impact values.
     */
    private float burnAtmosphereImpact;
    private float decayAtmosphereImpact;

    private float decayGroundImpact;

    private float burnEnvironmentImpact;
    private float decayEnvironmentImpact;

    /**
     * Estimated burn and decay times.
     */
    private LocalDateTime burnTime;
    private LocalDateTime decayTime;

    /**
     * Creates a material with environmental impact values.
     */
    public Material(
            String name,
            float recycleRate,
            float burnAtmosphereImpact,
            float decayAtmosphereImpact,
            float decayGroundImpact,
            float burnEnvironmentImpact,
            float decayEnvironmentImpact,
            LocalDateTime burnTime,
            LocalDateTime decayTime
    ) {

        // Generate unique id automatically
        this.id = nextId++;

        this.name = name;

        this.recycleRate = recycleRate;

        this.burnAtmosphereImpact = burnAtmosphereImpact;
        this.decayAtmosphereImpact = decayAtmosphereImpact;

        this.decayGroundImpact = decayGroundImpact;

        this.burnEnvironmentImpact = burnEnvironmentImpact;
        this.decayEnvironmentImpact = decayEnvironmentImpact;

        this.burnTime = burnTime;
        this.decayTime = decayTime;
    }

    /** Returns material id */
    public int getId() {
        return id;
    }

    /** Returns material name */
    public String getName() {
        return name;
    }

    /** Returns recyclability score */
    public float getRecycleRate() {
        return recycleRate;
    }

    /** Returns burn atmosphere impact */
    public float getBurnAtmosphereImpact() {
        return burnAtmosphereImpact;
    }

    /** Returns decay atmosphere impact */
    public float getDecayAtmosphereImpact() {
        return decayAtmosphereImpact;
    }

    /** Returns decay ground impact */
    public float getDecayGroundImpact() {
        return decayGroundImpact;
    }

    /** Returns burn environmental impact */
    public float getBurnEnvironmentImpact() {
        return burnEnvironmentImpact;
    }

    /** Returns decay environmental impact */
    public float getDecayEnvironmentImpact() {
        return decayEnvironmentImpact;
    }

    /** Returns burn time */
    public LocalDateTime getBurnTime() {
        return burnTime;
    }

    /** Returns decay time */
    public LocalDateTime getDecayTime() {
        return decayTime;
    }
}