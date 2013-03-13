/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import java.util.TreeMap;
import org.w3c.dom.Element;
/**
 *
 * @author Russell
 */
public class XmlFileSaver {
    public static <K, V extends XmlSerializable> void save(TreeMap<K, V> file, String Path) throws ParserConfigurationException, TransformerConfigurationException, TransformerException  { 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		//create Document.
		Document doc = docBuilder.newDocument();
                
                Element root = doc.createElement("Root");
                for (V v : file.values()) {
                    root.appendChild(v.Save(doc));
                }
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(Path));
 
		// Output to console for testing
		//StreamResult result = new StreamResult(System.out);
                
		transformer.transform(source, result);
 
		//System.out.println("File saved!");
    }
}
