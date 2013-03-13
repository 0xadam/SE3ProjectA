/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import Xml.XmlUnserializable;
import org.w3c.dom.Element;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class CustomerType implements XmlUnserializable<Integer> {

    private int id;
    private String name;
    private double priceMultiplier;

    @Override
    public void load(Element n) {
        this.id = Integer.parseInt(n.getAttribute("id"));
        this.name = n.getAttribute("Name");
        this.priceMultiplier = Double.parseDouble(n.getAttribute("pricemultiplier"));
    }

    @Override
    public String toString() {
        return "CustomerType: ID:" + id + "\tName: " + name + "\tpriceMultiplier: " + priceMultiplier;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
