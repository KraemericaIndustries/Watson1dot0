package watson1dot0;

import java.util.ArrayList;

public class Print {

	static int count = 0;
	
	public static void allPossibleLetterCombinations(Data wordData) {
		System.out.println("Proceed to make guesses from any of the possible letter combinations:");
		String letters = "";
		
		for(String letter : wordData.inOpponentsWord) {
			letters = letters + letter;	
		}
			
        count = 0; // counter to count the number of words formed
        permute (letters.toCharArray(), 0);
	}
        
	static void permute(char[] letters, int i) {
		if (i == letters.length-1) {
			count++; // count no of words output and print the permutation.
	        System.out.println(count + ") " + new String(letters));
	        return;
	    }
	    int curr = i;
	    for (; i < letters.length; i++) {
	        swap (letters, i, curr);
	        permute (letters, curr + 1);
	        swap (letters, i, curr);
	    }
	}  //  permute()
	 
	public static void swap(char[] letters, int i, int j) {
		char c = letters[i];
	    letters[i] = letters[j];
	    letters[j] = c;
	}  //  swap()

	
	public static void conclusion(String condition, Data wordData, ArrayList<Turn> Turns) {
		System.out.println("Pass #" + wordData.passNumber + " " + condition + " Conclusions:");
		
		if(Turns.size() == 0) {
			System.out.println("ALL letters from ALL previous turns are now KNOWN.\n");		
		} else {
			for(int x = 0; x <= (Turns.size() - 1); x++) {		  //  Iterate through all turns in the collection, to provide a summary
				Turn turn = Turns.get(x);					
				System.out.print("Of the letters in this guess: " + turn.guess + ", " + turn.response + " letter(s) are known to be IN your opponents word ");
				if(!(Turns.get(Turns.size() - 1).response == 5)) {
					System.out.println("(but we can't be certain which ones.)");
				} else System.out.println("\n");
			}
			if (condition == "(Most recent response is 5)") {
				System.out.println("ALL letter(s) IN your opponents word have been identified.");
			} else if (condition == "(only 1 turn exists)") {
				System.out.println("With only a single turn, very little may be learned.  (Unless the response is 0 or 5.)\n");
			} else if(Turns.get(Turns.size() - 1).response == 5) {
				System.out.println("Of the letters in this guess: " + Turns.get(Turns.size() - 1).guess + ", " + Turns.get(Turns.size() - 1).response + " letter(s) are known to be IN your opponents word.");
			}
//		System.out.println();
		}
	}
	
		
	public static void letterSummary(String caller, Data wordData) {  //  Print a summary of the letters we know about
		System.out.println("\n" + caller + ".Print.letterSummary Let's take a look at everything we know so far:");
		System.out.println(wordData);		
	}
	

	public static void result(Data wordData) {  //  Print the result of the recently completed turn
		System.out.println("Pass #" + wordData.passNumber + " Result:");
		System.out.println(wordData);
	}
	
		
	public static void strategy(Data wordData, ArrayList<Turn> Turns) {	 //  Print a strategy for your next turn based on the DATA AVAILABLE
			System.out.println("Pass #" + wordData.passNumber + " Strategy:\n" + 
							   "REMEMBER:\n" +
						   	   "- Your most strategic play, is the one that eliminates the most unknowns on any given turn!");
		if(wordData.inOpponentsWord.size() >= 3) {
			System.out.println("- Once " + wordData.inOpponentsWord.size() + " letters are KNOWN, start taking shots at your opponents word!  (UNLESS there are no previous turns.  In that case, draw 5 letters from UNKNOWN\n" +
							   "- USING IN: " + wordData.inOpponentsWord + " add UNKNOWN: " + wordData.doNotKnow + "\n");
		}
		System.out.println();
		
		System.out.println("Choose a strategy from the following.  The strongest suggestions are at the top of this list:\n");

//  IF the 'together' collection is NOT empty
		if(!(wordData.together.isEmpty()))
//				&& (Turns.size() != 0)) 
		{  //  IF the 'together' collection is NOT empty
			System.out.println("Determining any letters that are known to be together: " + wordData.together + "" + wordData.alsoTogether + " (whether IN or NOT IN) would eliminate (at least) 2 letters:\n"  +
					   "- Make a guess using the letters from: " + wordData.together + "" + wordData.alsoTogether + " and the remaining UNKNOWN letters: " + wordData.doNotKnow + 
					   ".  (Pad with IN: " + wordData.inOpponentsWord + " and NOT IN: " + wordData.notInOpponentsWord + " where necessary.)\n" + 
					   "- USING: " + wordData.together + "" + wordData.alsoTogether + " and UNKNOWN: " + wordData.doNotKnow + " Pad with IN: " + wordData.inOpponentsWord + "and NOT IN: " + wordData.notInOpponentsWord + 
					   " (where necessary.)\n" + 
					   "    - If no determination can be made, continue replacing 1 letter from the previous turn (if it exists), but NOT the " + wordData.together + "" + wordData.alsoTogether + " with UNKNOWN: " + 
					   wordData.doNotKnow + ") until a determination is possible.\n");
		}
		
		
//  IF the 'alsoTogether' collection is NOT empty
		if(!(wordData.alsoTogether.isEmpty()) && (Turns.size() != 0)) {  //  IF the 'knownTogether' collection is NOT empty
			System.out.println("Determining any letters that are known to be together: " + wordData.together + "" + wordData.alsoTogether + " (whether IN or NOT IN) would eliminate (at least) 2 letters:\n"  +
					   "- Make a guess using the letters from: " + wordData.together + "" + wordData.alsoTogether + " and the remaining UNKNOWN letters: " + wordData.doNotKnow + 
					   ".  (Pad with IN: " + wordData.inOpponentsWord + " and NOT IN: " + wordData.notInOpponentsWord + " where necessary.)\n" + 
					   "- USING: " + wordData.together + "" + wordData.alsoTogether + " and UNKNOWN: " + wordData.doNotKnow + " Pad with IN: " + wordData.inOpponentsWord + "and NOT IN: " + wordData.notInOpponentsWord + 
					   " (where necessary.)\n" + 
					   "    - If no determination can be made, continue replacing 1 letter from " + Turns.get(Turns.size() - 1).guess + "(NOT THE " + wordData.together + "" + wordData.alsoTogether + ") with UNKNOWN: " + 
					   wordData.doNotKnow + ") until a determination is possible.\n" + 
                       "    - CHANGE 1 of: " + Turns.get(Turns.size() - 1).guess + " (NOT: " + wordData.together + "" + wordData.alsoTogether + ") to UNKNOWN: " + wordData.doNotKnow + " Pad with IN: " + 
					   wordData.inOpponentsWord + " and NOT IN: " + wordData.notInOpponentsWord + " (where necessary.)\n");
		}

		
//  Once 3 OR MORE letters are KNOWN...
		if(wordData.inOpponentsWord.size() >= 3) {
			System.out.println("Once " + wordData.inOpponentsWord.size() + " letters are KNOWN, start taking shots at your opponents word!\n" +
							   "- Take all KNOWN letters: " + wordData.inOpponentsWord + " and try to create a 5 letter word using UNKNOWN letters: " + wordData.doNotKnow + "\n" +
							   "- " + wordData.inOpponentsWord + " + UNKNOWN letters: " + wordData.doNotKnow + "\n");
		}		
		
		
//  Once ALL letters from ALL previous turns are KNOWN...
		if(Turns.size() == 0) {
			System.out.println("ALL letters from ALL previous turns are now KNOWN:\n" +
					   		   "- Make another guess using as many letters as possible from: " + wordData.doNotKnow + ".  (Pad with KNOWN letters: " + wordData.inOpponentsWord + "" + wordData.notInOpponentsWord + 
					   		   " where necessary.)\n" + 
					   		   wordData.doNotKnow + " + KNOWN IN: " + wordData.inOpponentsWord + " and KNOWN NOT IN: " + wordData.notInOpponentsWord + " (where necessary.)\n");
		}
		
//  Propose a potential strategy for any turns where the remaining response is 1
		if(Turns.size() >= 2) {
			
			ArrayList<Turn> turnsResponse1 = new ArrayList<Turn>();  //  Declare the needed 'Array of Turns' object, used to identify all turns with a remaining response of 1

			for(int x = 0; x <= (Turns.size() - 1); x++) {  //  FOR ALL turns in the collection
				Turn turn = Turns.get(x);					//  GET a turn
				if(turn.response == 1) {					//  IF the response for this turn is 1
					turnsResponse1.add(turn);				//  In the 'turnsResponse1' collection, add this turn 
				}
			}
			if(turnsResponse1.size() > 0) {					//  IF the size of the 'turnsResponse1' collection is GREATER THAN 0 
				System.out.println("Having removed KNOWN letters, any turns that have a response of 1 may have between 1 and 5 letters identified after a subsequent turn:"); 
					for(int x = 0; x <= (turnsResponse1.size() - 1); x++) {	   //  FOR ALL turns in the 'turnsResponse1' collection
						Turn turn = turnsResponse1.get(x);					   //  GET a turn
						System.out.println(turn);							   //  PRINT the turn (padded with a little white space for visual aid)
					}   
					System.out.println("- Take any such guess, CHANGE ONLY 1 LETTER in the guess to one of the letters that remains UNKNOWN: " + wordData.doNotKnow + ".  (Pad with KNOWN letters: " + 
									   wordData.inOpponentsWord + "" + wordData.notInOpponentsWord + " where necessary.)\n" +  
									   "- Take any such guess, CHANGE ONLY 1 to UNKNOWN: " + wordData.doNotKnow + " Pad with IN: " + wordData.inOpponentsWord + " and NOT IN: " + wordData.notInOpponentsWord + 
									   " (where necessary.)");						   
			}						
		}
		
		
//  If no strategy is immediately evident...
		if(Turns.size() > 0) {
			System.out.println("If no strategy is immediately evident:\n" + 
							   "- Your next guess should CHANGE ONLY 1 LETTER from: " + Turns.get(Turns.size() - 1).guess + " to an UNKNOWN: " + wordData.doNotKnow + ".  (Pad with KNOWN letters: " + 
							   wordData.inOpponentsWord + "" + wordData.notInOpponentsWord + " where necessary.)\n" + 
							   "- CHANGE 1 of: " + Turns.get(Turns.size() - 1).guess + " to UNKNOWN: " + wordData.doNotKnow + ".  Pad with IN: " + wordData.inOpponentsWord + ", NOT IN: " + wordData.notInOpponentsWord + 
							   " (where necessary.)\n");
			System.out.println("Here are all the previous turns (oldest to newest) with all letters KNOWN having been removed to help to guide your choice:");				
			for(int x = 0; x <= (Turns.size() - 1); x++) {  //  Iterate through all turns in the collection, to remove known letters
				Turn turn = Turns.get(x);					//  Get a turn from the collection			
				System.out.println(turn);			
			}
		}
	}  //  strategy()

	
	public static void turnsSummary(ArrayList<Turn> Turns, Data wordData) {	 //  Print a summary of every turn taken thus far
		System.out.println("Print.turnsSummary Pass # " + wordData.passNumber + " Let's take a look at every turn we've taken so far (oldest to newest) and their outcomes, so we can make any conclusions we are able to draw:");
		for(Turn turnFromCollection : Turns) {
			System.out.println(turnFromCollection);
		}  //  for-each
	if(wordData.foundChangedTo != null) System.out.println("'" + wordData.foundChangedFrom + "' changed to '"  + wordData.foundChangedTo + "' on most recent turn.");
	if(wordData.numLettersChanged != 0) System.out.println("PRINT Number of letters changed on last 2 turns: " + wordData.numLettersChanged);
	}
}  //  class