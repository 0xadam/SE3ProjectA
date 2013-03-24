package se3projecta;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Represents money in the system
 * 
 * Not only is this used for formatting currency, but it also means that
 * money is rounded to the lowest unit of currency (e.g. cents) before
 * mathematical operations are performed. For example, $1.255 and $1.255 would
 * each individually be displayed as $1.26 and $1.26 but would display as only
 * $2.51 when added together.
 * 
 * This class also helps to enforce stronger typing (as "Money" is unambiguous,
 * unlike int or double, which may represent other things)
 *
 * In future, this class should be modified to support operations between two
 * Money instances.
 * 
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Money {

    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    private int value;

    /**
     * Create a Money object with the specified double value.
     *
     * @param v initial value for new Money object.
     */
    public Money(double v) {
        setValue(v);
    }

    /**
     * Create a Money object with the specified integer value.
     *
     * @param v initial value for new Money object.
     */
    public Money(int v) {
        setValue(v);
    }

    /**
     * Set the value of this Money object.
     *
     * @param v the new value for this Money object.
     */
    private void setValue(double v) {
        value = (int) (v * Math.pow(10, currencyFormatter.getMaximumFractionDigits()));
    }

    /**
     * Get the value of this Money object.
     *
     * @return the value of this Money object
     */
    public double getValue() {
        return value / Math.pow(10, currencyFormatter.getMaximumFractionDigits());
    }

    /**
     * returns a string representation of the Money object
     *
     * @return a string representation of the Money Object
     */
    @Override
    public String toString() {
        return currencyFormatter.format(getValue());
    }
}
