package se3projecta.Model;

import se3projecta.Persistence.*;
import org.w3c.dom.*;
import java.lang.UnsupportedOperationException;

/**
 * Allows individual seats to be booked as part of a TheatreSession.
 * 
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Seat implements XmlSerializable, XmlUnserializable {

    /**
     * Creates and returns an XML seat element, which stores seat state.
     *
     * @param doc an XML document used to create the seat element
     * @return an XML seat element, which stores seat state
     */
    @Override
    public Element save(Document doc) {
        Element element = doc.createElement("Seat");
        element.setAttribute("state", (state == SeatState.Occupied ? state.toString() : SeatState.Empty.toString()));

        return element;
    }

    /**
     * Loads seat data from XML
     *
     * @param n an XML node which contains seat data
     */
    @Override
    public void load(Element n) {
        try {
            state = SeatState.valueOf(n.getAttribute("state"));
        } catch (java.lang.IllegalArgumentException e) {
            //error loading seat type
            state = SeatState.Empty;
        }
        
    }
    private SeatType seatType;
    private SeatState state = SeatState.Empty;

    /**
     * Sets seat state
     *
     * @param s state to set seat to
     */
    public void setState(SeatState s) {
        state = s;
    }

    /**
     * Sets seat type
     *
     * @param s type to set seat type to
     */
    public void setSeatType(SeatType s) {
        seatType = s;
    }

    /**
     * Gets seat type
     *
     * @return seat type
     */
    public SeatType getType() {
        return seatType;
    }

    /**
     * Sets seat state
     *
     * @return seat state
     */
    public SeatState getState() {
        return state;
    }

    /**
     * Gets seat ID.
     *
     * @return seat ID
     */
    @Override
    public Integer getId() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the path to the Icon image from this Seat's seatType and state
     * @return the path to the Icon image from this Seat's seatType and state
     */
    public String getIcon() {
        if (seatType != null) {
            return seatType.getIcon(state);
        }
        return null;
    }
}
