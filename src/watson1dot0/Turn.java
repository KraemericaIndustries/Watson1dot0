package watson1dot0;

import java.util.LinkedList;

public class Turn {

	LinkedList<String> guess = new LinkedList<String>();
	int response;

	public Turn(LinkedList<String> guess, int response) {
		super();
		this.guess = guess;
		this.response = response;
	}  //  constructor
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guess == null) ? 0 : guess.hashCode());
		result = prime * result + response;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turn other = (Turn) obj;
		if (guess == null) {
			if (other.guess != null)
				return false;
		} else if (!guess.equals(other.guess))
			return false;
		if (response != other.response)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Turn = [guess=" + guess + ", response=" + response + "]";
	}  //  override
}  //  class