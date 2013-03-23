package se3projecta.GUI;

import java.util.EventListener;
import se3projecta.Model.TheatreSession;
import se3projecta.Model.Seat;

/**
 * A listener that listens for changes in a Transaction and makes required 
 * changes.
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public abstract class TransactionListener implements EventListener {

    void theatreSessionChanged(TheatreSession theatreSession) {
    }

    ;
    void allocationsChanged() {
    }

    ;
    void seatingChanged(Seat seat) {
    }

    ; // if one seat changes
    void seatingChanged(Seat[] seats) {
    }
; // if many seats change
}
