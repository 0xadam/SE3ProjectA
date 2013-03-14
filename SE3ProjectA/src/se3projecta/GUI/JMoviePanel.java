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

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JMoviePanel extends javax.swing.JPanel {

    public JMoviePanel(Collection<Movie> movies, Collection<SessionTime> sessionTimes) {
        //set layout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //create items
        JPanel dropdowns = new JPanel();
        dropdowns.setLayout(new BoxLayout(dropdowns, BoxLayout.Y_AXIS));
        JComboBox movieDropdown = new JComboBox();
        JComboBox sessionTimeDropdown = new JComboBox();
        JComboBox theatreDropdown = new JComboBox();
        JLabel label = null;

        //add movies to combobox
        for (Movie movie : movies) {
            movieDropdown.addItem(movie);
        }

        //add sessionTimes to combobox
        for (SessionTime session : sessionTimes) {
            sessionTimeDropdown.addItem(session);
        }

        //load image
        File promoImage = new File(((Movie) movieDropdown.getSelectedItem()).getPromotionalImage());
        if (!promoImage.exists()) {
            promoImage = new File("data\\noimage.jpg");
        }
        try {
            BufferedImage movieImage = ImageIO.read(promoImage);
            ImageIcon pic = new ImageIcon(movieImage);
            label = new JLabel(pic);
            label.setPreferredSize(new Dimension(100, 150));
        } catch (IOException e) {
            System.out.println("Unable to load image.");
        }
        dropdowns.add(movieDropdown);
        dropdowns.add(sessionTimeDropdown);
        dropdowns.add(theatreDropdown);
        add(dropdowns);
        add(label);
    }
}
