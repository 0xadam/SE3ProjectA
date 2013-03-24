package se3projecta.GUI;

import javax.swing.*;
import se3projecta.Model.*;

/**
 * JButton which wraps a Seat object and can be clicked to book or un-book. Icon
 * reflects the SeatState and SeatType, though care must be taken to ensure that
 * the icon displayed reflects that of the internal Seat object (through the
 * usage of refreshIcon)
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class SeatButton extends JButton {

    private Seat seat;
    private static final long serialVersionUID = 0; //remove warning in netbeans build process

    /**
     * Create SeatButton.
     *
     * @param seat_ the Seat which this SeatButton represents.
     */
    public SeatButton(Seat seat_) {
        seat = seat_;
        refreshIcon();
        setBorder(null); //remove the button look
        setContentAreaFilled(false); //make opaque so as to just show icon
    }

    /**
     * Refresh seat icon.
     */
    public void refreshIcon() {
        String path;
        if ((path = seat.getIcon()) != null) {
            setIcon(new ImageIcon(path));
        } else if (seat.getType() == null) {
            setIcon(new ImageIcon("data\\noseat.gif"));
            setEnabled(false);
        }
    }

    /**
     * Get the SeatType of the Seat this SeatButton represents.
     *
     * @return the SeatType of the Seat this SeatButton represents
     */
    public SeatType getSeatType() {
        return seat.getType();
    }

    /**
     * Get the Seat this SeatButton represents.
     *
     * @return the Seat this SeatButton represents
     */
    public Seat getSeat() {
        return seat;
    }
}
