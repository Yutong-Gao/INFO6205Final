package Menace;

import java.util.HashMap;

public class Menace {
	 /*
	  * this class is to store the different cheese state and next move
	  * state are presented by using string
	  * move is a random move with different probability
	  * probability learned by training
	  * both state and moved stored in a HashMap, state is the key and the value is move.
	  * 
	  * */
	HashMap<String,MoveDictionary> cheeseState;
	
	public Menace() {
		cheeseState = new HashMap<>();
		
	}
	
	@Override
	public String toString() {
		return "Menace [cheeseState=" + cheeseState + "]";
	}

	
	/* Every state are presented by a string,
	 * for example: 000010000 means only the fifth square has 'O'
	 * (0 means no cheese, 1 means O, 2 means X) 
	 * 
	 * This method is to return nextMove
	 * 
	 * If the current state is not exist in hashMap, will build a new MoveDictionary
	 * with current state, and the return a move from MD.
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
					md.addMove(new Move(i+1,10));
				}
			}			
			cheeseState.put(state, md);
			return md.randomMove();
			
		}
	}
	
	public void win(String state,Move move) {
		//change the probability of move
		MoveDictionary md = cheeseState.get(state);
		md.win(move);
	}
	
	public void lose(String state,Move move) {
		//change the probability of move
		MoveDictionary md = cheeseState.get(state);
		md.lose(move);
	}
	
	public void draw(String state,Move move) {
		//change the probability of move
		MoveDictionary md = cheeseState.get(state);
		md.draw(move);
	}
	
	
	
}
