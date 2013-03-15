/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import se3projecta.Model.TheatreSession;

/**
 *
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public interface TheatreSessionSubscriber {
    public void updateTheatreSession(TheatreSession ts);
}
