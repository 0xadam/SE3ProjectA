/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.*;

/**
 *
 * @author Adam
 */
public class JAllocationPanel extends JPanel {

    JLabel ticketTypeLabel, seatTypeLabel, numberOfTicketsLabel, costLabel;
    JComboBox ticketTypeComboBox, seatTypeComboBox, numberOfTicketsComboBox;
    JTextField costTextField;

    public JAllocationPanel() {
        ticketTypeLabel.setText("Ticket Type");
        seatTypeLabel.setText("Seat Type");
        numberOfTicketsLabel.setText("Number of Tickets");
        costLabel.setText("Cost");
    }
}
