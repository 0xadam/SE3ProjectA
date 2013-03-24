package se3projecta.GUI;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import se3projecta.Model.*;
import java.util.Collection;
import java.awt.event.*;
import se3projecta.*;

/**
 * JPanel which holds widgets that allow selection of Movie, Theatre, and
 * SessionTime
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JMoviePanel extends javax.swing.JPanel {

    private GUI gui;
    private Repository repository;
    private JLabel promoImage = new JLabel();
    private JPanel dropdownPanel = new JPanel();
    private JComboBox theatreDropdown = new JComboBox();
    private JComboBox movieDropdown = new JComboBox();
    private JComboBox sessionTimeDropdown = new JComboBox();
    private JButton bookTicketsButton = new JButton();
    private Transaction transaction;

    /**
     *
     * @param repository_ where to get widget values from
     * @param gui_ the GUI object which navigation changes the state of
     * @param transaction_ transaction which is modified as booking details are
     * changed
     */
    public JMoviePanel(Repository repository_, GUI gui_, Transaction transaction_) {
        gui = gui_;
        repository = repository_;
        transaction = transaction_;

        //set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(promoImage);
        promoImage.setPreferredSize(new Dimension(100, 150)); //TODO don't hardcode these two lines
        promoImage.setMaximumSize(new Dimension(100, 150));
        promoImage.setAlignmentX(JPanel.CENTER_ALIGNMENT);


        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        dropdownPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        //create items
        //add movies to combobox
        for (Movie movie : repository.getMovies()) {
            movieDropdown.addItem(movie);
        }

        dropdownPanel.add(movieDropdown);


        //add sessionTimes to combobox
        for (SessionTime session : repository.getSessionTimes()) {
            sessionTimeDropdown.addItem(session);
        }
        dropdownPanel.add(sessionTimeDropdown);


        dropdownPanel.add(theatreDropdown);


        bookTicketsButton.setText("Select Tickets");
        bookTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setState(GUI.GUIState.SelectSeating);
            }
        });
        dropdownPanel.add(bookTicketsButton);


        add(dropdownPanel);
        dropdownPanel.setMaximumSize(new Dimension(184, 110)); //TODO don't hardcode?
        dropdownPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);


        movieDropdown.addActionListener(new JMoviePanelAL());
        movieDropdown.setSelectedIndex(0);
        sessionTimeDropdown.addActionListener(new JMoviePanelAL());
        theatreDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (theatreDropdown.getSelectedItem() != null && e.getActionCommand().equalsIgnoreCase("comboboxchanged")) {
                    transaction.setTheatreSession((TheatreSession) theatreDropdown.getSelectedItem());
                    gui.fixWindowSize();
                }
            }
        });

    }

    /**
     * Action Listener listening for changes to the selected move - updates
     * promo image and TheatreSessions.
     */
    public class JMoviePanelAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == movieDropdown) {
                promoImage.setIcon(loadPromoImage(((Movie) movieDropdown.getSelectedItem()).getPromotionalImage()));
            }
            updateTheatreSessions();
        }
    }

    /**
     * Reload TheaterDropdown from repository using selected movie and
     * sessionTime
     */
    public void updateTheatreSessions() {
        theatreDropdown.removeAllItems(); //clear the panel
        Collection<TheatreSession> theatreSessions = repository.getTheatreSessions((Movie) movieDropdown.getSelectedItem(), (SessionTime) sessionTimeDropdown.getSelectedItem());
        for (TheatreSession theatreSession : theatreSessions) {
            theatreDropdown.addItem(theatreSession);
        }
        theatreDropdown.setSelectedIndex(0);
    }

    private ImageIcon loadPromoImage(String promoImageURI) {
        ImageIcon lpromoImage = new ImageIcon();
        File promoImageFile = new File(promoImageURI);
        if (!promoImageFile.exists()) {
            promoImageFile = new File("data\\noimage.jpg");
        }
        try {
            lpromoImage.setImage(ImageIO.read(promoImageFile).getScaledInstance(100, 150, Image.SCALE_DEFAULT));// TODO unhardcode this. and it's sloooow
        } catch (IOException e) { //TODO Show actual exception
            System.out.println("Unable to load image.");
        } finally {
            return lpromoImage;
        }
    }
}
