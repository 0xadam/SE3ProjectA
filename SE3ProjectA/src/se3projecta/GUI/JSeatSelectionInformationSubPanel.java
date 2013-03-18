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
 *
 * @author Adam
 */
public class JSeatSelectionInformationSubPanel extends JPanel {

    private SeatType seatType;
    private JLabel seatsRemainingLabel;
    private JTextField seatsRemainingTextField;

    public JSeatSelectionInformationSubPanel(SeatType seatType_) {
        seatType = seatType_;
        seatsRemainingLabel = new JLabel(seatType + " Seats Left To Allocate");
        seatsRemainingTextField = new JTextField("0");
        seatsRemainingTextField.setFocusable(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(seatsRemainingLabel);
        add(seatsRemainingTextField);
    }

    public int getSeatsRemaining() {
        return Integer.parseInt(seatsRemainingTextField.getText());
    }

    public void setSeatsRemaining(int numSeats) {
        seatsRemainingTextField.setText(Integer.toString(numSeats));
    }

    public SeatType getSeatType() {
        return seatType;
    }
}
