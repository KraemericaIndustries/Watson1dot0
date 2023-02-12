package watson1dot0;

import java.util.ArrayList;
import java.util.LinkedList;

public class Cleanup {
	
	//  As letters are identified, they are removed from all guesses.  Clean up any guesses that appear duplicated if there is more than one turn
	public static ArrayList<Turn> duplicatedTurns(Data wordData, ArrayList<Turn> Turns) {

		ArrayList<Integer> indexes = new ArrayList<Integer>();  //  Initialize a collection to store indexes in (Wile E. Coyote)    
		
		for(int x = (Turns.size() - 1); x >= 1 ; x--) {  //  Iterate over the 'Turns' collection (stack), with 'x' serving as an index for the collection
			if(Turns.get(x).equals(Turns.get(x - 1))) {  //  IF the latest turn is equal to the previous turn
				indexes.add(x);							 //  In the collection named 'indexes', add the index represented by the value stored in 'x'
			}
		}

		if(indexes.size() > 0) {
			System.out.println("As KNOWN letters are removed from guesses for easier comparison, guesses can and will appear DUPLICATED.  Removing DUPLICATED guesses...\n" + 
					   Turns + " becomes:");		
			for(int turnIndex : indexes) {			   //  For each index stored in the 'indexes' collection as an 'Integer' type
				int removeTurn = (Integer) turnIndex;  //  Cast the 'Integer' index to a type if 'int'
				Turns.remove(removeTurn);			   //  In the 'Turns' collection, remove the element(s) remove the elements for which an index appears in the 'removeTurn' collection
			}
			System.out.println(Turns + "\n");
		}
	return Turns;	
	}
	
	
	public static Data findLetter(ArrayList<Turn> Turns, Data wordData) {

		String extraLetter = "";
		boolean exists = false;														//  Initialize a flag to false
		
		for (String currentGuessLetter : Turns.get(Turns.size() - 1).guess) {		//  FOR each letter in the latest guess  
			exists = false;
			for (String previousGuessLetter : Turns.get(Turns.size() - 2).guess) {  //  FOR each letter in the previous Guess
				if (currentGuessLetter.equals(previousGuessLetter)) {				//  IF the letter from the current guess equals the letter from the previous guess
					exists = true;													//  Set the flag to 'true'
					break;  //  A match for a letter in the new guess has been found in the previous guess.  Break the loop since it is unnecessary to check the remainder of the previous guess.
				}  //  if
				//  When letters do not match, control is passed here:
				//  System.out.println(currentGuessLetter + " from the new word does not equal " + previousGuessLetter + " from the previous word.");  //  DEBUG
				//  System.out.println("The letter from the previous word that was changed is: " + previousGuessLetter);  							   //  DEBUG
			}  	//  inner-for
			//  When no match at all is found, control passes here.  When a BREAK statement is encountered in the IF block, control passes here:
			if (exists == false) {  //  Check the flag.  If it is not set, then, we have found the letter in the new word that does not match any letters in the previous word.
				//  System.out.println("The flag HAS NOT been set.  exists = " + exists);													   //  DEBUG
				//  System.out.println("The letter from the new guess that does not appear in the previous guess is: " + currentGuessLetter);  //  DEBUG
				//  System.out.println("Returning: " + currentGuessLetter);                                                                    //  DEBUG
				extraLetter = currentGuessLetter;																			   //  Update the data to reflect the letter changed to in the latest guess
				break;  //  The letter has been positively identified.  The outer enhanced-for loop may be broken, and control is passed outside the outer enhanced=for loop.
			}  //  if
		    //  System.out.println("The flag is set.  exists=" + exists);  	  //  DEBUG
		    //  System.out.println();										  //  DEBUG
		}   //  outer-for
	
		
//		IF current.size == previous.size + 1, && 1 letter changed && current.response == previous.response, extra letter NOT IN
//		IF current.size == previous.size + 1, && 1 letter changed && current.response == previous.response + 1, extra letter is IN
		
		System.out.println("extraLetter: " + extraLetter);
		
		if(Turns.get(Turns.size() - 1).guess.size() == (Turns.get(Turns.size() - 2).guess.size() + 1) &&  //  IF the size of the CURRENT guess is GREATER THEN the size of the PREVIOUS guess by 1, AND
		   wordData.numLettersChanged == 1 &&														//  The number of letters changed is 1 AND
		   Turns.get(Turns.size() - 1).response == Turns.get(Turns.size() - 2).response) {			//  The CURRENT response is EQUAL TO the PREVIOUS response
		
			if(!(wordData.notInOpponentsWord.contains(extraLetter))) {
				wordData.notInOpponentsWord.add(extraLetter);
				wordData.doNotKnow.remove(extraLetter);
			}
		}
		
		if(Turns.get(Turns.size() - 1).guess.size() == (Turns.get(Turns.size() - 2).guess.size() + 1) &&  //  IF the size of the CURRENT guess is GREATER THEN the size of the PREVIOUS guess by 1, AND
				   wordData.numLettersChanged == 1 &&														//  The number of letters changed is 1 AND
				   Turns.get(Turns.size() - 1).response == (Turns.get(Turns.size() - 2).response) + 1) {			//  The CURRENT response is EQUAL TO the PREVIOUS response
				
					if(!(wordData.inOpponentsWord.contains(extraLetter))) {
						wordData.inOpponentsWord.add(extraLetter);
						wordData.doNotKnow.remove(extraLetter);
					}
				}		
		return wordData;
	}  //  findLetter
	

	public static Data responseEqualsSize(Data wordData, ArrayList<Turn> Turns) {
		
		LinkedList<String> token = new LinkedList<String>();				//  Initialize a token list to allow for creation of a turn
		ArrayList<Integer> indexes = new ArrayList<Integer>();				//  Initialize a collection to store indexes in (Wile E. Coyote)
		Turn turn = new Turn(token, 0);										//  Create a token turn
	
		for(int x = (Turns.size() - 1); x >= 0 ; x--) {						//  Iterate over the 'Turns' collection (stack), with 'x' serving as an index for the collection
			turn = Turns.get(x);											//  GET the 'turn' from the stack at the index stored as the value to a variable of 'x' 
				if(turn.guess.size() == turn.response) {					//  IF the number of letters in the guess is the same as the response
					for(String letter : turn.guess) {						//  FOR each letter in the guess
						if(!(wordData.inOpponentsWord.contains(letter))) {  //  IF the 'inOpponentsWord' collection DOES NOT contain the letter 
							wordData.inOpponentsWord.add(letter);			//  In the 'inOpponentsWord' collection, add the letter
							wordData.doNotKnow.remove(letter);				//  In the 'doNotKnow' collection, remove the letter
						}
					}
					indexes.add(x);                            //  In the collection named 'indexes', add the index represented by the value stored in 'x'
					
					if(indexes.size() > 0) {
						System.out.println("As KNOWN letters are removed from guesses for easier comparison, turns can and will be reduced to a point where all remaining letters are known to be IN your opponents word.  " + 
								   "Updating where SIZE is THE SAME as the RESPONSE...\n" + 
								   Turns + " becomes:");				
						for(int turnIndex : indexes) {			   //  For each index stored in the 'indexes' collection as an 'Integer' type
							int removeTurn = (Integer) turnIndex;  //  Cast the 'Integer' index to a type if 'int'
							Turns.remove(removeTurn);			   //  In the 'Turns' collection, remove the element(s) remove the elements for which an index appears in the 'removeTurn' collection
						}
						System.out.println(Turns + "\n");				
					}		
				indexes.clear();
				}
			}
		return wordData;	
	}
	
	
	public static ArrayList<Turn> removeKnownLetters(Data wordData, ArrayList<Turn> Turns) {  //  Remove 'known' letters from all turns
		System.out.println("All KNOWN letters can be removed (so long as the response is DECREMENTED for every letter known to appear IN your opponents word) for easier comparison.  Removing KNOWN letters: " + 
						   wordData.inOpponentsWord + wordData.notInOpponentsWord + " from ALL turns...\n" + Turns + " becomes:");  //  DEBUG
		//  Create a collection to store letters from the guess, that are already known, so that they may be removed
		//  Removing from a collection using an enhanced-for loop 'Wile E. Coyote's the iterable...
		LinkedList<String> toRemove = new LinkedList<String>();

		for(int x = 0; x <= (Turns.size() - 1); x++) {		   //  Iterate through all turns in the collection, to remove known letters
			Turn turn = Turns.get(x);						   //  Get a turn from the collection		
			for(String guessLetter : turn.guess) {					   //  Iterate over the most recent guess
				if (wordData.inOpponentsWord.contains(guessLetter)) {  //  IF the collection of letters IN your opponents word contains the letter iterated from the most recent guess 
					toRemove.add(guessLetter);						   //  Add that letter from the guess to another collection (to be removed later)
					turn.response--;								   //  Decrement the response for this turn (since we are removing a letter known to exist, we must do this to maintain integrity)
				}
			}  //  for
			for(String guessLetter : turn.guess) {						  //  Iterate over the most recent guess
				if (wordData.notInOpponentsWord.contains(guessLetter)) {  //  IF the collection of letters NOT IN your opponents word contains the letter iterated from the most recent guess
					toRemove.add(guessLetter);							  //  Add that letter from the guess to another collection (to be removed later)
				}
			}  //  for
			turn.guess.removeAll(toRemove);								  //  Remove the collection of letters known from all turns	
		}
		System.out.println(Turns + "\n");  //  DEBUG
		return Turns;
	}  //  removeKnown()
	
	
	public static ArrayList<Turn> responseOfZero(Data wordData, ArrayList<Turn> Turns) {
		
		LinkedList<String> token = new LinkedList<String>();	//  Initialize a token list to allow for creation of a turn
		ArrayList<Integer> indexes = new ArrayList<Integer>();  //  Initialize a collection to store indexes in (Wile E. Coyote)
		Turn turn = new Turn(token, 0);							//  Create a token turn
	
		for(int x = (Turns.size() - 1); x >= 0 ; x--) {  //  Iterate over the 'Turns' collection (stack), with 'x' serving as an index for the collection
			turn = Turns.get(x);						 //  GET the 'turn' from the stack at the index stored as the value to a variable of 'x'
			if(turn.response == 0) {					 //  IF the response for this turn is 0
				for(String letter : turn.guess) {						   //  FOR each letter in the guess
					if(!(wordData.notInOpponentsWord.contains(letter))) {  //  IF the 'inOpponentsWord' collection DOES NOT contain the letter 
						wordData.notInOpponentsWord.add(letter);		   //  In the 'inOpponentsWord' collection, add the letter
						wordData.doNotKnow.remove(letter);				   //  In the 'doNotKnow' collection, remove the letter
					}
				}
			indexes.add(x);									   //  In the collection named 'indexes', add the index represented by the value stored in 'x'				
			}
		}
		if(indexes.size() > 0) {
			System.out.println("As KNOWN letters are removed from guesses for easier comparison, turns can and will be reduced to a point where all remaining letters are known to be NOT IN your opponents word.  Updating RESPONSE of 0...\n" + 
					   Turns + " becomes:");	
			for(int turnIndex : indexes) {			   //  For each index stored in the 'indexes' collection as an 'Integer' type
				int removeTurn = (Integer) turnIndex;  //  Cast the 'Integer' index to a type if 'int'
				Turns.remove(removeTurn);			   //  In the 'Turns' collection, remove the element corresponding to an index of 'removeTurn'
			}
			System.out.println(Turns + "\n");
		}
		return Turns;
	}
}  //  class