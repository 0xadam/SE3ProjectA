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
    private boolean updating = false;
    private Transaction transaction;

    public JMoviePanel(Repository repository_, GUI gui_, Transaction transaction) {
        gui = gui_;
        repository = repository_;
        this.transaction = transaction;

        //set layout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        
        //create items
        //add movies to combobox
        for (Movie movie : repository.getMovies()) {
            movieDropdown.addItem(movie);
        }
        movieDropdown.setSelectedIndex(0);
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
        
        add(promoImage);
        promoImage.setPreferredSize(new Dimension(100, 150)); //TODO don't hardcode these two lines
        promoImage.setMaximumSize(new Dimension(100, 150));
        promoImage.setAlignmentY(JPanel.TOP_ALIGNMENT);
        
        
        movieDropdown.addActionListener(new JMoviePanelAL());
        sessionTimeDropdown.addActionListener(new JMoviePanelAL());
        theatreDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!updating && e.getActionCommand().equalsIgnoreCase("comboboxchanged")) {
                        getTransaction().setTheatreSession((TheatreSession) theatreDropdown.getSelectedItem());
                }
            }
        });
    }
    
    private Transaction getTransaction() {
        return transaction;
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
        updating = true;
        theatreDropdown.removeAllItems(); //clear the panel
        Collection<TheatreSession> theatreSessions = repository.getTheatreSessions((Movie) movieDropdown.getSelectedItem(), (SessionTime) sessionTimeDropdown.getSelectedItem());
        for (TheatreSession theatreSession : theatreSessions) {
            theatreDropdown.addItem(theatreSession);
        }
        updating = false;
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
        } catch (IOException e) {
            System.out.println("Unable to load image.");
        } finally {
            return lpromoImage;
        }
    }
}
