package onion.lifeproducts.rms.application;


public interface ImpactCalculationStrategyInterface {
    public float calculateImpact(Product product);
    public float calculateImpact(Product[] products);
    public float calculateImpact(List<Products> products);
}
