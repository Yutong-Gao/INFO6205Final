package Model;

import java.util.HashMap;

public class CheeseState {
	 /*
	  * this class is to store the different cheese state and next move
	  * state are presented by using string
	  * move is a random move with different probability
	  * probability learned by training
	  * both state and moved stored in a Hashtable, state is the key and the probability is move.
	  * 
	  * */
	HashMap<String,MoveDictionary> cheeseState;
	
	public CheeseState() {
		cheeseState = new HashMap<>();
		
	}
	
	/* Every state are presented by a string,
	 * for example: 000010000 means only the fifth square has 'O'
	 * (0 means no cheese, 1 means X, 2 means O) 
	 * 
	 * This method is to return nextMove
	 * 
	 * If the current state is not exist on hashMap,will build a new MoveDictionary
	 * with current state, else will return a move from MD.
	 * 
	 * */

	public Move nextMove(String state) {
		if(cheeseState.containsKey(state)) {
			MoveDictionary md =cheeseState.get(state);
			return md.randomMove();
		}else {
			MoveDictionary md = new MoveDictionary();
			for(int i = 0;i<9;i++) {
				if(state.charAt(i)=='0') {
					md.addMove(new Move(String.valueOf(i+1),10));
				}
			}			
			cheeseState.put(state, md);
			return md.randomMove();
			
		}
	}
	
}
