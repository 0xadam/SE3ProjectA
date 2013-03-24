package se3projecta.Persistence;

import java.io.File;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 * Allows Objects which implement the XmlSerializable interface to be saved as
 * in an XML file in a serialized format.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class XmlFileSaver {

    /**
     * Saves a Map of objects which implement the XmlSerializable interface in
     * an XML file in a serialized format.
     *
     * @param <K> the Data type of the key of the supplied map. Data type is
     * that of the object ID.
     * @param <V> the Data type of the value of the supplied map. Data type is
     * that of the object itself.
     * @param file a collection of objects which implement the XmlSerializable
     * interface.
     * @param Path where to save the generated XML file.
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public static <K, V extends XmlSerializable> void save(TreeMap<K, V> file, String Path) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        //create Document.
        Document doc = docBuilder.newDocument();

        Element root = doc.createElement("Root");
        doc.appendChild(root);
        for (V v : file.values()) {
            root.appendChild(v.save(doc));
        }

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(Path));

        transformer.transform(source, result);
    }
}
