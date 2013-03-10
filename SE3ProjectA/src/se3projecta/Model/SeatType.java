/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import java.io.File;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Russell
 */
public class SeatType {
    private String name;
    private double price;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SeatType: ID:" + this.id + "\tName: " + this.name + "\tPrice: " + this.price;
    }
    
    public static TreeMap<String,SeatType> load(String path) {
        TreeMap<String, SeatType> list = new TreeMap<String, SeatType>();
        try {
        File fXmlFile = new File(path);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	NodeList nList = doc.getElementsByTagName("SeatType");
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode;
                        SeatType st = new SeatType();
                        st.setId(eElement.getAttribute("ID"));
                        st.setName(eElement.getAttribute("Name"));
                        st.setPrice(Double.parseDouble(eElement.getAttribute("Price")));
                        list.put(st.getId(), st);
		}
	}
        
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
