/*
 * ReservationSort.java
 */

package reservationViewerLogic;

import filesort.Reservation;
import Calendar.DateAD;

/**
 * A class specifically designed for sorting arrays of Reservations
 * 
 * @author Michael
 */
public class ReservationSort
{
    /**
     * Sorts an array of Reservations by their name.
     * @param reservations the array to be sorted 
     * @return the sorted array
     */
    public static Reservation[] sortByName(Reservation[] reservations) {
        return sort(reservations, BY_NAME);
    }
    
    /**
     * Sorts an array of Reservations by their arrival date.
     * @param reservations the array to be sorted 
     * @return the sorted array
     */
    public static Reservation[] sortByArrival(Reservation[] reservations) {
        return sort(reservations, BY_ARRIVAL);
    }
    
    /**
     * Sorts an array of Reservations by their departure date.
     * @param reservations the array to be sorted 
     * @return the sorted array
     */
    public static Reservation[] sortByDeparture(Reservation[] reservations) {
        return sort(reservations, BY_DEPARTURE);
    }
    
    /**
     * Sorts the array based on the given int value. If the int is a 0, the 
     * array will be sorted by name. If the int is a 1, the array will be 
     * sorted by arrival date. if the int is a 2, the array will be sorted by 
     * departure date.
     * @param array the array to be sorted 
     * @param sortBy a value indicating what to sort by
     * @return the sorted array
     */
    private static Reservation[] sort(Reservation[] array, int sortBy) {
        // run the entire array
        for (int i = 0; i < array.length; i++) {
            int lowest = i;
            // run starting from i to find the lowest value
            for (int j = i; j < array.length; j++) {
                boolean lessThan = false;
                
                // compare based on sortBy variable
                if (sortBy == BY_NAME) {
                    String firstName = array[i].getName();
                    String secondName = array[j].getName();
                    if (secondName.compareTo(firstName) < 0)
                        lessThan = true;
                } else if (sortBy == BY_ARRIVAL)
                {
                    DateAD firstDate = array[i].getArrivalDate();
                    DateAD secondDate = array[j].getArrivalDate();
                    if (secondDate.compareTo(firstDate) < 0)
                        lessThan = true;
                }
                else
                {
                    DateAD firstDate = array[j].getDepartureDate();
                    DateAD secondDate = array[j].getDepartureDate();
                    if (secondDate.compareTo(firstDate) < 0)
                        lessThan = true;
                }
                
                if (lessThan == false) 
                    lowest = j;
            }
            
            // swap the lowest with the first unswapped
            swap(array, i, lowest);
        }
        
        return array;
    }
    
    /**
     * Swaps two REservations in an array.
     * @param array the array with objects to be swapped
     * @param from the index to swap from
     * @param to the index to swap to
     */
    private static void swap(Reservation[] array, int from, int to) {
        Reservation hold = array[from];
        array[from] = array[to];
        array[to] = hold;
    }
    
    private static final int BY_NAME = 0;
    private static final int BY_ARRIVAL = 1;
    private static final int BY_DEPARTURE = 2;
}
