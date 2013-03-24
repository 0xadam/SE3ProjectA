package se3projecta;

import se3projecta.Persistence.ImportException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import org.w3c.dom.NodeList;
import se3projecta.Model.*;
import se3projecta.Persistence.XmlFileLoader;
import se3projecta.Persistence.XmlFileSaver;

/**
 * A Repository class stores data for a set of theatre bookings.
 * 
 * The current implementation of Repository simply stores a Map for each data-
 * type, allowing it to be retrieved by its primary key or iterated over.
 * 
 * The Repository class is presently responsible for both granting access to the
 * data and managing the persistence of data; i.e. saving and loading of
 * entities.
 * 
 * Because data loading and access is centralized to the one location, the
 * replacement of this class would enable the project to use a DBMS rather
 * than XML file storage in future, with minimal changes to the rest of the
 * project's code. Ideally, this would be achieved by turning Repository into
 * an interface and with subclasses implementing respective entity storage (XML
 * or database) as appropriate.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class Repository {

    private TreeMap<Character, SeatType> seatTypes;
    private TreeMap<Integer, Theatre> theatres;
    private TreeMap<Integer, Movie> movies;
    private TreeMap<Integer, SessionTime> sessionTimes;
    private TreeMap<Integer, CustomerType> customerTypes;
    private TreeMap<Integer, TheatreSession> theatreSessions;

    /**
     * Get all stored Movies.
     *
     * @return all stored Movies
     */
    public Collection<Movie> getMovies() {
        return movies.values();
    }

    /**
     * Get all stored SessionTimes.
     *
     * @return all stored SessionTimes
     */
    public Collection<SessionTime> getSessionTimes() {
        return sessionTimes.values();
    }

    /**
     * Get all stored SeatTypes.
     *
     * @return all stored SeatTypes
     */
    public Collection<SeatType> getSeatTypes() {
        return seatTypes.values();
    }

    /**
     * Get all stored CustomerTypes.
     *
     * @return all stored CustomerTypes
     */
    public Collection<CustomerType> getCustomerTypes() {
        return customerTypes.values();
    }

    /**
     * Get all stored TheatreSessions for the specified Movie and SessionTime.
     *
     * @param movie the Movie to get TheatreSessions for
     * @param sessionTime the SessionTime to get TheatreSessions for
     * @return all stored TheatreSessions for the specified Movie and
     * SessionTime
     */
    public Collection<TheatreSession> getTheatreSessions(Movie movie, SessionTime sessionTime) {
        ArrayList<TheatreSession> theatreSessionsList = new ArrayList<TheatreSession>();

        if (movie == null || sessionTime == null) { //if we have a movie or sessionTime that is unknown
            throw new IllegalArgumentException();
        }

        for (TheatreSession ts : theatreSessions.values()) {
            if (ts.getSessionTime().getId().equals(sessionTime.getId()) && ts.getMovie().getId().equals(movie.getId())) { //if the movie id and sessionTime id match
                theatreSessionsList.add(ts);
            }
        }
        return theatreSessionsList;
    }
    private static final String DATA_DIR = "data";
    private static final String SEAT_TYPES_PATH = DATA_DIR + "/SeatTypes.xml";
    private static final String SEAT_TYPE_TAG = "SeatType";
    private static final String CUSTOMER_TYPES_PATH = DATA_DIR + "/CustomerTypes.xml";
    private static final String CUSTOMER_TYPE_TAG = "CustomerType";
    private static final String MOVIES_PATH = DATA_DIR + "/Movies.xml";
    private static final String MOVIE_TAG = "Movie";
    private static final String SESSION_TIMES_PATH = DATA_DIR + "/SessionTimes.xml";
    private static final String SESSION_TIME_TAG = "SessionTime";
    private static final String THEATRES_PATH = DATA_DIR + "/Theatres.xml";
    private static final String THEATRE_TAG = "Theatre";
    private static final String THEATRE_SESSIONS_PATH = DATA_DIR + "/TheatreSessions.xml";
    private static final String THEATRE_SESSION_TAG = "TheatreSession";

    /**
     * Create a repository by importing persistent data from XML.
     *
     * @throws ImportException
     */
    public Repository() throws ImportException {
        ImportException ie = new ImportException();
        NodeList LoadedNodes = null;
        //load seatTypes;

        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(SEAT_TYPES_PATH, SEAT_TYPE_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressedBackport(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressedBackport(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressedBackport(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressedBackport(new java.io.IOException("An error reading file " + SEAT_TYPES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressedBackport(new Exception("An error occured when loading file " + SEAT_TYPES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                seatTypes = XmlFileLoader.loadIndexEntities(LoadedNodes, SeatType.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressedBackport(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + SeatType.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressedBackport(IAE);
            } catch (se3projecta.Persistence.ExistingKeyException EKE) {
                ie.addSuppressedBackport(EKE);
            } catch (Exception e) {
                ie.addSuppressedBackport(new Exception("An error occured when loading seat type data", e));
            }
        }


        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(CUSTOMER_TYPES_PATH, CUSTOMER_TYPE_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressedBackport(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressedBackport(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressedBackport(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressedBackport(new java.io.IOException("An error reading file " + CUSTOMER_TYPES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressedBackport(new Exception("An error occured when loading file " + CUSTOMER_TYPES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                customerTypes = XmlFileLoader.loadIndexEntities(LoadedNodes, CustomerType.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressedBackport(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + CustomerType.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressedBackport(IAE);
            } catch (se3projecta.Persistence.ExistingKeyException EKE) {
                ie.addSuppressedBackport(EKE);
            } catch (Exception e) {
                ie.addSuppressedBackport(new Exception("An error occured when loading customer type data", e));
            }
        }

        //load Movies
        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(MOVIES_PATH, MOVIE_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressedBackport(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressedBackport(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressedBackport(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressedBackport(new java.io.IOException("An error reading file " + MOVIES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressedBackport(new Exception("An error occured when loading file " + MOVIES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                movies = XmlFileLoader.loadIndexEntities(LoadedNodes, Movie.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressedBackport(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + Movie.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressedBackport(IAE);
            } catch (se3projecta.Persistence.ExistingKeyException EKE) {
                ie.addSuppressedBackport(EKE);
            } catch (Exception e) {
                ie.addSuppressedBackport(new Exception("An error occured when loading movie data", e));
            }
        }

        //Load SessionTimes
        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(SESSION_TIMES_PATH, SESSION_TIME_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressedBackport(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressedBackport(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressedBackport(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressedBackport(new java.io.IOException("An error reading file " + SESSION_TIMES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressedBackport(new Exception("An error occured when loading file " + SESSION_TIMES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                sessionTimes = XmlFileLoader.loadIndexEntities(LoadedNodes, SessionTime.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressedBackport(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + SessionTime.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressedBackport(IAE);
            } catch (se3projecta.Persistence.ExistingKeyException EKE) {
                ie.addSuppressedBackport(EKE);
            } catch (Exception e) {
                ie.addSuppressedBackport(new Exception("An error occured when loading session time data", e));
            }
        }

        //load Theatres
        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(THEATRES_PATH, THEATRE_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            ie.addSuppressedBackport(FNFE);
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressedBackport(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressedBackport(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressedBackport(new java.io.IOException("An error reading file " + THEATRES_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressedBackport(new Exception("An error occured when loading file " + THEATRES_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                theatres = XmlFileLoader.loadIndexEntities(LoadedNodes, Theatre.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressedBackport(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + Theatre.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressedBackport(IAE);
            } catch (se3projecta.Persistence.ExistingKeyException EKE) {
                ie.addSuppressedBackport(EKE);
            } catch (Exception e) {
                ie.addSuppressedBackport(new Exception("An error occured when loading theatre data", e));
            }
        }

        //set up seat plans.
        if (seatTypes != null) {
            for (Theatre t : theatres.values()) {
                t.loadSeatPlan(seatTypes);
            }
        }

        //load Theatre sessions
        try {
            LoadedNodes = XmlFileLoader.parseXmlFile(THEATRE_SESSIONS_PATH, THEATRE_SESSION_TAG);
        } catch (java.io.FileNotFoundException FNFE) {
            //ie.addSuppressedBackport(FNFE);
            //ignore file not found and recreate empty sessions from scratch.
            LoadedNodes = null;
        } catch (javax.xml.parsers.ParserConfigurationException PCE) {
            ie.addSuppressedBackport(PCE);
        } catch (org.xml.sax.SAXException SAXE) {
            ie.addSuppressedBackport(SAXE);
        } catch (java.io.IOException IOE) {
            ie.addSuppressedBackport(new java.io.IOException("An error reading file " + THEATRE_SESSIONS_PATH + " from drive", IOE));
        } catch (Exception e) {
            ie.addSuppressedBackport(new Exception("An error occured when loading file " + THEATRE_SESSIONS_PATH, e));
        }
        if (LoadedNodes != null) {
            try {
                theatreSessions = XmlFileLoader.loadIndexEntities(LoadedNodes, TheatreSession.class);
            } catch (java.lang.InstantiationException IE) {
                ie.addSuppressedBackport(new java.lang.InstantiationException("An error occured trying to create a new instance of class:" + TheatreSession.class.toString()));
            } catch (java.lang.IllegalAccessException IAE) {
                ie.addSuppressedBackport(IAE);
            } catch (se3projecta.Persistence.ExistingKeyException EKE) {
                ie.addSuppressedBackport(EKE);
            } catch (Exception e) {
                ie.addSuppressedBackport(new Exception("An error occured when loading theatre session data", e));
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
        if (theatres != null && movies != null && sessionTimes != null) {
            for (TheatreSession ts : theatreSessions.values()) {
                try {
                    ts.loadRelations(theatres, movies, sessionTimes);
                } catch (InvalidRelationException e) {
                    ie.addSuppressedBackport(e);
                }
            }
        }

        //other initialisation code

        //if any errors occured throw the list of them
        if (ie.hasSuppressed()) {
            throw ie;
        }

    }

    /**
     * Save booking to XML by saving TheatreSession changes.
     *
     * @throws javax.xml.transform.TransformerConfigurationException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws javax.xml.transform.TransformerException
     */
    public void save() throws javax.xml.transform.TransformerConfigurationException, javax.xml.parsers.ParserConfigurationException, javax.xml.transform.TransformerException {
        XmlFileSaver.save(theatreSessions, "data/TheatreSessions.xml");
    }
}
