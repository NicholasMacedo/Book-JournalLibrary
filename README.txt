
		=-=-=-=-=-=-=-=-= READ ME =-=-=-=-=-=-=-=-=

*************               *************************************
 The Problem                Nicholas Macedo - Van Horne (0889469)
*************               *************************************

	The program's task is to implement a GUI using Java Swing for the Library Search program. The library search program has the ability to add,store and search for the given books and journals from the user using the GUI. Books and journals have their own respective properties but each share three similar properties. They both have a title, year of publication and a call number. When creating a book or journal, the user selects the "ADD" option in the "Commands" drop down menu. Once this is done the user is brought to the add book screen. From here, the user can choose to enter a book or enter a journal. To add the information, the user simply inputs the values into the given fields and presses the "Add" button. When adding a book, the title,year and call number are a must with adding an author and publisher is optional. When adding a journal, the title, year, callnumber and title are a must and adding an organization is optional. Onc the information is entered into the fields, the user presses the add button and the program will check to see if the item already exists (matching year and call number). If the item isnt in the library, the item is added and the user is informed. If it is in the library, the user is informed that the item is already in the library.

	The user also has the ability to search for items in the library by entering the values they would like to search for in the given text field areas. The user also has the option to search for only books or only journals. This can be chosen theough the combo box provided on the GUI. In order to print all of the selected item, the user can leave the given field blank.

	The program has the ability to import from a file as well as output to a file via commandline arguments. If one commandline argument is given then it is an output file. If two commandline arguments are given then the first is the inputfile and the second is the output file.


***************************
 Assumptions & Limitations
***************************

Assumptions:
	-The file that is given to the program is in the proper 			format and unedited by anythinging other than the 			program.
	-When the user has chosen to add the information it is 			correct. There is no editing the information given to
		an already created item.
	-The user follows instructions given via the message box for 		adding a comma between authors and title key words.
	-When asked for strings, the user's input is assumed 				correct.

Limitations:
	- Tampered files generate an error and crashing.


*********
 Testing
*********

The program was tested for many cases.
Book Adding Test Cases:
	- Entering a book with just title,call number,year
	- Entering a book with title,author,call number, year
	- Entering a book with title, multiple authors,call  				number,year
	- Entering a book with title, author,call number,publisher, 		year
	- Enteting a book with title, multiple authors, call number,  		publisher, year
	- Entering a non numeric or incorrect value for year
	- Not giving the manditory fields (Title,CallNumber,Year).
Journal Adding Test Cases:
	- Entering a journal with title,call number, year
	- Entering a journal with title, call number, organization, 		year

Search Function Test Cases:
	- With Call Number given
	- Without Call Number given
	- With Single Year given
	- With Range Given
	- With B4 year Given
	- With after year given
	- With No year given
	- With multiple key words
	- With multiple key words that dont exist
	- With one key word
	- With one key word that doesnt exist
	- Without key words
** And many combinations of this **
*** These test cases can be seen in the input.txt and output.txt documents provided with the code. ***

	All test cases stated above were checked using print statements to display all values. If an error was encountered the NetBeans debugging tool was used to see values and help in the debugging and error discovering process. The values given were talored to the search that was being done to check for correct values. All test cases above were also fixed in the code to produce a more robust code. As well as being fixed, cases where an error message should be shown produce one.

	All user inputted values were tested to insure they do not fail and get the job done. They were tested with the following cases:
	- Incorrect Values (Strings for ints).
	- Nothing entered when value is manditory


	Loops and methods were checked for logical correctness using print statements at critical segments to test values and information being passed around for simple and easy to deduce logical understanding and logic checking.


**********************
Possible Improvements
**********************

	With more time I would generate more test cases as well as refactor code to be more object oriented. This also requires more java knowledge and experience. Geterate a better search function to decrease the computational complexity of algorythms would also improve the prefornance of the code.

