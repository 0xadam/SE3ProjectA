/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Adam
 */
public class JSeatSelectionInformationSubPanel extends JPanel {

    private String seatType;
    private JLabel seatsRemainingLabel;
    private JTextField seatsRemainingTextField;

    public JSeatSelectionInformationSubPanel(String seatType_) {
        seatType = seatType_;
        seatsRemainingLabel = new JLabel(seatType + " Seats Left To Allocate");
        seatsRemainingTextField = new JTextField("0");
        seatsRemainingTextField.setFocusable(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(seatsRemainingLabel);
        add(seatsRemainingTextField);
    }
}
