package Model;

import java.util.ArrayList;

public class MoveDictionary {
	/*
	 * to store all possible move
	 * 
	 * */
	
	ArrayList<Move> movelist;

	public MoveDictionary() {
		this.movelist = new ArrayList<>();
	}
	
	public void addMove(Move move) {
		this.movelist.add(move);
	}
	
	public void win(Move move) {
		for(Move m:movelist) {
			if(m.getName().equals(move.getName())) {
				int i =m.getProbability();
				m.setProbability(4*i);
			}
		}
	}
	
	public void lose(Move move) {
		for(Move m : movelist) {
			if(m.getName().equals(move.getName())) {
				int i = m.getProbability();
				m.setProbability(i/2);
			}
		}
	}
	
	public void draw(Move move) {
		for(Move m:movelist) {
			if(m.getName().equals(move.getName())) {
				int i =m.getProbability();
				m.setProbability(2*i);
			}
		}
	}
	
	public Move randomMove() {
		return null;
	}

}
