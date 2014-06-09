package reservationViewerLogic;

import hotelBooking.FileSort;
import p3.Reservation;
import hotelBooking.Sorts;
import Calendar.*;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains the logic behind the gui reservation viewer program.
 * Should include methods that will be useable in the gui version of 
 * the program such as methods to read from a database and/or process the 
 * information obtained there. The main method of this class contains a 
 * command line version of this program, however, it is no longer up to date.
 * @author Michael
 */
public class Viewer {
    /**
     * NO LONGER CURRENT
     * A command line version of the search program. Should work similaraly to 
     * the gui format
     * @param args 
     */
	public static void main(String[] args) {
		boolean containSearch = false;

		File database = FileSort.pickFile();
		System.out.print("Enter a search term: ");
		Scanner scanner = new Scanner(System.in);
		String searchTerm = scanner.next();
		String token = "";
//		if (scanner.hasNext())
//		token = scanner.next();
//		if (token.equals("-contains"))
//			containSearch = true;

		Reservation[] results = findReservations(database, searchTerm, containSearch);
		for (Reservation reservation : results) {
			System.out.println(reservation); 
		}
	}

    // This method will probably not be used since it makes more sense to 
    // read the database once, create a Reservation[] and search from that.
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
		String[] reservationNames = getNames(allReservations);
		int generalLocation = reservationViewerLogic.BinarySearch.search(
				reservationNames, searchTerm);
		ArrayList<Reservation> searchResults = new ArrayList<Reservation>();

		// for (int i = generalLocation; i < reservationNames.length; i++) {
		for (int i = 0; i < 2; i++) {
			int j = generalLocation - i;
			boolean stillSearching = true;

			while (stillSearching) {
				boolean isValidReservation = false;
				boolean isEquals = reservationNames[j].equals(searchTerm);
				boolean doesContain = reservationNames[j].contains(searchTerm);

				isValidReservation = isEquals || (doesContain && containSearch);
				if (isValidReservation) {
					searchResults.add(allReservations[j]);
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
     * Reads a database of Reservations. 
     * @param database the file containing the object data
     * @return the list of reservations read from the file
     */
	public static Reservation[] readDatabase(File database) {
		if (database.exists() && database.canRead()) {
			FileInputStream fileInputStream = null;
			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
			try {
				fileInputStream = new FileInputStream(database);
				ObjectInputStream objectInputStream = new ObjectInputStream(
						fileInputStream);
				try {
					while (true) {
						Object object = objectInputStream.readObject();
						if (object.getClass().equals(Reservation.class)) {
							Reservation res = (Reservation) object;
							reservationList.add(res);
						}
					}
				} catch (EOFException e) {
					// end of file
				} catch (Exception e) {
					// other exceptions.
					e.printStackTrace();
				}

			} catch (IOException e) {
				// but it's not going to happen
				e.printStackTrace();
			}
			Reservation[] reservationArray = reservationList
					.toArray(new Reservation[0]);
			return reservationArray;
		} else
			return null;
	}

    /**
     * Returns an array of Strings containing the names of Reservations in the 
     * given array
     * @param array the array of names
     * @return the array to read from
     */
	public static String[] getNames(Reservation[] array) {
		ArrayList<String> listOfNames = new ArrayList<String>();
		for (Reservation reservation : array) {
			listOfNames.add(reservation.getName());
		}
		String[] arrayOfNames = listOfNames.toArray(new String[0]);
		return arrayOfNames;

	}
    public static DateAD[] getArrivals(Reservation[] array) {
		ArrayList<DateAD> listOfArrivals = new ArrayList<>();
		for (Reservation reservation : array) {
			listOfArrivals.add(reservation.getArrivalDate());
		}
		DateAD[] arrivalArray = listOfArrivals.toArray(new DateAD[0]);
		return arrivalArray;

	}
    
    public static DateAD[] getDepartures(Reservation[] array) {
		ArrayList<DateAD> listOfDepartures = new ArrayList<>();
		for (Reservation reservation : array) {
			listOfDepartures.add(reservation.getDepartureDate());
		}
		DateAD[] departureArray = listOfDepartures.toArray(new DateAD[0]);
		return departureArray;

	}
    
    /**
     * Shows a fileChooser and returns the file selected by the user. This 
     * method is just a redirection of the FileSort.pickFile(), but this is 
     * done to simplify method calls so that all of the program logic is called 
     * from a dedicated logic class
     * @return The file chosen by the user
     */
    public static File pickFile() {
               File database = FileSort.pickFile();
               return database;
}
    
    // This method may be incorrect or no longer necessary
    
    /**
     * Searches for a default database in the directory from which the program 
     * is run
     * @return the file found to be a "default" database 
     */
    public static File findDefaultDatabase() {
        File defaultDatabase = new File("Reservations.dat");
        if (defaultDatabase.exists() && defaultDatabase.canRead())
            return defaultDatabase;
        else 
            return null;
    }
}
