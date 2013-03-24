package se3projecta.GUI;

import java.util.*;
import javax.swing.event.EventListenerList;
import se3projecta.Model.*;
import se3projecta.Money;

/**
 * Stores information about the current transaction; particularly what
 * allocations of seats there are, which TheatreSession the transaction is for.
 *
 * Also contains many helper functions relating to the transaction; for example,
 * methods which clear placed seats or return the total price of the
 * transaction.
 * 
 * The Transaction class can be subscribed to by sub-classing
 * TransactionListener.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Transaction {

    private TheatreSession theatreSession;
    private ArrayList<Allocation> allocations = new ArrayList<Allocation>();
    private Money cost;
    /**
     * A list of EventListeners linked to this allocation.
     */
    protected EventListenerList listeners = new EventListenerList();

    private void UpdateCost() {
        Money total = new Money(0);

        for (Allocation allocation : allocations) {
            total = new Money(total.getValue() + allocation.getCost().getValue());
        }

        cost = total;

        fireAllocationsChanged();
    }

    /**
     * Get the number of allocated Seats for each SeatType.
     *
     * @return the number of allocated Seats for each SeatType.
     */
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

    /**
     * Counts how many more seats for this allocation need to be placed in a map
     * indexed by SeatType.
     *
     * @return how many more seats for this allocation need to be placed in a
     * map indexed by SeatType
     */
    public Map<SeatType, Integer> countUnplacedBySeatTypes() {
        // this could be made more efficient by keeping a cache in TheatreSession
        Map<SeatType, Integer> unplaced = countAllocatedBySeatType();

        Iterator it = countAllocatedBySeatType().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<SeatType, Integer> pairs = (Map.Entry) it.next();

            unplaced.put(pairs.getKey(), pairs.getValue() - theatreSession.countHeld((SeatType) pairs.getKey()));

            it.remove();
        }

        return unplaced;
    }

    private int sumSeatTypeMap(Map<SeatType, Integer> map) {
        int sum = 0;

        for (int value : map.values()) {
            sum += value;
        }

        return sum;
    }

    /**
     * Check whether all allocated seats have been placed.
     *
     * @return true if all allocated seats have been placed else false.
     */
    public boolean placedAll() {
        return sumSeatTypeMap(countUnplacedBySeatTypes()) == 0;
    }

    /**
     * Check whether any allocated seats have been placed.
     *
     * @return true if any allocated seats have been placed else false.
     */
    public boolean placedAny() {
        for (Seat seat : theatreSession.getSeats()) {
            if (seat.getState() == SeatState.Held) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the number of unplaced seats for the specified SeatType.
     *
     * @param type the SeatType to get the number of unplaced seats for
     * @return the number of unplaced seats for the specified SeatType
     */
    public int countUnplaced(SeatType type) {
        Map<SeatType, Integer> unplacedSeatTypes = countUnplacedBySeatTypes();

        if (unplacedSeatTypes.get(type) != null) {
            return unplacedSeatTypes.get(type);
        }

        return 0;
    }

    /**
     * Get the TheatreSession linked to this Transaction.
     *
     * @return the TheatreSession linked to this Transaction
     */
    public TheatreSession getTheatreSession() {
        return theatreSession;
    }

    /**
     * Set the TheatreSession linked to this Transaction.
     *
     * @param ts TheatreSession to link to this Transaction
     */
    public void setTheatreSession(TheatreSession ts) {
        if (ts != theatreSession) {
            theatreSession = ts;
            fireTheatreSessionChanged();
        }
    }

    /**
     * Get the cost of this Transaction.
     *
     * @return the cost of this Transaction
     */
    public Money getCost() {
        return cost;
    }

    /**
     * Add another allocation to this Transaction.
     *
     * @param allocation to add to this Transaction.
     */
    public void addAllocation(Allocation allocation) {
        allocation.addAllocationListener(new AllocationListener() {
            @Override
            void changed() {
                UpdateCost();
            }
        });

        allocations.add(allocation);
        UpdateCost();
    }

    /**
     * Set state of specified Seat to Held if it is Empty.
     *
     * @param seat seat to Hold.
     * @throws IllegalArgumentException
     */
    public void holdSeat(Seat seat) throws IllegalArgumentException {
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

    /**
     * Set state of specified Seat to Empty if it is Held.
     *
     * @param seat seat to Release.
     * @throws IllegalArgumentException
     */
    public void releaseSeat(Seat seat) throws IllegalArgumentException {
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

    /**
     * Randomly allocate all unplaced Seats.
     */
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

    /**
     * Clear all held seats.
     */
    public void clearHeld() {
        theatreSession.clearHeld();
        fireSeatingChanged(theatreSession.getSeats());
    }

    /**
     * Remove the specified Allocation from this Transaction.
     *
     * @param allocation the Allocation to remove from this Transaction
     */
    public void removeAllocation(Allocation allocation) {
        allocations.remove(allocation);
        UpdateCost();
    }

    /**
     * Add the specified TransactionListener to this Transaction
     *
     * @param l the TransactionListener to add to this Transaction
     */
    public void addTransactionListener(TransactionListener l) {
        listeners.add(TransactionListener.class, l);
    }

    /**
     * Remove the specified TransactionListener from this Transaction.
     *
     * @param l the TransactionListener to remove from this Transaction
     */
    public void removeTransactionListener(TransactionListener l) {
        listeners.remove(TransactionListener.class, l);
    }

    /**
     * Get all TransactionListeners linked to this Transaction.
     *
     * @return all TransactionListeners linked to this Transaction
     */
    public TransactionListener[] getTransactionListeners() {
        return listeners.getListeners(TransactionListener.class);
    }

    private void fireTheatreSessionChanged() {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.theatreSessionChanged(theatreSession);
        }
    }

    private void fireAllocationsChanged() {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.allocationsChanged();
        }
    }

    /**
     * Fire a seating changed event (single seat) to all event listeners
     *
     * @param seat seat to fire a seating changed event for to all listeners
     */
    private void fireSeatingChanged(Seat seat) {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.seatingChanged(seat);
        }
    }

    /**
     * Fire a seating changed event (many seats) to all event listeners
     *
     * @param seats seats to fire a seating changed event for to all listeners
     */
    private void fireSeatingChanged(Seat[] seats) {
        for (TransactionListener tl : listeners.getListeners(TransactionListener.class)) {
            tl.seatingChanged(seats);
        }
    }
}
