package se3projecta.GUI;

import se3projecta.Model.*;
import java.awt.*;
import java.awt.event.*;

/**
 * JPanel that illustrates theatre layout and the state of seats in the theatre
 * for a particular session, and allows seating selection.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JTheatreSessionPanel extends javax.swing.JPanel {

    private Transaction transaction;
    private static final long serialVersionUID = 0; //remove warning in netbeans build process

    /**
     * Creates JTheatreSessionPanel.
     *
     * @param transaction_ stores information about the current transaction -
     * JTheatreSessionPanel must be updated when transaction is.
     */
    public JTheatreSessionPanel(Transaction transaction_) {
        setBackground(Color.white);
        transaction = transaction_;


        transaction.addTransactionListener(new TransactionListener() {
            @Override
            public void theatreSessionChanged(TheatreSession theatreSession) {
                setTheatreSession(theatreSession);
            }

            @Override
            public void seatingChanged(Seat[] seats) {
                refreshSeatIcons();
            }
        });
    }
    private Seat[][] seatRows;
    private SeatButton[][] seatButtons;
    int rows, columns;

    private void setTheatreSession(TheatreSession tSession) {
        seatRows = tSession.getSeatRows();
        rows = seatRows.length;
        columns = seatRows[0].length;
        setLayout(new GridLayout(rows, columns));
        renderTheatreSession();
        validate();
    }

    private void renderTheatreSession() {
        seatButtons = new SeatButton[seatRows.length][seatRows[0].length];
        removeAll(); //remove all previous seats
        for (int i = 0; i < seatRows.length; i++) {
            for (int j = 0; j < seatRows[0].length; j++) { //length is the same for all rows of seats
                seatButtons[i][j] = new SeatButton(seatRows[i][j]);
                add(seatButtons[i][j]);
            }
        }
    }

    /**
     * Refresh the icons for the seats in this TheatreSession.
     */
    public void refreshSeatIcons() {
        for (SeatButton[] row : seatButtons) {
            for (SeatButton sb : row) {
                sb.refreshIcon();
            }
        }
    }

    /**
     * Enable users to select seats they want to book.
     */
    public void enableSelection() {
        for (SeatButton[] row : seatButtons) {
            for (SeatButton sb : row) {
                final SeatButton seatButton = sb;

                seatButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Seat seat = seatButton.getSeat();
                        if (seat.getState() == SeatState.Held) {
                            transaction.releaseSeat(seat);
                        } else if (seat.getState() == SeatState.Empty) {
                            transaction.holdSeat(seat);
                        }
                        seatButton.refreshIcon();
                    }
                });
            }
        }
    }

    /**
     * Disable selection of seats - users can only view whether or not seats are
     * occupied.
     */
    public void disableSelection() {
        for (SeatButton[] row : seatButtons) {
            for (SeatButton sb : row) {
                for (ActionListener al : sb.getActionListeners()) {
                    sb.removeActionListener(al);
                }
            }
        }
    }
}
