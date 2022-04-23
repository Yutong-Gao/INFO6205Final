package Menace;

import java.util.HashMap;

import HumanStrategy.RandomHuman;

public class TrainRandom {
	char[] tic;
	int times;
	Menace menace = new Menace();
	RandomHuman human = new RandomHuman();
	HashMap<String,Move> movelist;
	public TrainRandom() {
		
	}
	
	public Menace train(int times) {
		this.times = times;
		
		for(int i = 0;i<times;i++) {
			int player =0;
			tic = new char[9];
			for(int j =0;j<9;j++) tic[j]='0';
			movelist = new HashMap<>();
			while(checkWinner(player)==-2) {
				nextStep(player);
				player = (player+1)%2;
			}
			int winner = checkWinner(1);
			if(winner==-1) trainDraw();
			else if(winner==1) trainWin();
			else trainLose();
			
		}
		
		return menace;
	}
	
	public void nextStep(int player) {
		if(player==0) humanMove();
		if(player==1) menaceMove();
	}
	
	public void menaceMove() {
		String state = getCheeseState();
		Move move =menace.nextMove(state);
		tic[move.getName()-1]='X';
		movelist.put(state, move);
		System.out.println(state);

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
