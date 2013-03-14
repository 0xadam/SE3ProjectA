/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Persistance;
import org.w3c.dom.Element;
/**
 *
 * @author Russell
 */
public interface XmlUnserializable<K> {
    public abstract void load(Element n);
    public abstract K getId();
    //public abstract Integer getIntId();
}
