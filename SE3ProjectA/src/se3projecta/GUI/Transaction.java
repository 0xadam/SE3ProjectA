package se3projecta.GUI;

import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import se3projecta.Model.SeatType;
import se3projecta.Model.TheatreSession;
import se3projecta.Money;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import se3projecta.Model.Seat;
import se3projecta.Model.SeatState;

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

    public Map<SeatType, Integer> countAllocatedBySeatType() {
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

    public Map<SeatType, Integer> countUnplacedBySeatTypes() {
        Map<SeatType, Integer> allocated = countAllocatedBySeatType();

        Iterator it = countAllocatedBySeatType().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<SeatType, Integer> pairs = (Map.Entry) it.next();

            allocated.put(pairs.getKey(), pairs.getValue() - theatreSession.countHeld((SeatType) pairs.getKey()));

            it.remove();
        }


        return allocated;
    }

    public int countUnplaced() {
        Map<SeatType, Integer> unplacedSeatTypes = this.countUnplacedBySeatTypes();

        int sum = 0;

        for (int value : unplacedSeatTypes.values()) {
            sum += value;
        }

        return sum;
    }

    public int countUnplaced(SeatType type) {
        // this could be made more efficient by keeping a cache in TheatreSession
        Map<SeatType, Integer> unplacedSeatTypes = this.countUnplacedBySeatTypes();

        if (unplacedSeatTypes.get(type) != null) {
            return unplacedSeatTypes.get(type);
        }

        return 0;
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

    public void addAllocation(Allocation allocation) {
        allocation.addAllocationListener(new AllocationListener() {
            @Override
            void costChanged(Money cost) {
                UpdateCost();
            }
        });

        allocations.add(allocation);
        UpdateCost();
    }

    public void holdSeat(Seat seat) {
        if (!theatreSession.ownsSeat(seat)) {
            throw new IllegalArgumentException("Specified seat does not belong to active theatre session!");
        }

        if (seat.getState() == SeatState.Empty) {
            if (countUnplaced(seat.getType()) > 0) {
                seat.setState(SeatState.Held);
                fireSeatingChanged(seat);
            }
        } else {
            throw new IllegalArgumentException("Seat is already held!");
        }
    }

    public void releaseSeat(Seat seat) {
        if (!theatreSession.ownsSeat(seat)) {
            throw new IllegalArgumentException("Specified seat does not belong to active theatre session!");
        }

        if (seat.getState() == SeatState.Held) {
            seat.setState(SeatState.Empty);
            fireSeatingChanged(seat);
        } else {
            throw new IllegalArgumentException("Seat is not held!");
        }
    }

    public void randomlyAllocate() {
        Map<SeatType, Integer> unplaced = countUnplacedBySeatTypes();

        Iterator it = unplaced.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<SeatType, Integer> pairs = (Map.Entry) it.next();

            Seat[] allocation = theatreSession.placeRandom(pairs.getKey(), pairs.getValue());
            fireSeatingChanged(allocation);

            it.remove();
        }

    }
    
    public void clearHeld() {
        theatreSession.clearHeld();
        fireSeatingChanged(theatreSession.getSeats());
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

    /**
     * Fire a seating changed event (single seat) to all event listeners
     *
     * @param seat
     */
    private void fireSeatingChanged(Seat seat) {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.seatingChanged(seat);
        }
    }

    /**
     * Fire a seating changed event (many seats) to all event listeners
     *
     * @param seats
     */
    private void fireSeatingChanged(Seat[] seats) {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.seatingChanged(seats);
        }
    }
}
