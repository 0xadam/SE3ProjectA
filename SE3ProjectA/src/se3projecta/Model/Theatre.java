/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import Xml.XmlUnserializable;
import se3projecta.SE3ProjectA;

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
public class Theatre implements XmlUnserializable<Integer> {

    private Integer id;
    private String name;
    private int width;
    private int height;
    private int movieId;
    private String rawSeatPlan;
    private SeatType[] seatTypes;
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMovieId() {
        return movieId;
    }
    
    @Override
    public void load(Element n) {
        this.id = Integer.parseInt(n.getAttribute("id"));
        this.name = n.getAttribute("name");
        this.movieId = Integer.parseInt(n.getAttribute("movie"));
        this.height = Integer.parseInt(n.getAttribute("height"));
        this.width = Integer.parseInt(n.getAttribute("width"));
        this.rawSeatPlan = n.getTextContent();
    }

    @Override
    public Integer getId() {
        return id;
    }
    
    SeatType getSeatTypebyIndex(int index) {
        return seatTypes[index];
    }

    public void loadSeatPlan(TreeMap<Character, SeatType> seatTypes) {
        this.seatTypes = parseSeats(this.rawSeatPlan.toCharArray(), this.width, this.height, seatTypes);
    }

    @Override
    public String toString() {
        String value = "Theatre: ID:" + id + "\tName: " + name + "\tsize: " + width + "x" + height + "\tPlan:\n";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SeatType temp = seatTypes[i * width + j];
                if (temp == null) {
                    value += " ";
                } else {
                    value += temp.getId();
                }
                value += '\t';
            }
            value += "\n";
        }
        return value;
    }

    private static SeatType[] parseSeats(char[] Seatlist, int width, int height, TreeMap<Character, SeatType> seatTypes) {
        if (Seatlist.length != (width * height)) {
            throw new java.util.InputMismatchException();
        }
        SeatType[] theatreSeatTypes = new SeatType[width * height];

        for (int i = 0; i < theatreSeatTypes.length; i++) {
            if (seatTypes.containsKey(Seatlist[i])) {
                theatreSeatTypes[i] = seatTypes.get(Seatlist[i]);
            } else {
                theatreSeatTypes[i] = null;
            }
        }

        return theatreSeatTypes;
    }
}
