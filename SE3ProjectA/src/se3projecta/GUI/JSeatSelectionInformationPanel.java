/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.JPanel;

/**
 *
 * @author Adam
 */
public class JSeatSelectionInformationPanel extends JPanel {

    Transaction transaction;

    public JSeatSelectionInformationPanel(Transaction transaction_) { //can probably pass the specifically needed data here
        transaction = transaction_;
    }
}
