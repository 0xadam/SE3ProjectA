/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import se3projecta.Persistance.XmlSerializable;
import se3projecta.Persistance.XmlUnserializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Seat implements XmlSerializable, XmlUnserializable {
    public enum State {
        Empty,
        Held,
        Occupied
    }
    
    /**
     * Creates and returns an XML seat element, which stores seat state. 
     * @param doc an XML document used to create the seat element
     * @return an XML seat element, which stores seat state
     */
    @Override
    public Element save(Document doc) {
        Element element = doc.createElement("Seat");
        element.setAttribute("state", (state == State.Occupied ? state.toString() : State.Empty.toString()));
        
        return element;
    }
    
    /**
     * loads seat data from XML
     * @param n an XML node which contains seat data
     */
    @Override
    public void load(Element n) {
        this.state = State.valueOf(n.getAttribute("state"));
    }
    
    private SeatType seatType;
    private State state = State.Empty;
    
    /**
     * sets seat state
     * @param s state to set seat to
     */
    public void setState(State s) {
        state = s;
    }
    
    /**
     * sets seat type
     * @param s type to set seat type to
     */
    public void setSeatType(SeatType s) {
        seatType = s;
    }
    
    /**
     * gets seat type
     * @return seat type
     */
    public SeatType getType() {
        return seatType;
    }
       
    /**
     * gets seat state
     * @return seat state
     */
    public State getState() {
        return state;
    }
    
    /**
     * get seat availability
     * @return seat availability
     */
    public boolean available() {
        return state == State.Empty;
    }
    
    /**
     * get seat ID
     * @return seat ID
     */
    // Crazy Russell hack
    @Override
    public Integer getId() {
        return 0;
    }
}
