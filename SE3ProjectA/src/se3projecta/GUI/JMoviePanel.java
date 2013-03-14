/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import se3projecta.Model.*;
import java.util.Collection;
import java.awt.event.*;
import se3projecta.*;

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JMoviePanel extends javax.swing.JPanel {

    JLabel label = new JLabel();
    Repository repository;
    JPanel dropdowns = new JPanel();
    JComboBox theatreDropdown = new JComboBox();
    JComboBox movieDropdown = new JComboBox();
    JComboBox sessionTimeDropdown = new JComboBox();

    public JMoviePanel(Repository repository_) {
        repository = repository_;
        //set layout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //create items

        dropdowns.setLayout(new BoxLayout(dropdowns, BoxLayout.Y_AXIS));

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
        updateTheatreSessions();
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

    private void updateTheatreSessions() {
        theatreDropdown.removeAllItems();
        Collection<TheatreSession> theatreSessions = repository.getTheatreSessions((Movie) movieDropdown.getSelectedItem(), (SessionTime) sessionTimeDropdown.getSelectedItem());
        for (TheatreSession theatreSession : theatreSessions) {
            theatreDropdown.addItem(theatreSession.getTheatre());
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
