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
    public enum States {
        Empty, Held, Occupied
    }
    private SeatType seatType;
    private States state;
    
}
