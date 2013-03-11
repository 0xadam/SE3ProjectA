/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

import java.util.TreeMap;
import org.w3c.dom.NodeList;
import se3projecta.Model.*;

/**
 *
 * @author Tobias
 */
public class SE3ProjectA {

    private TreeMap<Character, SeatType> SeatTypes;

    public SeatType getSeatTypebyId(char id) {
        if (SeatTypes.containsKey(id)) {
            return SeatTypes.get(id);
        }
        return null;
    }
    private TreeMap<Integer, Theatre> theatres;
    private TreeMap<Integer, Movie> movies;

    private String getMoviebyId(Integer id) {
        return movies.get(id).toString();
    }
    private TreeMap<Integer, SessionTime> sessionTimes;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final SE3ProjectA control = new SE3ProjectA();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">

        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*
         try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
         if ("Nimbus".equals(info.getName())) {
         javax.swing.UIManager.setLookAndFeel(info.getClassName());
         break;
         }
         }
         } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         }
         */

        //</editor-fold>
        /* Create and display the form */
        /*
         java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
         new GUI(control).setVisible(true);
                
         }
         });
         */
    }

    public SE3ProjectA() {
        //load seatTypes;
        NodeList SeatNodes = XML_Loader.loadXML("Data/SeatTypes.xml", "SeatType");
        try {
            SeatTypes = XML_Loader.loadCharIndexEntities(SeatNodes, SeatType.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load patronTypes

        //load Movies
        NodeList movieNodes = XML_Loader.loadXML("Data/Movies.xml", "movie");
        try {
            movies = XML_Loader.loadIntIndexEntities(movieNodes, Movie.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Load SessionTimes
        NodeList sessionTimeNodes = XML_Loader.loadXML("Data/SessionTimes.xml", "SessionTime");
        try {
            sessionTimes = XML_Loader.loadIntIndexEntities(sessionTimeNodes, SessionTime.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load Theatres
        NodeList theatreNodes = XML_Loader.loadXML("Data/Theatres.xml", "Theatre");
        try {
            theatres = XML_Loader.loadIntIndexEntities(theatreNodes, Theatre.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //set up seat plans.
        for (Theatre t : theatres.values()) {
            t.loadSeatPlan(SeatTypes);
            t.loadMovie(movies);
        }
        
        //load Theatre sessions


        //other initialisation code

        //TEST: test seat type loading
        for (int i = 0; i < SeatTypes.size(); i++) {
            System.out.println(SeatTypes.values().toArray(new SeatType[1])[i].toString());
        }

        for (int i = 0; i < movies.size(); i++) {
            System.out.println(movies.values().toArray(new Movie[1])[i].toString());
        }

        for (int i = 0; i < sessionTimes.size(); i++) {
            System.out.println(sessionTimes.values().toArray(new SessionTime[1])[i].toString());
        }

        for (int i = 0; i < theatres.size(); i++) {
            System.out.println(theatres.values().toArray(new Theatre[1])[i].toString());
        }
    }
}
