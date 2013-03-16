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

    private JLabel label = new JLabel();
    private Repository repository;
    private JPanel dropdowns = new JPanel();
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

    public JMoviePanel(Repository repository_) {
        repository = repository_;

        theatreSessionSubscribers = new ArrayList<TheatreSessionSubscriber>();

        //set layout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        dropdowns.setLayout(new BoxLayout(dropdowns, BoxLayout.Y_AXIS));
        //create items
        movieDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setIcon(loadPromoImage(((Movie) movieDropdown.getSelectedItem()).getPromotionalImage()));

            }
        });
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
        label.setPreferredSize(new Dimension(100, 150));
        dropdowns.add(movieDropdown);
        dropdowns.add(sessionTimeDropdown);
        dropdowns.add(theatreDropdown);
        add(dropdowns);
        add(label);
    }

    public class JMoviePanelAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateTheatreSessions();
        }
    }

    public void updateTheatreSessions() {
        theatreDropdown.removeAllItems();
        Collection<TheatreSession> theatreSessions = repository.getTheatreSessions((Movie) movieDropdown.getSelectedItem(), (SessionTime) sessionTimeDropdown.getSelectedItem());
        for (TheatreSession theatreSession : theatreSessions) {
            theatreDropdown.addItem(theatreSession.getTheatre());
            //TODO fix hacky. Only works when there is one theatreSession (which is currently the case)
            notifyTheatreSessionSubscribers(theatreSession);
        }
    }

    private ImageIcon loadPromoImage(String promoImageURI) {
        ImageIcon promoImage = new ImageIcon();
        File promoImageFile = new File(promoImageURI);
        if (!promoImageFile.exists()) {
            promoImageFile = new File("data\\noimage.jpg");
        }
        try {
            promoImage.setImage(ImageIO.read(promoImageFile));
        } catch (IOException e) {
            System.out.println("Unable to load image.");
        } finally {
            return promoImage;
        }
    }
}
