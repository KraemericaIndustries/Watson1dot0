package watson1dot0;

import java.util.LinkedList;

public class Data {
	
	int passNumber = 1;
	int numLettersChanged = 0;
	
	String foundChangedTo;
	String foundChangedFrom;
	
	LinkedList<String> inOpponentsWord = new LinkedList<String>();
	LinkedList<String> notInOpponentsWord = new LinkedList<String>();
	LinkedList<String> together = new LinkedList<String>();
	LinkedList<String> alsoTogether = new LinkedList<String>();
	LinkedList<String> doNotKnow = new LinkedList<String>();

	public Data() {
		doNotKnow.add("A");
		doNotKnow.add("B");
		doNotKnow.add("C");
		doNotKnow.add("D");
		doNotKnow.add("E");
		doNotKnow.add("F");
		doNotKnow.add("G");
		doNotKnow.add("H");
		doNotKnow.add("I");
		doNotKnow.add("J");
		doNotKnow.add("K");
		doNotKnow.add("L");
		doNotKnow.add("M");
		doNotKnow.add("N");
		doNotKnow.add("O");
		doNotKnow.add("P");
		doNotKnow.add("Q");
		doNotKnow.add("R");
		doNotKnow.add("S");
		doNotKnow.add("T");
		doNotKnow.add("U");
		doNotKnow.add("V");
		doNotKnow.add("W");
		doNotKnow.add("X");
		doNotKnow.add("Y");
		doNotKnow.add("Z");
	}  //  constructor
	
	@Override
	public String toString() {
		return "IN your opponents Word:               " + inOpponentsWord     + System.lineSeparator() +
			   "NOT IN your opponents Word:           " + notInOpponentsWord  + System.lineSeparator() +
			   "UNKNOWN:                              " + doNotKnow           + System.lineSeparator() +
			   "Together (either IN or NOT IN):       " + together            + System.lineSeparator() +
			   "Also Together (either IN or NOT IN):  " + alsoTogether        + System.lineSeparator();
//			   "'" + foundChangedFrom + "' changed to '"   + foundChangedTo     + "' on most recent turn." + System.lineSeparator();
//		       "Number of letters changed on last 2 turns: " + numLettersChanged  + System.lineSeparator();
	}  //  override
}  //  class