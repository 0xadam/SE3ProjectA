package se3projecta.GUI;

import javax.swing.event.EventListenerList;
import se3projecta.Model.CustomerType;
import se3projecta.Model.SeatType;
import se3projecta.Money;

/**
 * Links a together a particular SeatType, CustomerType and numberOfTickets,
 * resulting in a particular cost.
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Allocation {

    private SeatType seatType;
    private CustomerType customerType;
    private int numberOfTickets;
    private Money cost = new Money(0);
    /**
     * A list of EventListeners linked to this allocation.
     */
    protected EventListenerList listeners = new EventListenerList();

    private void update() {
        if (seatType == null || customerType == null) {
            return;
        }

        double ticketCost = seatType.getPrice();
        double multiplier = customerType.getPriceMultiplier();

        cost = new Money(ticketCost * multiplier * numberOfTickets);
        
        fireChanged();
    }

    /**
     * Get the cost of this allocation.
     * @return the cost of this allocation
     */
    public Money getCost() {
        return cost;
    }

    /**
     * Set the SeatType of this allocation.
     * @param st the SeatType to set this allocation to
     */
    public void setSeatType(SeatType st) {
        seatType = st;
        update();
    }

    /**
     * get the SeatType of this allocation.
     * @return the SeatType of this allocation
     */
    public SeatType getSeatType() {
        return seatType;
    }

    /**
     * Set the CustomerType of this allocation.
     * @param ct the CustomerType to set this allocation to
     */
    public void setCustomerType(CustomerType ct) {
        customerType = ct;
        update();
    }

    /**
     * get the CustomerType of this allocation.
     * @return the CustomerType of this allocation
     */
    public CustomerType getCustomerType() {
        return customerType;
    }

    /**
     * Set the numberOfTickets of this allocation.
     * @param tickets the numberOfTickets to set this allocation to
     */
    public void setNumberOfTickets(int tickets) {
        numberOfTickets = tickets;
        update();
    }

    /**
     * get the numberOfTickets of this allocation.
     * @return the numberOfTickets of this allocation
     */
    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    /**
     * Add an AllocationListener to this Allocation.
     * @param l the AllocationListener to add to this Allocation
     */
    public void addAllocationListener(AllocationListener l) {
        listeners.add(AllocationListener.class, l);
    }

    /**
     * Remove an AllocationListener from this Allocation.
     * @param l the AllocationListener to remove from this Allocation
     */
    public void removeAllocationListener(AllocationListener l) {
        listeners.remove(AllocationListener.class, l);
    }

    /**
     * Get the AllocationListeners linked to this Allocation.
     * @return the AllocationListeners linked to this Allocation
     */
    public AllocationListener[] getAllocationListeners() {
        return listeners.getListeners(AllocationListener.class);
    }

    private void fireChanged() {
        for (AllocationListener al : listeners.getListeners(AllocationListener.class)) {
            al.changed();
        }
    }
}
