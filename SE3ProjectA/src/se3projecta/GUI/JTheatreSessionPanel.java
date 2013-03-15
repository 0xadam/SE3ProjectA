/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import se3projecta.Model.Seat;
import se3projecta.Model.TheatreSession;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JTheatreSessionPanel extends javax.swing.JPanel implements TheatreSessionSubscriber {

    private TheatreSession tSession = null;

    public JTheatreSessionPanel() {
    }

    public void setTheatreSession(TheatreSession tSession_) {
        tSession = tSession_;
        renderTheatreSession();
    }

    public void renderTheatreSession() {
        Seat[][] rows = tSession.getSeatRows();
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[0].length; j++) { //length is the same for all
                if (rows[i][j].getType() != null) {
                    System.out.print(rows[i][j].getType().getName());
                } else {
                    System.out.print("n");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void updateTheatreSession(TheatreSession ts) {
        tSession = ts;
        renderTheatreSession();
    }
}
