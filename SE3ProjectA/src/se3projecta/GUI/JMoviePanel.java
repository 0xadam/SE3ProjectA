/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import se3projecta.Model.*;
import java.util.Collection;
import java.awt.event.*;
import java.util.ArrayList;
import se3projecta.*;

/**
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
    ArrayList<TheatreSessionSubscriber> theatreSessionSubscribers;

    public void addTheatreSessionSubscriber(TheatreSessionSubscriber subscriber) {
        theatreSessionSubscribers.add(subscriber);
    }

    public void notifyTheatreSessionSubscribers(TheatreSession ts) {
        for (TheatreSessionSubscriber tss : theatreSessionSubscribers) {
            tss.updateTheatreSession(ts);
        }
    }

    public JMoviePanel(GUI gui_, Repository repository_) {
        gui = gui_;
        repository = repository_;
        theatreSessionSubscribers = new ArrayList<TheatreSessionSubscriber>();

        //set layout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        //create items
        //add movies to combobox
        for (Movie movie : repository.getMovies()) {
            movieDropdown.addItem(movie);
        }
        //add sessionTimes to combobox
        for (SessionTime session : repository.getSessionTimes()) {
            sessionTimeDropdown.addItem(session);
        }
        movieDropdown.addActionListener(new JMoviePanelAL());
        sessionTimeDropdown.addActionListener(new JMoviePanelAL());
        theatreDropdown.addActionListener(new JMoviePanelAL());
        promoImage.setPreferredSize(new Dimension(100, 150)); //TODO don't hardcode these two lines
        promoImage.setMaximumSize(new Dimension(100, 150));
        bookTicketsButton.setText("Select Tickets");
        bookTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                ((JPanel) button.getParent().getParent()).setVisible(false); //hides the JMoviePanel (TODO make less hacky)
                gui.removeMoviePanel();
                gui.addTransactionPanel();
            }
        });
        dropdownPanel.add(movieDropdown);
        dropdownPanel.add(sessionTimeDropdown);
        dropdownPanel.add(theatreDropdown);
        dropdownPanel.add(bookTicketsButton);
        add(dropdownPanel);
        add(promoImage);
        dropdownPanel.setMaximumSize(new Dimension(184, 110)); //TODO don't hardcode?
        dropdownPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        promoImage.setAlignmentY(JPanel.TOP_ALIGNMENT);
    }

    public class JMoviePanelAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == movieDropdown) {
                promoImage.setIcon(loadPromoImage(((Movie) movieDropdown.getSelectedItem()).getPromotionalImage()));
            }
            updateTheatreSessions();
        }
    }

    public void updateTheatreSessions() {
        theatreDropdown.removeActionListener(theatreDropdown.getActionListeners()[0]); //only ever one actionlistener. Removed to stop ActionListener being called on adding of theatres
        theatreDropdown.removeAllItems(); //clear the panel
        Collection<TheatreSession> theatreSessions = repository.getTheatreSessions((Movie) movieDropdown.getSelectedItem(), (SessionTime) sessionTimeDropdown.getSelectedItem());
        for (TheatreSession theatreSession : theatreSessions) {
            theatreDropdown.addItem(theatreSession);
        }
        notifyTheatreSessionSubscribers((TheatreSession) theatreDropdown.getSelectedItem());
        theatreDropdown.addActionListener(new JMoviePanelAL()); //Add the ActionListener back
    }

    private ImageIcon loadPromoImage(String promoImageURI) {
        ImageIcon lpromoImage = new ImageIcon();
        File promoImageFile = new File(promoImageURI);
        if (!promoImageFile.exists()) {
            promoImageFile = new File("data\\noimage.jpg");
        }
        try {
            lpromoImage.setImage(ImageIO.read(promoImageFile).getScaledInstance(100, 150, Image.SCALE_DEFAULT));// TODO unhardcode this. and it's sloooow
        } catch (IOException e) {
            System.out.println("Unable to load image.");
        } finally {
            return lpromoImage;
        }
    }
}
