/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.search.a3;

import java.io.IOException;

/**
 *
 * @author Nicholas
 */
public class LibrarySearchA3 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, Exception {
        
        librarysearcha1.LibrarySearch library = new librarysearcha1.LibrarySearch(); //Creates the library
        String inputFile = "NULL";
        String outputFile = "NULL";
        if(args.length == 2){     //Checks to see how many arguments and assigns input/output values
            inputFile = args[0];
            outputFile = args[1]; 
        }
        else if (args.length == 1){
            outputFile = args[0];
        }
        else{
            System.out.println("Error. Commandline Arguments needed.  {output} or {input output}");
            System.exit(0);
        }
        //System.out.println(inputFile +":"+outputFile); /* Used for testing. */
        
        if(inputFile.equals("NULL") == false){   // If an input file is given load the library with the file.
            library.importFromFile(inputFile);
        }
        
        LibraryGui gui = new LibraryGui(library,outputFile);  //Calls the gui and passes in the library and output file.
        gui.setVisible(true);
    }

} //End Bracket




