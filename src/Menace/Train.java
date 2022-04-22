package Menace;

import java.util.HashMap;

import HumanStrategy.Human;

public class Train {
	int times;
	Menace menace = new Menace();
	Human human = new Human();
	char[] tic;
	HashMap<String,Move> movelist;
	public Train() {
		
	}
	
	public Menace train(int times) {
		this.times = times;
		
		for(int i = 0;i<times;i++) {
			tic = new char[9];
			for(int j =0;j<9;j++) tic[j]='0';
			int countO =10;
			int countX =0;
			int countNum = 0;
			movelist =  new HashMap<>();
			human.firstStep(tic, countO, countX);
			countO--;
			countX++;
			//human take the first move
			
			menaceMove();
			countO--;
			countX++;
			// menace take the second move
			
			int b = human.secondStep(tic, countO, countX, countNum);
			countO--;
			countX++;
			//human take the 3rd move
			
			menaceMove();
			countO--;
			countX++;
			//menace take the 4th move
			
			int c = human.thirdStep(tic, countO, countX, b);
			countO--;
			countX++;
			//human take the 5th move
			if(checkWinner(0)==0) {
				trainLose();
				continue;
			}
			//check win
			
			menaceMove();
			countO--;
			countX++;
			//menace take the 6th move
			if(checkWinner(1)==1) {
				trainWin();
				continue;
			}
			//check win
			
			int d = human.fourthStep(tic, countO, countX, c);
			countO--;
			countX++;
			//human take the 7th move
			if(checkWinner(0)==0) {
				trainLose();
				continue;
			}
			//check win
			
			menaceMove();
			countO--;
			countX++;
			//menace take the 8th move
			if(checkWinner(1)==1) {
				trainWin();
				continue;
			}
			//check win
			
			int f = human.fifthStep(tic, countO, countX, d);
			countO--;
			countX++;
			//human take the 9th move
			if(checkWinner(0)==0) {
				trainLose();
				
				continue;
			}else if(checkWinner(0)==-1) {
				trainDraw();
				continue;
			}
			//check win and draw
		}
		
		return menace;
	}
	
	public void menaceMove() {
		String state = getCheeseState();
		Move move =menace.nextMove(state);
		tic[move.getName()-1]='X';
		movelist.put(state, move);

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
    
    public void trainWin() {
    	//train the machine
    	for(String key:movelist.keySet()) {
    		menace.win(key, movelist.get(key));
    	}
    }
    
    public void trainLose() {
    	//train the machine
    	for(String key:movelist.keySet()) {
    		menace.lose(key, movelist.get(key));
    	}
    }
    
    public void trainDraw() {
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
        if(     (chess[1]==chess[2]&&chess[2]==chess[3])||
                (chess[4]==chess[5]&&chess[5]==chess[6])||
                (chess[7]==chess[8]&&chess[8]==chess[9])||
                (chess[1]==chess[5]&&chess[5]==chess[9])||
                (chess[7]==chess[5]&&chess[5]==chess[3])||
                (chess[1]==chess[4]&&chess[4]==chess[7])||
                (chess[2]==chess[5]&&chess[5]==chess[8])||
                (chess[3]==chess[6]&&chess[6]==chess[9])){
        	
            return playerNow;
        }
        else if(checkDogfall(chess))
            return -1;
        return -2;
    }
    
    public boolean checkDogfall(char[] chess){
        
        for(int i=1;i<chess.length;i++){
            if (chess[i]>='1'&&chess[i]<='9')
                return false;
        }
        return true;
    }

}
