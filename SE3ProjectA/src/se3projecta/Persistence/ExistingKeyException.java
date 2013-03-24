package se3projecta.Persistence;

/**
 * Exception class for exception thrown when an element is inserted into a
 * collection that already contains the key that is the same as that of the new
 * element.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class ExistingKeyException extends Exception {

    private static final long serialVersionUID = 0; //remove warning in netbeans build process

    public ExistingKeyException() {
        super();
    }

    public ExistingKeyException(String s) {
        super(s);
    }
}
