/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

/**
 * Exception class for exception thrown when an element is inserted into a 
 * collection that already contains the key that is the same as that of the new 
 * element.
 *
 * @author Russell
 */
public class ExistingKeyException extends Exception {

    public ExistingKeyException() {
        super();
    }

    public ExistingKeyException(String s) {
        super(s);
    }
}
