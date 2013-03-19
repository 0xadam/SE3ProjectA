/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.util.Map;
import java.util.HashMap;
import se3projecta.Repository;
import se3projecta.Model.SeatType;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Adam
 */
public class JSeatSelectionInformationPanel extends JPanel {

    Transaction transaction;
    Repository repository;
    Map<SeatType, JSeatSelectionInformationSubPanel> seatsRemainingSubPanels;
    JButton bookButton;

    public JSeatSelectionInformationPanel(Repository repository_, Transaction transaction_) { //can probably pass the specifically needed data here
        repository = repository_;
        transaction = transaction_;
        seatsRemainingSubPanels = new HashMap<SeatType, JSeatSelectionInformationSubPanel>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (SeatType seatType : repository.getSeatTypes()) {
            seatsRemainingSubPanels.put(seatType, new JSeatSelectionInformationSubPanel(seatType));
            JSeatSelectionInformationSubPanel seatsRemainingSubPanel = seatsRemainingSubPanels.get(seatType);
            Integer seatsRemaining = transaction.countAllocatedSeatTypes().get(seatType);
            seatsRemainingSubPanel.setSeatsRemaining(seatsRemaining != null ? seatsRemaining : 0);
            add(seatsRemainingSubPanel);
        }
        transaction.addTransactionListener(new TransactionListener() {
            @Override
            void seatingChanged(SeatType seatType) {
                seatsRemainingSubPanels.get(seatType).setSeatsRemaining(transaction.countUnplacedSeats(seatType));
            }
        });
        bookButton = new JButton("Book Seats");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaction.getTheatreSession().commitSeats();
                try {
                    repository.save();
                } catch (TransformerConfigurationException ex) {
                } catch (ParserConfigurationException ex) {
                } catch (TransformerException ex) {
                }
            }
        });
        add(bookButton);
    }

    public void updateSeatsRemaining() {
        for (JSeatSelectionInformationSubPanel seatsRemainingSubPanel : seatsRemainingSubPanels.values()) {
            Integer seatsRemaining = transaction.countAllocatedSeatTypes().get(seatsRemainingSubPanel.getSeatType());
            seatsRemainingSubPanel.setSeatsRemaining(seatsRemaining != null ? seatsRemaining : 0);
        }
    }
}
