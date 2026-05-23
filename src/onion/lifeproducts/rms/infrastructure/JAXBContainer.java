package onion.lifeproducts.rms.infrastructure;

import onion.lifeproducts.rms.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;


public class JAXBContainer<E>
{
	private ArrayList<E> elements;
	public JAXBContainer()
	{
		
	}
	public JAXBContainer(ArrayList<E> elements)
	{
		this.elements = elements;
	}
	public void setElements(ArrayList<E> elements)
	{
		this.elements = elements;
	}
	public ArrayList<E> getElements()
	{
		return this.elements;
	}
}
