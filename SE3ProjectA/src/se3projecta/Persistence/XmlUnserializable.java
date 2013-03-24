package se3projecta.Persistence;

import org.w3c.dom.Element;

/**
 * An Object which can be Unserialized from an XML file.
 *
 * @param <K> the datatype of the object ID
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public interface XmlUnserializable<K> {

    /**
     * Loads object data from XML
     *
     * @param n an XML node which contains object data
     */
    public abstract void load(Element n);

    /**
     * Gets the ID of this Object. ID used in unserialization as the key in the
     * resultant map.
     *
     * @return K the primary key of the element
     */
    public abstract K getId();
}
