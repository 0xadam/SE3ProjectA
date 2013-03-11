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
    private TreeMap<Character, SeatType> SeatTypes;
    private TreeMap<String, Theatre> theatres;
    private TreeMap<Integer, Movie> movies;
    private TreeMap<Integer, SessionTime> sessionTimes;
    final static String SEATTYPES_PATH = "Data/SeatTypes.xml";
    
    
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

    public static <V extends XMLSerialize> TreeMap<Character, V> loadCharIndexEntities(NodeList nList, Class<V> c1) throws InstantiationException, java.lang.IllegalAccessException {
        TreeMap<Character, V> list = new TreeMap<Character, V>();
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                V val = c1.newInstance();
                val.load(eElement);
                list.put(val.getCharId(), val);
            }

        }

        return list;
    }
    
    public static <V extends XMLSerialize> TreeMap<Integer, V> loadIntIndexEntities(NodeList nList, Class<V> c1) throws InstantiationException, java.lang.IllegalAccessException {
        TreeMap<Integer, V> list = new TreeMap<Integer, V>();
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                V val = c1.newInstance();
                val.load(eElement);
                list.put(val.getIntId(), val);
            }

        }

        return list;
    }
}
