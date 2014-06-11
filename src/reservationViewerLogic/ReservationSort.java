/*
 * ReservationSort.java
 */

package reservationViewerLogic;

import filesort.Reservation;

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
        return null;
    }
    
    /**
     * Sorts an array of Reservations by their arrival date.
     * @param reservations the array to be sorted 
     * @return the sorted array
     */
    public static Reservation[] sortByArrival(Reservation[] reservations) {
        return null;
    }
    
    /**
     * Sorts an array of Reservations by their departure date.
     * @param reservations the array to be sorted 
     * @return the sorted array
     */
    public static Reservation[] sortByDeparture(Reservation[] reservations) {
        return null;
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
        return null;
    }
    
    private static final int BY_NAME = 0;
    private static final int BY_ARRIVAL = 1;
    private static final int BY_DEPARTURE = 2;
}
