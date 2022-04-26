package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Menace.Move;
import Menace.MoveDictionary;

public class modelTest {

	@Test
	public void randomMoveTest() {
		MoveDictionary md = new MoveDictionary();
		md.addMove(new Move(1,10));
		md.addMove(new Move(2,20));
		md.addMove(new Move(3,20));
		md.addMove(new Move(4,20));
		md.addMove(new Move(5,20));
		md.addMove(new Move(6,20));
		assertTrue(md.randomMove()!=null);
	}
	
	@Test
	public void winTest() {
		MoveDictionary md = new MoveDictionary();
		Move move = new Move(1,10);
		md.addMove(move);
		md.win(move);
		assertTrue(move.getProbability()==40);	
	}
	
	@Test
	public void loseTest() {
		MoveDictionary md = new MoveDictionary();
		Move move = new Move(1,10);
		md.addMove(move);
		md.lose(move);
		assertTrue(move.getProbability()==5);	
	}
	@Test
	public void drawTest() {
		MoveDictionary md = new MoveDictionary();
		Move move = new Move(1,10);
		md.addMove(move);
		md.draw(move);
		assertTrue(move.getProbability()==20);	
	}

}
