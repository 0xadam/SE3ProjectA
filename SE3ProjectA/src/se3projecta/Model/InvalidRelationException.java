package se3projecta.Model;

/**
 * Exception class for exception thrown when a Model class tries to load a
 * non-existent relation
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class InvalidRelationException extends Exception {

    public InvalidRelationException(String message) {
        super(message);
    }
}
