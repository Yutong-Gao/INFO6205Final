package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Menace.Menace;
import Menace.Move;

public class menaceTest {
	
	Menace menace = new Menace();
	

	@Test
	public void nextMoveTest() {
		Move move =menace.nextMove("001210000");
		assertTrue(move!=null);
	}
	
	@Test
	public void winTest() {
		Move move =menace.nextMove("001210000");
		menace.win("001210000", move);
		assertTrue(move.getProbability()==40);
	}
	
	@Test
	public void loseTest() {
		Move move =menace.nextMove("001210000");
		menace.lose("001210000", move);
		assertTrue(move.getProbability()==5);
	}
	
	@Test
	public void drawTest() {
		Move move =menace.nextMove("001210000");
		menace.draw("001210000", move);
		assertTrue(move.getProbability()==20);
	}

}
