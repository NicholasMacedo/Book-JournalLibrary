/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearcha1;

import java.util.ArrayList;

/**
 * @author Nicholas
 */
public abstract class Reference {
    private String callNumber;
    private String title;
    private int year;
    private ArrayList<String> titleKeyWords;

    /**
     * Constructor for the super class. 
     * @param callNumber
     * @param title
     * @param year
     * @throws java.lang.Exception
     */
    public Reference(String callNumber, String title,int year) throws Exception{
        if (callNumber == null || title == null || year < 1000 || year > 9999){
            throw new Exception ("Error. Invalid Information.\n");
        }
        this.callNumber = callNumber;
        this.title = title;
        this.year = year;
        this.titleKeyWords = setTitleKeyWords(title);
    }
    
    /**
     * @return the callNumber
     */
    public String getCallNumber() {
        return callNumber;
    }

    /**
     * @param callNumber the callNumber to set
     */
    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the titleKeyWords
     */
    public ArrayList<String> getTitleKeyWords() {
        return titleKeyWords;
    }

    /**
     * @param titleKeyWords the titleKeyWords to set
     * @param title
     */
    
    /**
     * Used to create the array of title key words used in searching and hashing. 
     */
    private ArrayList<String> setTitleKeyWords(String title) {
        ArrayList<String> keyWords = new ArrayList<String>();
        String[] tokens = title.split(" ");
        for (String token : tokens)
        {
            keyWords.add(token.toLowerCase());
        }
        return(keyWords);
    }

    @Override
    /**
     * Converts the class to a string.
     * @return
     */
    public String toString(){
        return(callNumber + " "+ title + " " + year);
    }
    
    /**
     * Checks to see if one reference equal to the other in every regard.
     * @param checkMe
     * @return
     */
    public boolean equals(Reference checkMe){
        if(callNumber.equals(checkMe.getCallNumber()) && year == checkMe.getYear() && title.equals(checkMe.getTitle())){
            return(true);
        }
        return (false);
    }
}
