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

/**
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class JMoviePanel extends javax.swing.JPanel {

    public JMoviePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JPanel dropdowns = new JPanel();
        dropdowns.setLayout(new BoxLayout(dropdowns,BoxLayout.Y_AXIS));
        JComboBox movieDropdown = new JComboBox(); //array of items loaded here?
        JComboBox theatreDropdown = new JComboBox();
        JComboBox movieTimeDropdown = new JComboBox();
        JLabel label = null;
        try {
            BufferedImage movieImage = ImageIO.read(new File("Data\\asdf.jpg"));
            ImageIcon pic = new ImageIcon(movieImage);
            label = new JLabel(pic);
             label.setPreferredSize(new Dimension(40,100));
        } catch (IOException e) {
            System.out.println("Unable to find poster for movie. Not displayed..."); //maybe have a no image text
        }
        dropdowns.add(movieDropdown);
        dropdowns.add(movieTimeDropdown);
        dropdowns.add(theatreDropdown);
        add(dropdowns);
        add(label);


    }
}
