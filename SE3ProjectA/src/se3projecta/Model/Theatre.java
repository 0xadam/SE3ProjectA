/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;
import se3projecta.SE3ProjectA;

import java.util.TreeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Russell
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Theatre {
    private String id;
    private String name;
    private int width;
    private int height;
    private SeatType[] seatTypes;
    
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        String value = "Theatre: ID:" + id + "\tName: " + name + "\tsize: " + width+"x"+height + "Plan:\n";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SeatType temp = seatTypes[i*width + j];
                if (temp == null) value += " ";
                else value += temp.getId();
                value += '\t';
            }
            value += "\n";
        }
        return value;
    }
    
    public static TreeMap<String, Theatre> load(String path, TreeMap<Character, SeatType> seatTypes) {
        TreeMap<String, Theatre> list = new TreeMap<String, Theatre>();
        Document doc = null;
        try {
            doc = XML_Helper.LoadXML(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        NodeList nList = doc.getElementsByTagName("Theatre");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                Theatre theatre = new Theatre();
                theatre.id = eElement.getAttribute("id");
                theatre.name = eElement.getAttribute("name");
                theatre.height = Integer.parseInt(eElement.getAttribute("height"));
                theatre.width = Integer.parseInt(eElement.getAttribute("width"));
                theatre.seatTypes = parseSeats(eElement.getTextContent().toCharArray(), theatre.width, theatre.height, seatTypes);
                list.put(theatre.id, theatre);
            }
        }

        return list;
    }
    
    private static SeatType[] parseSeats(char[] Seatlist, int width, int height, TreeMap<Character, SeatType> seatTypes) {
        if (Seatlist.length != (width * height)) throw new java.util.InputMismatchException();
        SeatType[] theatreSeatTypes = new SeatType[width*height];
        
        for (int i = 0; i< theatreSeatTypes.length; i++) {
            if (seatTypes.containsKey(Seatlist[i])) {
                theatreSeatTypes[i] = seatTypes.get(Seatlist[i]);
            } else {
                theatreSeatTypes[i] = null;
            }
        }
        
        return theatreSeatTypes;
    }
}
