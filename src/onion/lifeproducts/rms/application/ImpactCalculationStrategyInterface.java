package onion.lifeproducts.rms.application;
import onion.lifeproducts.rms.domain.*;

import java.util.ArrayList; 


public interface ImpactCalculationStrategyInterface {
    public float calculateImpact(Product product);
    public float calculateImpact(Product[] products);
    public float calculateImpact(ArrayList<Products> products);
}
