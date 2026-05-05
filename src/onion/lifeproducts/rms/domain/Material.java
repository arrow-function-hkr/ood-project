package onion.lifeproducts.rms.application;

public class Material {

    private final String name;
    private final RecyclingCategory recyclingCategory;
    private final double carbonFootprint;

    public Material(String name, RecyclingCategory recyclingCategory, double carbonFootprint) {
        this.name = name;
        this.recyclingCategory = recyclingCategory;
        this.carbonFootprint = carbonFootprint;
    }

    public String getName() {
        return name;
    }

    public RecyclingCategory getRecyclingCategory() {
        return recyclingCategory;
    }

    public double getCarbonFootprint() {
        return carbonFootprint;
    }
}

/*
SRP just represents material properties
immutable with safe value object behavior
no business logic leakage yet
easy to extend later (OCP via new fields, not mutation) */
