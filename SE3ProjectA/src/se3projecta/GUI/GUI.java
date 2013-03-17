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

    enum GUIState {

        SelectTheaterSession, SelectSeating, PlaceSeats
    }
    private GUIState state;
    private Repository repository;
    private JMoviePanel moviePanel;
    private JTheatreSessionPanel theatreSessionPanel;
    private JTransactionPanel transactionPanel;
    private JSeatSelectionInformationPanel seatSelectionInformationPanel;
    private Container contentPane;

    /**
     * Creates new form GUI
     */
    public GUI(Repository repository_) {
        repository = repository_;
        initComponents();
    }

    private void fixWindowSize() { //TODO Fix this ugly hack
        if ((getExtendedState() & MAXIMIZED_BOTH) != MAXIMIZED_BOTH) { //if window is not maximized
            int oldPaneWidth = contentPane.getWidth();
            int oldPaneHeight = contentPane.getHeight();
            setVisible(false);
            pack();
            int newPaneWidth = contentPane.getWidth();
            int newPaneHeight = contentPane.getHeight();
            setMinimumSize(new Dimension(newPaneWidth + getInsets().left + getInsets().right, newPaneHeight + getInsets().top + getInsets().bottom));
            setSize(new Dimension((newPaneWidth > oldPaneWidth ? newPaneWidth : oldPaneWidth) + getInsets().left + getInsets().right, (newPaneHeight > oldPaneHeight ? newPaneHeight : oldPaneHeight) + getInsets().top + getInsets().bottom));
            setVisible(true);
        }

    }

    public void setState(GUIState s) {
        state = s;
        switch (state) {
            case SelectTheaterSession:
                remove(transactionPanel);
                contentPane.add(moviePanel, BorderLayout.LINE_END);
                break;
            case SelectSeating:
                contentPane.add(transactionPanel, BorderLayout.LINE_END);
                remove(moviePanel);
                break;
            case PlaceSeats:
                contentPane.add(seatSelectionInformationPanel, BorderLayout.LINE_END);
                remove(transactionPanel);
                break;
        }
        fixWindowSize();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        contentPane = getContentPane();
        // contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.setLayout(new BorderLayout());
        moviePanel = new JMoviePanel(this, repository);
        transactionPanel = new JTransactionPanel(repository, this);
        seatSelectionInformationPanel = new JSeatSelectionInformationPanel(transactionPanel);
        theatreSessionPanel = new JTheatreSessionPanel();
        contentPane.add(theatreSessionPanel, BorderLayout.CENTER);
        contentPane.add(moviePanel, BorderLayout.LINE_END);
        moviePanel.addTheatreSessionSubscriber(theatreSessionPanel);
        moviePanel.updateTheatreSessions();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack(); //automatically set the windowsize in relation to components placed
        setMinimumSize(getSize()); //minimum size is packed size
    }
}
