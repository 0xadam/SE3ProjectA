package se3projecta.GUI;

import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import se3projecta.Persistence.ImportException;
import se3projecta.Repository;

/**
 * Main GUI Frame which allows the entire booking process to take place.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class GUI extends javax.swing.JFrame {

    /**
     * The states the GUI can be in.
     */
    public enum GUIState {

        /**
         * SelectTheatreSession State allows users to select which movie they
         * want to see, which Theatre they want to see it at, and which session
         * the want to see it in.
         */
        SelectTheaterSession,
        /**
         * SelectSeating State allows users to choose how many seats they want
         * of each SeatType and CustomerType.
         */
        SelectSeating,
        /**
         * PlaceSeats State allows users to select which seats the want to sit
         * in, place the seats randomly or use a combination of the two to
         * select seating.
         */
        PlaceSeats
    }
    private GUIState state = GUIState.SelectTheaterSession;
    private Repository repository;
    private JMoviePanel moviePanel;
    private JTheatreSessionPanel theatreSessionPanel;
    private JTransactionPanel transactionPanel;
    private JSeatSelectionPanel seatSelectionPanel;
    private Container contentPane;
    private JScrollPane transactionHolder;
    private Transaction transaction;
    private static final long serialVersionUID = 0; //remove warning in netbeans build process

    /**
     * Creates new GUI form
     *
     * @param repository_ where to get widget values from
     * @param ie import exceptions which may possibly have occurred else null
     */
    public GUI(Repository repository_, ImportException ie) {
        setTitle("Movie Booking System");
        if (ie != null) {
            Object[] options = {"Yes", "No"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Erorrs occured while loading data, "
                    + "are you sure you wish to continue?",
                    "An error occured",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);

            if (choice == JOptionPane.NO_OPTION) {
                System.exit(1);
            }
        }
        repository = repository_;
        transaction = new Transaction();

        initComponents();
    }

    /**
     * Set the size of the GUI to fit all of its contents. If optimal size is
     * smaller, or the window is maximized it does not change size
     *
     */
    public void getOptimalWindowSize() {
        if ((getExtendedState() & MAXIMIZED_BOTH) != MAXIMIZED_BOTH) { //if window is not maximized
            int oldPaneWidth = contentPane.getWidth() + getInsets().left + getInsets().right;
            int oldPaneHeight = contentPane.getHeight() + getInsets().top + getInsets().bottom;
            setMinimumSize(new Dimension(0, 0)); //allow minimum so pack works when going from bigger window to smaller one
            pack();
            int newPaneWidth = contentPane.getWidth() + getInsets().left + getInsets().right;//width is width of pane + the border size
            int newPaneHeight = contentPane.getHeight() + getInsets().top + getInsets().bottom; //height is height of pane + the border size
            setMinimumSize(new Dimension(newPaneWidth, newPaneHeight));
            setSize(new Dimension((newPaneWidth > oldPaneWidth ? newPaneWidth : oldPaneWidth), (newPaneHeight > oldPaneHeight ? newPaneHeight : oldPaneHeight)));
            setVisible(true);
        }

    }

    /**
     * Set the GUIState of this GUI.
     *
     * @param s the GUIState to set the GUI to.
     */
    public void setState(GUIState s) {
        GUIState oldState = state;
        GUIState newState = s;

        switch (oldState) {
            case SelectTheaterSession:
                remove(moviePanel);
                break;
            case SelectSeating:
                remove(transactionHolder);
                break;
            case PlaceSeats:
                remove(seatSelectionPanel);
                transaction.clearHeld();
                theatreSessionPanel.refreshSeatIcons();
                theatreSessionPanel.disableSelection();
                break;
        }

        switch (newState) {
            case SelectTheaterSession:
                contentPane.add(moviePanel, BorderLayout.LINE_END);
                transactionPanel.clearAllocations();
                break;
            case SelectSeating:
                contentPane.add(transactionHolder, BorderLayout.LINE_END);
                break;
            case PlaceSeats:
                seatSelectionPanel.setAllSeatSubPanelsVisible(false);
                seatSelectionPanel.updateAllSeatsRemaining();
                contentPane.add(seatSelectionPanel, BorderLayout.LINE_END);
                theatreSessionPanel.enableSelection();
                break;
        }

        state = newState;

        getOptimalWindowSize();
        contentPane.validate();
        contentPane.repaint();
    }

    private void initComponents() {
        final JFrame gui = this;
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean success = true;
                try {
                    repository.save();
                } catch (TransformerConfigurationException ex) { //TODO implement errors here
                    success = false;
                } catch (ParserConfigurationException ex) {
                    success = false;
                } catch (TransformerException ex) {
                    success = false;
                }
                if (!success) {
                    Object[] options = {"Abort", "Exit"};

                    int choice = JOptionPane.showOptionDialog(gui,
                            "There was an error passing the data structure to XML when saving, please try again. \n"
                            + "If errors persist after closing and reopening the application, please contact technical support.",
                            "Error on save",
                            JOptionPane.OK_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            options,
                            options[0]);
                    if (choice == 0) //option 1 = abort {
                    {
                        return;
                    }

                }

                gui.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            }
        });

        contentPane = getContentPane();

        contentPane.setLayout(
                new BorderLayout());

        moviePanel = new JMoviePanel(repository, this, transaction);
        transactionPanel = new JTransactionPanel(repository, this, transaction);
        transactionHolder = new JScrollPane(transactionPanel);

        transactionHolder.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        seatSelectionPanel = new JSeatSelectionPanel(repository, transaction, this);
        theatreSessionPanel = new JTheatreSessionPanel(transaction);

        contentPane.add(theatreSessionPanel, BorderLayout.CENTER);

        contentPane.add(moviePanel, BorderLayout.LINE_END);

        moviePanel.updateTheatreSessions();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        pack();
    }
}
