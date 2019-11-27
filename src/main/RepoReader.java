package main;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepoReader {

    LinkedHashMap<String, LinkedHashMap<String, String>> objectRepo;

    public LinkedHashMap<String, LinkedHashMap<String, String>> getObjectRepo() {
        return objectRepo;
    }

    public RepoReader() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document xmlDocument = null;
        try {
            xmlDocument = builder.parse(new File(System.getProperty("user.dir") + "//src//test//ObjectRepo.xml"));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        String xmlString = writer.getBuffer().toString();
        xmlString = xmlString.replaceAll("[\\n\\t\\r]", "");
        xmlString = xmlString.replaceAll("  ","");
        Document xml = convertStringToDocument(xmlString);
        Node user = xml.getFirstChild();
        NodeList pages = user.getChildNodes();
        Node page;
        objectRepo = new LinkedHashMap();
        for (int i = 0; i < pages.getLength(); i++) {
            page = pages.item(i);
            NodeList elements = page.getChildNodes();
            for (int j = 0; j < elements.getLength(); j++) {
                Node element = elements.item(j);
                NodeList elementProperties = element.getChildNodes();
                String accessType = elementProperties.item(0).getTextContent();
                String accessName = elementProperties.item(1).getTextContent();
                LinkedHashMap<String, String> elementPropertiesMap = new LinkedHashMap<>();
                elementPropertiesMap.put("accessType", accessType);
                elementPropertiesMap.put("accessName", accessName);
                objectRepo.put(page.getNodeName() + "." + element.getNodeName(), elementPropertiesMap);
            }
        }
    }

    private Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(
                    xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
