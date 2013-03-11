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
public class SessionTime implements XMLSerialize<Integer> {

    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "SessionTime: ID:" + id + "\tName: " + name;
    }

    @Override
    public void load(Element n) {
        this.id = Integer.parseInt(n.getAttribute("id"));
        this.name = n.getAttribute("Name");
    }

    @Override
    public Integer getId() {
        return id;
    }
}