package com.baosight.sparkweb.service;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * generator mail xml
 *
 * @author huangjie
 */
public class SparkMail {


    private Document document = null;
    HashMap<String, Node> cache = new HashMap<String, Node>();


    public SparkMail() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.newDocument();
            //initialize List node
            initializeListNode();

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
    initialize List Node for mailList
     */
    private void initializeListNode() {
        //添加子node，名字是list
        Node rootkNode = document.createElement("list");
        document.appendChild(rootkNode);
        cache.put("list", rootkNode);

        //添加子node，标签名字是tab
        Node tabNode = document.createElement("tab");
        rootkNode.appendChild(tabNode);
        cache.put("tab", tabNode);

        //add title name
        Attr tabAttr = document.createAttribute("title");
        tabAttr.setValue("邮件");
        tabNode.getAttributes().setNamedItem(tabAttr);

        //tab类中添加，Metadata
        Node metadataNode = getMetadata();
        tabNode.appendChild(metadataNode);
        cache.put("metadata", metadataNode);

        //tab类中添加,实际邮件类容.
        Node contentNode = document.createElement("content");
        tabNode.appendChild(contentNode);
        cache.put("content", contentNode);

        //打完收工
    }

    private Node getMetadata() {
        Node metadataNode = document.createElement("metadata");

        Node columnNode = getColumnNode("URL", "");
        metadataNode.appendChild(columnNode);

        Node columnNode1 = getColumnNode("发件人", "");
        metadataNode.appendChild(columnNode1);

        Node columnNode2 = getColumnNode("主题", "");
        metadataNode.appendChild(columnNode2);

        Node columnNode3 = getColumnNode("时间", "");
        metadataNode.appendChild(columnNode3);


        return metadataNode;
    }

    private Node getColumnNode(String title, String name) {
        Node columnNode = document.createElement("column");

        Node titleNode = document.createElement("title");
        titleNode.setTextContent(title);
        columnNode.appendChild(titleNode);
//        cache.put("title",titleNode);

        Node nameNode = document.createElement("name");
        nameNode.setTextContent(name);
        columnNode.appendChild(nameNode);
//        cache.put("name",nameNode);

        return columnNode;
    }

    private Node getRowNode(String rowtag, String rowtext) {

        Node titleNode = document.createElement(rowtag);
        titleNode.setTextContent(rowtext);


        return titleNode;
    }

    public void addContent(String URL, String sender, String title, String sendTime) {
        //get content
        Node contentNode = cache.get("content");
        //add row to content
        Node rowNode = document.createElement("row");
        contentNode.appendChild(rowNode);

        Node columnNode = getRowNode("c0", URL);
        rowNode.appendChild(columnNode);

        Node columnNode1 = getRowNode("c1", sender);
        rowNode.appendChild(columnNode1);

        Node columnNode2 = getRowNode("c2", title);
        rowNode.appendChild(columnNode2);

        Node columnNode3 = getRowNode("c3", sendTime);
        rowNode.appendChild(columnNode3);
    }


    public String toXmlString() {
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
        return writer.getBuffer().toString();
    }

    /**
     * testBed
     *
     * @param args
     */
    public static void main(String[] args) {
        SparkMail tree = new SparkMail();

        tree.addContent("www.baidu.com","张三","张三很强大","2013年10月23日14:28:00");

        System.out.println(tree.toXmlString());
    }
}
