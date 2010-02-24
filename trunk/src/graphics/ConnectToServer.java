//package graphics;
//
//import java.awt.Image;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//
//public class ConnectToServer extends JFrame {
//	public static byte[] byteArray = new byte[4];
//	public ConnectToServer() {
//		initComponents();
//		try {
////			int[] integerArray = new int[4];
////			String[] temp = getIP.split("[.]");
////			for (int i = 0; i < temp.length; i++) {
////				integerArray[i] = Integer.parseInt(temp[i]);
////				byteArray[i] = (byte) integerArray[i];
////			}
//		} catch (Exception e) {
//			System.out.println("couldn't connect, check your input");
//		}
//	}
//	public void initComponents() {
//		jButton1 = new javax.swing.JButton();
//		jTextField = new javax.swing.JTextField();
//
//		setVisible(true);
//		setSize(400, 400);
//		setMinimumSize(new java.awt.Dimension(400, 400));
//		setTitle("Main Menu");
//		setResizable(false);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		final Image icon = new ImageIcon("src/images/Connect4Logo.png").getImage();
//		setIconImage(icon);
//		jButton1.setText("Connect");
//		jButton1.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				jButton1ActionPerformed(evt);
//			}
//		});
//		getContentPane().add(jButton1);
//		jButton1.setBounds(220, 340, 120, 31);
//		
//		jTextField.setText("");
//		jTextField.setBounds(290, 310, 150, 20);
//		getContentPane().add(jTextField);
//		pack();
//		repaint();
//	}
//	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
//		new DrawingOvals();
//	}
//	private javax.swing.JButton jButton1;
//	private javax.swing.JTextField jTextField;
//}
