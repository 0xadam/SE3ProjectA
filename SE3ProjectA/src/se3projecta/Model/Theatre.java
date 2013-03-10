/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

/**
 *
 * @author Russell
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Theatre {
    private String id;
    private String name;
    private int width;
    private int height;
    private SeatType[] seatTypes;
    
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
