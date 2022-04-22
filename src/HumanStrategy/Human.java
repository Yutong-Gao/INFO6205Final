package HumanStrategy;

public class Human {
	char[] tic = new char[9];
	int n = tic.length;
	
	public Human() {
		
	}
	
	public  void firstStep(char[] tic, int countX, int countO){
        if(countX == countO){
            return;
        }//Player1 move first;
        else if(countX - countO == 10){
            tic[4] = 'O';
        }
    }

    public  int secondStep(char[] tic, int countX, int countO, int countNum){
        if(countX == countO){
            return -1;
        }
        //Two conditions
        //2-1. Opponent placed 1st move on edge
        else if(countX - countO == 8){
            if(tic[1] == 'X'){
                tic[0] = 'O';
                return 1;
            }else if(tic[3] == 'X'){
                tic[6] = 'O';
                return 1;
            }else if(tic[5] == 'X'){
                tic[2] = 'O';
                return 1;
            }else if(tic[7] == 'X'){
                tic[8] = 'O';
                return 1;
            }//2-2. Opponent placed 1st move on a diagonal
            else if(tic[1] == '0' && tic[3] == '0' && tic[5] == '0' && tic[7] == '0'){
                for(int i = 0; i < n; i++){
                    if(tic[i] == 'X'){
                        //Place 2nd move on the same diagonal line
                        countNum = i;
                        tic[12-4-i] = 'O';
                    }
                }
                //count == 0, 2, 6, 8
                return countNum;
            }
        }
        return -1;
    }

    public  int thirdStep(char[] tic, int countX, int countO, int b){
        if(countX == countO){
            return -1;
        }
        else if(countX - countO == 6){
            //From secondStep's 1st condition; the opponent may have 2 options.
            int a = 0;
            //Find 2nd X
            for(int i = 0; i < n; i++){
                if(i!=4 && tic[i] == 'O'){
                    //a == index of 2nd 'X'
                    a = i;
                }
            }
            //3-1-1 the opponent ignored both X's lie on the same diagonal line
            //Player1 win the game - END
            if(b == 1){
                if(tic[12-a-4] == '0'){
                    tic[12-a-4] = 'O';
                    return 9;
                }
                //3-1-2 the opponent prevents Player1 from connecting three X into a line - return 1;
                else if(tic[0] == 'O' && tic[12-a-4] == 'X'){
                    tic[6] = 'O';
                    return 1;
                }else if(tic[6] == 'O' && tic[12-a-4] == 'X'){
                    tic[8] = 'O';
                    return 1;
                }else if(tic[8] == 'O' && tic[12-a-4] == 'X'){
                    tic[2] = 'O';
                    return 1;
                }else if(tic[2] == 'O' && tic[12-a-4] == 'X'){
                    tic[0] = 'O';
                    return 1;
                }
            }
            //3-2
            //From 2-2. return 0, 2, 6, 8;
            if(b == 0){
                for(int j = 0; j < n; j++){
                    if(tic[j] == 'X' && j != 0){
                        //3-2-1
                        if(j == 1 || j == 7){
                            tic[2] = 'O';
                        }else if(j == 3 || j == 5){
                            tic[6] = 'O';
                        }//3-2-2
                        else if(j == 2){
                            tic[1] = 'O';
                        }else if(j == 6){
                            tic[3] = 'O';
                        }
                    }
                }
                return 0;
            }else if(b == 2){
                for(int j = 0; j < n; j++){
                    if(tic[j] == 'X' && j != 2){
                        //3-2-1
                        if(j == 1 || j == 7){
                            tic[0] = 'O';
                        }else if(j == 3 || j == 5){
                            tic[8] = 'O';
                        }//3-2-2
                        else if(j == 0){
                            tic[1] = 'O';
                        }else if(j == 8){
                            tic[5] = 'O';
                        }
                    }
                }
                return 2;
            }else if(b == 6){
                for(int j = 0; j < n; j++){
                    if(tic[j] == 'X' && j != 2){
                        //3-2-1
                        if(j == 3 || j == 5){
                            tic[0] = 'O';
                        }else if(j == 7 || j == 1){
                            tic[8] = 'O';
                        }//3-2-2
                        else if(j == 8){
                            tic[7] = 'O';
                        }else if(j == 0){
                            tic[3] = 'O';
                        }
                    }
                }
                return 6;
            }else if(b == 8){
                for(int j = 0; j < n; j++){
                    if(tic[j] == 'X' && j != 8){
                        //3-2-1
                        if(j == 5 || j == 3){
                            tic[2] = 'O';
                        }else if(j == 7 || j == 1){
                            tic[6] = 'O';
                        }//3-2-2
                        else if(j == 6){
                            tic[7] = 'O';
                        }else if(j == 2){
                            tic[5] = 'O';
                        }
                    }
                }
                return 8;
            }
        }
        return -1;
    }

    public int fourthStep(char[] tic, int countX, int countO, int c){
        if(countX == countO){
            return -1;
        } else if(countX - countO == 4){
            //4-1. This move depends on 3-1-2 move
            int[] nums = new int[2];
            int count = 0;
            for(int i = 0; i < n; i++){
                if(i != 4 && tic[i] == 'X'){
                    nums[count] = i;
                }
            }
            //If the diagonals could be connected to form a line without 'O'
            //Then Player1 win - END
            if(c == 1){
                if(tic[12-nums[0]-4] == '0'){
                    tic[12-nums[0]-4] = 'O';
                    return 9;
                }else if(tic[12-nums[1]-4] == '0'){
                    tic[12-nums[1]-4] = 'O';
                    return 9;
                }else if(tic[12-nums[0]-4] == 'X' && tic[12-nums[1]-4] == 'X' && tic[0] == 'O' && tic[6] == 'O'){
                    tic[3] = 'O';
                    return 9;
                }else if(tic[12-nums[0]-4] == 'X' && tic[12-nums[1]-4] == 'X' && tic[0] == 'O' && tic[2] == 'O'){
                    tic[1] = 'O';
                    return 9;
                }else if(tic[12-nums[0]-4] == 'X' && tic[12-nums[1]-4] == 'X' && tic[6] == 'O' && tic[8] == 'O'){
                    tic[7] = 'O';
                    return 9;
                }else if(tic[12-nums[0]-4] == 'X' && tic[12-nums[1]-4] == 'X' && tic[8] == 'O' && tic[2] == 'O'){
                    tic[5] = 'O';
                    return 9;
                }
            }

            //4-2.
            if(c == 0){
                //4-2-1. From 3-2-1
                //Player1 win - END
                if(tic[2] == 'O'){
                    if(tic[5] == '0'){
                        tic[5] = 'O';
                        return 9;
                    }
                    if(tic[6] == '0'){
                        tic[6] = 'O';
                        return 9;
                    }
                }
                if(tic[6] == 'O'){
                    if(tic[2] == '0'){
                        tic[2] = 'O';
                        return 9;
                    }
                    if(tic[7] == '0'){
                        tic[7] = 'O';
                        return 9;
                    }
                }
                //4-2-2  From 3-2-2 If the vertical or horizontal could be connected to form a line without 'O'
                ////Player1 win - END
                if(tic[1]=='O' && tic[8] == 'O' && tic[7] == '0'){
                    tic[7] = 'O';
                    return 9;
                }else if(tic[3]=='O' && tic[8] == 'O' && tic[5] == '0'){
                    tic[5] = 'O';
                    return 9;
                }//4-2-3 The opponent prevents Player1 from being aligned vertically or horizontally
                else if(tic[1]=='O' && tic[8] == 'O' && tic[7] == 'X'){
                    tic[3] = 'O';
                    return 0;
                }else if(tic[3]=='O' && tic[8] == 'O' && tic[7] == 'X'){
                    tic[1] = 'O';
                    return 0;
                }
            }
            if(c == 2){
                //4-2-1. From 3-2-1
                //Player1 win - END
                if(tic[0] == 'O'){
                    if(tic[3] == '0'){
                        tic[3] = 'O';
                        return 9;
                    }
                    if(tic[8] == '0'){
                        tic[8] = 'O';
                        return 9;
                    }
                }
                if(tic[8] == 'O'){
                    if(tic[0] == '0'){
                        tic[0] = 'O';
                        return 9;
                    }
                    if(tic[7] == '0'){
                        tic[7] = 'O';
                        return 9;
                    }
                }
                //4-2-2. If the vertical or horizontal could be connected to form a line without 'O'
                //Player1 win - END
                if(tic[1]=='O' && tic[6] == 'O' && tic[7] == '0'){
                    tic[7] = 'O';
                    return 9;
                }else if(tic[5]=='O' && tic[6] == 'O' && tic[3] == '0'){
                    tic[3] = 'O';
                    return 9;
                }//4-2-3 The opponent prevents Player1 from being aligned vertically or horizontally
                else if(tic[1]=='O' && tic[6] == 'O' && tic[7] == 'X'){
                    tic[5] = 'O';
                    return 2;
                }else if(tic[5]=='O' && tic[6] == 'O' && tic[7] == 'X'){
                    tic[1] = 'O';
                    return 2;
                }
            }
            if(c == 6){
                //4-2-1. From 3-2-1
                //Player1 win - END
                if(tic[0] == 'O'){
                    if(tic[1] == '0'){
                        tic[1] = 'O';
                        return 9;
                    }
                    if(tic[8] == '0'){
                        tic[8] = 'O';
                        return 9;
                    }
                }
                if(tic[8] == 'O'){
                    if(tic[0] == '0'){
                        tic[0] = 'O';
                        return 9;
                    }
                    if(tic[5] == '0'){
                        tic[5] = 'O';
                        return 9;
                    }
                }
                //4-2-2. If the vertical or horizontal could be connected to form a line without 'O'
                //Player1 win - END
                if(tic[3]=='O' && tic[2] == 'O' && tic[5] == '0'){
                    tic[5] = 'O';
                    return 9;
                }else if(tic[7]=='O' && tic[2] == 'O' && tic[1] == '0'){
                    tic[1] = 'O';
                    return 9;
                }//4-2-3 The opponent prevents Player1 from being aligned vertically or horizontally
                else if(tic[3]=='O' && tic[2] == 'O' && tic[5] == 'X'){
                    tic[7] = 'O';
                    return 6;
                }else if(tic[3]=='O' && tic[2] == 'O' && tic[5] == 'X'){
                    tic[3] = 'O';
                    return 6;
                }
            }
            if(c == 8){
                //4-2-1. From 3-2-1
                //Player1 win - END
                if(tic[2] == 'O'){
                    if(tic[1] == 'X'){
                        tic[1] = 'O';
                        return 9;
                    }
                    if(tic[6] == '0'){
                        tic[6] = 'O';
                        return 9;
                    }
                }
                if(tic[6] == 'O'){
                    if(tic[3] == '0'){
                        tic[3] = 'O';
                        return 9;
                    }
                    if(tic[2] == '0'){
                        tic[2] = 'O';
                        return 9;
                    }
                }
                //4-2-2. If the vertical or horizontal could be connected to form a line without 'O'
                //Player1 win - END
                if(tic[0]=='O' && tic[7] == 'O' && tic[1] == '0'){
                    tic[1] = 'O';
                    return 9;
                }else if(tic[0]=='O' && tic[5] == 'O' && tic[3] == '0'){
                    tic[3] = 'O';
                    return 9;
                }//4-2-3 The opponent prevents Player1 from being aligned vertically or horizontally
                else if(tic[0]=='O' && tic[7] == 'O' && tic[1] == 'X'){
                    tic[5] = 'O';
                    return 8;
                }else if(tic[0]=='O' && tic[5] == 'O' && tic[3] == 'X'){
                    tic[7] = 'O';
                    return 8;
                }
            }
        }

        return -1;
    }
    public int fifthStep(char[] tic, int countX, int countO, int d){
        if(countX == countO){
            return -1;
        }
        //From 4-2-3
        //There's only one empty place left
        else if(countX - countO==2){
            for(int i = 0; i < n; i++){
                if(tic[i] == '0'){
                    tic[i] = 'O';
                    return 9;
                }
            }
        }
        return -1;
    }



    public boolean checkFull(char[] tic){
        for(int i=1;i<tic.length;i++){
            if (tic[i]>='1'&&tic[i]<='9')
                return false;
        }
        return true;
    }
	 
	 

}
