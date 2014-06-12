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
    public static Reservation[] sortByName(Reservation[] reservations)
    {
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
    
     public static void quickSort(Comparable[] array) 
            throws java.lang.ArrayIndexOutOfBoundsException
    {
        if(array.length == 0)
             throw new ArrayIndexOutOfBoundsException();
        quickSort(array, 0, array.length -1);
    }
    
    /**
     * It will call the partition method and recursively call itself until it
     * the elements sorted are less than 4, then it is going to sort them
     * by insertion sort
     * 
     * @param array a Comparable array which will be sorted
     * @param from the starting index
     * @param to the end index of the array
     * @throws java.lang.ArrayIndexOutOfBoundsException if indices not in array
     */
    public static void quickSort(Comparable[] array, int from, int to)
            throws java.lang.ArrayIndexOutOfBoundsException
    {
        if(array.length == 0)
             throw new ArrayIndexOutOfBoundsException();
        if(!(to-from < MIN_NUM_FOR_PARTITION))
        {
            int index = partition(array, from, to);
            quickSort(array, from, index - 1);
            quickSort(array, index + 1, to);
        }
        else
            insertionSort(array, from, to);
    }
    
    /**
     * It will sort the comparable array using insertion sort
     * 
     * @param array A comparable array which will be sorted by insertion sort
     * @param from  The starting index of the array
     * @param to    the end index of the array
     * @throws java.lang.ArrayIndexOutOfBoundsException if indeces not in array
     */
    public static void insertionSort(Comparable[] array, int from, int to)
            throws java.lang.ArrayIndexOutOfBoundsException 
    {
        if(array.length == 0)
             throw new ArrayIndexOutOfBoundsException();
        Comparable temp;
        for(int i = from + 1; i <= to; i++)
        {
            temp = array[i];
            int j = 0;
            for(j = i; j > from; j--)
            {
                if(temp.compareTo(array[j - 1]) < 0)
                    array[j] = array[j - 1];
                else
                    break;
            }
            array[j] = temp;
        }
    }
    
    /**
     * It will first sort middle first and last of the array, moves data around
     * the pivot value, and returns pivot index.
     * 
     * @param array A comparable array to be called in partition
     * @param from The starting index of the array
     * @param to the last index of the array
     * @return returns the pivot index after being partitioned
     * @throws java.lang.ArrayIndexOutOfBoundsException if indeces not in array
     */
    private static int partition(Comparable[] array, int from, int to)
            throws java.lang.ArrayIndexOutOfBoundsException 
    {
        if(array.length == 0)
             throw new ArrayIndexOutOfBoundsException();
        int pivot;
        pivot = (from + to) / 2;
        sortFirstMiddleLast(array, from, pivot, to);
        swap(array, pivot, to - 1);
        pivot = to - 1;
        
        int i = from + 1;
        int j = pivot - 1;
        
        //Infinite loop until i is bigger than j
	for ( ; ; )
        {
            while(array[i].compareTo(array[pivot]) < 0)
            {
                ++i;
            }
            while(array[j].compareTo(array[pivot]) > 0)
            {
                --j;
            }
            
            if(i < j)
            {
                swap(array, i, j);
                ++i;
                --j;
            }
            else 
                break;
        }
        
        swap(array, i, pivot);
        pivot = i;
        return pivot;
    }
    
    /**
     * It will swap two elements inside the array
     * 
     * @param array A comparable array that two elements of them will be swapped
     * @param from The first element that later be swapped with the second 
     *              element
     * @param to The second element to be swapped with the first one.
     * @throws java.lang.ArrayIndexOutOfBoundsException if indices not in array
     */
    private static void swap(Comparable[] array, int from, int to)
            throws java.lang.ArrayIndexOutOfBoundsException 
    {
        if(array.length == 0)
             throw new ArrayIndexOutOfBoundsException();
        Comparable temp = array[from];
        array[from] = array[to];
        array[to] = temp;
    }
    
    /**
     * It will sort the first, middle and last elements inside the array
     * 
     * @param array A comparable array that its first, middle and last will be 
     *              sorted 
     * @param from The first index of the array
     * @param mid the middle index of the array
     * @param to the last index of the array
     * @throws java.lang.ArrayIndexOutOfBoundsException if indeces not in array 
     */
    private static void sortFirstMiddleLast(Comparable[] array, int from, 
            int mid, int to) throws java.lang.ArrayIndexOutOfBoundsException
    {   
        if(array.length == 0)
             throw new ArrayIndexOutOfBoundsException();
        if(array[from].compareTo(array[mid]) > 0)
            swap(array, from, mid);
        if(array[mid].compareTo(array[to]) > 0)
            swap(array, mid, to);
        if(array[from].compareTo(array[mid]) > 0)
            swap(array, from, mid);
    }
    
    private static final int MIN_NUM_FOR_PARTITION = 4;
    
    private static final int BY_NAME = 0;
    private static final int BY_ARRIVAL = 1;
    private static final int BY_DEPARTURE = 2;
}
