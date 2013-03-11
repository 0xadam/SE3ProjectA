/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import java.util.TreeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Russell
 */
public class XML_Loader {
    public static NodeList loadXML(String Path, String NodeNames) {
        Document doc = null;
        try {
            doc = XML_Helper.LoadXML(Path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return doc.getElementsByTagName(NodeNames);
    }

    public static <K, V extends XMLSerialize<K>> TreeMap<K, V> loadIndexEntities(NodeList nList, Class<V> c1) throws InstantiationException, java.lang.IllegalAccessException {
        TreeMap<K, V> list = new TreeMap<K, V>();
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                V val = c1.newInstance();
                val.load(eElement);
                list.put(val.getId(), val);
            }

        }

        return list;
    }
}
