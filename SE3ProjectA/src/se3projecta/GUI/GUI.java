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

    private Repository repository;
    private JMoviePanel moviePanel;
    private JTheatreSessionPanel theatreSessionPanel;
    private JTransactionPanel transactionPanel;

    /**
     * Creates new form GUI
     */
    public GUI(Repository repository_) {
        repository = repository_;
        initComponents();
    }

    protected void setAllocationPanelTheatreSession(se3projecta.Model.TheatreSession tSession) {
        theatreSessionPanel.setTheatreSession(tSession);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        theatreSessionPanel = new JTheatreSessionPanel();
        moviePanel = new JMoviePanel(repository);
        getContentPane().add(moviePanel);
        getContentPane().add(theatreSessionPanel);
        moviePanel.updateTheatreSessions();
        
        moviePanel.addTheatreSessionSubscriber(theatreSessionPanel);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack(); //automatically set the windowsize in relation to components placed
    }
}
