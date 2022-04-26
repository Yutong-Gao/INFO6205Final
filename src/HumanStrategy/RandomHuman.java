package HumanStrategy;

import java.util.Random;

public class RandomHuman {
	char[] tic;
	
	public int nextMove(char[] tic) {
		if(check(tic)!=-1) return check(tic);
		else {
			Random random = new Random();
			int index = random.nextInt(9);
			while(tic[index]!='0'){
				index = random.nextInt(9);
			}
			return index;
		}
	}
	
	public int check(char[] tic) {
		if(tic[0]==tic[1]&&tic[1]!='0'&&tic[2]=='0') return 2;
		if(tic[0]==tic[2]&&tic[0]!='0'&&tic[1]=='0') return 1;
		if(tic[2]==tic[1]&&tic[1]!='0'&&tic[0]=='0') return 0;
		//check first line
		if(tic[3]==tic[4]&&tic[3]!='0'&&tic[5]=='0') return 5;
		if(tic[3]==tic[5]&&tic[3]!='0'&&tic[4]=='0') return 4;
		if(tic[5]==tic[4]&&tic[4]!='0'&&tic[3]=='0') return 3;
		//check second line
		if(tic[6]==tic[7]&&tic[7]!='0'&&tic[8]=='0') return 8;
		if(tic[6]==tic[8]&&tic[6]!='0'&&tic[7]=='0') return 7;
		if(tic[7]==tic[8]&&tic[7]!='0'&&tic[6]=='0') return 6;
		//check 3rd line
		
		if(tic[0]==tic[3]&&tic[3]!='0'&&tic[6]=='0') return 6;
		if(tic[0]==tic[6]&&tic[6]!='0'&&tic[3]=='0') return 3;
		if(tic[3]==tic[6]&&tic[6]!='0'&&tic[0]=='0') return 0;
		//check first column
		if(tic[1]==tic[4]&&tic[1]!='0'&&tic[7]=='0') return 7;
		if(tic[1]==tic[7]&&tic[7]!='0'&&tic[4]=='0') return 4;
		if(tic[7]==tic[4]&&tic[4]!='0'&&tic[1]=='0') return 1;
		//check second column
		if(tic[2]==tic[5]&&tic[5]!='0'&&tic[8]=='0') return 8;
		if(tic[2]==tic[8]&&tic[8]!='0'&&tic[5]=='0') return 5;
		if(tic[5]==tic[8]&&tic[5]!='0'&&tic[2]=='0') return 2;
		//check 3rd column
		
		if(tic[0]==tic[4]&&tic[4]!='0'&&tic[8]=='0') return 8;
		if(tic[0]==tic[8]&&tic[8]!='0'&&tic[4]=='0') return 4;
		if(tic[4]==tic[8]&&tic[8]!='0'&&tic[0]=='0') return 0;
		//check first diagonal
		if(tic[2]==tic[4]&&tic[4]!='0'&&tic[6]=='0') return 6;
		if(tic[2]==tic[6]&&tic[6]!='0'&&tic[4]=='0') return 4;
		if(tic[6]==tic[4]&&tic[4]!='0'&&tic[2]=='0') return 2;
		//check second diagonal
		
		return -1;
	}

}
