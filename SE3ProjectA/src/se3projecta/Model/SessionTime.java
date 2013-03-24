package se3projecta.Model;

import se3projecta.Persistence.XmlUnserializable;
import org.w3c.dom.Element;

/**
 * A time for which a theatre can be booked
 * 
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class SessionTime implements XmlUnserializable<Integer> {

    private Integer id;
    private String name;

    /**
     * Returns a string representation of the session time
     *
     * @return a string representation of the session time
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Loads session time data from XML
     *
     * @param n an XML node which contains session time data
     */
    @Override
    public void load(Element n) {
        id = Integer.parseInt(n.getAttribute("id"));
        name = n.getAttribute("Name");
    }

    /**
     * Gets session time ID
     *
     * @return session time ID
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Gets session time name
     *
     * @return session time name
     */
    public String getName() {
        return name;
    }
}
