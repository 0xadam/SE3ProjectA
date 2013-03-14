/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

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

    public JMoviePanel(LayoutManager layout) {
        super(layout);
        JComboBox movieDropdown = new JComboBox(); //array of items loaded here?
        JComboBox theatreDropdown = new JComboBox();
        JComboBox movieTimeDropdown = new JComboBox();

        try {
            BufferedImage movieImage = ImageIO.read(new File("Data\\asdf.jpg"));
            ImageIcon pic = new ImageIcon(movieImage);
            JLabel label = new JLabel(pic);
            add(label);
        } catch (IOException e) {
            System.out.println("Unable to find poster for movie. Not added...");
            e.printStackTrace();
            System.exit(-1);
        }
        add(movieDropdown);
        add(theatreDropdown);
        add(movieTimeDropdown);
    }
}
