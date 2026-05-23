package onion.lifeproducts.rms.infrastructure;

import onion.lifeproducts.rms.domain;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Optional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;


public interface XmlInterface
{
	public Optional<ArrayList<Material>> unmarshallMaterial(File xmlFile)
	{
		try 
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(MaterialContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();			
			MaterialContainer container = (MaterialContainer) jaxbUnmarshaller.unmarshal(xmlFile);
			return Optional.of(container.getElements());
		} 
		finally 
		{
			return Optional.empty();
		}
	}
	public Optional<ArrayList<Product>> unmarshallProduct(File xmlFile)
	{
		try 
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(ProductContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();			
			ProductContainer container = (ProductContainer) jaxbUnmarshaller.unmarshal(xmlFile);
			return Optional.of(container.getElements());
		} 
		finally 
		{
			return Optional.empty();
		}
	}
	public Optional<ArrayList<RecyclingCategory>> unmarshallRecyclingCategory(File xmlFile)
	{
		try 
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(RecyclingCategoryContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();			
			RecyclingCategoryContainer container = (RecyclingCategoryContainer) jaxbUnmarshaller.unmarshal(xmlFile);
			return Optional.of(container.getElements());
		} 
		finally 
		{
			return Optional.empty();
		}
	}
	public Optional<ArrayList<RecyclingGuidance>> unmarshallRecyclingGuidance(File xmlFile)
	{
		try 
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(RecyclingGuidanceContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();			
			RecyclingGuidanceContainer container = (RecyclingGuidanceContainer) jaxbUnmarshaller.unmarshal(xmlFile);
			return Optional.of(container.getElements());
		} 
		finally 
		{
			return Optional.empty();
		}
	}
	public Optional<ArrayList<RecyclingReport>> unmarshallRecyclingReport(File xmlFile)
	{
		try 
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(RecyclingReportContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();			
			RecyclingReportContainer container = (RecyclingReportContainer) jaxbUnmarshaller.unmarshal(xmlFile);
			return Optional.of(container.getElements());
		} 
		finally 
		{
			return Optional.empty();
		}
	}
	
	
	public Optional<String> marshallMaterial(ArrayList<Materials> materials)
	{
		try 
		{
			MaterialContainer container = new MaterialContainer(materials);
			JAXBContext jaxbContext = JAXBContext.newInstance(MaterialContainer.class);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(container, stringWriter);
			String result = stringWriter.toString();
			return Optional.of(result);
		}
		finally 
		{
			return Optional.empty();
		}
	}
	public Optional<String> marshallProduct(ArrayList<Product> products)
	{
		try 
		{
			ProductContainer container = new ProductContainer(products);
			JAXBContext jaxbContext = JAXBContext.newInstance(ProductContainer.class);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(container, stringWriter);
			String result = stringWriter.toString();
			return Optional.of(result);
		}
		finally 
		{
			return Optional.empty();
		}
	}
	public Optional<String> marshallRecyclingCategory(ArrayList<RecyclingCategory> recyclingCategories)
	{
		try 
		{
			RecyclingCategoryContainer container = new RecyclingCategoryContainer(recyclingCategories);
			JAXBContext jaxbContext = JAXBContext.newInstance(RecyclingCategoryContainer.class);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(container, stringWriter);
			String result = stringWriter.toString();
			return Optional.of(result);
		}
		finally 
		{
			return Optional.empty();
		}
	}
	public Optional<String> marshallRecyclingGuidance(ArrayList<RecyclingGuidance> recyclingGuidances)
	{
		try 
		{
			RecyclingGuidanceContainer container = new RecyclingGuidanceContainer(recyclingGuidances);
			JAXBContext jaxbContext = JAXBContext.newInstance(RecyclingGuidanceContainer.class);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(container, stringWriter);
			String result = stringWriter.toString();
			return Optional.of(result);
		}
		finally 
		{
			return Optional.empty();
		}
	}
	public Optional<String> marshallRecyclingReport(ArrayList<RecyclingReport> recyclingReports)
	{
		try 
		{
			RecyclingReportContainer container = new RecyclingReportContainer(recyclingReports);
			JAXBContext jaxbContext = JAXBContext.newInstance(RecyclingReportContainer.class);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(container, stringWriter);
			String result = stringWriter.toString();
			return Optional.of(result);
		}
		finally 
		{
			return Optional.empty();
		}
	}
}
