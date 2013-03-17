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
    private Container contentPane;

    /**
     * Creates new form GUI
     */
    public GUI(Repository repository_) {
        repository = repository_;
        initComponents();
    }

    public void addTransactionPanel() {
        contentPane.add(transactionPanel, BorderLayout.LINE_END);
        if((this.getExtendedState() & MAXIMIZED_BOTH)!=MAXIMIZED_BOTH)
        {
            pack();
        }
    }

    public void removeMoviePanel() {
        remove(moviePanel);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        contentPane = getContentPane();
        // contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.setLayout(new BorderLayout());
        moviePanel = new JMoviePanel(this, repository);
        theatreSessionPanel = new JTheatreSessionPanel();
        transactionPanel = new JTransactionPanel(repository);
        contentPane.add(theatreSessionPanel, BorderLayout.CENTER);
        contentPane.add(moviePanel, BorderLayout.LINE_END);
        moviePanel.addTheatreSessionSubscriber(theatreSessionPanel);
        moviePanel.updateTheatreSessions();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack(); //automatically set the windowsize in relation to components placed
    }
}
