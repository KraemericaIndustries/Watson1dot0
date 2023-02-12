package watson1dot0;

import java.util.Scanner;

public class Get {
	
	//  Receive a response in the form of a string.  Convert the type from 'String' to 'int', and return the 'int' to the caller.  
	public static int response(String nextPassType, int passNumber) {
		System.out.println(Make.UI("askOpponent", passNumber));
		String responseString = keyboardInput();
		int responseInt = stringToInt(responseString);
		return responseInt;
	}  //  response()
	
	
	//	Capture the 'InputStream' from the keyboard in a variable of type 'String' named 'capture'.  
	//  Return 'capture' to the caller for use.  
	public static String keyboardInput() {
//		System.out.println("Entering 'MakeGuess:keyboard' method...");  //  DEBUG
		Scanner input= new Scanner(System.in);
		String capture = input.nextLine();
//		input.close();
//		System.out.println("keyboard:capture = " + capture);            //  DEBUG
//		System.out.println("Exiting 'MakeGuess:keyboard' method...");   //  DEBUG
		System.out.println();
		return capture;
	}  //  keyboardInput()
	
	
    //  The response from your opponent is captured from keyboard with the scanner class are in the form of a string.  Number strings must be converted to integers to allow arithmetic operations on them:
	public static int stringToInt (String MakeGuessResponse) {	
//		System.out.println("Entering 'stringToInt' method...");                                //  DEBUG
//		System.out.println("MakeGuess:stringToInt:MakeGuessResponse = " + MakeGuessResponse);  //  DEBUG
		int MakeGuessResponseInt = Integer.parseInt(MakeGuessResponse);
//		System.out.println("stringToInt:responseInt: " + MakeGuessResponseInt);                //  DEBUG
//		System.out.println("Exiting 'stringToInt' method...");                                 //  DEBUG
//		System.out.println();                                                                  //  DEBUG
		return MakeGuessResponseInt;
	}  //  stringToInt	
}  //  class