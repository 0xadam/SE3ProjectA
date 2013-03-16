/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.*;
import se3projecta.Repository;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JTransactionPanel extends JPanel {

    Repository repository;
    JAllocationPanel allocationPanel;

    public JTransactionPanel(Repository repository_) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        repository = repository_;
        allocationPanel = new JAllocationPanel(repository);
        add(allocationPanel);
    }
}
