package se3projecta.Model;

/**
 * Exception class for exception thrown when a Model class tries to load a
 * non-existent relation
 * 
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class InvalidRelationException extends Exception{
    public InvalidRelationException(String message) {
        super(message);
    }
}
