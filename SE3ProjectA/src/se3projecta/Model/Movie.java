/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;
import java.util.TreeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Russell
 */
public class Movie {
    private int id;
    private String movieName;
    private String promotionalImage;
    
    public String getMovieName() {
        return movieName;
    }

    public String getPromotionalImage() {
        return promotionalImage;
    }

    @Override
    public String toString() {
        return "Movie: ID:" + id + "\tName: " + movieName + "\tPoromotionalImage: " + promotionalImage;
    }
    
    public static TreeMap<Integer, Movie> load(String path) {
        TreeMap<Integer, Movie> list = new TreeMap<Integer, Movie>();
        Document doc = null;
        try {
            doc = XML_Helper.LoadXML(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        NodeList nList = doc.getElementsByTagName("movie");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                Movie movie = new Movie();
                movie.id = Integer.parseInt(eElement.getAttribute("id"));
                movie.movieName = eElement.getAttribute("Name");
                movie.promotionalImage = eElement.getAttribute("promotionalImage");
                list.put(movie.id, movie);
            }
        }

        return list;
    }
}
