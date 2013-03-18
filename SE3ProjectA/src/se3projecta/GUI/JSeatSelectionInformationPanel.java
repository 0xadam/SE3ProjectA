/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.util.ArrayList;
import se3projecta.Repository;
import se3projecta.Model.SeatType;

/**
 *
 * @author Adam
 */
public class JSeatSelectionInformationPanel extends JPanel {

    Transaction transaction;
    Repository repository;
    ArrayList<JSeatSelectionInformationSubPanel> seatsRemainingSubPanels;

    public JSeatSelectionInformationPanel(Repository repository_, Transaction transaction_) { //can probably pass the specifically needed data here
        repository = repository_;
        transaction = transaction_;
        seatsRemainingSubPanels = new ArrayList<JSeatSelectionInformationSubPanel>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (SeatType seatType : repository.getSeatTypes()) {
            seatsRemainingSubPanels.add(new JSeatSelectionInformationSubPanel(seatType.getName()));
            add(seatsRemainingSubPanels.get(seatsRemainingSubPanels.size() - 1));
        }
    }
}
