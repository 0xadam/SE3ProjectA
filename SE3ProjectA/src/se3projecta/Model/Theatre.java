/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import se3projecta.Persistance.XmlUnserializable;

import java.util.TreeMap;
import org.w3c.dom.Element;

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
    
    /**
     * get theatre width
     * @return theatre width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * get theatre height
     * @return theatre height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * get the ID of the movie that theatre is showing
     * @return the ID of the movie that theatre is showing
     */
    public int getMovieId() {
        return movieId;
    }
    
    /**
     * loads theatre data from XML
     * @param n an XML node which contains theatre data
     */
    @Override
    public void load(Element n) {
        this.id = Integer.parseInt(n.getAttribute("id"));
        this.name = n.getAttribute("name");
        this.movieId = Integer.parseInt(n.getAttribute("movie"));
        this.height = Integer.parseInt(n.getAttribute("height"));
        this.width = Integer.parseInt(n.getAttribute("width"));
        this.rawSeatPlan = n.getTextContent();
    }
    
    /**
     * get theatre ID
     * @return theatre ID
     */
    @Override
    public Integer getId() {
        return id;
    }
    
    /**
     * get the seat type of the seat at the specified index in the theatre
     * @param index specifies which seat to get the seat type of
     * @return the seat type of the seat at the specified index in the theatre 
     */
    SeatType getSeatTypebyIndex(int index) {
        return seatTypes[index];
    }
    
    /**
     * load a theatre seat plan from a TreeMap of seat types
     * @param seatTypes a TreeMap of seat types representing a seating plan
     */
    public void loadSeatPlan(TreeMap<Character, SeatType> seatTypes) {
        this.seatTypes = parseSeats(this.rawSeatPlan.toCharArray(), this.width, this.height, seatTypes);
    }
    
    /**
     * returns a string representation of the theatre
     * @return a string representation of the theatre
     */
    @Override
    public String toString() {
        return this.name;
    }

    
    /**
     * returns a detailed string representation of the theatre for debugging 
     * purposes
     * @return a detailed string representation of the theatre for debugging 
     * purposes
     */
    public String toDebugString() {
        StringBuilder builder = new StringBuilder();
        
        builder.append("Theatre: ID:").append(id).
                append("\tName: ").append(name).
                append("\tsize: ").append(width).
                append("x").append(height).
                append("\tPlan:\n");
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SeatType temp = seatTypes[i * width + j];
                if (temp == null) {
                    builder.append(' ');
                } else {
                    builder.append(temp.getId());
                }
                builder.append('\t');
            }
            builder.append('\n');
        }
        
        return builder.toString();
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
