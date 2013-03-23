/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Represents money (Dollars and 2 decimal places for cents).
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
     * Create a Money object with the specified value.
     * @param v initial value for new Money object.
     */
    public Money(double v) {
        setValue(v);
    }

    /**
     * Create a Money object with the specified value.
     * @param v initial value for new Money object.
     */
    public Money(int v) {
        setValue(v);
    }

    private void setValue(double v) {
        value = (int) (v * Math.pow(10, currencyFormatter.getMaximumFractionDigits()));
    }

    /**
     * Get the value of this Money object.
     * @return the value of this Money object
     */
    public double getValue() {
        return value / Math.pow(10, currencyFormatter.getMaximumFractionDigits());
    }

    public @Override
    String toString() {
        return currencyFormatter.format(getValue());
    }
}
