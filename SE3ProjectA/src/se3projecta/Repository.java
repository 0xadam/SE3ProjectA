/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

import se3projecta.Persistance.ImportException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import org.w3c.dom.NodeList;
import se3projecta.Model.*;
import se3projecta.Persistance.XmlFileLoader;
import se3projecta.Persistance.XmlFileSaver;

/**
 *
 * @author Tobias
 */
public class Repository {

    private TreeMap<Character, SeatType> seatTypes;
    private TreeMap<Integer, Theatre> theatres;
    private TreeMap<Integer, Movie> movies;
    private TreeMap<Integer, SessionTime> sessionTimes;
    private TreeMap<Integer, CustomerType> customerTypes;
    private TreeMap<Integer, TheatreSession> theatreSessions;

    public Collection<Movie> getMovies() {
        return movies.values();
    }

    public Collection<SessionTime> getSessionTimes() {
        return sessionTimes.values();
    }

    public Collection<SeatType> getSeatTypes() {
        return seatTypes.values();
    }

    public Collection<CustomerType> getCustomerTypes() {
        return customerTypes.values();
    }

    public Collection<TheatreSession> getTheatreSessions(Movie movie, SessionTime sessionTime) {
        ArrayList<TheatreSession> theatreSessionsList = new ArrayList<TheatreSession>();
        for (TheatreSession ts : theatreSessions.values()) {
            if (ts.getSessionTime().getId() == sessionTime.getId() && ts.getMovie().getId() == movie.getId()) {
                theatreSessionsList.add(ts);
            }
        }
        return theatreSessionsList;
    }
    private final String DATA_DIR = "data";
    private final String SEAT_TYPES_PATH = DATA_DIR + "/SeatTypes.xml";
    private final String SEAT_TYPE_TAG = "SeatType";
    private final String CUSTOMER_TYPES_PATH = DATA_DIR + "/CustomerTypes.xml";
    private final String CUSTOMER_TYPE_TAG = "CustomerType";
    private final String MOVIES_PATH = DATA_DIR + "/Movies.xml";
    private final String MOVIE_TAG = "Movie";
    private final String SESSION_TIMES_PATH = DATA_DIR + "/SessionTimes.xml";
    private final String SESSION_TIME_TAG = "SessionTime";
    private final String THEATRES_PATH = DATA_DIR + "/Theatres.xml";
    private final String THEATRE_TAG = "Theatre";
    private final String THEATRE_SESSIONS_PATH = DATA_DIR + "/TheatreSessions.xml";
    private final String THEATRE_SESSION_TAG = "TheatreSession";

    public Repository() throws ImportException {
        ImportException ie = new ImportException();
        NodeList LoadedNodes = null;
        //load seatTypes;

        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(SEAT_TYPES_PATH, SEAT_TYPE_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressed(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressed(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressed(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressed(new java.io.IOException("An error reading file " + SEAT_TYPES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressed(new Exception("An error occured when loading file " + SEAT_TYPES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                seatTypes = XmlFileLoader.loadIndexEntities(LoadedNodes, SeatType.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressed(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + SeatType.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressed(IAE);
            } catch (se3projecta.Model.ExistingKeyException EKE) {
                ie.addSuppressed(EKE);
            } catch (Exception e) {
                ie.addSuppressed(new Exception("An error occured when loading file " + THEATRE_SESSIONS_PATH, e));
            }
        }


        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(CUSTOMER_TYPES_PATH, CUSTOMER_TYPE_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressed(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressed(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressed(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressed(new java.io.IOException("An error reading file " + CUSTOMER_TYPES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressed(new Exception("An error occured when loading file " + CUSTOMER_TYPES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                customerTypes = XmlFileLoader.loadIndexEntities(LoadedNodes, CustomerType.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressed(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + CustomerType.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressed(IAE);
            } catch (se3projecta.Model.ExistingKeyException EKE) {
                ie.addSuppressed(EKE);
            } catch (Exception e) {
                ie.addSuppressed(new Exception("An error occured when loading file " + THEATRE_SESSIONS_PATH, e));
            }
        }

        //load Movies
        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(MOVIES_PATH, MOVIE_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressed(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressed(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressed(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressed(new java.io.IOException("An error reading file " + MOVIES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressed(new Exception("An error occured when loading file " + MOVIES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                movies = XmlFileLoader.loadIndexEntities(LoadedNodes, Movie.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressed(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + Movie.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressed(IAE);
            } catch (se3projecta.Model.ExistingKeyException EKE) {
                ie.addSuppressed(EKE);
            } catch (Exception e) {
                ie.addSuppressed(new Exception("An error occured when loading file " + THEATRE_SESSIONS_PATH, e));
            }
        }

        //Load SessionTimes
        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(SESSION_TIMES_PATH, SESSION_TIME_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressed(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressed(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressed(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressed(new java.io.IOException("An error reading file " + SESSION_TIMES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressed(new Exception("An error occured when loading file " + SESSION_TIMES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                sessionTimes = XmlFileLoader.loadIndexEntities(LoadedNodes, SessionTime.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressed(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + SessionTime.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressed(IAE);
            } catch (se3projecta.Model.ExistingKeyException EKE) {
                ie.addSuppressed(EKE);
            } catch (Exception e) {
                ie.addSuppressed(new Exception("An error occured when loading file " + THEATRE_SESSIONS_PATH, e));
            }
        }

        //load Theatres
        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(THEATRES_PATH, THEATRE_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressed(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressed(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressed(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressed(new java.io.IOException("An error reading file " + THEATRES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressed(new Exception("An error occured when loading file " + THEATRES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                theatres = XmlFileLoader.loadIndexEntities(LoadedNodes, Theatre.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressed(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + Theatre.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressed(IAE);
            } catch (se3projecta.Model.ExistingKeyException EKE) {
                ie.addSuppressed(EKE);
            } catch (Exception e) {
                ie.addSuppressed(new Exception("An error occured when loading file " + THEATRE_SESSIONS_PATH, e));
            }
        }

        //set up seat plans.
        for (Theatre t : theatres.values()) {
            t.loadSeatPlan(seatTypes);
        }

        //load Theatre sessions
        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(THEATRE_SESSIONS_PATH, THEATRE_SESSION_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            //ie.addSuppressed(FNFE);
            //ignore file not found and recreate empty sessions from scratch.
            LoadedNodes = null;
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressed(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressed(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressed(new java.io.IOException("An error reading file " + THEATRE_SESSIONS_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressed(new Exception("An error occured when loading file " + THEATRE_SESSIONS_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                theatreSessions = XmlFileLoader.loadIndexEntities(LoadedNodes, TheatreSession.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressed(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + TheatreSession.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressed(IAE);
            } catch (se3projecta.Model.ExistingKeyException EKE) {
                ie.addSuppressed(EKE);
            } catch (Exception e) {
                ie.addSuppressed(new Exception("An error occured when loading file " + THEATRE_SESSIONS_PATH, e));
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
        for (TheatreSession ts : theatreSessions.values()) {
            ts.loadRelations(theatres, movies, sessionTimes);
        }

        //other initialisation code

        //if any errors occured throw the list of them
        if (ie.hasSuppressed()) {
            throw ie;
        }

    }

    public void save() throws javax.xml.transform.TransformerConfigurationException, javax.xml.parsers.ParserConfigurationException, javax.xml.transform.TransformerException {
        XmlFileSaver.save(theatreSessions, "data/TheatreSessions.xml");
    }

    public void testDump() {
        theatreSessions.get(0).getSeats()[0].setState(Seat.State.Occupied);
        try {
            save();
        } catch (Exception e) {
        } //TODO add good error handling when this code is actually used
        // TODO remove later
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
