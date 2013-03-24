package se3projecta.Model;

import se3projecta.Persistence.XmlUnserializable;
import java.util.TreeMap;
import org.w3c.dom.*;

/**
 * Allows different types of seats to be booked for different prices. The price
 * for the seat is determined by the price of the SeatType and the
 * priceMultiplier of the CustomerType.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class SeatType implements XmlUnserializable<Character> {

    private String name;
    private double price;
    private Character id;
    private TreeMap<SeatState, String> icons;

    /**
     * Loads seat type data from XML
     *
     * @param n an XML node which contains seat type data
     */
    @Override
    public void load(Element n) {
        setId(n.getAttribute("ID").charAt(0));
        setName(n.getAttribute("Name"));
        setPrice(Double.parseDouble(n.getAttribute("Price")));
        //get icon nodes
        NodeList iconNodes = n.getElementsByTagName("Icon");
        icons = new TreeMap<SeatState, String>();
        //load into tree map.
        SeatState s;
        String path;
        Element e;
        for (int i = 0; i < iconNodes.getLength(); i++) {
            e = (Element) iconNodes.item(i);
            s = SeatState.valueOf(e.getAttribute("id"));
            path = e.getAttribute("path");
            if (!icons.containsKey(s)) {
                icons.put(s, path);
            }
        }
    }

    /**
     * Gets seat type name
     *
     * @return seat type name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets seat type ID
     *
     * @return seat type ID
     */
    @Override
    public Character getId() {
        return id;
    }

    /**
     * Sets seat type name
     *
     * @param name name to set to seat type
     */
    public void setName(String name_) {
        name = name_;
    }

    /**
     * Gets seat type price
     *
     * @return seat type price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets seat type price
     *
     * @param price price to give this seat type
     */
    public void setPrice(double price_) {
        price = price_;
    }

    /**
     * Sets seat type id
     *
     * @param id id to give this seat type
     */
    public void setId(Character id_) {
        id = id_;
    }

    /**
     * returns a string representation of the seat type
     *
     * @return a string representation of the seat type
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Get the path to the Icon image for this SeatType for a specified
     * SeatState
     *
     * @param ss the SeatState to get the path to the Icon image for
     * @return the path to the Icon image for this SeatTypefor a specified
     * SeatState
     */
    public String getIcon(SeatState ss) {
        if (icons.containsKey(ss)) {
            return icons.get(ss);
        }
        return null;
    }
}
