/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;
import se3projecta.Money;
/**
 *
 * @author Russell
 */
public class JAllocationPanelNavigation extends JPanel {
    private GUI gui;
    private JLabel totalPrice;
    private JButton navigateBack;
    private JButton navigateForward;
    
    public JAllocationPanelNavigation(GUI _gui) {
        gui = _gui;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        totalPrice = new JLabel("0");
        navigateBack = new JButton("Back");
        navigateForward = new JButton("Forward");
        
        navigateForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setState(GUI.GUIState.PlaceSeats);
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
    }
    
    public void setTotalPrice(Money p) {
        totalPrice.setText(p.toString());
    }
    
}
