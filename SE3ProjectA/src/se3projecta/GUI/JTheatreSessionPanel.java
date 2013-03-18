/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import se3projecta.Model.Seat;
import se3projecta.Model.TheatreSession;
import se3projecta.Model.SeatType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JTheatreSessionPanel extends javax.swing.JPanel {
    private Transaction transaction;
    
    
    public JTheatreSessionPanel(Transaction transaction) {
        setBackground(Color.white);
        this.transaction = transaction;
        
        
        final JTheatreSessionPanel panel = this;
        transaction.addTransactionListener(new TransactionListener() {
            @Override
            public void transactionSessionChanged(TheatreSession theatreSession) {
                panel.setTheatreSession(theatreSession);
            }
        });
    }
    
    private Seat[][] seats;
    private SeatButton[][] seatButtons;
    int rows, columns;

    public void setTheatreSession(TheatreSession tSession) {
        seats = tSession.getSeatRows();
        rows = seats.length;
        columns = seats[0].length;
        this.setLayout(new GridLayout(rows, columns));
        renderTheatreSession();
        this.validate();
    }

    public void renderTheatreSession() {
        seatButtons = new SeatButton[seats.length][seats[0].length];
        removeAll(); //remove all previous seats
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) { //length is the same for all rows of seats
                seatButtons[i][j] = new SeatButton(seats[i][j]);
                add(seatButtons[i][j]);
            }
        }
    }

    public class seatButtonAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SeatButton seatButton = (SeatButton) e.getSource();
            SeatType seatType = seatButton.getSeatType();
        }
    }
}
