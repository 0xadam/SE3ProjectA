/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import java.awt.*;
import se3projecta.*;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class GUI extends javax.swing.JFrame {

    se3projecta.SE3ProjectA control;

    /**
     * Creates new form GUI
     */
    public GUI(se3projecta.SE3ProjectA control_) {
        control = control_;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        JMoviePanel moviePanel = new JMoviePanel(new BorderLayout());
        getContentPane().add(moviePanel);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack(); //automatically set the windowsize in relation to components placed
    }
}
