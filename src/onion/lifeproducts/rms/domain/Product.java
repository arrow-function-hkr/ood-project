package org.dom;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private final String id;
    private final String name;
    private ProductCategory category;

    private final List<Material> materials = new ArrayList<>();

    public Product(String id, String name, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // SRP: manage composition
    public void addMaterial(Material material) {
        if (material == null) return;
        this.materials.add(material);
    }

    public List<Material> getMaterials() {
        return List.copyOf(materials);
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // delegation to domain service (DIP-friendly)
    public ImpactReport calculateImpact(ImpactCalculator calculator) {
        return calculator.calculate(this);
    }
}

/*SRP
Product manages structure of product only
does NOT calculate complex impact logic itself 

OCP
impact calculation via ImpactCalculator
new logic can be added without modifying Product

DIP
Product depends on abstraction (ImpactCalculator)
not concrete calculation logic*/