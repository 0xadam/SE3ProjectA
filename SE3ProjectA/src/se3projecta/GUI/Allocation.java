package se3projecta.GUI;

import javax.swing.event.EventListenerList;
import se3projecta.Model.CustomerType;
import se3projecta.Model.SeatType;
import se3projecta.Money;

/**
 *
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Allocation {

    private SeatType seatType;
    private CustomerType customerType;
    private int numberOfTickets;
    private Money cost = new Money(0);
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

    public Money getCost() {
        return cost;
    }

    public void setSeatType(SeatType st) {
        seatType = st;
        update();
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setCustomerType(CustomerType ct) {
        customerType = ct;
        update();
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setNumberOfTickets(int tickets) {
        numberOfTickets = tickets;
        update();
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void addAllocationListener(AllocationListener l) {
        listeners.add(AllocationListener.class, l);
    }

    public void removeAllocationListener(AllocationListener l) {
        listeners.remove(AllocationListener.class, l);
    }

    public AllocationListener[] getAllocationListeners() {
        return listeners.getListeners(AllocationListener.class);
    }

    private void fireChanged() {
        for (AllocationListener al : listeners.getListeners(AllocationListener.class)) {
            al.changed();
        }
    }
}
