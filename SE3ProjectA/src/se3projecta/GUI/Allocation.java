package se3projecta.GUI;

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
    
    public Money getCost() {
        double ticketCost = seatType.getPrice();
        double multiplier = customerType.getPriceMultiplier();
        
        return new Money(ticketCost * multiplier * numberOfTickets);
    }
    
    public void setSeatType(SeatType st) {
        seatType = st;
    }
    public SeatType getSeatType() {
        return seatType;
    }
    
    
    public void setCustomerType(CustomerType ct) {
        customerType = ct;
    }
    public CustomerType getCustomerType() {
        return customerType;
    }
    
    public void setNumberOfTickets(int tickets) {
        numberOfTickets = tickets;
    }
    public int getNumberOfTickets() {
        return numberOfTickets;
    }
}
