/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

import java.util.TreeMap;
import java.util.Collection;
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
    private final String THEATRE_TAG = "TheatreTag";
    
    private final String THEATRE_SESSIONS_PATH = DATA_DIR + "/Theatres.xml";
    private final String THEATRE_SESSION_TAG = "TheatreSession";
    
    public Repository() {
        //load seatTypes;
        NodeList SeatNodes = XmlFileLoader.parseXmlFile(SEAT_TYPES_PATH, SEAT_TYPE_TAG);
        try {
            seatTypes = XmlFileLoader.loadIndexEntities(SeatNodes, SeatType.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load patronTypes
        NodeList CustomerTypeNodes = XmlFileLoader.parseXmlFile(CUSTOMER_TYPES_PATH, CUSTOMER_TYPE_TAG);
        try {
            customerTypes = XmlFileLoader.loadIndexEntities(CustomerTypeNodes, CustomerType.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load Movies
        NodeList movieNodes = XmlFileLoader.parseXmlFile(MOVIES_PATH, MOVIE_TAG);
        try {
            movies = XmlFileLoader.loadIndexEntities(movieNodes, Movie.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Load SessionTimes
        NodeList sessionTimeNodes = XmlFileLoader.parseXmlFile(SESSION_TIMES_PATH, SESSION_TIME_TAG);
        try {
            sessionTimes = XmlFileLoader.loadIndexEntities(sessionTimeNodes, SessionTime.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load Theatres
        NodeList theatreNodes = XmlFileLoader.parseXmlFile(THEATRES_PATH, THEATRE_TAG);
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
        NodeList theatreSessionNodes = XmlFileLoader.parseXmlFile(THEATRE_SESSIONS_PATH, THEATRE_SESSION_TAG);
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
        for (TheatreSession ts : theatreSessions.values()) {
            ts.loadRelations(theatres, movies, sessionTimes);
        }
        
        // TODO remove following line
        theatreSessions.get(0).getSeats()[0].setState(Seat.State.Occupied);

        //other initialisation code
        try {
            XmlFileSaver.save(theatreSessions, "data/TheatreSessions.xml");
        } catch (Exception e) {
            // TO-DO remove this code
        }
    }
    
    public void testDump() {
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
