/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Persistance;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Russell
 */
public interface XmlSerializable {

    public abstract Element save(Document dom);
}
