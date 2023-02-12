package watson1dot0;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		Data wordData = new Data();									 //  Declare and initialize the needed 'Data' object to allow it to have global scope
		ArrayList<Turn> Turns = new ArrayList<Turn>();				 //  Declare the needed 'Array of Turns' objects to allow them to have global scope
		
		System.out.println("Welcome to the Word Guessing Game!\n" +  //  Introduce the game, and how it is played 
						   "\n" + 
						   "Your opponent has chosen a familiar 5 letter word.\n" + 
						   "See if you can guess the word!  Each time you make a guess, your opponent will respond with a number.\n" +
						   "The number represents the number of letters in your guess that appear in the word chosen by your opponent.\n" +
						   "Example:  If the opponent chooses 'PUPPY' and you guess 'LOOPY' the response would be 2 ('P' makes 1, and 'Y' makes 2.)\n" +
						   "\n" +
						   "Let's play!\n");
		
		while (!(wordData.doNotKnow.isEmpty())) {					 //  Create a loop that will continue while a list of letters that is uncertain remains
		
			//  A turn consists of a word guess, and a response from your opponent
			//  Add a new turn, to an ArrayList of Turns, taking a pair of strings passed in from the keyboard to the InputStream, having converted the strings to more desirable data type prior to storage 			
			Turns.add(new Turn(Make.guess("newGuess", wordData.passNumber), (Get.response("askOpponent", wordData.passNumber))));

			
//  LATEST RESPONSE IS 5...			
			if(Turns.get(Turns.size() -1).response == 5) {														//  IF the latest response from your opponent is 5, break out of the 'while' loops
				Print.conclusion("(Most recent response is 5)", wordData, Turns);
				if(!(wordData.inOpponentsWord.containsAll(Turns.get(Turns.size() - 1).guess))) {				//  IF the 'inOpponentsWord' collection DOES NOT contain ALL the letters in the most recent guess  
					for(String letter : Turns.get(Turns.size() - 1).guess) {									//  FOR each letter that appears in the latest guess
						if(!(wordData.inOpponentsWord.contains(letter))) wordData.inOpponentsWord.add(letter);  //  IF the 'inOpponentsWord' collection DOES NOT contain the letter from the latest guess, add the letter  
					}					
				}						
			wordData.doNotKnow.removeAll(Turns.get(Turns.size() - 1).guess);	  //  In the 'doNotKnow' collection, remove all from the letters in the latest guess
			if(!(wordData.notInOpponentsWord.containsAll(wordData.doNotKnow))) {  //  IF the 'inOpponentsWord' collection DOES NOT contain ALL the letters in the most recent guess  
				for(String letter : wordData.doNotKnow) {														  //  FOR each letter that appears in the latest guess
					if(!(wordData.notInOpponentsWord.contains(letter))) wordData.notInOpponentsWord.add(letter);  //  IF the 'notInOpponentsWord' collection DOES NOT contain the letter from the 'doNotKnow' collection, add the letter  
				}
			}			
			wordData.doNotKnow.clear();		//  The collection named 'doNotKnow' is cleared (all elements are removed)
			wordData.together.clear();		//  The collection named 'knownTogether' is cleared (all elements are removed)
			wordData.alsoTogether.clear();  //  The collection named 'knownTogether' is cleared (all elements are removed)
			Print.result(wordData);			//  IF this is not the first pass, print the result
			break;	
			}

			
//  REMOVE 'KNOWN' letters from ALL guesses...
			if(wordData.passNumber != 1 &&											  //  IF this is not the first pass, AND   
			(!(wordData.inOpponentsWord.isEmpty())) ||                                //  The 'inOpponentsWord' collection IS NOT empty OR
			(!(wordData.notInOpponentsWord.isEmpty()))) {							  //  The 'notInOpponentsWord' collection IS NOT empty
				Turns = Cleanup.removeKnownLetters(wordData, Turns);				  //  Remove ALL letters that are known from ALL guesses
				if(Turns.size() > 1) Process.countChangedLetters(wordData, Turns);	  //  IF more than 1 turn remains, COUNT the number of letters that have changed between the last 2 turns
			}
						
			
//  IF subsequent turn has ONLY 1 additional letter, and the rest are the SAME...
			if(Turns.size() > 1) {																			  //  IF more than 1 turn remains
				if(Turns.get(Turns.size() -1).guess.size() == Turns.get(Turns.size() -2).guess.size() + 1 &&  //  IF the size of the LATEST turn is 1 GREATER THAN the size of the PREVIOUS turn, AND  
			       wordData.numLettersChanged == 1 ) {														  //  ALL the other letters are THE SAME...
					wordData = Cleanup.findLetter(Turns, wordData);											  //  FIND the extra letter that exists (this method also manages that letter among the rest of the wordData)
					Turns = Cleanup.removeKnownLetters(wordData, Turns);									  //  Remove ALL letters that are known from ALL guesses
				}				
			}
			
			
//  Sort every GUESS of every turn and every LIST in ALPHABETICAL order...
			Turns = Process.alphabetizeAllGuesses(Turns);	   //  Sort every guess of every turn in ALPHABETICAL order
			wordData = Process.alphabetizeAllLists(wordData);  //  Sort named lists in the 'wordData' object in ALPHABETICAL order

			
//  LATEST RESPONSE IS 0
			if(Turns.get(Turns.size() - 1).response == 0) {															  //  IF the most recent response from your opponent is 0
				System.out.println("Since the latest response from your opponent is now " + Turns.get(Turns.size() -1).response + ", we now know that: " + Turns.get(Turns.size() -1).guess + " are NOT IN your opponents word.\n");
				wordData.doNotKnow.removeAll(Turns.get(Turns.size() - 1).guess);									  //  Discard each of the letters from this guess from doNotKnow
				
				if(!(wordData.notInOpponentsWord.containsAll(Turns.get(Turns.size() - 1).guess))) {					  //  IF the 'notInOpponentsWord' collection DOES NOT contain ALL elements from the guess made during the latest turn
					for(String letter : Turns.get(Turns.size() - 1).guess) {										  //  FOR each letter that appears in the latest guess
						if(!(wordData.notInOpponentsWord.contains(letter))) wordData.notInOpponentsWord.add(letter);  //  IF the 'notInOpponentsWord' collection DOES NOT contain the letter from the latest guess, add the letter  
					}					
				}
				wordData.together.clear();																		  	//  The collection named 'knownTogether' is cleared (all elements are removed)
				wordData.alsoTogether.clear();																		//  The collection named 'knownTogether' is cleared (all elements are removed)
				Turns.remove(Turns.size() - 1);																		//  Since the guess is of no further value, discard the turn
				if(wordData.passNumber != 1 &&																	   	//  IF this is not the first pass, AND   
				((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
				(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
				if(wordData.passNumber > 1) Turns = Cleanup.duplicatedTurns(wordData, Turns);		 //  As letters are identified, these letters are removed from all guesses.  Clean up any guesses that appear duplicated if there is more than one turn				
				if(wordData.passNumber > 1) wordData = Cleanup.responseEqualsSize(wordData, Turns);  //  As letters are eliminated eventually guesses consist of only letters that are in your opponents word.  Clean these up
				Print.result(wordData);																				//  IF this is not the first pass, print the result
			} else
			

//  IF 'together' NOT empty			
				if(!(wordData.together.isEmpty()) && 
				  (Turns.get(Turns.size() - 1)).guess.containsAll(wordData.together) && 
				  (Turns.get(Turns.size() - 1).response) < 2) {  //  IF the 'together' collection is NOT empty, and the latest response is LESS THAN the previous response
					System.out.println("MOO Since: " + wordData.together + " are known to be TOGETHER, but the response to a guess of: " + Turns.get(Turns.size() - 1).guess + " is " + Turns.get(Turns.size() - 1).response + 
									   ", we now know that " + wordData.together + " are NOT IN your opponents word.\n");
					if(!(wordData.notInOpponentsWord.containsAll(wordData.together))) {  //  IF the 'notInOpponentsWord' collection DOES NOT contain ALL of the elements in the'together' collection  
						wordData.notInOpponentsWord.addAll(wordData.together);		 	 //  In the 'notInOpponentsWord' collection, Add ALL of the elements from the 'together' collection  
						wordData.doNotKnow.removeAll(wordData.together);		 		 //  In the 'notInOpponentsWord' collection, Add ALL of the elements from the 'together' collection
						wordData.together.clear();										 //  Clear the 'together' collection
					}
					
					if(!(wordData.alsoTogether.isEmpty())) {
						System.out.println("Since: " + wordData.alsoTogether + " are ALSO TOGETHER, we also know that: " + wordData.alsoTogether + " are IN your opponents word.\n");
						if(!(wordData.inOpponentsWord.containsAll(wordData.alsoTogether))) {  //  IF the 'notInOpponentsWord' collection DOES NOT contain ALL of the elements in the'together' collection  
							wordData.inOpponentsWord.addAll(wordData.alsoTogether);			  //  In the 'notInOpponentsWord' collection, Add ALL of the elements from the 'together' collection   
							wordData.doNotKnow.removeAll(wordData.alsoTogether);			  //  In the 'notInOpponentsWord' collection, Add ALL of the elements from the 'together' collection
							wordData.alsoTogether.clear();									  //  Clear the 'alsoTogether' collection
						}	
					}
					if(wordData.passNumber != 1 &&																	    //  IF this is not the first pass, AND   
					((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
					(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
					Print.result(wordData);																				//  IF this is not the first pass, print the result
				}
				
				
//  IF 'alsoTogether' NOT empty	
				if(!(wordData.together.isEmpty()) && 
				  (Turns.get(Turns.size() - 1).response) >= 4) {  //  IF the 'together' collection is NOT empty, and the latest response is LESS GREATER THAN or EQUAL TO 4
					System.out.println("BIFNOY Since: " + wordData.together + " are known to be TOGETHER, and the response to a guess of: " + Turns.get(Turns.size() - 1).guess + " is " + Turns.get(Turns.size() - 1).response + 
									   ", we now know that " + wordData.together + " are NOT IN your opponents word.\n");
					if(!(wordData.notInOpponentsWord.containsAll(wordData.together))) {  //  IF the 'inOpponentsWord' collection DOES NOT contain ALL of the elements in the'together' collection  
						wordData.notInOpponentsWord.addAll(wordData.together);	         //  In the 'inOpponentsWord' collection, Add ALL of the elements from the 'together' collection
						wordData.doNotKnow.removeAll(wordData.together);		 	     //  In the 'notInOpponentsWord' collection, Add ALL of the elements from the 'together' collection
						wordData.together.clear();									     //  Clear the 'together' collection
					}
							
					if(!(wordData.alsoTogether.isEmpty())) {
						System.out.println("Since: " + wordData.alsoTogether + " are ALSO TOGETHER, we also know that: " + wordData.alsoTogether + " are NOT IN your opponents word.\n");
						if(!(wordData.notInOpponentsWord.containsAll(wordData.alsoTogether))) {  //  IF the 'notInOpponentsWord' collection DOES NOT contain ALL of the elements in the'together' collection  
							wordData.notInOpponentsWord.addAll(wordData.alsoTogether);		 	 //  In the 'notInOpponentsWord' collection, Add ALL of the elements from the 'together' collection  
							wordData.doNotKnow.removeAll(wordData.alsoTogether);		 		 //  In the 'notInOpponentsWord' collection, Add ALL of the elements from the 'together' collection
							wordData.alsoTogether.clear();										 //  Clear the 'alsoTogether' collection
						}	
					}
					if(wordData.passNumber != 1 &&																	    //  IF this is not the first pass, AND   
					((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
					(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
					Print.result(wordData);																				//  IF this is not the first pass, print the result	
				}
				
				
//  MORE THAN 2 GUESSES, ONLY 1 LETTER IS DIFFERENT
			if(Turns.size() >= 2) {								  //  IF the size of the 'Turns' collection is GREATER THAN 2  
				Process.countChangedLetters(wordData, Turns);	  //  COUNT the number of letters that have changed between the last 2 turns
				if(wordData.numLettersChanged == 1) {			  //  IF the number of letters that have changed between the last 2 turns is 1
					Process.findChangedLetters(wordData, Turns);  //  FIND the letters that have changed between the last 2 turns
					if(Turns.get(Turns.size() - 1).response == Turns.get(Turns.size() - 2).response - 1) {  //  IF the latest response is ONE LESS THAN the previous response
						System.out.println("Since the latest response from your opponent is 1 LESS THAN the previous response, and only 1 letter has changed, we now know that:\n'" + 
										   wordData.foundChangedTo + "' is NOT IN your opponents word, and\n'" + 
										   wordData.foundChangedFrom + "' is IN your opponents word\n");
						if(!(wordData.notInOpponentsWord.contains(wordData.foundChangedTo))) wordData.notInOpponentsWord.add(wordData.foundChangedTo);  //  ADD 'foundChangedTo' to 'notInOpponentsWord' (if it does not already exist) 
						if(!(wordData.inOpponentsWord.contains(wordData.foundChangedFrom))) wordData.inOpponentsWord.add(wordData.foundChangedFrom);	//  ADD 'foundChangedFrom' to 'inOpponentsWord' (if it does not already exist)
						wordData.doNotKnow.remove(wordData.foundChangedTo);	   //  REMOVE 'foundChangedTo' from 'doNotKnow'
						wordData.doNotKnow.remove(wordData.foundChangedFrom);  //  REMOVE 'foundChangedFrom' from 'doNotKnow'
					} else if (Turns.get(Turns.size() - 1).response == Turns.get(Turns.size() - 2).response + 1) {  //  ELSE IF the latest response is ONE MORE THAN the previous response
						System.out.println("Since the latest response from your opponent is 1 GREATER THAN the previous response, and only 1 letter has changed, we now know that:\n'" + 
										   wordData.foundChangedTo + "' is NOT IN your opponents word, and\n'" + 
										   wordData.foundChangedFrom + "' is IN your opponents word\n");
						if(!(wordData.inOpponentsWord.contains(wordData.foundChangedTo))) wordData.inOpponentsWord.add(wordData.foundChangedTo);  //  ADD 'foundChangedTo' to 'inOpponentsWord' (if it does not already exist)
						if(!(wordData.notInOpponentsWord.contains(wordData.foundChangedFrom))) wordData.notInOpponentsWord.add(wordData.foundChangedFrom);  //  ADD 'foundChangedFrom' to 'notInOpponentsWord' (if it does not already exist)
						wordData.doNotKnow.remove(wordData.foundChangedTo);	   //  REMOVE 'foundChangedTo' from 'doNotKnow'
						wordData.doNotKnow.remove(wordData.foundChangedFrom);  //  REMOVE 'foundChangedFrom' from 'doNotKnow'
					} else if (Turns.get(Turns.size() - 1).response == Turns.get(Turns.size() - 2).response) {  //  IF the latest response is EQUAL TO the previous response
						System.out.println("Since the latest response from your opponent is EQUAL TO the previous response, and only 1 letter has changed, we now know that:\n'" + 
								   wordData.foundChangedTo + "' and '" + wordData.foundChangedFrom + "' are either both IN your opponents word, or NOT IN, but we can't be certain which is the case.\n");
						if(wordData.together.isEmpty()) {
							wordData.together.add(wordData.foundChangedTo);
							wordData.together.add(wordData.foundChangedFrom);
						} else if(wordData.together.contains(wordData.foundChangedTo) || 
								 (wordData.together.contains(wordData.foundChangedFrom))) {
							if(!(wordData.together.contains(wordData.foundChangedTo))) wordData.together.add(wordData.foundChangedTo);
							if(!(wordData.together.contains(wordData.foundChangedFrom))) wordData.together.add(wordData.foundChangedFrom);
						} else {
							wordData.alsoTogether.add(wordData.foundChangedTo);
							wordData.alsoTogether.add(wordData.foundChangedFrom);
						}
					}
				}
				Print.result(wordData);  //  IF this is not the first pass, print the result
			}
					
					
//  REMOVE 'KNOWN' letters from ALL guesses
			if(wordData.passNumber != 1 &&																	   //  IF this is not the first pass, AND   
			(!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
			(!(wordData.notInOpponentsWord.isEmpty())))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
			
			
//  REVIEW ALL TURNS
			if(wordData.passNumber == 1) {
				System.out.println("Pass # " + wordData.passNumber + ".  \nLet's take a look at every guess and response so far.  (All guesses will be sorted in ALPHABETICAL order for easier comparison):");
			} else {
			System.out.println("Pass # " + wordData.passNumber + ".  \nLet's take a closer look at every guess and response so far (oldest to newest).  All guesses will be sorted in ALPHABETICAL order for easier comparison:\n" +   
																 "(Once this is complete, we can make any conclusions we are able to draw)\n");			
			
			if(wordData.passNumber != 1 &&																	    //  IF this is not the first pass, AND   
			((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
			(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
			if(wordData.passNumber > 1) wordData = Cleanup.responseEqualsSize(wordData, Turns);  //  As letters are eliminated eventually guesses consist of only letters that are in your opponents word.  Clean these up   
			if(wordData.passNumber > 1) Turns = Cleanup.responseOfZero(wordData, Turns);		 //  Cleaning up guesses results in a 'turn' where the response is 0.  Clean these up
			if(wordData.passNumber != 1 &&																	    //  IF this is not the first pass, AND   
			((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
			(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
			if(wordData.passNumber > 1) Turns = Cleanup.responseOfZero(wordData, Turns);		 //  Cleaning up guesses results in a 'turn' where the response is 0.  Clean these up
			if(wordData.passNumber > 1) wordData = Cleanup.responseEqualsSize(wordData, Turns);  //  As letters are eliminated eventually guesses consist of only letters that are in your opponents word.  Clean these up
			if(wordData.passNumber > 1) Turns = Cleanup.responseOfZero(wordData, Turns);		 //  Cleaning up guesses results in a 'turn' where the response is 0.  Clean these up
			if(Turns.size() >= 2)  Turns = Cleanup.duplicatedTurns(wordData, Turns);			 //  As letters are identified, these letters are removed from all guesses.  Clean up any guesses that appear duplicated if there is more than one turn
			} 				

			
//  Check for 'together' IN...
			if(!(wordData.together.isEmpty()) && (!(wordData.inOpponentsWord.isEmpty()))) {
				for(String letterIN : wordData.inOpponentsWord) {
					for(String letterTogether : wordData.together) {
						if(letterIN.equals(letterTogether)) {
							System.out.println("MEOW In the event any letters known to be together are IN your opponents word, then ALL letters in that collection are IN.  Updating IN with letters KNOWN TOGETHER...");
							for(String letterTogether2 : wordData.together) {
								if(!(wordData.inOpponentsWord.contains(letterTogether2))) {
									System.out.println("MEOW Since these are known TOGETHER: " + wordData.together + " and these are known IN: " + wordData.inOpponentsWord + ", we now know that: " + wordData.together + " are ALL IN your " +
									   		   		   "opponents word.  Adding: '" + letterTogether2 + "' to known IN: " + wordData.inOpponentsWord);	
									wordData.inOpponentsWord.add(letterTogether2);
									wordData.together.clear();
								}
							}							
							break;		
						}  //  IF
					}  //  inner-for
					break;
				}  //  outer-for
				if(wordData.passNumber != 1 &&																	    //  IF this is not the first pass, AND   
				((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
				(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
				if(wordData.passNumber > 1) wordData = Cleanup.responseEqualsSize(wordData, Turns);  //  As letters are eliminated eventually guesses consist of only letters that are in your opponents word.  Clean these up
				if(wordData.passNumber > 1) Turns = Cleanup.responseOfZero(wordData, Turns);		 //  Cleaning up guesses results in a 'turn' where the response is 0.  Clean these up
				Print.result(wordData);																 //  IF this is not the first pass, print the result
			}  //  IF

			
//  Check for 'alsoTogether' IN...
			if(!(wordData.alsoTogether.isEmpty())) {
				for(String letterIN : wordData.inOpponentsWord) {
					for(String letterTogether : wordData.alsoTogether) {
						if(letterIN.equals(letterTogether)) {
							System.out.println("WOOF In the event any letters also known to be together are IN your opponents word, then ALL letters in that collection are IN.  Updating IN with letters ALSO KNOWN TOGETHER...");
							for(String letterTogether2 : wordData.alsoTogether) {
								if(!(wordData.inOpponentsWord.contains(letterTogether2))) {
									System.out.println("WOOF Since these are known TOGETHER: " + wordData.alsoTogether + " and these are known IN: " + wordData.inOpponentsWord + ", we now know that: " + wordData.alsoTogether + " are ALL IN your " +
									   		   		   "opponents word.  Adding: '" + letterTogether + "' to known IN: " + wordData.inOpponentsWord);	
									wordData.inOpponentsWord.add(letterTogether2);
									wordData.alsoTogether.clear();
								}
							}
							break;
						}  //  IF
					}  //  inner-for
					break;
				}  //  outer-for
				if(wordData.passNumber != 1 &&																	   //  IF this is not the first pass, AND   
				((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
				(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
				if(wordData.passNumber > 1) wordData = Cleanup.responseEqualsSize(wordData, Turns);  //  As letters are eliminated eventually guesses consist of only letters that are in your opponents word.  Clean these up
				if(wordData.passNumber > 1) Turns = Cleanup.responseOfZero(wordData, Turns);		 //  Cleaning up guesses results in a 'turn' where the response is 0.  Clean these up
				Print.result(wordData);																 //  IF this is not the first pass, print the result
			}  //  IF
			
			
//  Check for 'together' OUT...
					if(!(wordData.together.isEmpty()) && (!(wordData.notInOpponentsWord).isEmpty())) {
						for(String letterNOTIN : wordData.notInOpponentsWord) {
							for(String letterTogether : wordData.together) {
								if(letterNOTIN.equals(letterTogether)) {
									System.out.println("MOO In the event any letters known to be together are IN your opponents word, then ALL letters in that collection are IN.  Updating IN with letters KNOWN TOGETHER...");
									for(String letterTogether2 : wordData.together) {
										if(!(wordData.notInOpponentsWord.contains(letterTogether2))) {
											System.out.println("MOO Since these are known TOGETHER: " + wordData.together + " and these are known IN: " + wordData.notInOpponentsWord + ", we now know that: " + wordData.together + " are ALL IN your " +
											   		   		   "opponents word.  Adding: '" + letterTogether2 + "' to known IN: " + wordData.notInOpponentsWord);	
											wordData.notInOpponentsWord.add(letterTogether2);
											wordData.together.clear();
										}									
									break;		
									}  //  IF
								}  //  IF
							break;
							}  //  outer-for
						}
						if(wordData.passNumber != 1 &&																	    //  IF this is not the first pass, AND   
						((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
						(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
						if(wordData.passNumber > 1) wordData = Cleanup.responseEqualsSize(wordData, Turns);  //  As letters are eliminated eventually guesses consist of only letters that are in your opponents word.  Clean these up
						if(wordData.passNumber > 1) Turns = Cleanup.responseOfZero(wordData, Turns);		 //  Cleaning up guesses results in a 'turn' where the response is 0.  Clean these up
						Print.result(wordData);																 //  IF this is not the first pass, print the result
					}  //  IF

					
//  Check for 'alsoTogether' OUT...
					if(!(wordData.alsoTogether.isEmpty())) {
						for(String letterIN : wordData.notInOpponentsWord) {
							for(String letterTogether : wordData.alsoTogether) {
								if(letterIN.equals(letterTogether)) {
									System.out.println("NEIGH In the event any letters also known to be together are IN your opponents word, then ALL letters in that collection are IN.  Updating IN with letters ALSO KNOWN TOGETHER...");
									for(String letterTogether2 : wordData.alsoTogether) {
										if(!(wordData.notInOpponentsWord.contains(letterTogether2))) {
											System.out.println("NEIGH Since these are known TOGETHER: " + wordData.alsoTogether + " and these are known IN: " + wordData.notInOpponentsWord + ", we now know that: " + wordData.alsoTogether + " are ALL IN your " +
											   		   		   "opponents word.  Adding: '" + letterTogether2 + "' to known IN: " + wordData.notInOpponentsWord);	
											wordData.notInOpponentsWord.add(letterTogether2);
											wordData.alsoTogether.clear();
										}
									}
									break;		
								}  //  IF
							}  //  inner-for
							break;
						}  //  outer-for
						if(wordData.passNumber != 1 &&																	    //  IF this is not the first pass, AND   
						((!(wordData.inOpponentsWord.isEmpty())) ||                                                         //  The 'inOpponentsWord' collection IS NOT empty OR
						(!(wordData.notInOpponentsWord.isEmpty()))))  Turns = Cleanup.removeKnownLetters(wordData, Turns);  //  The 'inOpponentsWord' collection IS NOT empty...
						if(wordData.passNumber > 1) wordData = Cleanup.responseEqualsSize(wordData, Turns);  //  As letters are eliminated eventually guesses consist of only letters that are in your opponents word.  Clean these up
						if(wordData.passNumber > 1) Turns = Cleanup.responseOfZero(wordData, Turns);		 //  Cleaning up guesses results in a 'turn' where the response is 0.  Clean these up
						Print.result(wordData);																 //  IF this is not the first pass, print the result
					}  //  IF
			
			
//  IF Turns.size() is NOT 0, PRINT a VERTICAL summary list of all guesses...			
			if(Turns.size() != 0) {								//  IF the size of the 'Turns' collection IS NOT 0, PRINT a VERTICAL summary list of all guesses
				if(wordData.passNumber > 1) System.out.println("Having removed all KNOWN letters from ALL guesses, this leaves:");
				for(int x = 0; x <= (Turns.size() - 1); x++) {  //  Iterate through all turns in the collection, to provide a summary
					Turn turn = Turns.get(x);					//  GET a turn from the 'Turns' collection
					System.out.println(turn);
				}
				System.out.println();				
			}
			
			
//  IF Turns.size() is GREATER THAN (or EQUAL to) 2...
			if(Turns.size() >= 2) {							   //  IF the size of the 'Turns' collection is GREATER THEN (OR EQUAL TO) 2
				Process.countChangedLetters(wordData, Turns);  //  Execute the countChangedLetters() method
				Process.findChangedLetters(wordData, Turns);   //  Execute the findChangedLetters() method
			}


//  Update the wordData collection with the results of processing all 'Turns' taken...
			if((Turns.size() > 1) && (Turns.get(Turns.size() - 1).response !=  0)) {  //  IF more than one turn exists
				Process.Turn(wordData, Turns);										  //  Update the wordData collection with the results of processing all 'Turns' taken
			} else {
				Print.conclusion("(only 1 turn exists)", wordData, Turns);
			}
			

//  Sort ALL NAMED LISTS in the 'wordData' object in ALPHABETICAL order...			
			wordData = Process.alphabetizeAllLists(wordData);  //  Sort named lists in the 'wordData' object in ALPHABETICAL order


//  Print the results...
			Print.result(wordData);  //  IF this is not the first pass, print the result


//  Develop a 'data driven' strategy...
			Print.strategy(wordData, Turns);  //  Develop a 'data driven' strategy
			System.out.println();


//  Prepare for the next iteration...
			wordData.foundChangedTo = null;
			wordData.foundChangedFrom = null; 
			wordData.numLettersChanged = 0;
			wordData.passNumber++;  //  Increment the loop counter
		}							//  while...  Breaks when responseInt == 5/(!(doNotKnow.isEmpty()))

		
//  Print all possible letter combinations...
		Print.allPossibleLetterCombinations(wordData);
	}  //  main()
}  //  class