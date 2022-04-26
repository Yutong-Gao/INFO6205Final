package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import HumanStrategy.RandomHuman;

public class randomHumanTest {

	RandomHuman rh = new RandomHuman();
	@Test
	public void checkTest() {
		char[] c1 = {'X','X','0','0','0','O','O','0','0'};
		int i1 = rh.check(c1);
		char[] c2 = {'X','0','0','0','0','O','O','0','0'};
		int i2 = rh.check(c2);
		assertTrue(i1==2);
		assertTrue(i2==-1);
	}
	
	@Test
	public void nextMoveTest() {
		char[] c1 = {'X','X','0','0','0','O','O','0','0'};
		int i1 = rh.nextMove(c1);
		char[] c2 = {'X','0','0','0','0','O','O','0','0'};
		int i2 = rh.nextMove(c2);
		assertTrue(i1==2);
		assertTrue(i2!=0);
		assertTrue(i2!=5);
		assertTrue(i2!=6);
	}

}
