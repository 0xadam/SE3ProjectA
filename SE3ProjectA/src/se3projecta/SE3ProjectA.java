/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta;

import java.util.TreeMap;
import se3projecta.Model.*;
/**
 *
 * @author Tobias
 */
public class SE3ProjectA {
    private static TreeMap<String, SeatType> SeatTypes;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SeatTypes = SeatType.load("Data/SeatTypes.xml");
        for (int i = 0; i < SeatTypes.size(); i++) {
            System.out.println(SeatTypes.values().toArray(new SeatType[1])[i].toString());
        }
    }
}
