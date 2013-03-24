package se3projecta.GUI;

import javax.swing.*;
import se3projecta.Model.SeatType;
import java.awt.Dimension;

/**
 * JPanel which shows how many seats are remaining for a specific SeatType
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JSeatSelectionSubPanel extends JPanel {

    private SeatType seatType;
    private JLabel seatsRemainingLabel;
    private JTextField seatsRemainingTextField;

    /**
     * Create JSeatSelectionSubPanel.
     *
     * @param seatType_ SeatType which JSeatSelectionSubPanel shows how many
     * seats are remaining for
     */
    public JSeatSelectionSubPanel(SeatType seatType_) {
        seatType = seatType_;
        setVisible(false);
        seatsRemainingLabel = new JLabel(seatType + " Seats Left To Allocate");
        seatsRemainingTextField = new JTextField("0");
        seatsRemainingTextField.setFocusable(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(seatsRemainingLabel);
        add(seatsRemainingTextField);
        setMaximumSize(new Dimension(170, 42));
    }

    /**
     * get number of SeatsRemaining
     *
     * @return number of SeatsRemaining
     */
    public int getSeatsRemaining() {
        return Integer.parseInt(seatsRemainingTextField.getText());
    }

    /**
     * set number of SeatsRemaining
     *
     * @param numSeats number of SeatsRemaining to set to
     */
    public void setSeatsRemaining(int numSeats) {
        seatsRemainingTextField.setText(Integer.toString(numSeats));
    }

    /**
     * get SeatType linked to this JSeatSelectionSubPanel
     *
     * @return SeatType linked to this JSeatSelectionSubPanel
     */
    public SeatType getSeatType() {
        return seatType;
    }
}
