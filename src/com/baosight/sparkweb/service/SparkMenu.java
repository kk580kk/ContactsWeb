package com.baosight.sparkweb.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class SparkMenu {
	
	private Document document = null;
	HashMap<String,Node> cache =  new HashMap<String,Node>();
	
	public SparkMenu()
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public String addRow(String guid,String name,String url)
	{
		Node parentNode = cache.get(guid);
		if(parentNode != null || "root".equals(guid))
		{
			Node newNode = document.createElement("row");
			Attr guidAttr = document.createAttribute("guid");
			Attr urlAttr = document.createAttribute("url");
			Attr nameAttr = document.createAttribute("name");
			String uuid = UUID.randomUUID().toString();
			guidAttr.setNodeValue(uuid);
			urlAttr.setNodeValue(url);
			nameAttr.setNodeValue(name);
			
			newNode.getAttributes().setNamedItem(guidAttr);
			newNode.getAttributes().setNamedItem(urlAttr);
			newNode.getAttributes().setNamedItem(nameAttr);
			
			if(parentNode != null){
				parentNode.appendChild(newNode);
			}else{
				document.appendChild(newNode);
			}
			cache.put(uuid, newNode);
			return uuid;
		}
		return null;
	}
	
	public String toXmlString()
	{
		StringWriter writer = new StringWriter();
		writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		try {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
	    transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(new DOMSource(document), new StreamResult(writer));
//		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  writer.getBuffer().toString();
	}
	
	
}
