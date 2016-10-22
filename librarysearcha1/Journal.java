/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearcha1;


/**
 * @author Nicholas Macedo
 */
public class Journal extends Reference {
    private String organization;
    
    /**
     * Uses the reference class constructor as well.
     * @param callNumber
     * @param title
     * @param year
     * @param organization
     * @throws java.lang.Exception
     */
    public Journal(String callNumber, String title, int year, String organization) throws Exception{
        super(callNumber,title,year);
         if (organization == null){
            throw new Exception ("Error. Invalid Information.\n");
        }
        this.organization = organization;
    }
    /** 
    * Prints the journal to the screen.
     * @return 
    */
    public String printJournal() {
        if (getOrganization().equals("NULL") == false){
        return("\n~~Journal~~\n"+ getCallNumber() +"\n"+ getTitle() +"\n"+ getOrganization() +"\n"+ getYear() +"\n");
        }
        return("\n~~Journal~~\n"+ getCallNumber() +"\n"+ getTitle() +"\n"+ getYear() +"\n");
    }    
    
    
    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }
    
    @Override
     /**
     * Converts the entire class into a string.
     * @return
     */
    public String toString(){
        return("J,"+getCallNumber()+","+getYear()+","+getTitle()+","+getOrganization());
    }  
    
    /**
     * Checks to see if then given journal is equal to the current journal in every regard.
     * @param checkMe
     * @return
     */
    public boolean equals(Journal checkMe){
        if(checkMe.equals(checkMe)==true){
            if(getOrganization().equals(checkMe.getOrganization())){
                return(true);
            }
        }
        return(false);
    }
}// End Brace
