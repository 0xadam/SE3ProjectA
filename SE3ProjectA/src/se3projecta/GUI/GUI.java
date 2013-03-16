/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import java.awt.*;
import javax.swing.BoxLayout;
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

    public void setTransactionPanelVisibility(boolean visibility) {
        transactionPanel.setVisible(visibility);
        pack();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        moviePanel = new JMoviePanel(this, repository);
        theatreSessionPanel = new JTheatreSessionPanel();
        transactionPanel = new JTransactionPanel(repository);
        transactionPanel.setVisible(false);
        contentPane.add(theatreSessionPanel);
        contentPane.add(moviePanel);
        contentPane.add(transactionPanel);
        moviePanel.addTheatreSessionSubscriber(theatreSessionPanel);
        moviePanel.updateTheatreSessions();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack(); //automatically set the windowsize in relation to components placed
    }
}
