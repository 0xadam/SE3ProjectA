/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Persistence;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An object which can be serialized into an XML file.
 * 
 * @author Russell
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
