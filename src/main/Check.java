package main;

public class Check {
	int[][] arr = new int[6][7]; // this is a test array, you can erase it whenever you want
	public static void main(String[] args) {
		new Check();
	}

	public Check() {
		int f=checkwin();
		System.out.println(f);

		// a loop for printing the "arr" array
		for (int i=0;i < 6; i++) {
			for (int j=0 ; j < 7; j++) {
				System.out.print(" "+arr[i][j]);
			}
			System.out.println("");
		}
		System.out.println(getScore());
	}

	public int checkwin() {
		/**
		 * checkWin method is used for checking the board for a winner
		 * it uses Three main variables, "r" for storing the value of the player (takes 1 or 2)
		 * "x" and "y" for the axis X and axis Y
		 * it returns "r"
		 * */
		int r,x,y;
		r=0;
		for(y=2;y>-1;y--) {
			for(x=0;x<6;x++) {
				checku(x,y, r);
			}
		}
		for(y=0;y<7;y++) {
			for(x=0;x<4;x++)
			{
				check2r(x,y, r);
			}
		}
		for(y=2;y>-1;y--)
		{
			for(x=0;x<4;x++)
			{
				checkr(x,y, r);
			}
		}
		for(y=2;y>-1;y--)
		{
			for(x=3;x<6;x++)
			{
				checkl(x,y, r);
			}
		}
		return r;
	}

	public void checku(int x,int y,int r)
	{
		/** A method for checking the "Y" axis for a winner*/
		if ((arr[x][y]==2)&&(arr[x][y+1]==2)&&(arr[x][y+2]==2)&&(arr[x][y+3]==2))r=2;
		if ((arr[x][y]==1)&&(arr[x][y+1]==1)&&(arr[x][y+2]==1)&&(arr[x][y+3]==1))r=1;
	}

	public void check2r(int x,int y,int r) {
		/** A method for checking the "X" axis for a winner*/
		if ((arr[x][y]==2)&&(arr[x+1][y]==2)&&(arr[x+2][y]==2)&&(arr[x+3][y]==2))r=2;
		if ((arr[x][y]==1)&&(arr[x+1][y]==1)&&(arr[x+2][y]==1)&&(arr[x+3][y]==1))r=1;
	}

	public void checkr(int x,int y,int r) {
		/** A method for checking the diagonals for a winner*/
		if ((arr[x][y]==2)&&(arr[x+1][y+1]==2)&&(arr[x+2][y+2]==2)&&(arr[x+3][y+3]==2))r=2;
		if ((arr[x][y]==1)&&(arr[x+1][y+1]==1)&&(arr[x+2][y+2]==1)&&(arr[x+3][y+3]==1))r=1;
	}

	public void checkl(int x,int y,int r) {
		if ((arr[x][y]==2)&&(arr[x-1][y+1]==2)&&(arr[x-2][y+2]==2)&&(arr[x-3][y+3]==2))r=2;
		if ((arr[x][y]==1)&&(arr[x-1][y+1]==1)&&(arr[x-2][y+2]==1)&&(arr[x-3][y+3]==1))r=1;
	}

	public int getScore()
    {
		/**this method checks the returned value from "checkwin" method
		 * if it's 1, then Player 1 is the winner
		 * if it's 2, then Player 2 is the winner
		 * else, it'll return 0, which means nobody won this game*/
		int checked = checkwin();
        if(checked == 1)
        {
            //Connect 4 the score only go to 1
            return 1;
        }
        else if (checked == 2) { //second player
            //Connect 4 the score only go to 1
            return 2;
        }
        else {
        	return 0;
        }
    }
}