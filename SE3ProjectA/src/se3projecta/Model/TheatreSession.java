/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import java.util.Arrays;

/**
 *
 * @author Russell Peake
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class TheatreSession {
    private SessionTime sessionTime;
    private Movie movie;
    private Theatre theatre;
    private Seat[] seats;
    
    public Seat[] getSeats() {
        return seats;
    }
    
    public Seat[][] getSeatRows() {
        Seat[][] rows = new Seat[theatre.getHeight()][theatre.getWidth()];
        
        int rowStart = 0;
        int rowSize = theatre.getWidth();
        for (int i = 0; i != theatre.getHeight(); i++) {
            rows[i] = Arrays.copyOfRange(seats, rowStart, rowStart + rowSize);
            rowStart += rowSize;
        }
        
        return rows;
    }
    
    public Seat[] placeRandom() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public Theatre getTheatre() {
        return theatre;
    }
}
