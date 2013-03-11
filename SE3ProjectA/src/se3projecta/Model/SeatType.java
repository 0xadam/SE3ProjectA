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
public class SeatType implements XMLSerialize<Character> {

    private String name;
    private double price;
    private Character id;

    @Override
    public void load(Element n) {
        setId(n.getAttribute("ID").charAt(0));
        setName(n.getAttribute("Name"));
        setPrice(Double.parseDouble(n.getAttribute("Price")));
    }

    public String getName() {
        return name;
    }

    @Override
    public Character getId() {
        return id;
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

    public void setId(Character id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SeatType: ID:" + this.id + "\tName: " + this.name + "\tPrice: " + this.price;
    }
}
