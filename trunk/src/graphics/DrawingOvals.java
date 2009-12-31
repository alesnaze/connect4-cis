package graphics;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingOvals extends JFrame implements Runnable {
	/**
	 * This class draws Ovals (Circles) on the main frame and checks whether any
	 * of these Ovals are being clicked on then it performs action according to
	 * the place of the circle
	 * */

	// define chat components
	JTextField sendSpace = new JTextField(55);
	JTextArea recieveSpace = new JTextArea(4, 65);
	JScrollPane sp_recieveSpace = new JScrollPane(recieveSpace);
	JButton send = new JButton("send");
	 JButton replay=new JButton("replay");
	Socket socket;
	BufferedReader in = null;
	BufferedWriter out = null;

	public void run() {

		while (true) {
			try {
				InetAddress ia = socket.getInetAddress();
				recieveSpace.append(ia.getHostName() + " : " + in.readLine()
						+ "\n");
			} catch (IOException e) {

			}
		}
	}

	public void mainFrame() {

		// Creating the Frame and setting it's properties [visibility, size,
		// resizability and closing]
		DrawingOvals frame = new DrawingOvals();
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setTitle("Connect 4");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		final Image icon = new ImageIcon("src/images/Connect4Logo.png").getImage();
		frame.setIconImage(icon);
		// Adding the JPanel to the JFrame and edit their properties
		Panel panel = new Panel();
		panel.setOpaque(false);

		frame.add(panel, BorderLayout.NORTH);
		frame.pack();

		// adding chat panel to the frame and add chat components to it
		ChatPanel cp = new ChatPanel();
		cp.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 12));
		cp.add(sp_recieveSpace);
		recieveSpace.setEditable(false);
		cp.add(sendSpace);
		cp.add(send);
		 cp.add(replay);
		frame.getRootPane().setDefaultButton(send);
		frame.add(cp, BorderLayout.SOUTH);
		
		c4Game m = new c4Game();
		frame.add(m,BorderLayout.NORTH);
		m.paint();
		
	}

	// getting the mouse position when clicked
	int x, y;

	public DrawingOvals() {
		// chat client code
		try {

			socket = new Socket("localhost", 8000);
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream(), "UTF8"));
			out = new BufferedWriter(new OutputStreamWriter(socket
					.getOutputStream(), "UTF8"));

			Thread t = new Thread(this);
			t.start();

		} catch (IOException ioe) {

		}

		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recieveSpace.append(" you : " + sendSpace.getText() + "\n");
				try {
					out.write(sendSpace.getText());
					out.newLine();
					out.flush();
					sendSpace.setText("");
				} catch (IOException ie) {
				}
			}
		});
		 replay.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e) {
            	 DrawingOvals x=new DrawingOvals();
                 x.hide();
                 mainFrame();    
                 
 
     }
         });


		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) {
				x = mouseClick.getX();
				y = mouseClick.getY();
				repaint();
			}
		});
	}

	// Creating an ArrayList that will hold the Ovals dimensions
	ArrayList<int[]> positions = new ArrayList<int[]>();

	// Painting the Circles and filling them
	public void paint(Graphics g) {
		super.paint(g);

		// Drawing the Circles
		for (int i = 1; i <= 7; i++) {
			for (int j = 1; j <= 6; j++) {
				g.setColor(Color.white);
				int ovalX = 130 + (i * 60);
				int ovalY = (j * 60);
				g.drawOval(ovalX, ovalY, 50, 50);

				if (x >= ovalX && x <= 50 + ovalX) {
					if (y >= ovalY && y <= 50 + ovalY) {
						int[] p = new int[2];
						p[0] = ovalX;
						p[1] = ovalY;
						if (positions.isEmpty() == true) {
							positions.add(p);
						}
						else {
							boolean equals = false;
							for (int z = 0; z < positions.size(); z++) {
								int[] arr = positions.get(z);
								if (arr[0] == p[0]) {
									if (arr[1] == p[1]) {
										equals = true;
									}
								}
							}
							if (equals == false) {
								positions.add(p);
							}
						}
						System.out.println(positions.size());
					}
				}
				for (int h = 0; h < positions.size(); h++) {
					int[] arr = positions.get(h);
					g.fillOval(arr[0], arr[1], 50, 50);
				}
			}
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}
}
class c4Game extends AbstractGrid  
{
	
	public static final int numOfRows = 6, numOfColumns = 7;
	
	private int cellBorderOffset = 5; //How far from a cell's border should the naught/cross be drawn from, the margin/padding
	private int coinHolderWidth = 7; //How big the coin holder is
	private int outsideGridBorder = 15; //How much gap is between the grid and the game canvas border

	private final Color clrRedPiece = new Color(255, 0, 0);
	private final Color clrBluePiece = new Color(0, 0, 255);
	private final Color clrCellBorder = new Color(6, 79, 167);

	private int numMovesMade = 0; //If get up to 42 then the game is a draw	
	private int columnMouseOver = -1;
	
	private boolean onlyPaintingHangingCoin = false; //When true, only paints the hanging coin, saves drawing time

	public c4Game(){}

	public c4Game (GameInterface parent)
	{
		
		super(parent, numOfRows, numOfColumns);
		playerMatrix = new c4PlayerMatrix(numOfRows, numOfColumns);
		
		borderSize = 0;		
		clrGridBackground = new Color(75, 141, 221);
		
		gameHeight = gridHeight + (outsideGridBorder * 2);
		gameWidth = gridWidth + (outsideGridBorder * 2);
		gridStartX = outsideGridBorder;
		gridStartY = outsideGridBorder;
		
		setupCanvasSize();

	}	
		
	private boolean canMove ()
	{
		
		if (isConnected && (currentPlayer != meNetworkPlayer))
		{
			return false;
		}
		else if (playerMatrix.isBoardLocked())
		{
			return false;
		}
		
		return true;
		
	}	

	public void decodeNetworkMove (String strMove)
	{

		String[] strKeyValue = strMove.split("=");
		doMove(Integer.parseInt(strKeyValue[1]));

	}

	private void doMove (int column)
	{

		numMovesMade++;
		playerMatrix.setCellContents(-1, column, currentPlayer);
		boolean win = playerMatrix.isWinner(-1, column, currentPlayer);

		//If this move was made by you, then send it to the other player
		if (isConnected && currentPlayer == meNetworkPlayer)
		{
			parent.gameEncodedMove("column=" + column);
		}

		if (win)
		{
			gameWon();			
		}
		else if (numMovesMade >= 42)
		{
			gameDraw();
		}
		else
		{
			moveMadeSwitchPlayers();
		}
		
		repaint();

	}
	
	private void drawCell (int fromX, int fromY, Graphics g)
	{
		
		g.setColor(clrCellBorder);
		
		g.fillRect(fromX, fromY, CELLSIZE, coinHolderWidth); //NW - NE
		g.fillRect(fromX, (fromY + (CELLSIZE - coinHolderWidth)), CELLSIZE, coinHolderWidth); //SW - SE
		g.fillRect(fromX, fromY, coinHolderWidth, CELLSIZE); //NW - SW
		g.fillRect((fromX + (CELLSIZE - coinHolderWidth)), fromY, coinHolderWidth, CELLSIZE); //NE - SE
		
	}
	
	private void drawPiece (int fromX, int fromY, Graphics g, int piece)
	{
		
		if (piece == PLAYER_ONE)
		{
			g.setColor(clrRedPiece);
		}
		else
		{
			g.setColor(clrBluePiece);
		}
		
		int size = CELLSIZE - (cellBorderOffset * 2);
		g.fillRoundRect((fromX + cellBorderOffset), (fromY + cellBorderOffset), size, size, size, size);

	}
	
	private boolean canDrawHangingCoin ()
	{
		
		if (!canMove() || columnMouseOver == -1)
		{
			return false;
		}
		
		return true;
		
	}		
		
	private void drawHangingCoin (Graphics g)
	{
		
		if (!canDrawHangingCoin())
		{
			return;
		}
		else if (!playerMatrix.isLegalMove(-1, columnMouseOver, 0))
		{
			return;
		}
		
		int fromX = gridStartX + borderSize + (columnMouseOver * CELLSIZE);
		int fromY = gridStartY - outsideGridBorder;
		
		drawPiece(fromX, fromY, g, currentPlayer);
		
	}		
	
	private int getMouseOverColumn (int x, int y)
	{
		//If not inside the grid
		if (y < (gridStartY + borderSize) || y > (gridStartY + borderSize + gridHeight))
		{
			return -1;
		}
		
		int fromX = gridStartX + borderSize;		
		
		for (int column = 0; column < numOfColumns; column++)
		{
			
			if (x > fromX && x < (fromX + CELLSIZE))
			{
				return column;
			}
			
			fromX += CELLSIZE;
			
		}
		
		return -1;
		
	}

	public void mouseMoved (MouseEvent e)
	{
		
		int column = getMouseOverColumn(e.getX(), e.getY());
		
		if (column == columnMouseOver)
		{
			return;
		}
		
		columnMouseOver = column;
		
		if (canDrawHangingCoin())
		{
			
			onlyPaintingHangingCoin = true;
			repaint();	
					
		}
		
	}

	public void mouseClicked (MouseEvent e)
	{	
		
		columnMouseOver = getMouseOverColumn(e.getX(), e.getY());
		
		if (canDrawHangingCoin() && playerMatrix.isLegalMove(-1, columnMouseOver, 0))
		{
			doMove(columnMouseOver);
		}

	}

	public void newGame ()
	{
		
		numMovesMade = 0;		
		resetGame();
		repaint();

	}

	public void paint (Graphics g)
	{
		
		int fromX = gridStartX + borderSize;
		int fromY = gridStartY + borderSize;
		int cellContents = 0;
		
		if (onlyPaintingHangingCoin)
		{
			g.setClip(0, 0, canvasWidth, (fromX + CELLSIZE));				
		}
		
		drawGrid(g);	
		drawHangingCoin(g);
		
		for (int row = 0; row < numRowCells; row++)
		{
						
			for (int column = 0; column < numColumnCells; column++)
			{
				
				cellContents = playerMatrix.getCellContents(row, column);				
				
				if (cellContents != 0)
				{
					drawPiece(fromX, fromY, g, cellContents);
				}
				
				drawCell(fromX, fromY, g);
				fromX += CELLSIZE;
				
			}
			
			if (onlyPaintingHangingCoin)
			{
				break;
			}
			
			fromX = gridStartX + borderSize;
			fromY += CELLSIZE;
			
		}
		
		onlyPaintingHangingCoin = false;
		
	}
	
}