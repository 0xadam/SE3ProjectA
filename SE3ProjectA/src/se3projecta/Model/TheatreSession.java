/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import java.util.Arrays;
import java.util.TreeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Russell Peake
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class TheatreSession implements XmlSerializable, XmlUnserializable<Integer> {

    private Integer id;
    private int sessionTimeId;
    private SessionTime sessionTime;
    private int movieId;
    private Movie movie;
    private int theatreId;
    private Theatre theatre;
    private Seat[] seats;

    @Override
    public void load(Element n) {
        this.id = Integer.parseInt(n.getAttribute("id"));
        this.movieId = Integer.parseInt(n.getAttribute("movieId"));
        this.theatreId = Integer.parseInt(n.getAttribute("theatreId"));
        this.sessionTimeId =Integer.parseInt(n.getAttribute("sesionTimeId"));
        //load seats
        Element seatsElement = (Element)n.getElementsByTagName("Seats").item(0);
        NodeList seatNodes = seatsElement.getElementsByTagName("Seat"); 
        seats = new Seat[Integer.parseInt(seatsElement.getAttribute("count"))];
        for (int i = 0; i < seats.length; i ++) {
            seats[i].load((Element)seatNodes.item(i));
        }
        
    }
    
    public void loadRelations(TreeMap<Integer, Theatre> theatres, TreeMap<Integer, Movie> movies, TreeMap<Integer, SessionTime> sessionTimes ){
        if (theatres.containsKey(theatreId)) {
            theatre = theatres.get(theatreId);
        }
        if (movies.containsKey(movieId)) {
            movie = movies.get(movieId);
        }
        if (sessionTimes.containsKey(sessionTimeId)) {
            sessionTime = sessionTimes.get(sessionTimeId);
        }
        //set up seat details
        if (theatre != null) {
            for (int i = 0; i < seats.length; i++) {
                seats[i].setSeatType(theatre.getSeatTypebyIndex(i));
            }
        }
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Element Save(Document doc) {
        Element sessionElement = doc.createElement("TheatreSession");
        
        sessionElement.setAttribute("id", id.toString());
        sessionElement.setAttribute("modieId", Integer.toString(movieId));
        sessionElement.setAttribute("theatreId", Integer.toString(theatreId));
        sessionElement.setAttribute("sessionTimeId", Integer.toString(sessionTimeId));

        // staff elements
        Element seatsElement = doc.createElement("Seats");
        sessionElement.appendChild(seatsElement);
        seatsElement.setAttribute("count", Integer.toString(seats.length));

        for (int i = 0; i < seats.length; i++) {
            seatsElement.appendChild(seats[i].Save(doc));
        }

        return sessionElement;
    }

    public void loadMovie(TreeMap<Integer, Movie> movies) {
        if (movies.containsKey(movieId)) {
            this.movie = movies.get(movieId);
        } else {
            this.movie = null;
        }
    }

    public Seat[] getSeats() {
        return seats;
    }

    public Seat[][] getSeatRows() {
        Seat[][] rows = new Seat[theatre.getHeight()][theatre.getWidth()];

        int rowStart = 0;
        int rowSize = theatre.getWidth();
        for (int i = 0; i != theatre.getHeight(); i++) {
            rows[i] = Arrays.copyOfRange(seats, rowStart, rowStart + rowSize);
            rowStart += rowSize;
        }

        return rows;
    }

    public Seat[] placeRandom() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public int numAvailable(SeatType type) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public boolean hasAvailable(SeatType type, int seats) {
        return numAvailable(type) > seats;
    }

    public SessionTime getSessionTime() {
        return sessionTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }
}
