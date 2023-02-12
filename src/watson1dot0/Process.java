package watson1dot0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Process {

	public static Data Turn(Data wordData, ArrayList<Turn> Turns) {
		
		if(Turns.get(Turns.size() - 1).response == Turns.get(Turns.size() - 2).response &&  //  IF the most recent response is EQUAL to the previous response AND
		   wordData.numLettersChanged == 1) {												//  The number of letters changed between turns is 1
			findChangedLetters(wordData, Turns);                                            //  Execute findChangedLetters()
			
			if(wordData.together.isEmpty()) {//  IF the 'together' collection is EMPTY...
				wordData.together.add(wordData.foundChangedTo);
				wordData.together.add(wordData.foundChangedFrom);	
			} 
			
//  Print conclusions...
			Print.conclusion("(Consecutive responses equal)", wordData, Turns);
			if(!(wordData.together.isEmpty())) {
				System.out.println("PROCESS.Turn Since the previous 2 turns have the same response and differ by 1 letter only, we know that: " + wordData.together + ", are either both IN your opponents word, or NOT IN, " + 
								   " but we can't be certain which is the case.");				
			}
			if(!(wordData.alsoTogether.isEmpty())) {
				System.out.println("PROCESS.Turn Since the previous 2 turns have the same response and differ by 1 letter only, we know that: " + wordData.alsoTogether + ", are either both IN your opponents word, or " + 
								   "NOT IN, but we can't be certain which is the case.");				
			}
			wordData.doNotKnow.removeAll(wordData.together);
			wordData.doNotKnow.removeAll(wordData.alsoTogether);
//  ELSE IF more than 1 turn has been taken...			
		} else if(Turns.size() > 1) {															 //  IF more than 1 turn has been taken
			if(Turns.get(Turns.size() - 1).response < (Turns.get(Turns.size() - 2).response)) {  //  IF the latest response is LESS THAN the previous response
				Print.conclusion("(Latest response is LESS THAN previous response)", wordData, Turns);
																					//  Clear the 'knownTogether' collection
				}
			} else if(Turns.get(Turns.size() - 1).response > (Turns.get(Turns.size() - 2).response) && (wordData.numLettersChanged == 1)) {  //  IF the latest response is GREATER THAN the previous response 
				Print.conclusion("(Latest response is GREATER THAN the previous response, 1 letter changed)", wordData, Turns);
			
			} else if (Turns.get(Turns.size() - 1).response >= (Turns.get(Turns.size() - 2).response + 2)) {									//  ELSE IF the latest response is GREATER THAN (OR EQUAL TO) the previous response
				Print.conclusion("(Latest response is GREATER THAN (OR EQUAL TO) the previous response PLUS 2)", wordData, Turns);

			}			
			countChangedLetters(wordData, Turns);	   //  Count the number of letters that have changed between consecutive turns
			if(wordData.numLettersChanged == 1) {	   //  IF the number of letters changed between the latest and previous turn is 1
				findChangedLetters(wordData, Turns);   //  Execute findChangedLetters() 
				System.out.println("'" + wordData.foundChangedFrom + "' changed to '" + wordData.foundChangedTo + "' on most recent turn.");
				System.out.println("# Letters changed in last 2 turns: " + wordData.numLettersChanged + "\n");
			}
		System.out.println();
		return wordData;	
		}  //  Turn()

	
	public static ArrayList<Turn> alphabetizeAllGuesses(ArrayList<Turn> Turns) {  //  Sort every guess of every turn in ALPHABETICAL order
		
		LinkedList<String> token = new LinkedList<String>();					//  Initialize a token list to allow for creation of a turn
		
		Turn turnToSort = new Turn(token, 0);									//  Create a token turn
		for(int x = (Turns.size() - 1); x >= 0 ; x--) {							//  Iterate over the 'Turns' collection (stack), with 'x' serving as an index for the collection
			turnToSort  = Turns.get(x);											//  GET the 'turn' from the stack at the index stored as the value to a variable of 'x' 
			Collections.sort(turnToSort.guess);									//  Sort the guess for this 'turn' alphabetically
		}
		return Turns;
	}
	
	
	public static Data alphabetizeAllLists(Data wordData) {  //  Sort named lists in the 'wordData' object in ALPHABETICAL order
		if(!(wordData.inOpponentsWord.isEmpty())) Collections.sort(wordData.inOpponentsWord);
		if(!(wordData.notInOpponentsWord.isEmpty())) Collections.sort(wordData.notInOpponentsWord);
		if(!(wordData.together.isEmpty())) Collections.sort(wordData.together);
		if(!(wordData.alsoTogether.isEmpty())) Collections.sort(wordData.alsoTogether);
		return wordData;
	}
	
	
	public static Data countChangedLetters(Data wordData, ArrayList<Turn> Turns) {	 //  Count the number of letters that has changed between consecutive turns		
		
		wordData.numLettersChanged = 0;																						  //  Initialize to zero
		
		System.out.println("Counting the changed letters between: " + Turns.get(Turns.size() - 2).guess + " and: " + Turns.get(Turns.size() - 1).guess + "...");
		
		for(String currentLetter : Turns.get(Turns.size() - 1).guess) {									  //  FOR each letter in the most recent guess
			if(!(Turns.get(Turns.size() - 2).guess.contains(currentLetter))) wordData.numLettersChanged++;  //  IF the previous guess contains that letter, increment the 'numLettersChanged' counter
		}
		System.out.println("The number of letters changed between: " + Turns.get(Turns.size() - 2).guess + " and: " + Turns.get(Turns.size() - 1).guess + " is: " + wordData.numLettersChanged + "\n");
		return wordData;																									  //  Return 'wordData' to the caller
	}  //  countChangedLetters()
	
	
	public static Data findChangedLetters(Data wordData, ArrayList<Turn> Turns) {  //  When consecutive guesses differ by only 1 letter, identify the letter changed to, and changed from
		
		wordData.foundChangedTo = null;	   //  Initialize to null
		wordData.foundChangedFrom = null;  //  Initialize to null
		
//		System.out.println("Entering 'ProcessTurn:findChangedLetters' method...");  	   			  //  DEBUG
//		System.out.println("Data received by ProcessTurn:ChangedTo method:");  						  //  DEBUG
//		System.out.println(Data);											   						  //  DEBUG
//  	System.out.println("First, let's find the letter that was CHANGED TO in the current guess");  //  DEBUG
		boolean exists = false;														//  Initialize a flag to false
		for (String currentGuessLetter : Turns.get(Turns.size() - 1).guess) {		//  FOR each letter in the latest guess  
			exists = false;
			for (String previousGuessLetter : Turns.get(Turns.size() - 2).guess) {  //  FOR each letter in the previous Guess
				if (currentGuessLetter.equals(previousGuessLetter)) {				//  IF the letter from the current guess equals the letter from the previous guess
					exists = true;													//  Set the flag to 'true'
					break;  //  A match for a letter in the new guess has been found in the previous guess.  Break the loop since it is unnecessary to check the remainder of the previous guess.
				}  //  if
				//  When letters do not match, control is passed here:
//				System.out.println(currentGuessLetter + " from the new word does not equal " + previousGuessLetter + " from the previous word.");  //  DEBUG
//				System.out.println("The letter from the previous word that was changed is: " + previousGuessLetter);  							   //  DEBUG
			}  //  inner-for
				//  When no match at all is found, control passes here.  When a BREAK statement is encountered in the IF block, control passes here:
				if (exists == false) {  //  Check the flag.  If it is not set, then, we have found the letter in the new word that does not match any letters in the previous word.
//					System.out.println("The flag HAS NOT been set.  exists = " + exists);													   //  DEBUG
//					System.out.println("The letter from the new guess that does not appear in the previous guess is: " + currentGuessLetter);  //  DEBUG
//					System.out.println("Returning: " + currentGuessLetter);                                                                    //  DEBUG
					wordData.foundChangedTo = currentGuessLetter;																			   //  Update the data to reflect the letter changed to in the latest guess
					break;  //  The letter has been positively identified.  The outer enhanced-for loop may be broken, and control is passed outside the outer enhanced=for loop.
				}  //  if
//				System.out.println("The flag is set.  exists=" + exists);  	  //  DEBUG
//				System.out.println();										  //  DEBUG
		}  //  outer-for
//		System.out.println("Exiting the 'ProcessTurn:findChangedLetters' method...");  //  DEBUG
//		System.out.println(); 		   												   //  DEBUG
			
//	  	System.out.println("Next, let's find the letter that was CHANGED FROM in the previous guess");  //  DEBUG 
		exists = false;															   //  Set the flag to 'false'
		for (String previousGuessLetter : Turns.get(Turns.size() - 2).guess) {	   //  FOR each letter in the previous Guess
			exists = false;														   //  Set the flag to 'false'
			for (String currentGuessLetter : Turns.get(Turns.size() - 1).guess) {  //  FOR each letter in the latest guess
				if (previousGuessLetter.equals(currentGuessLetter)) {			   //  IF the letter from the previous guess equals the letter from the current guess
//					System.out.println(previousGuessLetter + " from the previous guess does equal " + currentGuessLetter + " from the current guess.");
					exists = true;												   //  Set the flag to 'true'
					break;  //  A match for a letter in the previous guess has been found in the current guess.  Break the loop since it is unnecessary to check the remainder of the current guess.
				}  //  if
				//  When letters do not match, control is passed here:
//				System.out.println(previousGuessLetter + " from the previous guess does not equal " + currentGuessLetter + " from the current guess.");  //  DEBUG
//				System.out.println("The letter from the previous word that was changed is: " + previousGuessLetter);  									 //  DEBUG
			}  //  inner-for
			//  When no match at all is found, control passes here.  When a BREAK statement is encountered in the IF block, control passes here:
			if (exists == false) {																												//  Check the flag.  If it is not set, then, we have found the letter in the new word that does not match any letters in the previous word.
//				System.out.println("The flag HAS NOT been set.  exists = " + exists);															//  DEBUG
//				System.out.println("The letter from the previous guess that does not appear in the current guess is: " + previousGuessLetter);  //  DEBUG
//				System.out.println("Returning: " + previousGuessLetter);                                                                        //  DEBUG
				wordData.foundChangedFrom = previousGuessLetter;																				//  Update the data to reflect the letter changed from in the previous guess
			}  //  if
//			System.out.println("The flag is NOT set.  exists=" + exists);
//			System.out.println();  											//  DEBUG
		}  //  outer-for				
//		System.out.println("Exiting 'ProcessTurn:ChangedFrom' method...");  //  DEBUG
//		System.out.println();                                      			//  DEBUG
//		wordData.doNotKnow.removeAll(wordData.knownTogether);  //  In the 'doNotKnow' collection, remove all items found in the 'knownTogether' collection
		return wordData;
	}  //  findChangedLetters			

	
//	public static ArrayList<Turn> removeKnown(Data wordData, ArrayList<Turn> Turns) {  //  Remove 'known' letters from all turns
////		System.out.println("All letters that are KNOWN: " + wordData.inOpponentsWord + wordData.notInOpponentsWord + " can be removed from all guesses (provided the response is decremented accordingly):\n" + Turns + " becomes:");  //  DEBUG
//		//  Create a collection to store letters from the guess, that are already known, so that they may be removed
//		//  Removing from a collection using an enhanced-for loop 'Wile E. Coyote's the iterable...
//		LinkedList<String> toRemove = new LinkedList<String>();
//
//		for(int x = 0; x <= (Turns.size() - 1); x++) {		   //  Iterate through all turns in the collection, to remove known letters
//			Turn turn = Turns.get(x);						   //  Get a turn from the collection		
//			for(String guessLetter : turn.guess) {					   //  Iterate over the most recent guess
//				if (wordData.inOpponentsWord.contains(guessLetter)) {  //  IF the collection of letters IN your opponents word contains the letter iterated from the most recent guess 
//					toRemove.add(guessLetter);						   //  Add that letter from the guess to another collection (to be removed later)
//					turn.response--;								   //  Decrement the response for this turn (since we are removing a letter known to exist, we must do this to maintain integrity)
//				}
//			}  //  for
//			for(String guessLetter : turn.guess) {						  //  Iterate over the most recent guess
//				if (wordData.notInOpponentsWord.contains(guessLetter)) {  //  IF the collection of letters NOT IN your opponents word contains the letter iterated from the most recent guess
//					toRemove.add(guessLetter);							  //  Add that letter from the guess to another collection (to be removed later)
//				}
//			}  //  for
//			turn.guess.removeAll(toRemove);								  //  Remove the collection of letters known from all turns	
//		}
////		System.out.println(Turns + "\n");  //  DEBUG
//		return Turns;
//	}  //  removeKnown()
}  //  class