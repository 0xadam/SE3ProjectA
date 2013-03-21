/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Persistance;

import org.w3c.dom.Element;

/**
 * An Object which can be Unserialized from an XML file.
 * @param <K> the datatype of the object ID
 * 
 * @author Russell
 */
public interface XmlUnserializable<K> {

    /**
     * loads object data from XML
     *
     * @param n an XML node which contains object data
     */
    public abstract void load(Element n);

    /**
     * Get the ID of this Object. ID used in unserialization as the key in the
     * resultant map.
     * @return
     */
    public abstract K getId();
    //public abstract Integer getIntId();
}
