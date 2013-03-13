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
public class Seat implements XmlSerializable, XmlUnserializable {
    public enum State {
        Empty,
        Held,
        Occupied
    }
    
    public Element Save(Document doc) {
        Element element = doc.createElement("Seat");
        element.setAttribute("state", (state == State.Occupied ? state.toString() : State.Empty.toString()));
        
        return element;
    }
    
    public void load(Element n) {
        this.state = State.valueOf(n.getAttribute("state"));
    }
    
    private SeatType seatType;
    private State state;
    
    public void setSeatType(SeatType s) {
        seatType = s;
    }
    
    public SeatType getType() {
        return seatType;
    }
    
    public State getState() {
        return state;
    }
    
    public boolean available() {
        return state == State.Empty;
    }
}
