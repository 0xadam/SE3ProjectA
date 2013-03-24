package se3projecta.GUI;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;
import se3projecta.Money;
import se3projecta.Model.SeatType;

/**
 * JPanel which holds JButtons that allow navigation back to
 * SelectTheatreSession state and forward to PlaceSeats State.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JAllocationPanelNavigation extends JPanel {

    private GUI gui;
    private JLabel totalPrice, totalPriceLabel;
    private JButton navigateBack;
    private JButton navigateForward;
    private Transaction transaction;
    private static final long serialVersionUID = 0; //remove warning in netbeans build process

    /**
     * Creates JAllocationPanelNavigation.
     *
     * @param _gui the GUI object which navigation changes the state of
     * @param t the Transaction which must be validated on forward navigation
     */
    public JAllocationPanelNavigation(GUI _gui, Transaction t) {
        gui = _gui;
        transaction = t;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        totalPrice = new JLabel(new se3projecta.Money(0).toString());
        totalPriceLabel = new JLabel("Total Cost: ");
        navigateBack = new JButton("Back");
        navigateForward = new JButton("Forward");

        navigateForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateTransaction()) {
                    gui.setState(GUI.GUIState.PlaceSeats);
                }

            }
        });

        navigateBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setState(GUI.GUIState.SelectTheaterSession);
            }
        });

        add(navigateBack);
        add(navigateForward);
        add(totalPriceLabel);
        add(totalPrice);

        transaction.addTransactionListener(new TransactionListener() {
            @Override
            void allocationsChanged() {
                setTotalCost(transaction.getCost());
            }
        });
    }

    private void setTotalCost(Money cost) {
        totalPrice.setText(cost.toString());
    }

    private boolean validateTransaction() {
        Iterator<Entry<SeatType, Integer>> it = transaction.countAllocatedBySeatType().entrySet().iterator();
        int totaltickets = 0;
        while (it.hasNext()) {
            Map.Entry<SeatType, Integer> pairs = it.next();

            if (!transaction.getTheatreSession().hasAvailable(pairs.getKey(), pairs.getValue())) {
                JOptionPane.showMessageDialog(gui, "There are not that many " + pairs.getKey().getName().toLowerCase() + " seats available. Please select a maximum of " + transaction.getTheatreSession().countEmpty(pairs.getKey()) + " seats and try again.");
                return false;
            }

            totaltickets += pairs.getValue();
            it.remove();
        }

        if (totaltickets > 0) {
            return true;
        }

        return false;
    }
}
