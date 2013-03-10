/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

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
    
    private SeatType seatType;
    private State state;
    
    public Seat(SeatType type) {
        seatType = type;
    }
    
    public State getState() {
        return state;
    }
}
