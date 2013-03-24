package se3projecta.Persistence;

import org.w3c.dom.*;

/**
 * An object which can be serialized into an XML file.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public interface XmlSerializable {

    /**
     * Creates and returns a XML element, which stores object details, including
     * seat state
     *
     * @param dom a XML document used to create the element
     * @return a XML element, which stores object details
     */
    public abstract Element save(Document dom);
}
