package onion.lifeproducts.rms.infrastructure;
import onion.lifeproducts.rms.domain;

@XmlElementWrapper(name="ProductContainer")
@XmlElement(name="Product")
public class ProductContainer extends JAXBContainer<Product>
{
	
}