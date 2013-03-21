/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import se3projecta.Model.SeatType;

/**
 * JPanel which shows how many seats are remaining for a specific SeatType
 * @author Adam
 */
public class JSeatSelectionSubPanel extends JPanel {

    private SeatType seatType;
    private JLabel seatsRemainingLabel;
    private JTextField seatsRemainingTextField;

    /**
     * Create JSeatSelectionSubPanel.
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
    }

    /**
     * get number of SeatsRemaining 
     * @return number of SeatsRemaining
     */
    public int getSeatsRemaining() {
        return Integer.parseInt(seatsRemainingTextField.getText());
    }

    /**
     * set number of SeatsRemaining
     * @param numSeats number of SeatsRemaining to set to
     */
    public void setSeatsRemaining(int numSeats) {
        seatsRemainingTextField.setText(Integer.toString(numSeats));
    }

    /**
     * get SeatType linked to this JSeatSelectionSubPanel
     * @return SeatType linked to this JSeatSelectionSubPanel
     */
    public SeatType getSeatType() {
        return seatType;
    }
}
