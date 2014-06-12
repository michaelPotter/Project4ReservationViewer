package reservationViewerLogic;

import hotelBooking.Sorts;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class for searching through an array of Comparables. Methods for returning
 * many results which meet a certain search criteria still need to be
 * implemented
 *
 * @author Michael
 *
 */
public class BinarySearch
{
    public static void main(String[] args) {
        String[] array = {"Alpha", "Alpha", "Alpha","Alpha","Bravo", "Bravo", "Bravo", "Charlie", "Delta"};
        
        int location = search(array, "Charlie");
        System.out.println("Location: " + location);
        
        Integer[] locations = searchForAll(array, "Alpha");
        for (int i : locations) {
            System.out.print(i + ", ");
        }
    }
    /**
     * A static version of the binarySearch. Allows searching without creating a
     * BinarySearch Object.
     *
     * @param array the array to search through
     * @param searchObject the object to search for
     * @return the index of the match if one is found, else a number less than
     * one is returned if no matches are found
     */
    public static int search(Comparable[] array, Comparable searchObject)
    {
        return searchFor(array, searchObject, 0, array.length);

    }
    
    /**
     * Searches for all matches of a given search. By comparing to a given 
     * object
     * @param array the array to search through
     * @param searchObject the searchObject to compare to
     * @return an array containing all of the found matches. returns null if 
     * no location found
     */
    public static Integer[] searchForAll(Comparable[] array, Comparable searchObject) 
    {
        int generalLocation = search(array, searchObject);
        if (generalLocation < 0) {
            return null;
        }
        return findNear(array, searchObject, generalLocation);
    }

    /**
     * Searches an interval from the startIndex to the endIndex of an array to
     * find a match for the given word. Uses a recursive binary search function
     *
     * @param searchObject the word to search for
     * @param startIndex the beginning of the interval that is being searched
     * @param endIndex the end of the interval that is begin searched
     * @return returns the index of a matched term if one is found, otherwise a
     * negative number is returned.
     */
    private static int searchFor(Comparable[] array,
            Comparable searchObject, int startIndex, int endIndex)
    {
        final int DOES_NOT_EXIST = -1;
         if(startIndex > endIndex)
        {
            return DOES_NOT_EXIST;
        }
        
        else
        {
            int mid = (endIndex + startIndex) / 2;
            
            if(array[mid].compareTo(searchObject) > 0)
            {
                return searchFor(array, searchObject, startIndex, mid - 1);
            }
            
            else if(array[mid].compareTo(searchObject) < 0)
            {   
                return searchFor(array, searchObject, mid + 1, endIndex);
            }
            
            else 
            {
                return mid;
            }
        
        }
    }

    /**
     * Given a general location, this method will search for all objects 
     * that are the same as the searched for object and are next to the general
     * location in the array.
     * @param array the array to search through
     * @param searchObject the object to search for
     * @param index the general location of the search
     * @return an array containing all of the found matches
     */
    private static Integer[] findNear(Comparable[] array, Comparable searchObject,
            int index)
    {
        ArrayList<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < 2; i++)
        {
            int j = index - i;
            //Changed here
            if(j == -1)
            {
                break;
            }
            boolean stillSearching = true;

            while (stillSearching)
            {
                if (array[j].equals(searchObject))
                {
                    
                    if (j == 0 || j == array.length-1) // changed it here, b4 was array.length. 
                        stillSearching = false;
                    indexList.add(j);
                    if (i == 0)
                        j++;
                    else
                        j--;
                }
                else
                {
                    stillSearching = false;
                }
            }
        }
        Integer[] searchResults = indexList.toArray(new Integer[0]);
        Sorts.quickSort(searchResults);
        return searchResults;
    }

}
