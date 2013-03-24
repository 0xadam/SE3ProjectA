package se3projecta.Model;

import se3projecta.Persistence.XmlUnserializable;
import org.w3c.dom.Element;

/**
 * Allows different types of customers to book seats for different prices. The
 * price for the seat is determined by the price of the SeatType and the
 * priceMultiplier of the CustomerType.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class CustomerType implements XmlUnserializable<Integer> {

    private int id;
    private String name;
    private double priceMultiplier;

    /**
     * Loads customer type data from XML
     *
     * @param n an XML node which contains customer data
     */
    @Override
    public void load(Element n) {
        id = Integer.parseInt(n.getAttribute("id"));
        name = n.getAttribute("Name");
        priceMultiplier = Double.parseDouble(n.getAttribute("pricemultiplier"));
    }

    /**
     * Returns a string representation of the customer type
     *
     * @return a string representation of the customer type
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets the customer type ID
     *
     * @return the customer type ID
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Get the priceMultiplier (the amount the SeatType price will be multiplied
     * to get the price of the Seat) for this CustomerType
     *
     * @return the priceMultiplier for this CustomerType
     */
    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}
