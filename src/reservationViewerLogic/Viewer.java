package reservationViewerLogic;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* <pre>
* Class: Viewer
* File: Viewer.java
* Description: Helper class for ReservationGUI for IO, and Reservaiton
* manipulation. Handles reading, and finding as well as other
* Reservation database hadling.
* @author: Weston, Michael, Vincent
* Environment: PC, Windows 7, Windows 8, NetBeans 7.4
* Date: 6.15.2014
* @version 2.0
* @see javax.swing.JFrame
* </pre>
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
import hotelBooking.FileSort;
import filesort.Reservation;
import hotelBooking.Sorts;
import Calendar.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class contains the logic behind the gui reservation viewer program.
 * Should include methods that will be useable in the gui version of 
 * the program such as methods to read from a database and/or process the 
 * information obtained there.
 * @author Michael
 */
public class Viewer {

    /**
     * Given a database and a searchTerm, this method will return the search 
     * results. This method will utilize the Search Class so that it does not 
     * need to be called directly. 
     * @param database the database file to search through
     * @param searchTerm the name of the reservation to search for
     * @param containSearch whether to search in whole or containing 
     * @return an array of reservations that met the search parameters
     */
	public static Reservation[] findReservations(File database,
			String searchTerm, boolean containSearch) {
		Reservation[] allReservations = readDatabase(database);
                System.out.println(allReservations);
		String[] reservationNames = getNames(allReservations);
		int generalLocation = reservationViewerLogic
                        .BinarySearch.search(
				reservationNames, searchTerm);
		ArrayList<Reservation> searchResults =
                        new ArrayList<Reservation>();

		for (int i = 0; i < 2; i++) {
			int j = generalLocation - i;
			boolean stillSearching = true;

			while (stillSearching) {
				boolean isValidReservation = false;
				boolean isEquals =
                                        reservationNames[j]
                                                .equals(searchTerm);
				boolean doesContain = 
                                        reservationNames[j]
                                                .contains(searchTerm);

				isValidReservation = isEquals ||
                                        (doesContain && containSearch);
				if (isValidReservation) {
					searchResults
                                                .add(allReservations[j]);
					if (i == 0)
						j++;
					else
						j--;
				} else {
					stillSearching = false;
				}
			}
		}

		Reservation[] searchResultsArray = searchResults
				.toArray(new Reservation[0]);
		Sorts.quickSort(searchResultsArray);
		return searchResultsArray;
	}
        /**
         * IO method for reading in all reservations in a database
         * file
         * @param database the database file to read in
         * @return reservationArray an array of all reservations
         */
        public static Reservation[] readDatabase(File database) 
        {
            ArrayList<Reservation> reservationArrayList =
                    new ArrayList<>();
            Reservation[] reservationArray;
            
            try
            {
                FileInputStream fis = new FileInputStream(database);
                ObjectInputStream ois = new ObjectInputStream(fis);

                //Read the number of Reservation objects inside the file
                int j = ois.readInt();
                for(int i = 0; i < j; i++)
                {
                    reservationArrayList.add((Reservation)ois.readObject());
                }
            }
            
            catch(ClassNotFoundException i){}
            catch(IOException e){}
            
            reservationArray = new Reservation[reservationArrayList.size()];
            reservationArrayList.toArray(reservationArray);
            return reservationArray;
        }
    /**
     * Returns an array of Strings containing the names of Reservations
     * in the given array
     * @param array the array of names
     * @return arrayOfNames array containing all names in the array
     * passed in
     */
	public static String[] getNames(Reservation[] array) {
		ArrayList<String> listOfNames = new ArrayList<String>();
		for (Reservation reservation : array) {
			listOfNames.add(reservation.getName());
		}
		String[] arrayOfNames = listOfNames.toArray(new String[0]);
		return arrayOfNames;

	}
        /**
         * Returns the arrival times of the Reservation array
         * @param array array of type reservation arrival times
         * @return arrivalArray standard array of arrival times
         */
         public static DateAD[] getArrivals(Reservation[] array) {
		ArrayList<DateAD> listOfArrivals = new ArrayList<>();
		for (Reservation reservation : array) {
			listOfArrivals.add(reservation.getArrivalDate());
		}
		DateAD[] arrivalArray = listOfArrivals
                        .toArray(new DateAD[0]);
		return arrivalArray;

	}
        /**
        * Returns  the departure times of the Reservation array
        * @param array array of type reservation departure times
        * @return departureArray standard array of departure times
        */
        public static DateAD[] getDepartures(Reservation[] array) {
		ArrayList<DateAD> listOfDepartures = new ArrayList<>();
		for (Reservation reservation : array) {
			listOfDepartures.add(
                                reservation.getDepartureDate());
		}
		DateAD[] departureArray = listOfDepartures.toArray(
                        new DateAD[0]);
		return departureArray;

	}
    
        /**
        * Shows a fileChooser and returns the file selected by
        * the user. This method is just a redirection of the 
        * FileSort.pickFile(), but this is done to simplify method
        * calls so that all of the program logic is called 
        * from a dedicated logic class
        * @return database The file chosen by the user
        */
        public static File pickFile() {
               File database = FileSort.pickFile();
               return database;
        }
    /**
     * Searches for a default database in the directory from which
     * the program is to run
     * @return the file found to be a "default" database 
     */
    public static File findDefaultDatabase() {
        File defaultDatabase = new File("Reservations.dat");
        if (defaultDatabase.exists() && defaultDatabase.canRead())
            return defaultDatabase;
        else 
            return null;
    }
    /**
     * Method to removed duplicate items in an array
     * @param array String[] array containing strings
     * @return arrayNoDuplicates String[] array with duplicates removed
     */
    public static String[] removeDuplicates(String[] array) {
        int i = 0;
        ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
        while (i < list.size() - 1) {
            if (list.get(i).equals(list.get(i + 1))) {
                list.remove(i + 1);
            } else {
                i++;
            }
        }
        String[] arrayNoDuplicates = list.toArray(new String[0]);
        return arrayNoDuplicates;
    }
    /**
     * Method to get the reservation info of an individual user.
     * @param reservations array of Reservations
     * @param locations index locations of database info
     * @return results Reservation[] array of reservations matching location
     */
    public static Reservation[] getReservationsAtLocation(
            Reservation[] reservations, Integer[] locations)
    {
        
        ArrayList<Reservation> list = new ArrayList<> ();
        for (Integer location : locations) {
            list.add(reservations[location]);
        }
        Reservation[] results = list.toArray(new Reservation[0]);
        return results;
    }
    
    
}
