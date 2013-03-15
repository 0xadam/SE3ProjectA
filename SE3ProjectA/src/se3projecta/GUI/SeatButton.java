/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.*;
import se3projecta.Model.*;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class SeatButton extends JButton {

    private Seat seat;

    public SeatButton(Seat seat_) {
        seat = seat_;
        if (seat.getType() == null) {
            setIcon(new ImageIcon("data\\noseat.gif"));
            setEnabled(false);
        } else if (seat.getType().getName().equals("Gold")) {
            if (seat.getState() == seat.getState().Empty) {
                setIcon(new ImageIcon("data\\unoccupied.gif"));
            } else if (seat.getState() == seat.getState().Occupied) {
                setIcon(new ImageIcon("data\\occupied.gif"));
            }
        } else if (seat.getType().getName().equals("Silver")) {
            if (seat.getState() == seat.getState().Empty) {
                setIcon(new ImageIcon("data\\unoccupied.gif"));
            } else if (seat.getState() == seat.getState().Occupied) {
                setIcon(new ImageIcon("data\\occupied.gif"));
            }
        } else if (seat.getType().getName().equals("Bronze")) {
            if (seat.getState() == seat.getState().Empty) {
                setIcon(new ImageIcon("data\\unoccupied.gif"));
            } else if (seat.getState() == seat.getState().Occupied) {
                setIcon(new ImageIcon("data\\occupied.gif"));
            }
        }

        setBorder(null); //remove the button look
        setContentAreaFilled(false); //make opaque so as to just show icon
    }
}
