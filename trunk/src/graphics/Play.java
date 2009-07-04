package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Play extends JFrame{
	private int x,y;

	public static void main(String[] args) {
		Play frame = new Play();
	}

	public Play() {
		setVisible(true);
		setSize(550,400);
		setTitle("Main Menu");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		repaint();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) { 
				x=mouseClick.getX();
				y=mouseClick.getY();				
				if (x>=190 && x<=370) {
					if (y>=235 && y<=285) {
						setVisible(false);
						DrawingOvals game = new DrawingOvals();
						game.mainFrame();
					}
					else if (y>=288 && y<=348) {
						setVisible(false);
						AboutUs aboutFrame = new AboutUs();
					}
					else if (y>=350 && y<=497) {
						System.exit(0);
					}
				}
			}
		});
	}

	final Image image = new ImageIcon("src/images/SplashScreen.png").getImage();
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

    public Dimension getPreferredSize() {
        return new Dimension(550,400);
    }
}
public void moveMade(Move aMove) 
    {
        int column = aMove.toInt();
        int row = board.numerOfChipsInColumn(column) - 1;
        
        if(aMove.maker().getNumber() == C4Board.FIRST_PLAYER_NUMBER)
        {
            drawBlackToken(row,column);
            update();
        }
        else
        {
            drawRedToken(row, column);
            
            if(lastComputerMove != null)
            {
                clearRedTip(lastComputerMove.toInt());
            }
            
            drawRedTip(aMove.toInt());
            lastComputerMove = aMove;
            update();
            
        }
        
    }

	
	
	
	
	
	 public void update()
    {
        Graphics g = this.getGraphics();
        g.drawImage(offscreenImage,0,0, this);
    }
	 
	 
	 
	 public class Asynchronous extends Player
	 {
	   

	     
	     private Move lastMove = null;

	   
	     /** Creates new AsynchronousPlayer */
	     public AsynchronousPlayer(String name, int number) 
	     {
	         super(name, number);
	     }
	     
	    
	     public synchronized  Move getMove(Board b)
	     {
	         try
	         {
	             wait();
	         }
	         
	         catch(InterruptedException e)
	         {
	             return null;
	         }
	      
	         return lastMove;
	     }
	   

	    public synchronized void makeMove(Move aMove)
	     {
	         lastMove = aMove;
	         notifyAll();
	     }
	  
	 }

