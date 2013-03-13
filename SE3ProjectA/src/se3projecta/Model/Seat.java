/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Russell
 */
public class Seat {
    public enum State {
        Empty,
        Held,
        Occupied
    }
    
    public Element toXml(Document doc) {
        Element element = doc.createElement("Seat");
        element.setAttribute("state", (state == State.Occupied ? state.toString() : State.Empty.toString()));
        
        return element;
    }
    
    private SeatType seatType;
    private State state;
    
    public Seat(SeatType type) {
        seatType = type;
    }
    
    public State getState() {
        return state;
    }
}
