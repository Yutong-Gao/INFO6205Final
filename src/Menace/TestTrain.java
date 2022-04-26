package Menace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class TestTrain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TrainRandom trainRandom = new TrainRandom();
		Train train = new Train();
		Menace menacetrained =trainRandom.trainRecord(1000000,new Menace(),0);
		Menace menacetrained2 = trainRandom.trainRecord(1000000, new Menace(), 1);
		//Menace menace = train.trainRecord(10000, menacetrained);
		
		

	}

}
