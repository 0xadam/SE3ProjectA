/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

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

    public JTransactionPanel(Repository repository_, GUI gui, Transaction transaction_) {
        transaction = transaction_;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        repository = repository_;
        allocationPanels = new ArrayList<JAllocationPanel>();
        
        addAllocationPanel();
        
        add(allocationPanels.get(0));
        navigationPanel = new JAllocationPanelNavigation(gui, repository, this, transaction);
        add(navigationPanel);
    }

    public void removeAllocationPanel(JAllocationPanel allocationPanel) {
        transaction.removeAllocation(allocationPanel.getAllocation());
        remove(allocationPanel);
        allocationPanels.remove(allocationPanel);
    }

    public void addAllocationPanel() {
        Allocation allocation = new Allocation();
        transaction.addAllocation(allocation);
        
        allocationPanels.add(new JAllocationPanel(0, repository, this, allocation));
        add(allocationPanels.get(allocationPanels.size() - 1), allocationPanels.size() - 1);
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
}
