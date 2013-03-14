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

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JMoviePanel extends javax.swing.JPanel {

    JLabel label = new JLabel();

    public JMoviePanel(Collection<Movie> movies, Collection<SessionTime> sessionTimes) {
        //set layout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //create items
        JPanel dropdowns = new JPanel();
        dropdowns.setLayout(new BoxLayout(dropdowns, BoxLayout.Y_AXIS));
        JComboBox movieDropdown = new JComboBox();
        movieDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setIcon(loadPromoImage(((Movie) ((JComboBox) e.getSource()).getSelectedItem()).getPromotionalImage()));

            }
        });
        JComboBox sessionTimeDropdown = new JComboBox();
        JComboBox theatreDropdown = new JComboBox();
        //add movies to combobox
        for (Movie movie : movies) {
            movieDropdown.addItem(movie);
        }
        //add sessionTimes to combobox
        for (SessionTime session : sessionTimes) {
            sessionTimeDropdown.addItem(session);
        }
        label.setPreferredSize(new Dimension(100, 150));
        dropdowns.add(movieDropdown);
        dropdowns.add(sessionTimeDropdown);
        dropdowns.add(theatreDropdown);
        add(dropdowns);
        add(label);
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
