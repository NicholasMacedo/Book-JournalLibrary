/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearcha1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Nicholas Macedo - Van Horne
 */
public class LibrarySearch {
    private ArrayList<Reference> libraryItemList;
    private HashMap <String, ArrayList<Reference>> itemHashMap; //Creates HashMap using String keys and array lists of reference for the values.
    
    /**
     * Constructor creates array list for references and hashmap.
     */
    public LibrarySearch(){
        libraryItemList = new ArrayList<Reference>();
        itemHashMap = new HashMap<String, ArrayList<Reference>>();
    }
    
    /**
     * Adds the given reference (book or journal) to the list and HashMap.
     * @param newItem
     */
    public void addItem(Reference newItem) {
        this.libraryItemList.add(newItem);
        addToHashMap(newItem);
    }

    /**
     * Checks if the book or journal (Reference) is in the library using a for loop.
     * @param toBeChecked
     * @return
     */
    public boolean isInLibrary(Reference toBeChecked){
        int i;
        if (libraryItemList.isEmpty() == true){ //If there is nothing in the library then it isnt in there.
            return(false);
        }
        for (i=0;i < libraryItemList.size();i++){ //Loops through list and checks to see if the reference exists through callNumber and year.
           if (toBeChecked.getYear()== libraryItemList.get(i).getYear() && toBeChecked.getCallNumber().equals(libraryItemList.get(i).getCallNumber())){
               return(true);    
           } 
        }
        return(false); //If it doesnt match then its not in library.
    } 
    
    /**
     * Uses the passed in info to generate and print a list of specific matched items.
     * @param itemCallNumber
     * @param yearOne
     * @param yearTwo
     * @param keyWords
     * @return
     */
    public ArrayList<Reference> printSpecificItems(String itemCallNumber, int yearOne, int yearTwo, ArrayList<String> keyWords) {
        ArrayList<Reference> matchItemList = new ArrayList<Reference>();
        boolean callNumMatch;
        boolean yearMatch;
        ArrayList<Reference> keyWordMatch = new ArrayList<Reference>();
        keyWordMatch = keyWordMatch(keyWords); //Gets list of matches from the key words.
        if(keyWordMatch == null){
            //System.out.println("No items in the library matched this search.");
            return matchItemList;
        }
        for(Reference currItem: keyWordMatch){ //Loop through the list of passed keyWord matches
            callNumMatch = callNumMatch(currItem.getCallNumber(),itemCallNumber);
            yearMatch = yearMatch(currItem.getYear(),yearOne,yearTwo);
            if (callNumMatch == true && yearMatch == true){
                matchItemList.add(currItem);
            }
        }
        if(matchItemList.isEmpty() == false){ //If there are items in the list, print it.
            return matchItemList;
        }
        //System.out.println("No items in the library matched this search.\n");
        return matchItemList;
    }

    /**
     * Checks to see if the callNumber given matches the call number for the check. Returns true if match and false if doesn't.
     * @param callNumber
     * @param itemCallNumber
     * @return
     */
    private boolean callNumMatch(String callNumber, String itemCallNumber) {
        if("NULL".equals(itemCallNumber)){
            return(true);
        }
        else if (itemCallNumber.equals(callNumber)){
            return(true);
        }
        return(false);
    }

    /**
     * Checks if the year follows the rules given in the year search.
     * @param year
     * @param yearOne
     * @param yearTwo
     * @return
     */
    private boolean yearMatch(int year, int yearOne, int yearTwo) {
        if(yearOne == 0 && yearTwo == 0){
            return(true);
        }
        else if(year == yearOne){
            return(true);
        }
        else if(year >= yearOne && year <= yearTwo){
            return(true);
        }
        return(false);
    }

    /**
     * Uses the list of keyWords to get a list of References that follow the keywords rule.
     * @param keyWords 
     * @return
     */
    private ArrayList<Reference> keyWordMatch(ArrayList<String> keyWords) {
        ArrayList<Reference> sameRefs = new ArrayList<Reference>();
        if(keyWords.isEmpty() == true){ //if no words have been entered return all refrences in the list.
            return(libraryItemList);
        }
        if(keyWords.size() == 1){ //If there is just one key word given then check if its in the table table and returns the list under that key word. Else returns null.
            if(itemHashMap.containsKey(keyWords.get(0))){
                return(itemHashMap.get(keyWords.get(0)));
            }
            else{
                return(null);
            }
        }
        else{ 
            for(String currKeyWord: keyWords){    //Loops through each word given in the key words.
                if(itemHashMap.containsKey(currKeyWord)){ //If the map contains the key word it does more.
                    if(sameRefs.isEmpty() == true){  //If sameRefs is empty then it is the first word in the list and saves the entire list.
                        sameRefs = itemHashMap.get(currKeyWord);
                    }
                    else{ //Compares the two lists sameRefs and the keyList using the compareLists function and saves the similarities in the list sameRefs
                        sameRefs = compareLists(sameRefs,itemHashMap.get(currKeyWord));
                        if(sameRefs.isEmpty()){
                            return(null);
                        }
                    }
                }
                else{  //Returns null if keyword is not found because it wouldent match anything.
                    return(null);
                }
                
            }
        }
        return(sameRefs); //Returns the list of sameRefs after going through.
    }

    /**
     * Outputs the information in the reference list to the file given.
     * @param outputFile
     */
    public void addToFile(String outputFile) {
       PrintWriter outputStream = null;
       
        try{
           outputStream = new PrintWriter(new FileOutputStream(outputFile));  //Opens the file and catches the error if one is created.
        } catch (FileNotFoundException e){
            System.out.println("Error opening the file "+outputFile);
            System.exit(0);
        }
        
        for (Reference currItem: libraryItemList){  //Downcasts the reference to print the info as a Book or journal using the toString funtion.
            if (currItem instanceof Book){
                Book aBook = (Book) currItem;
                outputStream.println(aBook.toString());
            }
            else if (currItem instanceof Journal){
                Journal aJournal = (Journal) currItem;
                outputStream.println(aJournal.toString());
            }
        }
        outputStream.close();
    }

    /**
     * Ads the information from the file given to the reference list.
     * @param inputFile
     * @throws java.lang.Exception
     */
    public void importFromFile(String inputFile) throws Exception {
        Scanner inputStream = null;
        int i;
        String callNumber;
        int year;
        boolean isInLibrary;
        String title;
        String orgOrPub;

        try {
              inputStream = new Scanner(new FileInputStream(inputFile));   //Opens the file and catches error is one occurs.
        } catch (FileNotFoundException e) {
            System.out.println("File was not found or could not be opened. Critical Error. Exiting Program.");
            System.exit(0);
        }
        while(inputStream.hasNextLine() == true){
            String[] tokens = inputStream.nextLine().split(",");    //Parses the info from the file.  
            if ("B".equals(tokens[0])){
                ArrayList<String> authors = new ArrayList<String>();
                callNumber = tokens[1];
                year = Integer.parseInt(tokens[2]);
                title = tokens[3];
                orgOrPub = tokens[4];
                if(!tokens[5].equals("NULL")){
                if(tokens.length > 5){
                        for(i=5;i<tokens.length;i++){
                            authors.add(tokens[i]);
                        }
                    }
                }
                Book newBook = new Book(callNumber,title,year,authors,orgOrPub);
                isInLibrary = isInLibrary(newBook);
                if (isInLibrary == false) /* If it is not in the library add it */
                {
                    addItem(newBook);
                    //newBook.printBook();      //Used for testing
                }
                
            }
            else{
                callNumber = tokens[1];
                year = Integer.parseInt(tokens[2]);
                title = tokens[3];
                orgOrPub = tokens[4];
                Journal newJournal = new Journal(callNumber,title,year,orgOrPub);
                isInLibrary = isInLibrary(newJournal);
                if (isInLibrary == false) /* If it is not in the library add it */
                {
                    addItem(newJournal);
                   // newJournal.printJournal();    //Used for testing
                }
            }
        }
        inputStream.close();
    }
    
    /**
     * Adds the given reference to the hash map.
     * @param toBeAdded
     */
    public void addToHashMap(Reference toBeAdded){ //Takes HM and class to be added to the HM.
       for(String currKey: toBeAdded.getTitleKeyWords()){
            if (itemHashMap.containsKey(currKey) == false){
                itemHashMap.put(currKey,new ArrayList<Reference>()); //Check if the key exists. if not make a list in there for the value to be added.
            }
            itemHashMap.get(currKey).add(toBeAdded); //Add the element to the list under the key word.
       } 
    }
    /**
     * compares the two given lists and returns the similar objects.
     * @param listOne
     * @param listTwo
     * @return
     */
    private ArrayList<Reference> compareLists(ArrayList<Reference> listOne, ArrayList<Reference> listTwo) {
        ArrayList<Reference> similar = new ArrayList<Reference>();
        for(Reference currItemOne: listOne){
            for(Reference currItemTwo: listTwo){
                if(currItemOne.getYear() == currItemTwo.getYear()){
                    if(currItemOne.getCallNumber().equals(currItemTwo.getCallNumber())){
                        similar.add(currItemOne);
                    }
                }
            }
        }
        return(similar);
    }
    
}//End Bracket
