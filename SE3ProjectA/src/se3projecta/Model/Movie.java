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
public class Movie implements XMLSerialize {

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
    public Character getCharId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer getIntId() {
        return id;
    }

    @Override
    public String toString() {
        return "Movie: ID:" + id + "\tName: " + movieName + "\tPoromotionalImage: " + promotionalImage;
    }
}
