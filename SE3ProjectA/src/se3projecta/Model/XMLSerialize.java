/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;
import org.w3c.dom.Element;
/**
 *
 * @author Russell
 */
public interface XMLSerialize {
    public abstract void load(Element n);
    public abstract Character getCharId();
    public abstract Integer getIntId();
}
