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
public class JTransactionPanel extends JPanel implements PriceAggregator {

    private Repository repository;
    private ArrayList<JAllocationPanel> allocationPanels;
    private JAllocationPanelNavigation navigationPanel;

    public JTransactionPanel(Repository repository_, GUI gui) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        repository = repository_;
        allocationPanels = new ArrayList<JAllocationPanel>();
        
        addAllocationPanel();
        
        add(allocationPanels.get(0));
        navigationPanel = new JAllocationPanelNavigation(gui);
        add(navigationPanel);
    }

    public void removeAllocationPanel(JAllocationPanel allocationPanel) {
        remove(allocationPanel);
        allocationPanels.remove(allocationPanel);
    }

    public void addAllocationPanel() {
        allocationPanels.add(new JAllocationPanel(0, (PriceAggregator)this, repository));
        add(allocationPanels.get(allocationPanels.size() - 1), allocationPanels.size() - 1);
    }

    @Override
    public void updatePrice() {
        if (navigationPanel != null) {
            navigationPanel.setTotalPrice(getPrice());
        }
    }
    
    public void clearAllocations() {
        for (JAllocationPanel jap : allocationPanels) {
           removeAllocationPanel(jap);
        }
    }
    
    public Money getPrice() {
        Money total = new Money(0);
        
        for (JAllocationPanel a : allocationPanels) {
            total = new Money(total.getValue() + a.getCost().getValue());
        }
        
        return total;
    }
}
