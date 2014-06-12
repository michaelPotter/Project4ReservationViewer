package reservationUserInterface;

// SUMMARY OF CHANGES
// SO I USED MY RESERVATION CLASS FROM PROJECT THREE, AND I ALSO USED MY DATABASE FILE WHICH IS DIFFERENT FROM YOURS. THAT IS WHY I NEED TO USE MY RESERVATION CLASS
// I CHANGED IN THE  BINARY SEACH CLASS. I USED MY VERSION OF BINARY SEARCH. U KNOW IT IS WORKING IF U SEARCH A TERM, IT WILL PRINT OUT ALL 
// RESULTS IN THE CONSOLE. SO YEAH.. GG
// update, I polish the interface!!! so labels are working right now.
// Please don't do anythig to the File object stuffs guys :) Thanks.
// update, see line 296


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import filesort.Reservation;
import reservationViewerLogic.*;
import java.io.*;
import Calendar.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/**
 *
 * @author Weston
 */
public class ReservationGUI extends JFrame {
    
    private static final int FRAME_HEIGHT = 400;
    private static final int FRAME_WIDTH = 800;
    
    private static JFrame reservationFrame;
    
    // Array of All Reservations
    private Reservation[] allReservations;
    private String[] allNames;
    private String[] allNamesInCaps;
    private DateAD[] allArrivals;
    private DateAD[] allDepartures;
    
    // These arrays will change depending on search specifications
    private Reservation[] selectedReservations;
    // This array will contain the indexes of the reservations that match
    // the current search
    private Integer[] currentReservationIndexes;
    private String[] listOfNamesToDisplay;
    
    //menu bar
    private String defaultFileName = "Reservations.dat";
    private File defaultFileObject;
    private File fileObject;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem load;
    private JMenuItem setDefault; 
//    private JComboBox cardComboBox;
    private JButton cardButton;
    //card panels
    private JPanel controlPanel;
    private JPanel comboPanel;
    private JPanel cardLayoutPanel;
    private JPanel databasePanel; //BorderLayout
    private JPanel searchCardLayoutPanel;
    
        //reseration panels
    private JPanel searchingJPanel;
    private JPanel searchControlPanel; //contains combos for date searching
    private JPanel searchByComboPanel; // combobox of search by options
    private JPanel searchPanel; //contains search bar and button
    private JPanel reservationPanel; //holds reservations and label
    private JPanel reservationListPanel; //holds list of reservations
    
    //searchDatePanel
    private JComboBox monthJComboBox;
    private JComboBox dayJComboBox;
    private JComboBox yearJComboBox;
    private JLabel monthJLabel;
    private JLabel dayJLabel;
    private JLabel yearJLabel;
    private JButton searchDateJButton;
    private JRadioButton startDateJRadioButton;
    private JRadioButton endDateJRadioButton;
    private ButtonGroup radioGroup;
    
    //start page panels
    private JLabel databaseName;
    
    //search card
    private JLabel reservationJLabel;
    private JLabel reservationTitleJLabel;
    private JTextField startSearch;
    private JButton backButton;  // this is if after they search, they can go back to able to see the all bookings again.
    private JButton searchJButton;
    
    //reservation card
    private JLabel searchBarLabel;
    private JTextField searchBar;
    private JButton searchDatabaseJButton;
    private JLabel comboLabel;
    private JComboBox searchByComboBox;
    private JTextArea reservationTextArea;
    private JList reservationJList;
    
    //added preference var
    private Preferences prefs; 
    
    //listener
    private final ActionListener listener; // for everything in the club!
    private final ListSelectionListener listListener; // because JList needs a special 
    // kind of listener
    
    /**
     * Constructor for ReservationGUI contains listener class as well
     * as create the user interface. Anything that needs to be done before
     * program displays goes here.
     */
    public ReservationGUI()
    {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        defaultFileObject = new File("Reservations.dat");
        fileObject = null; 
        fileObject = new File(prefs.get("LAST_FILE", ""));
        
        if(!(defaultFileObject.exists()))
        {
            JOptionPane.showMessageDialog(null, "No default database.");
        }
        
        if(defaultFileObject.exists() && !(fileObject.exists()))
        {
            fileObject = new File(defaultFileObject.getName());
        }
        
        // new thing
        if(defaultFileObject.exists() && fileObject.exists())
        {
            fileObject = new File(prefs.get("LAST_FILE", ""));
        }
        
        
        /**
         * Class Action listener listens for all user input
         */
        class UserSelection implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                //If user selection is load menu item
                if(event.getSource() == load) //JMenu Item
                {
                    
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(fileObject.getAbsoluteFile()
                        .getParentFile());
                    int returnVal = chooser.showOpenDialog(null);

                    if (returnVal == JFileChooser.APPROVE_OPTION) 
                    {
                        fileObject = chooser.getSelectedFile();
                        defaultFileName = fileObject.getName().trim();
                    }
                    
                    if (returnVal == JFileChooser.CANCEL_OPTION)
                    {
                        return;
                    }
                    
                    databaseName.setText("Database: " + fileObject.getName());
                    
                    allReservations = Viewer.readDatabase(fileObject);
                    setArrays(allReservations);
                    reservationJList.setListData(listOfNamesToDisplay);
                }
                
                if(event.getSource() == setDefault)
                {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(fileObject.getAbsoluteFile()
                        .getParentFile());
                    int returnVal = fileChooser.showOpenDialog(null);

                    if (returnVal == JFileChooser.APPROVE_OPTION) 
                    {
                        defaultFileName = fileChooser.getSelectedFile().getName();
                        prefs.put("LAST_FILE", defaultFileName);
                    }
                    
                    if (returnVal == JFileChooser.CANCEL_OPTION) 
                    {
                        return;
                    }
                    databaseName.setText("Database: " + defaultFileName);
                    
                    
                    fileObject = new File(defaultFileName);
                    
                    allReservations = Viewer.readDatabase(fileObject);
                    setArrays(allReservations);
                    reservationJList.setListData(listOfNamesToDisplay);
                }
                // if user selects a different search method
                if (event.getSource() == searchByComboBox)
                {
                    CardLayout c2 = (CardLayout) 
                            searchCardLayoutPanel.getLayout();
                    c2.show(searchCardLayoutPanel,
                            (String)searchByComboBox.getSelectedItem());
                }
                //If user searches also searchDateJButton needs to be implemented
                if(event.getSource() == searchDatabaseJButton)
                {
                    searchByName();
                }
                
                if(event.getSource() == backButton)
                {
                    
                }
                /**
                 * If the user canges the year or month to search fo
                 */
                if(event.getSource() == yearJComboBox ||
                        event.getSource() == monthJComboBox)
                {
                    DateAD today = new DateAD();
                    dayJComboBox.removeAllItems();
                    dayJComboBox.setModel(new DefaultComboBoxModel(getDaysInMonth(today)));
                }
            }
            
        }
        
        class ListListener implements ListSelectionListener 
        {

            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                String output = "";
                String selectedName = 
                        (String) reservationJList.getSelectedValue();
                for (int i = 0; i < allReservations.length; i++) {
                    if (allReservations[i].getName().equals(selectedName)) {
                        output += allReservations[i].toString();
                        output += String.format("%n");
                    }
                }
                reservationTextArea.setText(output);
            }
            
        }
        
        
        // create Listeners
        listener = new UserSelection();
        listListener = new ListListener();
        createDatabasePage();
        
        
        // Check for default database

        //default file obejct = reservatinos.dat
        //file objecct = the default set ups

        if(defaultFileObject.exists() && fileObject.exists())
        {
        }
        
        else if(defaultFileObject.exists() && !(fileObject.exists()))
        {
            fileObject = defaultFileObject;
        }
        
        allReservations = Viewer.readDatabase(fileObject);
        setArrays(allReservations);
        reservationJList.setListData(listOfNamesToDisplay);
    }
    
    /**
     * Main driver for ReservationGUI initializes class and sets visible
     * @param args 
     */
    public static void main(String[] args)
    {
        reservationFrame = new ReservationGUI();
        reservationFrame.setTitle("Reservation Viewer");
        reservationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reservationFrame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        
        //reservationFrame.pack();
        reservationFrame.setVisible(true);
    }

    /**
     * create menu bar to allow user to load a directory of there own
     */
    private void createMenuBar()
    {      
        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        load = new JMenuItem("Load Database");
        setDefault = new JMenuItem("Set default Database");
        file.add(load);
        file.add(setDefault);
        menuBar.add(file);
        setJMenuBar(menuBar);
        load.addActionListener(listener);
        setDefault.addActionListener(listener);
    }

    /**
     * Simple method to get the number of days in a given month/year
     * using DateAD as an object
     * @param date (date to get the days of month)
     * @return (int) number of days in indicated month/year
     */
    private String[] getDaysInMonth(DateAD date)
    {

        short monthIndex = (short)monthJComboBox.getSelectedIndex();
        int year = (int)yearJComboBox.getSelectedItem();
        int daysInMonth = date.daysInMonth(monthIndex, (short)year);
        String[] days = new String[daysInMonth];
        for(int i = 0; i < daysInMonth; i++)
        {
            days[i] = Integer.toString(i + 1);
        }
        return days;
    }
        /**
     * Method for setting up the card layout of the searching methods
     */
    private void createSearchCardLayout()
    {
        
        searchingJPanel = new JPanel(new BorderLayout());
        searchCardLayoutPanel = new JPanel(new CardLayout());
        searchControlPanel = new JPanel();
        
        //objects
        DateAD today = new DateAD();
        String[] months =
            {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",};
        int max = today.getYear() + 10;
        int min = today.getYear() - 2;
        int size = max - min;
        
        
        backButton = new JButton("Back"); //maybe we can put an icon for back? 
        startDateJRadioButton = new JRadioButton("Start Date");
        startDateJRadioButton.setSelected(true);
        startDateJRadioButton.addActionListener(listener);
        endDateJRadioButton = new JRadioButton("End Date");
        endDateJRadioButton.addActionListener(listener);
        radioGroup = new ButtonGroup();
        radioGroup.add(startDateJRadioButton);
        radioGroup.add(endDateJRadioButton);
        
        monthJComboBox = new JComboBox(months);
        monthJComboBox.setSelectedIndex(today.getMonth());
        yearJComboBox = new JComboBox();
                
        for(int i = 0; i < size + 1; i++)
        {
            yearJComboBox.addItem((min));
            min++;
        }
        yearJComboBox.setSelectedItem(today.getYear());
        System.out.println(today.getYear());
        dayJComboBox = new JComboBox(getDaysInMonth(today));
        monthJLabel = new JLabel("Month");
        dayJLabel = new JLabel("Day");
        yearJLabel = new JLabel("Year");
        searchDateJButton = new JButton("Search");
        
        // Set database name
        if(defaultFileObject.exists() && fileObject.exists())
        {
            databaseName = new JLabel("Database: " + fileObject.getName());
        }
        else if(defaultFileObject.exists())
        {
            databaseName = new JLabel("Database: " + defaultFileName);
        }
        
        else if(fileObject.exists())
        {
            databaseName = new JLabel("Database: " + fileObject.getName());
        }
        
        else
            databaseName = new JLabel("Database: No Database");
        
        //listeners
        searchDateJButton.addActionListener(listener);
        searchByComboBox.addActionListener(listener);
        searchDatabaseJButton.addActionListener(listener);
        yearJComboBox.addActionListener(listener);
        monthJComboBox.addActionListener(listener);
        
        //set up search control panel
        searchControlPanel.add(comboLabel);
        searchControlPanel.add(searchByComboBox);
        searchControlPanel.add(databaseName);
        
        //set up search panel
        searchPanel.add(backButton);
        searchPanel.add(searchBarLabel);
        searchPanel.add(searchBar);
        searchPanel.add(searchDatabaseJButton);
        //searchPanel.add(comboLabel);
        //searchPanel.add(searchByComboBox);
        
        //set up searchby panel
        searchByComboPanel.add(monthJLabel);
        searchByComboPanel.add(monthJComboBox);
        searchByComboPanel.add(dayJLabel);
        searchByComboPanel.add(dayJComboBox);
        searchByComboPanel.add(yearJLabel);
        searchByComboPanel.add(yearJComboBox);
        searchByComboPanel.add(searchDateJButton);
        searchByComboPanel.add(startDateJRadioButton);
        searchByComboPanel.add(endDateJRadioButton);
        
        searchCardLayoutPanel.add(searchPanel, "Name");
        searchCardLayoutPanel.add(searchByComboPanel, "Date");
        
        searchingJPanel.add(searchControlPanel, BorderLayout.NORTH);
        searchingJPanel.add(searchCardLayoutPanel, BorderLayout.SOUTH);
    }
    /**
     * Creates the reservation page that contains all Reservation displaying
     * boxes and such. 
     */
    private void createReservationPage()
    {
        searchByComboPanel = new JPanel();
        searchByComboPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
        searchPanel = new JPanel(); //flow layout
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.black,
                1, true));
        
        reservationPanel = new JPanel(); //Box layout
        reservationPanel.setBorder(BorderFactory.createLineBorder(Color.black,
                1, true));
        reservationPanel.setLayout(new GridLayout());
        reservationListPanel = new JPanel(); // doesn't matter
        reservationListPanel.setBorder(BorderFactory.createLineBorder(Color.black,
                1, true));
        reservationListPanel.setLayout(new GridLayout());
        
        String comboItems[] = {"Name","Date"};
        
        searchBarLabel = new JLabel("Search Database: ");
        searchBar = new JTextField(20);
        searchDatabaseJButton = new JButton("Search");
        comboLabel = new JLabel("Search database by: ");
        searchByComboBox = new JComboBox(comboItems);
        
        createSearchCardLayout();
        
        //set up reservation panel
        reservationTextArea = new JTextArea();
        reservationTextArea.setEditable(false);
        reservationTextArea.setFont(new Font("Courier", Font.PLAIN, 14));
        JScrollPane textScroller = new JScrollPane(reservationTextArea);
        reservationPanel.add(textScroller);
        //set up reservationlist panel
        reservationJList = new JList();
        reservationJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationJList.addListSelectionListener(listListener);
        JScrollPane listScroller = new JScrollPane(reservationJList);
        reservationJList.setPrototypeCellValue("XXXXXXXXXXXXXXXX"
                + "XXXXXX");
        reservationListPanel.add(listScroller);
        
        //add to databasePanel
        databasePanel.add(searchingJPanel, BorderLayout.NORTH);
        databasePanel.add(reservationPanel, BorderLayout.CENTER);
        databasePanel.add(reservationListPanel, BorderLayout.WEST);
        
    }
    /**
     * Create the card layout of the program. powered by the cardComboBox
     */
    private void createDatabasePage()
    {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        //add frame controls that persist outside card layout
        createMenuBar();
        
        databasePanel = new JPanel();
        databasePanel.setLayout(new BorderLayout());
        createReservationPage();
        
//        controlPanel.add(comboPanel, BorderLayout.NORTH);
        controlPanel.add(databasePanel, BorderLayout.CENTER);
        add(controlPanel);
    }
    
    private void setArrays(Reservation[] arrayWithAllReservations) 
    {
        allReservations = arrayWithAllReservations;
        allNames = Viewer.getNames(allReservations);
        allNamesInCaps = new String[allNames.length];
        for (int i = 0; i < allNames.length; i++) 
        {
            allNamesInCaps[i] = allNames[i].toUpperCase();
        }
        ReservationSort.quickSort(allNamesInCaps);
        // I HAVE SORTED THE ALLNAMESINCAPS ARRAY
        allArrivals = Viewer.getArrivals(allReservations);
        allDepartures = Viewer.getDepartures(allReservations);
        
        listOfNamesToDisplay = Viewer.removeDuplicates(allNames.clone());
    }
    
    private void searchByName()
    {
        String search = searchBar.getText().toUpperCase();
        
        Integer[] locations = BinarySearch.searchForAll(allNamesInCaps, search);
        if (locations == null)
        {
            JOptionPane.showMessageDialog(null, "Search is not found.");
            return;
        }
        
        currentReservationIndexes = locations;
        
        //the locations are different from the array of allReservations.
    
        
        
        
        Reservation[] reservations = Viewer.getReservationsAtLocation(
                allReservations, locations);
        
        // I NEED TO HAVE ARRAY OF RESERVATIONS THAT IS SORTED IN THE SAME WAY AS THE ALL CAPS ARRAY SORTED. 
        
        listOfNamesToDisplay = Viewer.getNames(reservations);

        listOfNamesToDisplay = Viewer.removeDuplicates(listOfNamesToDisplay);
        
        reservationJList.setListData(listOfNamesToDisplay);
    }
    
    private void goBackPage()
    {
        
        
        
    
    }
}
