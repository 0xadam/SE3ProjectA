/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import org.w3c.dom.Element;

/**
 *
 * @author Russell
 */
public class CustomerType implements XMLSerialize<Integer> {

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
