package Menace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class TestTrain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Train train = new Train();
		Menace menace =train.train(3);
		System.out.println(menace);


	}

}
