/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import java.awt.Dimension;
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
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(393, 42));
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
        costTextField.setEditable(false);
        addAllocationButton = new JButton("+");

        //creating panels for layout
        JPanel ticketTypePanel = new JPanel();
        ticketTypePanel.setLayout(new BoxLayout(ticketTypePanel, BoxLayout.Y_AXIS));
        ticketTypePanel.add(ticketTypeLabel);
        ticketTypePanel.add(ticketTypeComboBox);

        JPanel seatTypePanel = new JPanel();
        seatTypePanel.setLayout(new BoxLayout(seatTypePanel, BoxLayout.Y_AXIS));
        seatTypePanel.add(seatTypeLabel);
        seatTypePanel.add(seatTypeComboBox);

        JPanel numberOfTicketsPanel = new JPanel();
        numberOfTicketsPanel.setLayout(new BoxLayout(numberOfTicketsPanel, BoxLayout.Y_AXIS));
        numberOfTicketsPanel.add(numberOfTicketsLabel);
        numberOfTicketsPanel.add(numberOfTicketsComboBox);

        JPanel costPanel = new JPanel();
        costPanel.setLayout(new BoxLayout(costPanel, BoxLayout.Y_AXIS));
        costPanel.add(costLabel);
        costPanel.add(costTextField);

        JPanel addAllocationButtonPanel = new JPanel();
        addAllocationButtonPanel.setLayout(new BoxLayout(addAllocationButtonPanel, BoxLayout.Y_AXIS));
        addAllocationButtonPanel.add(Box.createRigidArea(new Dimension(1, 15))); //padding for the button to be aligned
        addAllocationButtonPanel.add(addAllocationButton);

        add(ticketTypePanel);
        add(seatTypePanel);
        add(numberOfTicketsPanel);
        add(costPanel);
        add(addAllocationButtonPanel);

        ticketTypePanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        seatTypePanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        numberOfTicketsPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        costPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        addAllocationButtonPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
    }
}
