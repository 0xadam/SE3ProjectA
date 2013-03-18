/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setIcon();
        setBorder(null); //remove the button look
        setContentAreaFilled(false); //make opaque so as to just show icon
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (seat.getState() == SeatState.Held) {
                    seat.setState(SeatState.Empty);
                } else if (seat.getState() == SeatState.Empty) {
                    seat.setState(SeatState.Held);
                }
                setIcon();
            }
        });
    }

    private void setIcon() {
        String path;
        if ((path = seat.getIcon()) != null) {
            setIcon(new ImageIcon(path));
        } else if (seat.getType() == null) {
            setIcon(new ImageIcon("data\\noseat.gif"));
            setEnabled(false);
        }
    }

    public SeatType getSeatType() {
        return seat.getType();
    }
}
