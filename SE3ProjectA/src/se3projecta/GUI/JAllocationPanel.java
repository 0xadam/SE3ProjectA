/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;
import se3projecta.Repository;

/**
 *
 * @author Adam
 */
public class JAllocationPanel extends JPanel {

    private JLabel ticketTypeLabel, seatTypeLabel, numberOfTicketsLabel, costLabel, seatsRemainingLabel;
    private JComboBox ticketTypeComboBox, seatTypeComboBox, numberOfTicketsComboBox;
    private JTextField costTextField, seatsRemainingTextField;
    private JButton addAllocationButton;
    private Repository repository;

    public void setRemovable() {
            addAllocationButton.setText("-");
            addAllocationButton.removeActionListener(addAllocationButton.getActionListeners()[0]); //there will only ever be one action listener
            addAllocationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JAllocationPanel allocationPanel = (JAllocationPanel) ((JButton) e.getSource()).getParent().getParent();
                    JTransactionPanel transactionPanel = (JTransactionPanel) allocationPanel.getParent();
                    transactionPanel.remove(allocationPanel); //TODO fix hacks. gets button, then the panel, then the JAllocationPanel
                    transactionPanel.revalidate();
                    transactionPanel.repaint();
                }
            });

    }

    public JAllocationPanel(Repository repository_) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(493, 42));
        repository = repository_;
        ticketTypeLabel = new JLabel("Ticket Type");

        seatTypeLabel = new JLabel("Seat Type");
        numberOfTicketsLabel = new JLabel("Number of Tickets");
        costLabel = new JLabel("Cost");
        seatsRemainingLabel = new JLabel("Seats Remaining");
        ticketTypeComboBox = new JComboBox(repository.getCustomerTypes().toArray());
        seatTypeComboBox = new JComboBox(repository.getSeatTypes().toArray());
        numberOfTicketsComboBox = new JComboBox();
        for (int i = 0; i <= 10; i++) {
            numberOfTicketsComboBox.addItem(i);
        }
        costTextField = new JTextField("0");
        costTextField.setEditable(false);
        seatsRemainingTextField = new JTextField("0");
        seatsRemainingTextField.setEditable(false);
        addAllocationButton = new JButton("+");
        addAllocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //TODO kinda hacky. Probably a better way to do it
                ((JTransactionPanel) getParent()).addAllocationPanel();
                ((JAllocationPanel) (((JButton) e.getSource()).getParent().getParent())).setRemovable();
            }
        });

        //creating panels for layout
        JPanel ticketTypePanel = new JPanel();
        ticketTypePanel.setLayout(new BoxLayout(ticketTypePanel, BoxLayout.Y_AXIS));
        ticketTypeLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        ticketTypeComboBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        ticketTypePanel.add(ticketTypeLabel);
        ticketTypePanel.add(ticketTypeComboBox);

        JPanel seatTypePanel = new JPanel();
        seatTypePanel.setLayout(new BoxLayout(seatTypePanel, BoxLayout.Y_AXIS));
        seatTypeLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        seatTypeComboBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        seatTypePanel.add(seatTypeLabel);
        seatTypePanel.add(seatTypeComboBox);

        JPanel numberOfTicketsPanel = new JPanel();
        numberOfTicketsPanel.setLayout(new BoxLayout(numberOfTicketsPanel, BoxLayout.Y_AXIS));
        numberOfTicketsLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        numberOfTicketsComboBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        numberOfTicketsPanel.add(numberOfTicketsLabel);
        numberOfTicketsPanel.add(numberOfTicketsComboBox);

        JPanel costPanel = new JPanel();
        costPanel.setLayout(new BoxLayout(costPanel, BoxLayout.Y_AXIS));
        costLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        costTextField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        costPanel.add(costLabel);
        costPanel.add(costTextField);

        JPanel seatsRemainingPanel = new JPanel();
        seatsRemainingPanel.setLayout(new BoxLayout(seatsRemainingPanel, BoxLayout.Y_AXIS));
        seatsRemainingLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        seatsRemainingTextField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        seatsRemainingPanel.add(seatsRemainingLabel);
        seatsRemainingPanel.add(seatsRemainingTextField);

        JPanel addAllocationButtonPanel = new JPanel();
        addAllocationButtonPanel.setLayout(new BoxLayout(addAllocationButtonPanel, BoxLayout.Y_AXIS));
        addAllocationButtonPanel.add(Box.createRigidArea(new Dimension(1, 15))); //padding for the button to be aligned
        addAllocationButtonPanel.add(addAllocationButton);

        add(ticketTypePanel);
        add(seatTypePanel);
        add(numberOfTicketsPanel);
        add(costPanel);
        add(seatsRemainingPanel);
        add(addAllocationButtonPanel);

        ticketTypePanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        seatTypePanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        numberOfTicketsPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        costPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        seatsRemainingPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        addAllocationButtonPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
    }
}
