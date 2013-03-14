/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Xml;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Russell
 */
public class XmlFileLoader {

    private static Document readXml(String path) throws ParserConfigurationException, SAXException, IOException {
        File fXmlFile = new File(path);
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        return dBuilder.parse(fXmlFile);
    }

    public static NodeList parseXmlFile(String Path, String NodeNames) {
        try {
            Document doc = readXml(Path);
            return doc.getElementsByTagName(NodeNames);
        } catch (IOException ioe) {
            System.err.println("File not found");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <K, V extends XmlUnserializable<K>> TreeMap<K, V> loadIndexEntities(NodeList nodes, Class<V> entityClass)
            throws InstantiationException, java.lang.IllegalAccessException {
        
        TreeMap<K, V> list = new TreeMap<K, V>();
        for (int temp = 0; temp < nodes.getLength(); temp++) {

            Node nNode = nodes.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                V entity = entityClass.newInstance();
                entity.load(eElement);
                list.put(entity.getId(), entity);
            }
        }

        return list;
    }
}
