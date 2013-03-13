/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

/**
 *
 * @author Russell
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Money {
    private static final int decimalPlaces = 2;
    private static final String textPrefix = "$";
    private static final String textSuffix = "";
    
    private int value;
    
    public double getValue() {
        return value/Math.pow(10, decimalPlaces);
    }
    
    
    public @Override String toString() {
        return textPrefix + getValue() + textSuffix;
    }
}
