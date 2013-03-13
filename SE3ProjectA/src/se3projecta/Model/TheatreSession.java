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
    
    public TheatreSession(Theatre t, SessionTime st, int id) {
        
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
    
    private Seat[] getContiguousAllocation(Seat[] row, SeatType type, int width) {
        Seat[] allocation = null;
        
        for (int spanStart = 0; spanStart < row.length; spanStart++) {
            boolean found = true;
            
            for (int spanOffset = 0; spanOffset < width; spanOffset++) {
                int index = spanStart + spanOffset;
                if (!row[index].available()) {
                    spanStart = spanStart + spanOffset + 1; // Advance to one beyond what we were just looking at
                    found = false;
                    break;
                }
            }
            
            if (found == true) {
                allocation = Arrays.copyOfRange(row, spanStart, spanStart + width);
                break;
            }
        }
        
        return allocation;
    }
    
    private Seat[] getContiguousAllocation(Seat[][] rows, SeatType type, int width) {
        Seat[] allocation = null;
        
        for (Seat[] row : rows) {
            allocation = getContiguousAllocation(row, type, width);
            
            if (allocation != null) {
                break;
            }
        }
        
        return allocation;
    }
    
    public Seat[] findBestFit(SeatType type, int seats) {
        if (!hasAvailable(type, seats)) {
            throw new IllegalArgumentException("Cannot allocate " + seats + " seats of type " + type.getName());
        }
        
        Seat[][] rows = getSeatRows();

        int remainingSeats = seats;
        Seat[] allocations = new Seat[seats];
        
        // allocate seats using a best-fit algorithm
            // Try to allocate as big a span as we can each time
            // Start with the biggest span 
        for (int width = Math.max(theatre.getWidth(), remainingSeats); width > 0; width = Math.min(remainingSeats, width-1)) {
            while (true) {
                Seat[] allocation = getContiguousAllocation(rows, type, width);

                if (allocation == null) {
                    break;
                }
                else {
                    for (Seat seat : allocation) {
                        allocations[seats - remainingSeats] = seat;
                        remainingSeats--;
                    }
                }
            };
        }
        
        return allocations;
    }
    
    public Seat[] placeRandom(SeatType type, int seats) {
        return findBestFit(type, seats);
    }

    public int numAvailable(SeatType type) {
        // This could be optimized quite significantly by updating a counter
        // stored in TheatreSession when the available flag on each seat is
        // changed.
        int count = 0;
        
        for (Seat seat : seats) {
            if (seat.getType() == type && seat.available()) {
                count++;
            }
        }
        
        return count;
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
