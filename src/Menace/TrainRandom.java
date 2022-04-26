package Menace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import HumanStrategy.RandomHuman;

public class TrainRandom {
	char[] tic;
	int times;
	Menace menace;
	RandomHuman human = new RandomHuman();
	HashMap<String,Move> movelist;
	int player;
	public TrainRandom() {
		
	}
	
	public Menace train(Menace menace,int times,int player){
		//when player = 0, menace take the first move; when player =1, human take the first move
		this.times = times;
		this.menace = menace;
		for(int i = 0;i<times;i++) {
			this.player=player;
			tic = new char[9];
			for(int j =0;j<9;j++) tic[j]='0';
			movelist = new HashMap<>();
			while(checkWinner(player)==-2) {
				nextStep(player,menace);
				player = (player+1)%2;
			}
			int winner = checkWinner(player);
			if(winner==-1) {
				trainDraw(menace);
			}
			else if(winner==1) {
				trainWin(menace);
			} 
			else {
				 trainLose(menace);
			}			
			
		}
		return menace;
	}
	
	public Menace trainRecord(int times,Menace menace,int player) throws IOException {
		this.times = times;
		this.menace = menace;
		File file;
		if(player==0) {
			file=new File("src/trainResult_menacefirst.txt");
		}else {
			file = new File("src/trainResult_humanfirst.txt");
		}
		
		FileOutputStream fileOutputStream=new FileOutputStream(file);
		OutputStreamWriter dos=new OutputStreamWriter(fileOutputStream);//record training
		int win = 0;
		int lose = 0;
		int draw = 0;
		for(int i = 0;i<times;i++) {
			dos.write("\n\nrun "+(i+1)+" times\n");
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			dos.write("train time: "+dateString+"\n");
			this.player=player;
			tic = new char[9];
			for(int j =0;j<9;j++) tic[j]='0';
			movelist = new HashMap<>();
			while(checkWinner(player)==-2) {
				nextStep(player,menace);
				dos.write(getCheeseState()+"\n");
				player = (player+1)%2;
			}
			int winner = checkWinner(player);
			if(winner==-1) {
				trainDraw(menace);
				draw +=1;
				dos.write("Draw!");
			}
			else if(winner==1) {
				trainWin(menace);
				win +=1;
				dos.write("Win!");
			} 
			else {
				 trainLose(menace);
				 lose +=1;
				 dos.write("Lose!");
			}			
			
		}
		dos.write("\n\nTOTAL:"+times+" Win:"+win+" Draw:"+draw+" Lose:"+lose);
		dos.close();
		return menace;
	}
	
	public void nextStep(int player,Menace menace) {
		if(player==1) humanMove();
		if(player==0) menaceMove(menace);
	}
	
	public void menaceMove(Menace menace) {
		String state = getCheeseState();
		Move move =menace.nextMove(state);
		tic[move.getName()-1]='X';
		movelist.put(state, move);

	}
	
	public void humanMove() {
		int index = human.nextMove(tic);
		tic[index]='O';
	}
	
	public String getCheeseState() {
    	//get current cheese state
    	String state = "";
        for(int i=0;i<9;i++){
            if(tic[i]=='O') state +="1";
            else if(tic[i]=='X') state +="2";
            else state +="0";
        }
        return state;
    }
    
    public void trainWin(Menace menace) {
    	//train the machine
    	for(String key:movelist.keySet()) {
    		menace.win(key, movelist.get(key));
    	}
    }
    
    public void trainLose(Menace menace) {
    	//train the machine
    	for(String key:movelist.keySet()) {
    		menace.lose(key, movelist.get(key));
    	}
    }
    
    public void trainDraw(Menace menace) {
    	//train the machine
    	for(String key:movelist.keySet()) {
    		menace.draw(key, movelist.get(key));
    	}
    }
    
    public int checkWinner(int playerNow) {
        
        char[] chess=new char[10];
        for(int i=1;i<=9;i++){
            chess[i]=tic[i-1];
        }
        if(     (chess[1]==chess[2]&&chess[2]==chess[3]&&chess[1]!='0')||
                (chess[4]==chess[5]&&chess[5]==chess[6]&&chess[6]!='0')||
                (chess[7]==chess[8]&&chess[8]==chess[9]&&chess[9]!='0')||
                (chess[1]==chess[5]&&chess[5]==chess[9]&&chess[9]!='0')||
                (chess[7]==chess[5]&&chess[5]==chess[3]&&chess[3]!='0')||
                (chess[1]==chess[4]&&chess[4]==chess[7]&&chess[7]!='0')||
                (chess[2]==chess[5]&&chess[5]==chess[8]&&chess[8]!='0')||
                (chess[3]==chess[6]&&chess[6]==chess[9]&&chess[9]!='0')){
        	
            return playerNow;
        }
        else if(checkDogfall(chess))
            return -1;
        return -2;
    }
    
    public boolean checkDogfall(char[] chess){
        
        for(int i=1;i<chess.length;i++){
            if (chess[i]=='0')
                return false;
        }
        return true;
    }


}
