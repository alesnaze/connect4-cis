package main;

import java.util.Collections;
import java.util.Vector;
import java.io.*;

public class Connect4 {
	private static final int WIDTH=7, HEIGHT=6;
	private static final int MAX_RECURDEPTH=6;
	private int[][] grid=new int[WIDTH][HEIGHT];
	private int[] columnHeight=new int[WIDTH];
	private static BufferedReader in;

	public Connect4() { //constructor
		int column;
		int player=1;
		Vector moves=new Vector();
		while(true) {
			if(player==1) {
				printGrid();
				do {
					column=readInt()-1;
				}
				while(column<0 || column >=WIDTH || columnHeight[column]>=HEIGHT);
			}
			else {
				moves.removeAllElements();
				column=0;
				int prevValue=-MAX_RECURDEPTH-1;
				for(int x=0; x<WIDTH; x++) {
					if(columnHeight[x]>=HEIGHT) continue;
					int value=twoPlayers(2, x); // method for 2player
					if(value>prevValue) {
						moves.removeAllElements();
	                    prevValue=value;
					}
					if(value==prevValue) moves.add(new Integer(x));
				}
				if(moves.size()>0) {
					Collections.shuffle(moves);
					column=((Integer)moves.get(0)).intValue();
				}
				if(moves.size()==0) {
					System.out.println("Its error .");
					break;
				}
			}
			grid[column][columnHeight[column]]=player;
			columnHeight[column]++;
			int won=0;
			won=four(); // method that check four
			if(won>0) {
				printGrid();
				System.out.println("Player "+player+" won the game.");
				break;
			}
			player=3-player;
		}
	}

	int readInt() {
		try {
			String input=in.readLine();
			int number=Integer.parseInt(input);
			return number;
		}
		catch(NumberFormatException e) {
			return -1;
		}
		catch(IOException e) {
			return -1;
		}
	}

	int four() {
		// method for checking 4
		return 0;
	}

	int twoPlayers(int i, int x) {
		// 2 player
		return 0;
	}

	void printGrid() {
		// show the values of play
	}
}