package se3projecta.GUI;

import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import se3projecta.Model.TheatreSession;

/**
 *
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Transaction {
    private TheatreSession theatreSession;
    private ArrayList<Allocation> allocations = new ArrayList<Allocation>();
    
    protected EventListenerList listeners = new EventListenerList();
    
    public TheatreSession getTheatreSession() {
        return theatreSession;
    }
    
    public void setTheatreSession(TheatreSession ts) {
        if (ts != theatreSession) {
            theatreSession = ts;
            fireTheatreSessionChanged();
        }
    }
    
    public ArrayList<Allocation> getAllocations() {
        return allocations;
    }
    
    public void addTransactionListener(TransactionListener l) {
        listeners.add(TransactionListener.class, l);
    }

    public void removeTransactionListener(TransactionListener l) {
        listeners.remove(TransactionListener.class, l);
    }

    public TransactionListener[] getTransactionListeners() {
        return listeners.getListeners(TransactionListener.class);
    }
    
    private void fireTheatreSessionChanged () {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.theatreSessionChanged(this.theatreSession);
        }
    }
}
