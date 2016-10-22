/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearcha1;

import java.util.ArrayList;

/**
 * @author Nicholas Macedo
 */
public class Book extends Reference {
    private ArrayList<String> bookAuthors;
    private String publisher = null;

    /**
     * Uses the Reference class constructor inside the constructor for the class.
     * @param callNumber
     * @param title
     * @param year
     * @param authors
     * @param publisher
     * @throws java.lang.Exception
     */
    public Book(String callNumber, String title, int year, ArrayList authors, String publisher) throws Exception{
        super(callNumber,title,year);
        if (authors == null || publisher == null){
            throw new Exception ("Error. Invalid Information.\n");
        }
        this.bookAuthors = authors;
        this.publisher = publisher;
    }
    
    /**
     * Prints the book to the screen.
     * @return 
     */
    public String printBook() {
        int i;
        String toPrint;
        toPrint = "\n~~Book~~\n"+ getCallNumber() +"\n";
        if(!getBookAuthors().isEmpty() == true){
            for(i=0;i<(getBookAuthors().size()-1);i++){
            toPrint = toPrint +getBookAuthors().get(i)+",";    
            }
            toPrint = toPrint +getBookAuthors().get(i) + "\n";
        }
        toPrint = toPrint + getTitle() + "\n" + getYear() + "\n";
        return(toPrint);
    }
    
    /**
     * @return the bookAuthors
     */
    public ArrayList<String> getBookAuthors() {
        return bookAuthors;
    }

    /**
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }
    
    /**
     * Converts the book into a string.
     * @return
     */
    @Override
    public String toString(){
        int i;
        String toBeReturned = "B,";
        toBeReturned = toBeReturned + getCallNumber()+","+getYear()+","+getTitle()+","+getPublisher()+",";
        if(getBookAuthors().isEmpty() == true){
            toBeReturned = toBeReturned + "NULL";  
        }
        else{
            for(i=0;i<(getBookAuthors().size()-1);i++){
            toBeReturned = toBeReturned +getBookAuthors().get(i)+",";    
            }
            toBeReturned = toBeReturned +getBookAuthors().get(i);
        }
        return(toBeReturned);
    }
    
    /**
     * Checks to see if two books are equal in every regard.
     * @param checkMe
     * @return
     */
    public boolean equals(Book checkMe){
        if(getCallNumber().equals(checkMe.getCallNumber()) && getYear() == checkMe.getYear() && getTitle().equals(checkMe.getTitle())){
            if(publisher.equals(checkMe.getPublisher())){
                for(String currString: checkMe.getBookAuthors()){
                    if (bookAuthors.contains(currString) == false){
                        return (false);
                    }
                }
            }
            return (true);
        }
        return (false);
    }
} //End Brace
