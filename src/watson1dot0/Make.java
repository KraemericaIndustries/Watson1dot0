package watson1dot0;

import java.util.LinkedList;

public class Make {

	//  Make broadcasts to the UI
	public static String UI(String type, int passNumber) {
		
		String message = "";
		
		switch(type) {
		case "newGuess": message =        "Pass #" + passNumber + ".  Go ahead and Guess a word:";
			break;
		case "askOpponent": message =     "Pass #" + passNumber + ".  What is the response from your opponent?  (0, 1, 2, 3, 4, 5):"; 
			break;
		case "changeOneLetter": message = "Pass #" + passNumber + ".  Guess a word.  Change 1 letter from the previous guess.";
			break;
		case "result": message = "Pass #" + passNumber + " Result:";
			break;
		default: type = "Unknown type";
			break;
		}  //  switch
		return message;		
	}  //  UI()
	
	
	//  Make a word Guess by storing a string from the InputStream from the keyboard.  Convert the string to a 'LinkedList' (for easier iteration and manipulation), and return it to the caller.  
	public static LinkedList<String> guess(String nextPassType, int passNumber) {

		LinkedList<String> guessList = new LinkedList<String>();  //  Declare a collection in which to store the guess
		
		System.out.println(Make.UI("newGuess", passNumber));	  //  Pass direction to the console
		String guessString = Get.keyboardInput();				  //  Take the InputStream typed at the keyboard, and store the resulting String in the 'guessString' variable 
		guessString = guessString.toUpperCase();				  //  Regardless of the case in the InputStream typed at the keyboard, convert the String contents of the 'guessString' variable to UPPERCASE
		guessList = stringToList(guessString);					  //  Execute the stringToList() method to parse the String such that each element of the collection contains a single letter
		return guessList;
	}  //  guess
	
	
	//  Load the guess captured from the keyboard in the form of a string passed in as the parameter 'MakeGuessGuessString' into a LinkedList<String> collection for ease of 
	//  iteration and manipulation, and return it to the caller:
	public static LinkedList<String> stringToList (String guessString) {
//		System.out.println("Entering 'MakeGuess:stringToList' method...");                            //  DEBUG
//		System.out.println("MakeGuess:stringToList:MakeGuessGuessString = " + MakeGuessGuessString);  //  DEBUG
		LinkedList<String> guessList = new LinkedList<String>();
		for(int x = 0; x <= 4; x++) {
			guessList.add(guessString.substring(x, (x + 1)));
		}  //  for
//		System.out.println("MakeGuess:stringToList:guessList = " + guessList);                        //  DEBUG
//		System.out.println("Exiting 'MakeGuess:stringToList' method...");                             //  DEBUG
//		System.out.println();
		return guessList;		
	}  //  stringToList
}  //  class