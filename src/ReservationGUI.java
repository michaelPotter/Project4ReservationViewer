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
    private DateAD[] allArrivals;
    private DateAD[] allDepartures;
    
    // These arrays will change depending on search specifications
    private Reservation[] selectedReservations;
    private String[] selectedNames;
    private DateAD[] selectedArrivals;
    private DateAD[] selectedDepartures;
    
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
    private JPanel startPagePanel; //Boxlayout?
    private JPanel searchCardLayoutPanel;
    
        //reseration panels
    private JPanel searchingJPanel;
    private JPanel searchControlPanel; //contains combos for date searching
    private JPanel searchByComboPanel; // combobox of search by options
    private JPanel searchPanel; //contains search bar and button
    private JPanel searchDatePanel; //
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
    
    //start page panels
    private JPanel title;
    private JPanel search;
//    private JPanel buttonPanel;
    private JLabel databaseName;
    
    //search card
    private JLabel reservationJLabel;
    private JLabel reservationTitleJLabel;
    private JTextField startSearch;
    private JButton searchJButton;
    
    //reservation card
    private JLabel searchBarLabel;
    private JTextField searchBar;
    private JButton searchDatabaseJButton;
    private JLabel comboLabel;
    private JComboBox searchByComboBox;
    private JLabel reservationAreaJLabel;
    private JTextArea reservationTextArea;
    private JList reservationJList;
    
    //added preference var
    private Preferences prefs; 
    
    //listener
    private ActionListener listener; // for everything in the club!
    private ListSelectionListener listListener; // because JList needs a special 
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
                    
                    ArrayList<Reservation> reservationArrayList = new ArrayList<>();
                    allReservations = Viewer.readDatabase(fileObject);
                    
                    //this becomes complicated.. Change this part when everything is working fine, if u want
                    
//                    fileObject = Viewer.pickFile();
//                    defaultFileName = fileObject.getName().trim();
//                    databaseName.setText("Database: " + fileObject.getName());
//                    allReservations = Viewer.readDatabase(fileObject);
                    
                    
                    allNames = Viewer.getNames(allReservations);
                    reservationJList.setListData(allNames);
                    
                    //MICHAEL CODE=========================================
//                    System.out.println("Menu");
//                    File database = Viewer.pickFile();
//                    reservationArray = Viewer.readDatabase(database);
//                    nameArray = Viewer.getNames(reservationArray); //new String[reservationArray.length];
////                    for (int i = 0; i < reservationArray.length; i++) 
////                    {
////                        nameArray[i] = reservationArray[i].getName();
////                    }
//                    reservationJList.setListData(nameArray);
//                    System.out.println(nameArray);
                            //==================================================
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
                    allNames = Viewer.getNames(allReservations);
                    reservationJList.setListData(allNames);
                }
                
                
                //If user selects the cardComboBox
//                if(event.getSource() == cardComboBox)
//                {
//                    CardLayout c1 = (CardLayout) cardLayoutPanel.getLayout();
//                    c1.show(cardLayoutPanel, cardComboBox.getSelectedItem().toString());
//                }
                // If user presses the cardButton
                if (event.getSource() == cardButton) 
                {
                    CardLayout c1 = (CardLayout) cardLayoutPanel.getLayout();
                    c1.next(cardLayoutPanel);
                    if (cardButton.getText().equals("See Reservations")) 
                        cardButton.setText("New Search");
                    else
                        cardButton.setText("See Reservations");
                    
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
                    // SOMEONE UGHH,, SOMEONE PUT SEARCHBAR.GETTEXT() WHICH IS THE JTEXTFIELD FOR THE SECOND CARD!
                    String search = searchBar.getText();
                    Integer[] locations = BinarySearch.searchForAll(allNames,
                            search);
                    if(locations == null)
                    {
                        JOptionPane.showMessageDialog(null, "Search is not found.");
                        return;
                    }
                   
                    selectedReservations = Viewer.getReservationsAtLocation(
                            allReservations, locations);
                    for (Reservation r : selectedReservations) {
                        System.out.println(r + ", ");
                    }
                }
                //If user selects searchByComboBox
                if(event.getSource() == searchByComboBox)
                {
                    
                }
            }
            
        }
        
        class ListListener implements ListSelectionListener 
        {

            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int index = reservationJList.getSelectedIndex();
                if (index >= 0)
                {
                    Reservation outputReservation = allReservations[index];
                // Each name on the left will probably represent several
                    // reservations (what if one person has multiple?) so when
                    // that name is clicked, all of the reservations should show
                    // up on the right
                    String output = outputReservation.toString();
                    reservationTextArea.setText(output);
                }

            }
            
        }
        
        
        // create Listeners
        listener = new UserSelection();
        listListener = new ListListener();
        createDatabasePage();
        
        
        // Check for default database

        //default file obejct = reservatinos.dat
        //file objecct = the default set ups

        //changed this part
        if(defaultFileObject.exists() && fileObject.exists())
        {
        }
        else if(defaultFileObject.exists() && !(fileObject.exists()))
        {
            fileObject = defaultFileObject;
        }
        
        allReservations = Viewer.readDatabase(fileObject);
        allNames = Viewer.getNames(allReservations);
        reservationJList.setListData(allNames);
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
     * Create the card combobox for switching between cards of the form
     */
    private void createComboBox()
    {
        comboPanel = new JPanel();
        String comboItems[] = {"Search","Reservations"};
//        cardComboBox = new JComboBox(comboItems);
//        cardComboBox.setEditable(false);
        //add an item listener
//        cardComboBox.addActionListener(listener);
//        comboPanel.add(cardComboBox);
        
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
        
        //cardButton = new JButton("See Reservations");
        //cardButton.addActionListener(listener);
        //comboPanel.add(cardButton);
        comboPanel.add(databaseName);
        //add panel to frame 
        add(comboPanel);
    }
    
    
    
    /**
     * Creates the start page of ReservationGUI still a work in progress
     * @return panel (JPanel) panel containing all objects of the start page
     */
    private JPanel createSearchPage()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        reservationJLabel = new JLabel();
        reservationJLabel.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/Doge.jpg")));
        reservationTitleJLabel = new JLabel("Reservation Database");
        reservationTitleJLabel.setFont(new Font("Arial", Font.BOLD, 24));
        startSearch = new JTextField();
        //startSearch.setMaximumSize(new Dimension(200,20));
        //set the maximum size at some point
        searchJButton = new JButton("Search");
        searchJButton.addActionListener(listener);
        panel.add(reservationJLabel);
        panel.add(reservationTitleJLabel);
        panel.add(startSearch);
        panel.add(searchJButton);
        
        return panel;
    }
    private String[] getDaysInMonth(DateAD date)
    {
        int daysInMonth = date.daysInMonth(date.getMonth(), date.getYear());
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
        int min = today.getYear() - 10;
        int size = max - min;
        //int[] years = new int[size];
        
        for(int i = 0; i < size - 1; i++)
        {
            
        }
        
        monthJComboBox = new JComboBox(months);
        monthJComboBox.addActionListener(listener);
        dayJComboBox = new JComboBox(getDaysInMonth(today));
        yearJComboBox = new JComboBox();
        yearJComboBox.addActionListener(listener);
                
        for(int i = 0; i < size - 1; i++)
        {
            yearJComboBox.addItem((min));
            min++;
        }
        //yearJComboBox.setSelectedItem(Short.toString(today.getYear()));
        monthJLabel = new JLabel("Month");
        dayJLabel = new JLabel("Day");
        yearJLabel = new JLabel("Year");
        searchDateJButton = new JButton("Search");
        
        //listeners
        searchDateJButton.addActionListener(listener);
        searchByComboBox.addActionListener(listener);
        searchDatabaseJButton.addActionListener(listener);
        
        //set up search control panel
        searchControlPanel.add(comboLabel);
        searchControlPanel.add(searchByComboBox);
        
        //set up search panel
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
        createComboBox();
        
        databasePanel = new JPanel();
        databasePanel.setLayout(new BorderLayout());
        createReservationPage();
        
        controlPanel.add(comboPanel, BorderLayout.NORTH);
        controlPanel.add(databasePanel, BorderLayout.CENTER);
        add(controlPanel);
    }
}
