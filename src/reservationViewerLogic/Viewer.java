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
 * A Command line version of the search program. Should include methods that
 * will be useable in the gui version of the program
 * @author Michael
 */
public class Viewer {
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

	public static String[] getNames(Reservation[] array) {
		ArrayList<String> listOfNames = new ArrayList<String>();
		for (Reservation reservation : array) {
			listOfNames.add(reservation.getName());
		}
		String[] arrayOfNames = listOfNames.toArray(new String[0]);
		return arrayOfNames;

	}
    
    public static File pickFile() {
               File database = FileSort.pickFile();
               return database;
}
    
    public static File findDefaultDatabase() {
        File defaultDatabase = new File("Reservations.dat");
        if (defaultDatabase.exists() && defaultDatabase.canRead())
            return defaultDatabase;
        else 
            return null;
    }
}
