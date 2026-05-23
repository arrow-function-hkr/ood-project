package onion.lifeproducts.rms.domain;

// Since regular Java has no Pair type, the class servers purpose as Material-name Material-content-ratio object
// Used inside a product class instead of HashMap<> which is tidious to store with JAXB to XML file

public class Ingredient
{
	private String materialName;
	private Float materialRatio;
	
	public Ingredient()
	{
		
	}
	public Ingredient(String materialName, Float materialRatio)
	{
		this.materialName = materialName;
		this.materialRatio = materialRatio;
	}
	
	public void setMaterialName(String materialName)
	{
		this.materialName = materialName;
	}
	public void setMaterialRatio(Float materialRatio)
	{
		this.materialRatio = materialRatio;
	}
	
	public String getMaterialName()
	{
		return this.materialName;
	}
	public Float getMaterialRatio()
	{
		return this.materialRatio;
	}
}