package se3projecta.GUI;

import java.util.EventListener;
import se3projecta.Model.TheatreSession;

/**
 *
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public abstract class TransactionListener implements EventListener {
    /**
     *
     */
    void theatreSessionChanged(TheatreSession theatreSession) {};
    void allocationChanged() {};
    void allocationAdded() {};
    void allocationRemoved() {};
}
