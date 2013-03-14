/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Money {
    private static final int decimalPlaces = 2;
    private static final String textPrefix = "$";
    private static final String textSuffix = "";
    
    private int value;

    public void setValue(double v) {
        value = (int)(v * Math.pow(10, decimalPlaces));
    }
    
    public void setValue(int v) {
        value = v;
    }
    
    public double getValue() {
        return value/Math.pow(10, decimalPlaces);
    }
    
    
    public @Override String toString() {
        return textPrefix + getValue() + textSuffix;
    }
}
