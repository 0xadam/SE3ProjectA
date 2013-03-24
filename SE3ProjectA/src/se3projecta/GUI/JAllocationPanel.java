package se3projecta.GUI;

import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import se3projecta.*;
import se3projecta.Model.*;

/**
 * JPanel which holds allocation widgets.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JAllocationPanel extends JPanel {

    private JLabel ticketTypeLabel, seatTypeLabel, numberOfTicketsLabel, costLabel;
    private JComboBox ticketTypeComboBox, seatTypeComboBox;
    private JSpinner numberOfTicketsSpinner;
    private JTextField costTextField;
    private JButton removeAllocationButton;
    private JTransactionPanel jtp;
    private Allocation allocation;
    private static final long serialVersionUID = 0; //remove warning in netbeans build process

    /**
     * Enables (or disables) the removal of the JAllocationPanel.
     *
     * @param b true to enable the removal of the JAllocationPanel, otherwise
     * false
     */
    public void setRemovable(boolean b) {
        removeAllocationButton.setEnabled(b);
    }

    /**
     * Create a JAllocationPanel
     *
     * @param id
     * @param repository where to get widget values from
     * @param jtp_ parent of this JAllocationPanel
     * @param allocation_ handles backend code for this allocation
     */
    public JAllocationPanel(int id, Repository repository, JTransactionPanel jtp_, Allocation allocation_) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        jtp = jtp_;
        allocation = allocation_;

        setMaximumSize(new Dimension(493, 45));

        ticketTypeLabel = new JLabel("Ticket Type");

        seatTypeLabel = new JLabel("Seat Type");

        numberOfTicketsLabel = new JLabel("Number of Tickets");

        costLabel = new JLabel("Cost");

        ticketTypeComboBox = new JComboBox(repository.getCustomerTypes().toArray());
        ticketTypeComboBox.addActionListener(new ComboBoxAL());

        seatTypeComboBox = new JComboBox(repository.getSeatTypes().toArray());
        seatTypeComboBox.addActionListener(new ComboBoxAL());

        numberOfTicketsSpinner = new JSpinner();
        numberOfTicketsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                update();
            }
        });
        numberOfTicketsSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));

        costTextField = new JTextField(new Money(0).toString());
        costTextField.setEditable(false);
        costTextField.setFocusable(false);
        costTextField.setPreferredSize(new Dimension(60, 0));
        removeAllocationButton = new JButton("-");
        final JAllocationPanel allocationPanel = this;
        final ActionListener removeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtp.removeAllocationPanel(allocationPanel);
                jtp.revalidate();
                jtp.repaint();
            }
        };
        removeAllocationButton.addActionListener(removeListener);

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
        numberOfTicketsSpinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        numberOfTicketsPanel.add(numberOfTicketsLabel);
        numberOfTicketsPanel.add(numberOfTicketsSpinner);

        JPanel costPanel = new JPanel();
        costPanel.setLayout(new BoxLayout(costPanel, BoxLayout.Y_AXIS));
        costLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        costTextField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        costPanel.add(costLabel);
        costPanel.add(costTextField);

        JPanel seatsRemainingPanel = new JPanel();
        seatsRemainingPanel.setLayout(new BoxLayout(seatsRemainingPanel, BoxLayout.Y_AXIS));

        JPanel removeAllocationButtonPanel = new JPanel();
        removeAllocationButtonPanel.setLayout(new BoxLayout(removeAllocationButtonPanel, BoxLayout.Y_AXIS));
        removeAllocationButtonPanel.add(Box.createRigidArea(new Dimension(1, 15))); //padding for the button to be aligned
        removeAllocationButtonPanel.add(removeAllocationButton);

        add(ticketTypePanel);
        add(seatTypePanel);
        add(numberOfTicketsPanel);
        add(costPanel);
        add(seatsRemainingPanel);
        add(removeAllocationButtonPanel);

        ticketTypePanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        seatTypePanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        numberOfTicketsPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        costPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        seatsRemainingPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        removeAllocationButtonPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        update();
    }

    private void update() {
        allocation.setNumberOfTickets(getNumberofSeats());
        allocation.setSeatType(getSeatType());
        allocation.setCustomerType((CustomerType) ticketTypeComboBox.getSelectedItem());

        if (costTextField != null) {
            costTextField.setText(allocation.getCost().toString());
        }
    }

    private int getNumberofSeats() {
        return ((SpinnerNumberModel) numberOfTicketsSpinner.getModel()).getNumber().intValue();
    }

    private SeatType getSeatType() {
        return (SeatType) seatTypeComboBox.getSelectedItem();
    }

    /**
     * get Allocation for this JAllocationPanel.
     *
     * @return Allocation for this JAllocationPanel
     */
    public Allocation getAllocation() {
        return allocation;
    }

    /**
     *
     */
    public class ComboBoxAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            update();
        }
    }
}
