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

    private TreeMap<Character, SeatType> seatTypes;
    private TreeMap<Integer, Theatre> theatres;
    private TreeMap<Integer, Movie> movies;
    private TreeMap<Integer, SessionTime> sessionTimes;
    private TreeMap<Integer, CustomerType> customerTypes;
    private TreeMap<Integer, TheatreSession> theatreSessions;
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
        
         try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
         if ("Nimbus".equals(info.getName())) {
         javax.swing.UIManager.setLookAndFeel(info.getClassName());
         break;
         }
         }
         } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(se3projecta.GUI.GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(se3projecta.GUI.GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(se3projecta.GUI.GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(se3projecta.GUI.GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         }
         

        //</editor-fold>
        /* Create and display the form */
        
         java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
         new se3projecta.GUI.GUI(control).setVisible(true);
                
         }
         });
         
    }

    public SE3ProjectA() {
        //load seatTypes;
        NodeList SeatNodes = XmlFileLoader.parseXmlFile("Data/SeatTypes.xml", "SeatType");
        try {
            seatTypes = XmlFileLoader.loadIndexEntities(SeatNodes, SeatType.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load patronTypes
        NodeList CustomerTypeNodes = XmlFileLoader.parseXmlFile("Data/CustomerTypes.xml", "CustomerType");
        try {
            customerTypes = XmlFileLoader.loadIndexEntities(CustomerTypeNodes, CustomerType.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //load Movies
        NodeList movieNodes = XmlFileLoader.parseXmlFile("Data/Movies.xml", "movie");
        try {
            movies = XmlFileLoader.loadIndexEntities(movieNodes, Movie.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Load SessionTimes
        NodeList sessionTimeNodes = XmlFileLoader.parseXmlFile("Data/SessionTimes.xml", "SessionTime");
        try {
            sessionTimes = XmlFileLoader.loadIndexEntities(sessionTimeNodes, SessionTime.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load Theatres
        NodeList theatreNodes = XmlFileLoader.parseXmlFile("Data/Theatres.xml", "Theatre");
        try {
            theatres = XmlFileLoader.loadIndexEntities(theatreNodes, Theatre.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //set up seat plans.
        for (Theatre t : theatres.values()) {
            t.loadSeatPlan(seatTypes);
        }
        
        //load Theatre sessions
        NodeList theatreSessionNodes = XmlFileLoader.parseXmlFile("Data/Theatres.xml", "TheatreSession");
        if (theatreSessionNodes != null) {
            try {
            theatreSessions = XmlFileLoader.loadIndexEntities(theatreSessionNodes, TheatreSession.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        } else {
            int i = 0;
            theatreSessions = new TreeMap<Integer, TheatreSession>();
            for (Theatre t : theatres.values()) {
                for (SessionTime st : sessionTimes.values()) {
                    TheatreSession ts = new TheatreSession(t, st, i++);
                    theatreSessions.put(ts.getId(), ts);
                }
            }
        }

        //load movies, theatres and sessiontimes to sessions

        //other initialisation code

        //TEST: test seat type loading
        for (int i = 0; i < seatTypes.size(); i++) {
            System.out.println(seatTypes.values().toArray(new SeatType[1])[i].toString());
        }
        
        for (CustomerType ct : customerTypes.values()) {
            System.out.println(ct.toString());
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
