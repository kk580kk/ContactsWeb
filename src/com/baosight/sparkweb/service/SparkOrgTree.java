package com.baosight.sparkweb.service;

import java.io.StringWriter;
import java.util.HashMap;

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

public class SparkOrgTree {
	
	private Document document = null;
	HashMap<String,Node> cache =  new HashMap<String,Node>();
	
	
	
	public SparkOrgTree()
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			document = builder.newDocument();
			Node rootkNode = document.createElement("blocks");
			document.appendChild(rootkNode);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * example
	 *<row>
         <userName>董丽菊</userName>
         <userLabel>L00001</userLabel>
      </row>
	 */
	public void addUser(String userName, String userLabel)
	{
		Node rowBlock = document.createElement("row");
		
		Node userNameNode = document.createElement("userName");
		Node userLabelNode = document.createElement("userLabel");
		userNameNode.appendChild(document.createTextNode(userName));
		userLabelNode.appendChild(document.createTextNode(userLabel));
		rowBlock.appendChild(userNameNode);
		rowBlock.appendChild(userLabelNode);
		
		Node userRootNode = cache.get("user");
		if(userRootNode == null)
		{
			addBlock("user");
			userRootNode = cache.get("user");
		}
		//userRootNode must not null
		userRootNode.appendChild(rowBlock);
	}
	
	
	
/**	<row>
    <groupName>test2333</groupName>
    <groupLabel>00000000_BSTA</groupLabel>
    </row>
*/
	public void addOrg(String groupName,String groupLable)
	{
		Node rowBlock = document.createElement("row");
		Node groupNameNode = document.createElement("groupName");
		Node groupLabelNode = document.createElement("groupLabel");
		groupNameNode.appendChild(document.createTextNode(groupName));
		groupLabelNode.appendChild(document.createTextNode(groupLable));
		rowBlock.appendChild(groupNameNode);
		rowBlock.appendChild(groupLabelNode);
		
		Node userRootNode = cache.get("org");
		if(userRootNode == null)
		{
			addBlock("org");
			userRootNode = cache.get("org");
		}
		//userRootNode must not null
		userRootNode.appendChild(rowBlock);
	}
	
	public void addBlock(String blockName)
	{
		Attr blockAttr = document.createAttribute("name");
		blockAttr.setNodeValue(blockName);
		Node blockNode = document.createElement("block");
		//增加attribute
		blockNode.getAttributes().setNamedItem(blockAttr);
		cache.put(blockName, blockNode);
		document.getElementsByTagName("blocks").item(0).appendChild(blockNode);
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
	
	
	/**
	 * testBed
	 * @param args
	 */
	public static void main(String[] args)
	{
		SparkOrgTree tree = new SparkOrgTree();
		
		tree.addOrg("a", "0001");
		tree.addUser("UserA", "0002");
		System.out.println(tree.toXmlString());
	}
}
