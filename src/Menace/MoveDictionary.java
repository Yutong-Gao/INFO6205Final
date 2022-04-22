package Menace;

import java.util.ArrayList;
import java.util.Random;

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
			if(m.getName()==move.getName()) {
				int i =m.getProbability();
				if(i*4>0) m.setProbability(i*4);
			}
		}
	}
	
	public void lose(Move move) {
		for(Move m : movelist) {
			if(m.getName()==move.getName()) {
				int i = m.getProbability();
				if(i/2>0) m.setProbability(i/2);
			}
		}
	}
	
	public void draw(Move move) {
		for(Move m:movelist) {
			if(m.getName()==move.getName()) {
				int i =m.getProbability();
				if(i*2>0) m.setProbability(i*2);
			}
		}
	}
	
	public Move randomMove() {
		int sum = 1;
		for(Move m:movelist) {
			sum +=m.getProbability();
		}
		
		
		Random ran = new Random();
		int i = ran.nextInt(sum);
		for(Move m:movelist) {
			if(i<=m.getProbability()) return m;
			else i -= m.getProbability();
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "MoveDictionary [movelist=" + movelist + "]";
	}
	
	

}
