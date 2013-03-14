/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import Xml.XmlUnserializable;
import java.util.TreeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Movie implements XmlUnserializable<Integer> {

    private int id;
    private String movieName;
    private String promotionalImage;

    public String getMovieName() {
        return movieName;
    }

    public String getPromotionalImage() {
        return promotionalImage;
    }

    @Override
    public void load(Element n) {
        this.id = Integer.parseInt(n.getAttribute("id"));
        this.movieName = n.getAttribute("Name");
        this.promotionalImage = n.getAttribute("promotionalImage");
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Movie: ID:" + id + "\tName: " + movieName + "\tPromotionalImage: " + promotionalImage;
    }
}
