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
import java.util.Iterator;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import se3projecta.Model.Seat;

/**
 *
 * @author Adam
 */
public class JSeatSelectionInformationPanel extends JPanel {

    Transaction transaction;
    Repository repository;
    Map<SeatType, JSeatSelectionInformationSubPanel> seatsRemainingSubPanels;
    JButton bookButton, randomAllocationButton, backButton;
    GUI gui;

    public JSeatSelectionInformationPanel(Repository repository_, Transaction transaction_, GUI _gui) { //can probably pass the specifically needed data here
        repository = repository_;
        transaction = transaction_;
        gui = _gui;
        
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
            void seatingChanged(Seat seat) {
                updateSeatsRemaining(seat.getType());
            }
            
            @Override
            void seatingChanged(Seat[] seat) {
                updateAllSeatsRemaining();
            }
        });

        randomAllocationButton = new JButton("Randomly Allocate Remaining Seats");
        randomAllocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaction.randomlyAllocate();
                updateAllSeatsRemaining();
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
                gui.setState(GUI.GUIState.SelectTheaterSession);
            }
        });
        add(randomAllocationButton);
        
                
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.setState(GUI.GUIState.SelectSeating);
            }
        });
        add(randomAllocationButton);
        
        JPanel navButtons = new JPanel();
        navButtons.setLayout(new BoxLayout(navButtons, BoxLayout.X_AXIS));
        navButtons.add(bookButton);
        navButtons.add(backButton);
        
        add(navButtons);
    }

    public void updateAllSeatsRemaining() {
        
        Iterator it = transaction.countUnplacedSeatTypes().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<SeatType, Integer> pairs = (Map.Entry) it.next();
            seatsRemainingSubPanels.get(pairs.getKey()).setSeatsRemaining(pairs.getValue());
            it.remove();
        }
    }
    
    private void updateSeatsRemaining(SeatType seatType) {
        seatsRemainingSubPanels.get(seatType).setSeatsRemaining(transaction.countUnplacedSeats(seatType));
    }
}