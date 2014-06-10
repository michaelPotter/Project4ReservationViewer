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
import p3.Reservation;
import reservationViewerLogic.*;
import hotelBooking.FileSort;
import java.io.*;   
import Calendar.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.prefs.Preferences;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



// SEE LINE 374,, and i put the last method on the most bottom print dialog that is all. VJ 6/9/14/ 8:45





























/**
 *
 * @author Weston
 */
public class ReservationGUI extends JFrame {
    
    private static final int FRAME_HEIGHT = 400;
    private static final int FRAME_WIDTH = 800;
    
    private static JFrame reservationFrame;
    
    // Array of All Reservations
    private Reservation[] reservationArray;
    
    // Array of reservations meeting search Results
    private Reservation[] searchedReservations;
    
    // Array of all Reservation names
    private String[] nameArray;
    
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
    
    //reseration panels
    private JPanel titlePanel; // title and combobox of options
    private JPanel searchPanel; //contains search bar and button
    private JPanel reservationPanel; //holds reservations and label
    private JPanel reservationListPanel; //holds list of reservations
    
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
                    
//                    JFileChooser chooser = new JFileChooser();
//                    chooser.setCurrentDirectory(fileObject.getAbsoluteFile()
//                        .getParentFile());
//                    int returnVal = chooser.showOpenDialog(null);
//
//                    if (returnVal == JFileChooser.APPROVE_OPTION) 
//                    {
//                        fileObject = chooser.getSelectedFile();
//                        defaultFileName = fileObject.getName().trim();
//                    }
                    fileObject = Viewer.pickFile();
                    defaultFileName = fileObject.getName().trim();
                    databaseName.setText("Database: " + fileObject.getName());
                    reservationArray = Viewer.readDatabase(fileObject);
                    nameArray = Viewer.getNames(reservationArray);
                    reservationJList.setListData(nameArray);
                    
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
                    
                    
                    reservationArray = Viewer.readDatabase(fileObject);
                    nameArray = Viewer.getNames(reservationArray);
                    reservationJList.setListData(nameArray);
                    
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
                //If user searches
                if(event.getSource() == searchJButton)
                {
                    System.out.print("AAAAAAAAAAAAAAAH");
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
                Reservation outputReservation = reservationArray[index];
                // Each name on the left will probably represent several
                // reservations (what if one person has multiple?) so when
                // that name is clicked, all of the reservations should show
                // up on the right
                String output = outputReservation.toString();
                reservationTextArea.setText(output);
            }
            
        }
        
        
        // create Listeners
        listener = new UserSelection();
        listListener = new ListListener();
        createCardLayout();
        
        
        // Check for default database
        File database = Viewer.findDefaultDatabase();
        if (database != null) {
            reservationArray = Viewer.readDatabase(database);
            nameArray = Viewer.getNames(reservationArray);
            reservationJList.setListData(nameArray);
        }
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
        
        if(fileObject.exists())
        {
            databaseName = new JLabel("Database: " + fileObject.getName());
        }
        else
            databaseName = new JLabel("Database: No Database");
        
        cardButton = new JButton("See Reservations");
        cardButton.addActionListener(listener);
        comboPanel.add(cardButton);
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
    
    // I CHANGED STARTED HERE THEN LINE 407 408 and 416.
    class PrintListner implements ActionListener
    {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                createPrintDialog();
            }
    }
    /**
     * Creates the reservation page that contains all Reservation displaying
     * boxes and such. 
     */
    private void createReservationPage()
    {
        PrintListner listener = new PrintListner();
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
        
        String comboItems[] = {"Name","Start Date", "End Date"};
        
        searchBarLabel = new JLabel("Search Database: ");
        searchBar = new JTextField(20);
        searchDatabaseJButton = new JButton("Search");
        JButton printButton = new JButton("Print");
        printButton.addActionListener(listener);
        comboLabel = new JLabel("Search database by: ");
        searchByComboBox = new JComboBox(comboItems);
        
        //set up search panel
        searchPanel.add(searchBarLabel);
        searchPanel.add(searchBar);
        searchPanel.add(searchDatabaseJButton);
        searchPanel.add(printButton);
        searchPanel.add(comboLabel);
        searchPanel.add(searchByComboBox);
        
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
        databasePanel.add(searchPanel, BorderLayout.NORTH);
        databasePanel.add(reservationPanel, BorderLayout.CENTER);
        databasePanel.add(reservationListPanel, BorderLayout.WEST);
        
    }
    /**
     * Create the card layout of the program. powered by the cardComboBox
     */
    private void createCardLayout()
    {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        //add frame controls that persist outside card layout
        createMenuBar();
        createComboBox();
        
        //create cards for card layout
        startPagePanel = new JPanel();
        startPagePanel.setLayout(new GridBagLayout());
        startPagePanel.add(createSearchPage());
        
        databasePanel = new JPanel();
        databasePanel.setLayout(new BorderLayout());
        createReservationPage();
        
        cardLayoutPanel = new JPanel(new CardLayout());
        cardLayoutPanel.add(startPagePanel, "Search");
        cardLayoutPanel.add(databasePanel, "Reservations");
        
        controlPanel.add(comboPanel, BorderLayout.NORTH);
        controlPanel.add(cardLayoutPanel, BorderLayout.CENTER);
        add(controlPanel);
        
    }
    
    private void createPrintDialog()
    {
        PrinterJob pj = PrinterJob.getPrinterJob();
        
            if(pj.printDialog())
            {
                try
                {
                    pj.print();
                }
                
                catch(PrinterException e)
                {
                    return;
                }
            }
    }
    
}
