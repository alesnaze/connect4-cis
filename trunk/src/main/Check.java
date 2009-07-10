package main;

public class Check {
	int[][] arr = new int[6][7];
	public static void main(String[] args) {
		new Check();
	}

	public void Check() {
		int f=checkwin();
		System.out.println(f);
	
		for (int i=0;i < 6; i++) {
			for (int j=0 ; j < 7; j++) {
				System.out.print(" "+arr[i][j]);
			}
			System.out.println("");
		}
	}

	public  int checkwin() {
		int r,x,y;
		r=0;
		for(y=2;y>-1;y--) {
			for(x=0;x<7;x++) {
				checku(x,y, r);
			}
		}
		for(y=0;y<6;y++) {
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
			for(x=3;x<7;x++)
			{
				checkl(x,y, r);
			}
		}
		return r;
	}

	public void checku(int x,int y,int r)
	{
		if ((arr[x][y]==2)&&(arr[x][y+1]==2)&&(arr[x][y+2]==2)&&(arr[x][y+3]==2))r=2;
		if ((arr[x][y]==1)&&(arr[x][y+1]==1)&&(arr[x][y+2]==1)&&(arr[x][y+3]==1))r=1;
	}
	

	public void check2r(int x,int y,int r) {
		if ((arr[x][y]==2)&&(arr[x+1][y]==2)&&(arr[x+2][y]==2)&&(arr[x+3][y]==2))r=2;
		if ((arr[x][y]==1)&&(arr[x+1][y]==1)&&(arr[x+2][y]==1)&&(arr[x+3][y]==1))r=1;
	}

	public void checkr(int x,int y,int r) {
		if ((arr[x][y]==2)&&(arr[x+1][y+1]==2)&&(arr[x+2][y+2]==2)&&(arr[x+3][y+3]==2))r=2;
		if ((arr[x][y]==1)&&(arr[x+1][y+1]==1)&&(arr[x+2][y+2]==1)&&(arr[x+3][y+3]==1))r=1;
	}

	public void checkl(int x,int y,int r) {
		if ((arr[x][y]==2)&&(arr[x-1][y+1]==2)&&(arr[x-2][y+2]==2)&&(arr[x-3][y+3]==2))r=2;
		if ((arr[x][y]==1)&&(arr[x-1][y+1]==1)&&(arr[x-2][y+2]==1)&&(arr[x-3][y+3]==1))r=1;
	}
	
	public int getScore(Player aPlayer)
    {
        if(aPlayer.getNumber() == PLAYER1)
        {
            //Connect 4 the score only go to 1
            if(checkwin()!= 0)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        else //second player
        {
            //Connect 4 the score only go to 1
            if(checkwin() != 0)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    } 
}		