/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.search.a3;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import librarysearcha1.*;


/**
 * @author Nicholas
 */
public class LibraryGui extends JFrame {

    /**
     * Height of the GUI Window
     */
    public static final int WIDTH = 500;

    /**
     * Height of the GUI Window
     */
    public static final int HEIGHT = 420;

    private JPanel masterPanel, initialPanel, addBookPanel, addJournalPanel, searchPanel;
    private JLabel blankSpace = new JLabel("     ");
    private JLabel typeLabel = new JLabel("Type: ");
    private JLabel topLabel = new JLabel("<html><u>Adding A Reference:</u></html>");
    private JLabel topLabel2 = new JLabel("<html><u>Searching References:</u></html>");
    private JLabel messageLabel = new JLabel("Messages");
    private JLabel searchLabel = new JLabel("Search Results");
    private JLabel callNumberLabel = new JLabel("Call Number: ");
    private JLabel yearLabel = new JLabel("Year: ");
    private JLabel year1Label = new JLabel("Start Year: ");
    private JLabel year2Label = new JLabel("End Year: ");
    private JLabel titleLabel = new JLabel("Title: ");
    private JLabel titleLabel2 = new JLabel("Title Keywords: ");
    private JLabel authorLabel = new JLabel("Authors: ");
    private JLabel publisherLabel = new JLabel("Publisher: ");
    private JLabel organizationLabel = new JLabel("Organization: ");
    private JTextField callNumberTF = new JTextField(20);
    private JTextField yearTF = new JTextField(10);
    private JTextField year1TF = new JTextField(10);
    private JTextField year2TF = new JTextField(10);
    private JTextField titleTF = new JTextField(20);
    private JTextField authorTF = new JTextField(20);
    private JTextField publisherTF = new JTextField(20);
    private JTextField organizationTF = new JTextField(20);
    private GridBagConstraints cords;
    private JTextArea textSpace = new JTextArea (6,38);
    private JScrollPane messageArea = new JScrollPane(textSpace);
    private JButton resetB = new JButton("Reset");
    private JButton addB = new JButton("  Add  ");
    private JButton searchB = new JButton("Search");
    private LibrarySearch library;
    
    private CardLayout currScreen = new CardLayout();
    private String bJString[] = {"Book","Journal"};
    private JComboBox bORJ = new JComboBox(bJString);
    private String typeChoice = "Book";
    private String bJSearchString[] = {"Both","Book","Journal"};
    private JComboBox searchFind = new JComboBox(bJSearchString);
    private String searchType = "Both";
    private String outputFile;
    
    /* This listener responds to the add menu. Defaults to book. */
    private class AddListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            callNumberTF.setText("");
            yearTF.setText("");
            titleTF.setText("");
            publisherTF.setText("");      //Clears the fields of junk values.
            authorTF.setText("");
            organizationTF.setText("");
            year1TF.setText("");
            year2TF.setText("");
            if (typeChoice.equals("Book")){
                setBook();                    //Draws the book screen
            } else{
                setJournal();
            }
        }
    } 

    /* This listener responds to the menu choice for search. */
    private class SearchListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            callNumberTF.setText("");
            titleTF.setText("");
            year1TF.setText("");    //Clears the fields of junk.
            year2TF.setText("");
            setSearch();            //Draws the search screen.
        }
    } 

    /* This listener responds to the menu choice to quit. */
    private class QuitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            library.addToFile(outputFile);
            System.exit(0);
        }
    }

    /* This listener responds to the menu choice to quit. */
    private class onExit extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            library.addToFile(outputFile);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
    
    /* This listener listens to the combo box choice for book or journal. */
    private class BorJComboBoxListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            callNumberTF.setText("");
            yearTF.setText("");
            titleTF.setText("");
            publisherTF.setText("");
            authorTF.setText("");
            organizationTF.setText("");
            year1TF.setText("");
            year2TF.setText("");
            //System.out.println(bORJ.getSelectedItem().toString());
            typeChoice = bORJ.getSelectedItem().toString();
            if (bORJ.getSelectedItem().toString().equals("Journal")){
                setJournal();
            }else{
                setBook();
            }
        }
    }

    /* This listener listens to the combo box choice for book or journal. */
    private class SearchComboBoxListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            searchType = searchFind.getSelectedItem().toString();
            //System.out.println(searchType);
        }
    }    
    
    /* This listener responds to the add class for both book and journal. */
    private class AddButtonListener implements ActionListener
    {
        int givenYear;
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Boolean year = null,callNumber = null,title = null;
            if(bORJ.getSelectedItem().toString().equals("Journal")){
                textSpace.setText("Enter a year from 1000-9999.\n A call number, title and year are MANDATORY.\n");
            } else {
                textSpace.setText("Seperate the authors via comma. \nEnter a year from 1000-9999.\nA call number, title and year are MANDATORY.\n");
            }
            
            if(!yearTF.getText().isEmpty()){
                for (int i = 0; i<yearTF.getText().length();i++)
                {
                    if (!Character.isDigit(yearTF.getText().charAt(i))){
                        year = false;
                        break;
                    }
                    else{
                        year = true;   
                    }
                }
            } else {
                year = false;
            }
            if (yearTF.getText().length() > 4){
                year = false;
            }
            if (year == true){
                givenYear = Integer.parseInt(yearTF.getText());
                if (givenYear < 1000 || givenYear > 9999){
                    year = false;
                }
            }
            
            if(callNumberTF.getText().isEmpty()){
                callNumber = false;
            }else{
                callNumber = true;
            }
            
            if(titleTF.getText().isEmpty()){
                title = false;
            }else{
                title = true;
            }
            
            if (callNumber == true && year == true && title == true){
                if (bORJ.getSelectedItem().toString().equals("Journal")){
                    String jcallNumStr = callNumberTF.getText();
                    String jtitleStr = titleTF.getText();
                    String jorgStr;
                    if (!organizationTF.getText().isEmpty()){
                        jorgStr = organizationTF.getText();
                    }else{
                        jorgStr = "NULL";
                    }
                    Journal newJournal = null;
                    try {
                        newJournal = new Journal(jcallNumStr,jtitleStr,givenYear,jorgStr);
                    } catch (Exception ex) {
                        Logger.getLogger(LibraryGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(!library.isInLibrary(newJournal)){
                                textSpace.setText(textSpace.getText() + "The following journal has been added to the library.\n");
                                library.addItem(newJournal);
                    } else{
                     textSpace.setText(textSpace.getText() +"An item with the same year and call number already exists.\nThe following journal has not been added to the library.\n");   
                    } 
                } else {
                    String bcallNumStr = callNumberTF.getText();
                    String btitleStr = titleTF.getText();
                    String bpubStr;
                    ArrayList <String> authorList = new ArrayList <String>();
                    if (!publisherTF.getText().isEmpty()){
                        bpubStr = publisherTF.getText();
                    }else{
                        bpubStr = "NULL";
                    }
                    if(!authorTF.getText().isEmpty()){
                        String[] tokens = authorTF.getText().split(" ");
                        for (String token : tokens)
                        {
                            authorList.add(token);
                        }
                    }
                    Book newBook = null;
                    try {
                        newBook = new Book(bcallNumStr,btitleStr,givenYear,authorList,bpubStr);
                    } catch (Exception ex) {
                        Logger.getLogger(LibraryGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(!library.isInLibrary(newBook)){
                                textSpace.setText(textSpace.getText() + "The following book has been added to the library.\n");
                                library.addItem(newBook);
                    } else{
                     textSpace.setText(textSpace.getText() +"An item with the same year and call number already exists.\nThe following book has not been added to the library.\n");   
                    }                    
                }
            
            }else{
                textSpace.setText(textSpace.getText() +"The information you are trying to add is invalid. Please fix any incorrect fields.\n");   
            }
        }
    } 
    
    /* This listener responds to all reset buttons. */
    private class ResetButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
                callNumberTF.setText("");
                yearTF.setText("");
                titleTF.setText("");
                publisherTF.setText("");
                authorTF.setText("");
                organizationTF.setText("");
                year1TF.setText("");
                year2TF.setText("");
        }
    }
    
    /* This listener responds to the search button. */
    private class SearchButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int givenYear1 = 0;
            int givenYear2 = 0;
            String callNumStr;
            ArrayList <String> keyWords = new ArrayList <String>();
            Boolean year1 = false, year2 = false;
            ArrayList <Reference> matchItemList;
            if(!year1TF.getText().isEmpty()){
                for (int i = 0; i<year1TF.getText().length();i++)
                {
                    if (!Character.isDigit(year1TF.getText().charAt(i))){
                        year1 = false;
                        break;
                    }
                    else{
                        year1 = true;   
                    }
                }
            }
            if (year1TF.getText().length() > 4){
                year1 = false;
            }
            if (year1 == true){
                givenYear1 = Integer.parseInt(year1TF.getText());
                if (givenYear1 < 1000 || givenYear1 > 9999){
                    year1 = false;
                }
                if (givenYear1 == 0){
                    year1 = true;
                }
            }
            if (year1TF.getText().isEmpty()){
                givenYear1 = 0;
                year1 = true;
            }
            
            if(!year2TF.getText().isEmpty()){
                for (int i = 0; i<year2TF.getText().length();i++)
                {
                    if (!Character.isDigit(year2TF.getText().charAt(i))){
                        year2 = false;
                        break;
                    }
                    else{
                        year2 = true;   
                    }
                }
            }
            if (year2TF.getText().length() > 4){
                year2 = false;
            }
            if (year2 == true){
                givenYear2 = Integer.parseInt(year2TF.getText());
                if (givenYear2 < 1000 || givenYear2 > 9999){
                    year2 = false;
                }
                if (givenYear2 == 0){
                    year2 = true;
                }
            }
            if (year2TF.getText().isEmpty()){
                givenYear2 = 0;
                year2 = true;
            }
            
            if (year1 == true && year2TF.getText().isEmpty()){
                givenYear2 = givenYear1;
                year2 = true;
            }
            
            
            if (year1 == true && year2 == true){
                if (givenYear1 > givenYear2){
                    textSpace.setText(textSpace.getText() +"Error. Start year is before end year.\n");
                    year1 = false;
                }
            }

            if(callNumberTF.getText().isEmpty()){
                callNumStr = "NULL";
            }else{
                callNumStr = callNumberTF.getText();
            }
            
            if(!titleTF.getText().isEmpty()){
                 String[] tokens = titleTF.getText().split(" ");
                    for (String token : tokens)
                    {
                        keyWords.add(token.toLowerCase());
                    }
            }
            if (year1 == true && year2 == true){
                matchItemList = library.printSpecificItems(callNumStr,givenYear1,givenYear2,keyWords);
            } else{
                textSpace.setText(textSpace.getText() +"Unable to search. Invalid information is bring searched for.\n");
                return;
            }
            textSpace.setText("");
            if (matchItemList.isEmpty()){
                textSpace.setText("No items matched this search.\n");
                return;
            }
            textSpace.setText("Matched Items:\n");
            if(searchType.isEmpty()){
                searchType = "Both";
            }
            for (Reference currItem: matchItemList){
                if (currItem instanceof Book){
                    Book aBook = (Book) currItem;
                    if(searchType.equals("Book") || searchType.equals("Both")){
                        textSpace.setText(textSpace.getText() + aBook.printBook());
                    }
                }
                else if (currItem instanceof Journal){
                    Journal aJournal = (Journal) currItem;
                    if(searchType.equals("Journal") || searchType.equals("Both")){
                        textSpace.setText(textSpace.getText() + aJournal.printJournal());
                    }
                }
            }
            
        }
    }
    
    /* This is where the main part of the GUI is constructed. */

    /**
     *
     * @param lib
     * @param file
     */
    
    public LibraryGui(LibrarySearch lib, String file)
    {
        super("Library Search GUI");
        library = lib;
        outputFile = file;
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new onExit());
        //setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        
        masterPanel = new JPanel();    //Creates the master panel that holds all the panels.
        masterPanel.setLayout(currScreen);
        
        initialPanel = new JPanel( );     //Create the initial pannel with the welcome message.
        initialPanel.setBackground(Color.LIGHT_GRAY);
        initialPanel.setVisible(true);
        initialPanel.setLayout(new BorderLayout());
        JLabel intro = new JLabel("<html><center><u>Welcome to Library Search!</u></center>Choose a command from the<em> \"Commands\" </em>menu above to be able to <br> add a book or journal, search for references, or exit the program.</html>",JLabel.CENTER);
        intro.setVerticalAlignment(JLabel.CENTER);
        initialPanel.add(intro);
        add(initialPanel);
        
        bORJ.addActionListener(new BorJComboBoxListener());
        searchFind.addActionListener(new SearchComboBoxListener());
        addBookPanel = new JPanel( );         //Creates the book panel for adding.
        addBookPanel.setLayout(new GridBagLayout());
        addBookPanel.setBackground(Color.LIGHT_GRAY);
        
        addJournalPanel = new JPanel( );      //Creates the journal panel for adding.
        addJournalPanel.setBackground(Color.LIGHT_GRAY);
        addJournalPanel.setLayout(new GridBagLayout());
        
        searchPanel = new JPanel( );     //Creates the search panel.
        searchPanel.setBackground(Color.LIGHT_GRAY);
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setVisible(false);

        masterPanel.add(initialPanel,"0");
        masterPanel.add(addBookPanel,"1");     //Places all the panels inside the master panel location with their "Vlue number"
        masterPanel.add(addJournalPanel,"2");
        masterPanel.add(searchPanel,"3");
        
        JMenu commands = new JMenu("Commands");   //Creates the commands menu.
        JMenuItem addChoice = new JMenuItem("Add");
        addChoice.addActionListener(new AddListener( ));
        commands.add(addChoice);

        JMenuItem searchChoice = new JMenuItem("Search");
        searchChoice.addActionListener(new SearchListener( ));
        commands.add(searchChoice);

        JMenuItem quitChoice = new JMenuItem("Quit");
        quitChoice.addActionListener(new QuitListener( ));
        commands.add(quitChoice);
        
        JMenuBar bar = new JMenuBar( );
        bar.add(commands);
        setJMenuBar(bar);
        
        add(masterPanel); 
        
        addB.addActionListener(new AddButtonListener());  //Sets up the buttons with their listeners from above.
        resetB.addActionListener(new ResetButtonListener());
        searchB.addActionListener(new SearchButtonListener());
    }
  
    /* This function is used to set up the Journal Screen */
    private void setJournal() {
        textSpace.setEditable(false);
        cords = new GridBagConstraints();
        //bORJ.addActionListener(new BorJComboBoxListener());
        cords.anchor = GridBagConstraints.NORTHWEST;
        cords.insets = new Insets(4,10,4,0);
        cords.gridx = 0;
        cords.gridy = 0;
        addJournalPanel.add(topLabel,cords);     //Sets title on first line  
        cords.gridx = 0;
        cords.gridy = 1;
        addJournalPanel.add(typeLabel,cords); //Sets type (book or journal) label on second line 
        cords.gridx = 1;
        cords.gridy = 1;
        addJournalPanel.add(bORJ,cords); //Sets combo boxe on second line beside label 
        cords.gridx = 0;
        cords.gridy = 2;
        addJournalPanel.add(callNumberLabel,cords); //Places the Call number on the third line
        cords.gridx = 0;
        cords.gridy = 3;
        addJournalPanel.add(titleLabel,cords);  //Places the title label on the fourth line 
        cords.gridx = 0;
        cords.gridy = 4;
        addJournalPanel.add(organizationLabel,cords); //Places the organization label on the fifth line
        cords.gridx = 0;
        cords.gridy = 5;
        addJournalPanel.add(yearLabel,cords); //places the year label on the sixth line
        cords.gridx = 1;
        cords.gridy = 2;
        addJournalPanel.add(callNumberTF,cords);  //places call number info box beside call number
        cords.gridx = 1;
        cords.gridy = 3;
        addJournalPanel.add(titleTF,cords);   //places title info box beside title
        cords.gridx = 1;
        cords.gridy = 4;
        addJournalPanel.add(organizationTF,cords);  //places organization info box beside organization
        cords.gridx = 1;
        cords.gridy = 5;
        addJournalPanel.add(yearTF,cords);   //places year info box beside year
        cords.gridx = 0;
        cords.gridy = 6;
        addJournalPanel.add(blankSpace,cords);
        cords.gridx = 0;
        cords.gridy = 7;
        addJournalPanel.add(blankSpace,cords);
        cords.gridx = 0;
        cords.gridy = 8;
        addJournalPanel.add(messageLabel,cords); //places messages label
        cords.gridx = 0;
        cords.gridy = 9;
        cords.gridwidth = 4;
        cords.gridheight = 2;
        cords.insets = new Insets(1,10,1,0);    //Sets up message box
        addJournalPanel.add(messageArea,cords);
        messageArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        textSpace.setText("Enter a year from 1000-9999.\n A call number, title and year are MANDATORY.\n");
        
        cords.insets = new Insets(1,10,1,0);
        cords.gridx = 3;     //Places buttons
        cords.gridy = 3;
        addJournalPanel.add(addB,cords);
        cords.gridx = 3;
        cords.gridy = 5;
        addJournalPanel.add(resetB,cords);
        
        addJournalPanel.revalidate();   //Sets the newly created gui screen to the masterPanel and display
        addJournalPanel.repaint();
        masterPanel.add(addJournalPanel,"1");
        add(masterPanel);
        currScreen.show(masterPanel,"1");
    }
    
    private void setBook() {
        textSpace.setEditable(false);
        cords = new GridBagConstraints();
        //bORJ.addActionListener(new BorJComboBoxListener());  //Same as above.
        cords.anchor = GridBagConstraints.NORTHWEST;
        cords.insets = new Insets(4,10,4,0);
        cords.gridx = 0;
        cords.gridy = 0;
        addBookPanel.add(topLabel,cords);       
        cords.gridx = 0;
        cords.gridy = 1;
        addBookPanel.add(typeLabel,cords);
        cords.gridx = 1;
        cords.gridy = 1;
        addBookPanel.add(bORJ,cords);
        cords.gridx = 0;
        cords.gridy = 2;
        addBookPanel.add(callNumberLabel,cords);
        cords.gridx = 0;
        cords.gridy = 3;
        addBookPanel.add(authorLabel,cords);
        cords.gridx = 0;
        cords.gridy = 4;
        addBookPanel.add(titleLabel,cords);
        cords.gridx = 0;
        cords.gridy = 5;
        addBookPanel.add(publisherLabel,cords);
        cords.gridx = 1;
        cords.gridy = 2;
        addBookPanel.add(callNumberTF,cords);
        cords.gridx = 1;
        cords.gridy = 3;
        addBookPanel.add(authorTF,cords);
        cords.gridx = 1;
        cords.gridy = 4;
        addBookPanel.add(titleTF,cords);
        cords.gridx = 1;
        cords.gridy = 5;
        addBookPanel.add(publisherTF,cords);
        cords.gridx = 0;
        cords.gridy = 6;
        addBookPanel.add(yearLabel,cords);
        cords.gridx = 1;
        cords.gridy = 6;
        addBookPanel.add(yearTF,cords);
        cords.gridx = 0;
        cords.gridy = 8;
        addBookPanel.add(messageLabel,cords);
        cords.gridx = 0;
        cords.gridy = 9;
        cords.gridwidth = 4;
        cords.gridheight = 2;
        cords.insets = new Insets(1,10,1,0);
        addBookPanel.add(messageArea,cords);
        messageArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        textSpace.setText("Seperate the authors via comma. \n" + "Enter a year from 1000-9999.\n A call number, title and year are MANDATORY.\n");
        
        cords.insets = new Insets(1,10,1,0);
        cords.gridx = 3;
        cords.gridy = 3;
        addBookPanel.add(addB,cords);
        cords.gridx = 3;
        cords.gridy = 5;
        addBookPanel.add(resetB,cords);
        
        addBookPanel.revalidate();
        addBookPanel.repaint();
        masterPanel.add(addBookPanel,"1");
        add(masterPanel);
        currScreen.show(masterPanel,"1");
    }
    
    private void setSearch() {
        
        cords = new GridBagConstraints();
        cords.anchor = GridBagConstraints.NORTHWEST;
        cords.insets = new Insets(4,10,4,0);
        cords.gridx = 0;
        cords.gridy = 0;
        searchPanel.add(topLabel2,cords);     //Sets title on first line  
        cords.gridx = 0;
        cords.gridy = 1;
        searchPanel.add(typeLabel,cords); //Sets type (Both or book or journal) label on second line 
        cords.gridx = 1;
        cords.gridy = 1;
        searchPanel.add(searchFind,cords); //Sets combo boxe on second line beside label       
        cords.gridx = 0;
        cords.gridy = 2;
        searchPanel.add(callNumberLabel,cords);
        cords.gridx = 1;
        cords.gridy = 2;
        searchPanel.add(callNumberTF,cords);
        cords.gridx = 0;
        cords.gridy = 3;
        searchPanel.add(titleLabel2,cords);
        cords.gridx = 1;
        cords.gridy = 3;
        searchPanel.add(titleTF,cords);
        cords.gridx = 0;
        cords.gridy = 4;
        searchPanel.add(year1Label,cords);  //Sets the cords up for the text areas
        cords.gridx = 1;
        cords.gridy = 4;
        searchPanel.add(year1TF,cords);
        cords.gridx = 0;
        cords.gridy = 5;
        searchPanel.add(year2Label,cords);
        cords.gridx = 1;
        cords.gridy = 5;
        searchPanel.add(year2TF,cords);
        cords.gridx = 0;
        cords.gridy = 6;
        searchPanel.add(blankSpace,cords);
        cords.gridx = 0;
        cords.gridy = 7;
        searchPanel.add(searchLabel,cords);
        cords.gridx = 0;
        cords.gridy = 8;
        cords.gridwidth = 4;
        cords.gridheight = 2;
        cords.insets = new Insets(1,10,1,0);
        searchPanel.add(messageArea,cords);
        messageArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        textSpace.setText("Seperate the title key words by a comma.\n" + "Enter a start year from 1000-9999.\n" + "Enter an end year from 1000-9999.\nIf given, start year must be smaller than end year.\n");
        
        cords.insets = new Insets(1,10,1,0);
        cords.gridx = 3;
        cords.gridy = 2;
        searchPanel.add(searchB,cords);
        cords.gridx = 3;
        cords.gridy = 4;
        searchPanel.add(resetB,cords);
        
        searchPanel.revalidate();
        searchPanel.repaint();
        masterPanel.add(searchPanel,"3");
        add(masterPanel);
        currScreen.show(masterPanel,"3");
    }
}
