/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import se3projecta.Repository;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import se3projecta.Money;
import se3projecta.Model.SeatType;
import se3projecta.Model.TheatreSession;

/**
 *
 * @author Russell
 */
public class JAllocationPanelNavigation extends JPanel {

    private GUI gui;
    private JLabel totalPrice, totalPriceLabel;
    private JButton navigateBack;
    private JButton navigateForward;
    private Transaction transaction;

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
        Iterator it = transaction.countAllocatedBySeatType().entrySet().iterator();
        int totaltickets = 0;
        while (it.hasNext()) {
            Map.Entry<SeatType, Integer> pairs = (Map.Entry<SeatType, Integer>) it.next();

            if (!transaction.getTheatreSession().hasAvailable(pairs.getKey(), pairs.getValue())) {
                JOptionPane.showMessageDialog(gui, "There are not that many " + pairs.getKey().getName().toLowerCase() + " seats available. Please select fewer and try again.");
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
