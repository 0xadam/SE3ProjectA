/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Adam
 */
public class JSeatSelectionInformationPanel extends JPanel {

    Transaction transaction;
    JLabel remainingGoldLabel, remainingSilverLabel, remainingBronzeLabel;
    JTextField remainingGoldTextField, remainingSilverTextField, remainingBronzeTextField;

    public JSeatSelectionInformationPanel(Transaction transaction_) { //can probably pass the specifically needed data here
        transaction = transaction_;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        remainingGoldLabel = new JLabel("Gold Seats Left To Allocate");
        remainingGoldTextField = new JTextField("0");
        JPanel remainingGoldPanel = new JPanel();
        remainingGoldPanel.setLayout(new BoxLayout(remainingGoldPanel, BoxLayout.Y_AXIS));
        remainingGoldPanel.add(remainingGoldLabel);
        remainingGoldPanel.add(remainingGoldTextField);
        add(remainingGoldPanel);

        remainingSilverLabel = new JLabel("Silver Seats Left To Allocate");
        remainingSilverTextField = new JTextField("0");
        JPanel remainingSilverPanel = new JPanel();
        remainingSilverPanel.setLayout(new BoxLayout(remainingSilverPanel, BoxLayout.Y_AXIS));
        remainingSilverPanel.add(remainingSilverLabel);
        remainingSilverPanel.add(remainingSilverTextField);
        add(remainingSilverPanel);

        remainingBronzeLabel = new JLabel("Bronze Seats Left To Allocate");
        remainingBronzeTextField = new JTextField("0");
        JPanel remainingBronzePanel = new JPanel();
        remainingBronzePanel.setLayout(new BoxLayout(remainingBronzePanel, BoxLayout.Y_AXIS));
        remainingBronzePanel.add(remainingBronzeLabel);
        remainingBronzePanel.add(remainingBronzeTextField);
        add(remainingBronzePanel);

    }
}
