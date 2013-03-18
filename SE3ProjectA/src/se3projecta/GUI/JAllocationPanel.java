/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;
import se3projecta.Repository;
import se3projecta.Model.SeatType;
import se3projecta.Money;
import se3projecta.Model.CustomerType;

/**
 *
 * @author Adam
 */
public class JAllocationPanel extends JPanel {
    private JLabel ticketTypeLabel, seatTypeLabel, numberOfTicketsLabel, costLabel;
    private JComboBox ticketTypeComboBox, seatTypeComboBox, numberOfTicketsComboBox;
    private JTextField costTextField;
    private JButton addAllocationButton;
    private Money cost;
    private JTransactionPanel jtp;
    private int id;
    private PriceAggregator priceAggregator;

    @Override
    public boolean equals(Object obj) { //enables removal from ArrayList
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof JAllocationPanel)) {
            return false;
        }
        JAllocationPanel o = (JAllocationPanel) obj;
        return o.getID() == this.getID();
    }

    public int getID() {
        return id;
    }

    public void setRemovable(ActionListener removeListener) {
        addAllocationButton.setText("-");
        addAllocationButton.removeActionListener(addAllocationButton.getActionListeners()[0]); //there will only ever be one action listener
        addAllocationButton.addActionListener(removeListener);

    }

    public JAllocationPanel(int id, PriceAggregator pa, Repository repository, JTransactionPanel _jtp) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(493, 45)); //TODO remove hardcodedness
        
        jtp = _jtp;
        
        this.id = id;
        this.priceAggregator = pa;
        
        ticketTypeLabel = new JLabel("Ticket Type");
        
        seatTypeLabel = new JLabel("Seat Type");
        
        numberOfTicketsLabel = new JLabel("Number of Tickets");
        
        costLabel = new JLabel("Cost");
        
        ticketTypeComboBox = new JComboBox(repository.getCustomerTypes().toArray());
        ticketTypeComboBox.addActionListener(new ComboBoxAL());
        
        seatTypeComboBox = new JComboBox(repository.getSeatTypes().toArray());
        seatTypeComboBox.addActionListener(new ComboBoxAL());
        
        numberOfTicketsComboBox = new JComboBox();
        numberOfTicketsComboBox.addActionListener(new ComboBoxAL());
        
        for (int i = 0; i <= 10; i++) {
            numberOfTicketsComboBox.addItem(i);
        }
        costTextField = new JTextField(new Money(0).toString());
        costTextField.setEditable(false);
        costTextField.setFocusable(false);
        costTextField.setPreferredSize(new Dimension(60, 0)); //TODO fix hardcodedness (allows for big money values)
        addAllocationButton = new JButton("+");
        final JAllocationPanel allocationPanel = this;
        final ActionListener removeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtp.removeAllocationPanel(allocationPanel);
                jtp.revalidate();
                jtp.repaint();
            }
        };
        ActionListener addListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jtp.addAllocationPanel();
                allocationPanel.setRemovable(removeListener);
            }
        };
        addAllocationButton.addActionListener(addListener);

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

    private void updateCost() { //TODO need to add more checks for things not existing due to being called when textboxes populated
        int numberOfTickets = getNumberofSeats();
        double ticketCost = getSeatType().getPrice();
        double multiplier = ((CustomerType) ticketTypeComboBox.getSelectedItem()).getPriceMultiplier();
        cost = new Money(ticketCost * multiplier * numberOfTickets);
        priceAggregator.updatePrice();

        if (costTextField != null) {
            costTextField.setText(cost.toString());
        }
    }
    
    public int getNumberofSeats() {
        return ((Integer) numberOfTicketsComboBox.getSelectedItem());
    }
    
    public SeatType getSeatType() {
        return (SeatType) seatTypeComboBox.getSelectedItem();
    }
    
    public Money getCost() {
        return cost;
    }

    public class ComboBoxAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateCost();
        }
    }
}
