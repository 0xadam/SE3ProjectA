/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import java.awt.*;
import javax.swing.JScrollPane;
import se3projecta.*;
import se3projecta.Persistance.ImportException;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class GUI extends javax.swing.JFrame {

    public enum GUIState {

        SelectTheaterSession,
        SelectSeating,
        PlaceSeats
    }
    private GUIState state = GUIState.SelectTheaterSession;
    private Repository repository;
    private JMoviePanel moviePanel;
    private JTheatreSessionPanel theatreSessionPanel;
    private JTransactionPanel transactionPanel;
    private JSeatSelectionInformationPanel seatSelectionInformationPanel;
    private Container contentPane;
    private JScrollPane transactionHolder;
    private Transaction transaction;

    /**
     * Creates new form GUI
     */
    public GUI(Repository repository_, ImportException ie) {
        if (ie != null) {
            //todo handle import errors.
        }
        repository = repository_;
        transaction = new Transaction();

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
            //setMinimumSize(new Dimension(newPaneWidth + getInsets().left + getInsets().right, newPaneHeight + getInsets().top + getInsets().bottom));
            setSize(new Dimension((newPaneWidth > oldPaneWidth ? newPaneWidth : oldPaneWidth) + getInsets().left + getInsets().right, (newPaneHeight > oldPaneHeight ? newPaneHeight : oldPaneHeight) + getInsets().top + getInsets().bottom));
            setVisible(true);
        }

    }

    public void setState(GUIState s) {
        state = s;
        switch (state) {
            case SelectTheaterSession:
                remove(seatSelectionInformationPanel);
                remove(transactionHolder);
                contentPane.add(moviePanel, BorderLayout.LINE_END);
                //todo remove all allocations.
                transactionPanel.clearAllocations();
                break;
            case SelectSeating:
                contentPane.add(transactionHolder, BorderLayout.LINE_END);
                remove(moviePanel);
                remove(seatSelectionInformationPanel);
                break;
            case PlaceSeats:
                contentPane.add(seatSelectionInformationPanel, BorderLayout.LINE_END);
                remove(transactionHolder);
                break;
        }

        fixWindowSize();
        contentPane.validate();
        contentPane.repaint();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        moviePanel = new JMoviePanel(repository, this, transaction);

        transactionPanel = new JTransactionPanel(repository, this, transaction);
        transactionHolder = new JScrollPane(transactionPanel);

        seatSelectionInformationPanel = new JSeatSelectionInformationPanel(repository, transaction);

        theatreSessionPanel = new JTheatreSessionPanel(transaction);

        contentPane.add(theatreSessionPanel, BorderLayout.CENTER);
        contentPane.add(moviePanel, BorderLayout.LINE_END);

        moviePanel.updateTheatreSessions();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack(); //automatically set the windowsize in relation to components placed
        //setMinimumSize(getSize()); //minimum size is packed size
    }
}
