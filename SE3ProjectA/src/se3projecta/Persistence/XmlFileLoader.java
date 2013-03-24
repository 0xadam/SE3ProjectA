package se3projecta.Persistence;

import java.io.*;
import java.util.TreeMap;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Allows a collection of Objects to be loaded from an XML file.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class XmlFileLoader {

    private static Document readXml(String path) throws ParserConfigurationException, SAXException, IOException, java.io.FileNotFoundException {
        File fXmlFile = new File(path);
        if (!fXmlFile.exists()) {
            throw new java.io.FileNotFoundException("File: " + path + " could not be found");
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        return dBuilder.parse(fXmlFile);
    }

    /**
     * Gets the elements from an XML file with the specified tag names and
     * returns them as a NodeList
     *
     * @param Path the path to the XML file
     * @param NodeNames the names of which tags to get from the XML file
     * @return the elements from an XML file with the specified tag names as a
     * NodeList
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws java.io.FileNotFoundException
     */
    public static NodeList parseXmlFile(String Path, String NodeNames) throws ParserConfigurationException, SAXException, IOException, java.io.FileNotFoundException {
        Document doc = readXml(Path);
        return doc.getElementsByTagName(NodeNames);
    }

    /**
     * Loads a collection of objects which are instances of a specified class
     * from a NodeList.
     *
     * @param <K> the Data type of the key of the returned collection. Data type
     * is that of the object ID.
     * @param <V> the Data type of the value of the returned collection. Data
     * type is that of the object itself (specified in entityClass).
     * @param nodes NodeList that collection of objects is loaded from
     * @param entityClass a class which the collection objects will be instances
     * of
     * @return a collection of objects which are instances of a specified class
     * loaded from a NodeList
     * @throws InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws se3projecta.Model.ExistingKeyException
     */
    public static <K, V extends XmlUnserializable<K>> TreeMap<K, V> loadIndexEntities(NodeList nodes, Class<V> entityClass)
            throws InstantiationException, java.lang.IllegalAccessException, se3projecta.Model.ExistingKeyException {

        TreeMap<K, V> list = new TreeMap<K, V>();
        for (int temp = 0; temp < nodes.getLength(); temp++) {

            Node nNode = nodes.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                V entity = entityClass.newInstance();
                entity.load(eElement);
                if (list.containsKey(entity.getId())) {
                    throw new se3projecta.Model.ExistingKeyException("the key: " + entity.getId() + " alread exists in the collection.");
                }
                list.put(entity.getId(), entity);
            }
        }

        return list;
    }
}
