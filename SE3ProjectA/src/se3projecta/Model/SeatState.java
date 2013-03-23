/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

/**
 * SeatState defines whether a Seat is Empty (not booked or allocated), Held
 * (allocated but not booked), or Occupied (Booked).
 * @author Russell
 */
public enum SeatState {

    /**
     * A Seat with SeatState Empty currently has no booking and may be booked or
     * allocated.
     */
    Empty,
    /**
     * A Seat with SeatState Held has been allocated but not booked, and may be 
     * unallocated.
     */
    Held,
    /**
     * A Seat with SeatState Occupied has been booked and may not be unbooked.
     */
    Occupied
}
