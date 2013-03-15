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

    @Override
    public Element save(Document doc) {
        Element element = doc.createElement("Seat");
        element.setAttribute("state", (state == State.Occupied ? state.toString() : State.Empty.toString()));
        
        return element;
    }
    
    @Override
    public void load(Element n) {
        this.state = State.valueOf(n.getAttribute("state"));
    }
    
    private SeatType seatType;
    private State state = State.Empty;
    
    public void setState(State s) {
        state = s;
    }
    
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
    
    // Crazy Russell hack
    @Override
    public Integer getId() {
        return 0;
    }
}
