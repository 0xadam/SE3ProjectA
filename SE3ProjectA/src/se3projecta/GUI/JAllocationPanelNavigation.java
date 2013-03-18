/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;
import se3projecta.Repository;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;
import se3projecta.Money;
import se3projecta.Model.SeatType;
import se3projecta.Model.TheatreSession;
/**
 *
 * @author Russell
 */
public class JAllocationPanelNavigation extends JPanel {
    private GUI gui;
    private JLabel totalPrice;
    private JButton navigateBack;
    private JButton navigateForward;
    private JTransactionPanel jtp;
    private Repository repository;
    private Transaction transaction;
    
    public JAllocationPanelNavigation(GUI _gui, Repository _repository, JTransactionPanel _jtp, Transaction t) {
        gui = _gui;
        jtp = _jtp;
        repository = _repository;
        transaction = t;
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        totalPrice = new JLabel("0");
        navigateBack = new JButton("Back");
        navigateForward = new JButton("Forward");
        
        navigateForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                //TODO validate ticket selection and set up seat selection info.
                TheatreSession ts = transaction.getTheatreSession();
                
                boolean valid = true;
                for (SeatType st : repository.getSeatTypes()) {
                    if (!ts.hasAvailable(st, jtp.getSeatCount(st))) {
                        valid = false;
                    }
                }
                if (!valid) {
                    //todo show error message.
                } else {
                    gui.setState(GUI.GUIState.PlaceSeats);
                }
                
            }
        });
        
        navigateBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setState(GUI.GUIState.SelectTheaterSession);
            }
        });
        
        add(navigateBack);
        add(navigateForward);
        add(totalPrice);
        
        transaction.addTransactionListener(new TransactionListener() {
            void costChanged(Money cost) {
                setTotalCost(cost);
            }
        });
    }
    
    private void setTotalCost(Money cost) {
        totalPrice.setText(cost.toString());
    }
    
}
