package se3projecta.GUI;

import java.util.EventListener;
import se3projecta.Model.TheatreSession;
import se3projecta.Model.Seat;

/**
 * A set of listeners listener that are executed when change are made to the
 * subscribed Transaction object.
 * 
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
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
