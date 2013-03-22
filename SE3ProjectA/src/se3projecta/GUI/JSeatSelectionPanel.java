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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import se3projecta.Model.Seat;

/**
 * JPanel which allows users to select which seats the want to sit in, place the
 * seats randomly or use a combination of the two to select seating.
 *
 * @author Adam
 */
public class JSeatSelectionPanel extends JPanel {
    
    Transaction transaction;
    Repository repository;
    Map<SeatType, JSeatSelectionSubPanel> seatsRemainingSubPanels;
    JButton bookButton, randomAllocationButton, backButton;
    GUI gui;

    /**
     * Create JSeatSelectionPanel.
     *
     * @param repository_ where to get widget values from
     * @param transaction_ used to get & set number of unallocated seats
     * @param _gui the GUI object which navigation changes the state of
     */
    public JSeatSelectionPanel(Repository repository_, Transaction transaction_, GUI _gui) { //can probably pass the specifically needed data here
        repository = repository_;
        transaction = transaction_;
        gui = _gui;
        
        seatsRemainingSubPanels = new HashMap<SeatType, JSeatSelectionSubPanel>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (SeatType seatType : repository.getSeatTypes()) {
            seatsRemainingSubPanels.put(seatType, new JSeatSelectionSubPanel(seatType));
            JSeatSelectionSubPanel seatsRemainingSubPanel = seatsRemainingSubPanels.get(seatType);
            Integer seatsRemaining = transaction.countAllocatedBySeatType().get(seatType);
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
                if (!transaction.placedAll()) {
                    JOptionPane.showMessageDialog(gui, "Please allocate all seats before continuing");
                    return;
                }
                
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
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Object[] options = {"Yes", "No"};
            
                int choice = JOptionPane.showOptionDialog(gui,
                        "Are you sure you wish to go back? You will lose your "
                        + "seats placement.",
                        "Back",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);

                if (choice == JOptionPane.YES_OPTION) {
                    gui.setState(GUI.GUIState.SelectSeating);
                }
                
            }
        });
        add(randomAllocationButton);
        
        JPanel navButtons = new JPanel();
        navButtons.setLayout(new BoxLayout(navButtons, BoxLayout.X_AXIS));
        navButtons.add(bookButton);
        navButtons.add(backButton);
        
        add(navButtons);
    }

    /**
     * Update the number of seats remaining for each SeatType.
     */
    public void updateAllSeatsRemaining() {
        
        Iterator it = transaction.countUnplacedBySeatTypes().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<SeatType, Integer> pairs = (Map.Entry) it.next();
            JSeatSelectionSubPanel seatsRemainingSubPanel = seatsRemainingSubPanels.get(pairs.getKey());
            seatsRemainingSubPanel.setSeatsRemaining(pairs.getValue());
            //seatsRemainingSubPanel.setVisible(pairs.getValue() > 0);
            seatsRemainingSubPanel.setVisible(true);
            it.remove();
        }
    }
    
    public void setSeatSubPanelsVisible(boolean visible) {
        for (JSeatSelectionSubPanel seatsRemainingSubPanel : seatsRemainingSubPanels.values()) {
            seatsRemainingSubPanel.setVisible(false);
        }
    }
    
    private void updateSeatsRemaining(SeatType seatType) {
        seatsRemainingSubPanels.get(seatType).setSeatsRemaining(transaction.countUnplaced(seatType));
    }
}
