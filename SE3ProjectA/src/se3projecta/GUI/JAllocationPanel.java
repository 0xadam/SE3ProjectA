/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.*;
import se3projecta.Repository;
import se3projecta.Model.SeatType;
import se3projecta.Model.CustomerType;

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
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        repository = repository_;
        ticketTypeLabel = new JLabel("Ticket Type");
        seatTypeLabel = new JLabel("Seat Type");
        numberOfTicketsLabel = new JLabel("Number of Tickets");
        costLabel = new JLabel("Cost");
        ticketTypeComboBox = new JComboBox(repository.getCustomerTypes().toArray());
        seatTypeComboBox = new JComboBox(repository.getSeatTypes().toArray());
        numberOfTicketsComboBox = new JComboBox();
        for (int i = 0; i <= 10; i++) {
            numberOfTicketsComboBox.addItem(i);
        }
        costTextField = new JTextField("0");
        costTextField.setEnabled(false);

        //creating panels for layout
        JPanel ticketTypePanel = new JPanel();
        ticketTypePanel.setLayout(new BoxLayout(ticketTypePanel, BoxLayout.Y_AXIS));
        ticketTypePanel.add(ticketTypeLabel);
        ticketTypePanel.add(ticketTypeComboBox);

        JPanel seatTypePanel = new JPanel();
        seatTypePanel.setLayout(new BoxLayout(seatTypePanel, BoxLayout.Y_AXIS));
        seatTypePanel.add(seatTypeLabel);
        seatTypePanel.add(seatTypeComboBox);

        add(ticketTypePanel);
        add(seatTypePanel);
    }
}
