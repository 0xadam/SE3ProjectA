/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import se3projecta.Persistance.XmlUnserializable;
import org.w3c.dom.Element;

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

    /**
     * gets the movie name
     *
     * @return the movie name
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * gets the promotional image. This could be a movie poster or cover.
     *
     * @return the promotional image. This could be a movie poster or cover.
     */
    public String getPromotionalImage() {
        return promotionalImage;
    }

    /**
     * loads movie data from XML
     *
     * @param n an XML node which contains movie data
     */
    @Override
    public void load(Element n) {
        this.id = Integer.parseInt(n.getAttribute("id"));
        this.movieName = n.getAttribute("Name");
        this.promotionalImage = n.getAttribute("promotionalImage");
    }

    /**
     * gets the movie ID
     *
     * @return the movie ID
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * returns a string representation of the movie
     *
     * @return a string representation of the movie
     */
    @Override
    public String toString() {
        return movieName;
    }
}
