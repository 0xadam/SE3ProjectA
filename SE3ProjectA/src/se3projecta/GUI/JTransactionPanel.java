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
import se3projecta.Model.SeatType;

/**
 * JPanel which holds one or more JAllocationPanels, and allows their addition 
 * and removal, and also holds a JAllocationPanelNavigation.
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

    /**
     *
     * @param repository_ where to get widget values from
     * @param gui the GUI object which navigation changes the state of
     * @param transaction_ stores information about the current transaction
     */
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
        
        navigationPanel = new JAllocationPanelNavigation(gui, transaction);
        addAllocationPanel();
        add(navigationPanel);
    }

    /**
     * Remove the specified allocationPanel.
     * @param allocationPanel the allocationPanel to remove
     */
    public void removeAllocationPanel(JAllocationPanel allocationPanel) {
        transaction.removeAllocation(allocationPanel.getAllocation());
        remove(allocationPanel);
        allocationPanels.remove(allocationPanel);
        checkAllocationRemoval();
    }

    /**
     * Add an allocationPanel.
     */
    public void addAllocationPanel() {
        Allocation allocation = new Allocation();
        transaction.addAllocation(allocation);
        allocationPanels.add(new JAllocationPanel(0, repository, this, allocation));
        add(allocationPanels.get(allocationPanels.size() - 1), (allocationPanels.size()));
        checkAllocationRemoval();
    }

    /**
     * Clear all allocations - remove all and add one new one.
     */
    public void clearAllocations() {
        for (JAllocationPanel jap : allocationPanels) {
            transaction.removeAllocation(jap.getAllocation());
            remove(jap);
        }

        allocationPanels.clear();

        addAllocationPanel();
    }

    /**
     * Get the number of allocated Seats for the specified SeatType
     * @param st the SeatType to get the number of allocated Seats for
     * @return the number of allocated Seats for the specified SeatType
     */
    public int getSeatCount(SeatType st) {
        int seatCount = 0;
        for (JAllocationPanel jap : allocationPanels) {
            if (jap.getAllocation().getSeatType() == st) {
                seatCount += jap.getAllocation().getNumberOfTickets();
            }
        }
        return seatCount;
    }

    /**
     * Get the number of Allocation Panels. 
     * @return the number of Allocation Panels
     */
    public int getAllocationCount() {
        return allocationPanels.size();
    }

    /**
     * disable allocation removal button if there is only one allocation left,
     * otherwise enable
     */
    public void checkAllocationRemoval() {
        if (allocationPanels.size() > 0) { //remove possiblilty of error
            allocationPanels.get(0).setRemovable(allocationPanels.size() > 1);
        }


    }
}
