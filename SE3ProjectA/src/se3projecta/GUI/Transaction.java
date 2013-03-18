package se3projecta.GUI;

import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import se3projecta.Model.TheatreSession;
import se3projecta.Money;

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
    
    public Allocation[] getAllocations() {
        Allocation[] a = new Allocation[allocations.size()];
        allocations.toArray(a);
        return a;
    }
    
    public void addAllocation(Allocation allocation) {
        allocations.add(allocation);
    }
    public void removeAllocation(Allocation allocation) {
        allocations.remove(allocation);
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
    
    public Money getTotal() {
        Money total = new Money(0);
        
        for (Allocation allocation : allocations) {
            total = new Money(total.getValue() + allocation.getCost().getValue());
        }
        
        return total;
    }
}
