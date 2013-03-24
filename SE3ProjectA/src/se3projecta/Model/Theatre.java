package se3projecta.Model;

import se3projecta.Persistence.XmlUnserializable;

import java.util.TreeMap;
import org.w3c.dom.Element;

/**
 * Allows description of a Seating Plan for a specific Theatre. One movie is
 * viewable at each Theatre.
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
     * Gets theatre width
     *
     * @return theatre width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets theatre height
     *
     * @return theatre height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the ID of the movie that theatre is showing
     *
     * @return the ID of the movie that theatre is showing
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Loads theatre data from XML
     *
     * @param n an XML node which contains theatre data
     */
    @Override
    public void load(Element n) {
        id = Integer.parseInt(n.getAttribute("id"));
        name = n.getAttribute("name");
        movieId = Integer.parseInt(n.getAttribute("movie"));
        height = Integer.parseInt(n.getAttribute("height"));
        width = Integer.parseInt(n.getAttribute("width"));
        rawSeatPlan = n.getTextContent().replaceAll("\\s", "");

    }

    /**
     * Get theatre ID
     *
     * @return theatre ID
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Get the seat type of the seat at the specified index in the theatre
     *
     * @param index specifies which seat to get the seat type of
     * @return the seat type of the seat at the specified index in the theatre
     */
    SeatType getSeatTypebyIndex(int index) {
        return seatTypes[index];
    }

    /**
     * Load a theatre seat plan from a TreeMap of seat types
     *
     * @param seatTypes_ a TreeMap of seat types representing a seating plan
     */
    public void loadSeatPlan(TreeMap<Character, SeatType> seatTypes_) {
        seatTypes = parseSeats(rawSeatPlan.toCharArray(), width, height, seatTypes_);
    }

    /**
     * Returns a string representation of the theatre
     *
     * @return a string representation of the theatre
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns a detailed string representation of the theatre for debugging
     * purposes
     *
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
