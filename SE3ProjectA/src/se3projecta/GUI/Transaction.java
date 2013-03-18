package se3projecta.GUI;

import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import se3projecta.Model.SeatType;
import se3projecta.Model.TheatreSession;
import se3projecta.Money;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 *
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Transaction {

    private TheatreSession theatreSession;
    private ArrayList<Allocation> allocations = new ArrayList<Allocation>();
    private Money cost;
    protected EventListenerList listeners = new EventListenerList();

    private void UpdateCost() {
        Money total = new Money(0);

        for (Allocation allocation : allocations) {
            total = new Money(total.getValue() + allocation.getCost().getValue());
        }

        if (total != cost) {
            cost = total;
            fireCostChanged();
        }
    }

    public boolean isValidRequest() {
        Iterator it = countAllocatedSeatTypes().entrySet().iterator();
        int totaltickets = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            
            if (!theatreSession.hasAvailable((SeatType)pairs.getKey(), (Integer)pairs.getValue())) {
                return false;
            }
            totaltickets += (Integer)pairs.getValue();
            it.remove();
        }
        
        if (totaltickets > 0) {
            return true;
        }
        return false;
    }

    public HashMap<SeatType, Integer> countAllocatedSeatTypes() {
        HashMap<SeatType, Integer> counts = new HashMap<SeatType, Integer>();

        for (Allocation a : allocations) {
            SeatType type = a.getSeatType();

            if (counts.containsKey(type)) {
                counts.put(type, a.getNumberOfTickets() + counts.get(type));
            } else {
                counts.put(type, a.getNumberOfTickets());
            }
        }

        return counts;
    }

    public TheatreSession getTheatreSession() {
        return theatreSession;
    }

    public void setTheatreSession(TheatreSession ts) {
        if (ts != theatreSession) {
            theatreSession = ts;
            fireTheatreSessionChanged();
        }
    }

    public Money getCost() {
        return cost;
    }

    public Allocation[] getAllocations() {
        Allocation[] a = new Allocation[allocations.size()];
        allocations.toArray(a);
        return a;
    }

    public void addAllocation(Allocation allocation) {
        allocation.addAllocationListener(new AllocationListener() {
            void costChanged(Money cost) {
                UpdateCost();
            }
        });

        allocations.add(allocation);
        UpdateCost();
    }

    public void removeAllocation(Allocation allocation) {
        allocations.remove(allocation);
        UpdateCost();
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

    private void fireTheatreSessionChanged() {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.theatreSessionChanged(this.theatreSession);
        }
    }

    private void fireCostChanged() {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.costChanged(getCost());
        }
    }
}
