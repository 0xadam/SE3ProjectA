/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import se3projecta.Repository;
import java.util.ArrayList;
import se3projecta.Model.CustomerType;
import se3projecta.Model.SeatType;
import se3projecta.Money;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flidners.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JTransactionPanel extends JPanel {

    private Repository repository;
    private ArrayList<JAllocationPanel> allocationPanels;
    private JAllocationPanelNavigation navigationPanel;
    private Transaction transaction;
    private JButton addAllocationButton;

    public JTransactionPanel(Repository repository_, GUI gui, Transaction transaction_) {
        transaction = transaction_;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addAllocationButton = new JButton("+");
        ActionListener addListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAllocationPanel();
            }
        };
        addAllocationButton.addActionListener(addListener);
        add(addAllocationButton);

        repository = repository_;
        allocationPanels = new ArrayList<JAllocationPanel>();

        addAllocationPanel();

        add(allocationPanels.get(0));
        navigationPanel = new JAllocationPanelNavigation(gui, transaction);
        add(navigationPanel);
    }

    public void removeAllocationPanel(JAllocationPanel allocationPanel) {
        transaction.removeAllocation(allocationPanel.getAllocation());
        remove(allocationPanel);
        allocationPanels.remove(allocationPanel);
        checkAllocationRemoval();
    }

    public void addAllocationPanel() {
        Allocation allocation = new Allocation();
        transaction.addAllocation(allocation);
        allocationPanels.add(new JAllocationPanel(0, repository, this, allocation));
        add(allocationPanels.get(allocationPanels.size() - 1), allocationPanels.size() - 1);
        checkAllocationRemoval();
    }

    public void clearAllocations() {
        for (JAllocationPanel jap : allocationPanels) {
            transaction.removeAllocation(jap.getAllocation());
            remove(jap);
        }

        allocationPanels.clear();

        addAllocationPanel();
    }

    public int getSeatCount(SeatType st) {
        int seatCount = 0;
        for (JAllocationPanel jap : allocationPanels) {
            if (jap.getAllocation().getSeatType() == st) {
                seatCount += jap.getAllocation().getNumberOfTickets();
            }
        }
        return seatCount;
    }

    public int getAllocationCount() {
        return allocationPanels.size();
    }
    
    /**
     * disable allocation removal button if there is only one allocation left,
     * otherwise enable
     */
    public void checkAllocationRemoval() {
        if (allocationPanels.size() <= 0) {
            if (allocationPanels.size() <= 1) {
                allocationPanels.get(0).setNonRemovable();
            } else {
                allocationPanels.get(0).setRemovable();
            }
        }


    }
}
