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
public class SessionTime {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "SessionTime: ID:" + id +"\tName: " + name;
    }
    
    public static TreeMap<Integer, SessionTime> load(String path) {
        TreeMap<Integer, SessionTime> list = new TreeMap<Integer, SessionTime>();
        Document doc = null;
        try {
            doc = XML_Helper.LoadXML(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        NodeList nList = doc.getElementsByTagName("SessionTime");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                SessionTime sestime = new SessionTime();
                sestime.id = Integer.parseInt(eElement.getAttribute("id"));
                sestime.name = eElement.getAttribute("Name");
                list.put(sestime.id, sestime);
            }
        }

        return list;
    }
}