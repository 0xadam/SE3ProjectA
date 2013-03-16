/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.*;
import se3projecta.Repository;

/**
 *
 * @author Adam
 */
public class JAllocationPanel extends JPanel {

    JLabel ticketTypeLabel, seatTypeLabel, numberOfTicketsLabel, costLabel;
    JComboBox ticketTypeComboBox, seatTypeComboBox, numberOfTicketsComboBox;
    JTextField costTextField;
    JButton addAllocationButton;
    Repository repository;

    public JAllocationPanel(Repository repository_) {
        repository = repository_;
        ticketTypeLabel.setText("Ticket Type");
        seatTypeLabel.setText("Seat Type");
        numberOfTicketsLabel.setText("Number of Tickets");
        costLabel.setText("Cost");
        ticketTypeComboBox.addItem(repository.getSeatTypes());
    }
}
