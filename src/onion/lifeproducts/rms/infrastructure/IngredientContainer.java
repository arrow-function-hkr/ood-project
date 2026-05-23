package onion.lifeproducts.rms.infrastructure;
import onion.lifeproducts.rms.domain;
import javax.xml.bind.annotation.*;

@XmlElementWrapper(name="IngredientContainer")
@XmlElement(name="Ingredient")
public class IngredientContainer extends JAXBContainer<Ingredient>
{
	
}