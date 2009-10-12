package network;

import javax.swing.JOptionPane;

public class NewJFrame extends javax.swing.JFrame {
	public String[] jListIPs;

	public NewJFrame() throws Exception {
		jListIPs = jListIPsContent();
		initComponents();
	}

	public String[] jListIPsContent() throws Exception {
		GetOwnIP IPGetter = new GetOwnIP();
		String[] tempz = IPGetter.getScannedIPs();
		return tempz;
	}

	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jButton1 = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList(jListIPs);
		jLabel3 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(550, 400));
		setResizable(false);
		getContentPane().setLayout(null);

		jButton1.setText("Connect");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		getContentPane().add(jButton1);
		jButton1.setBounds(220, 320, 120, 31);
		jScrollPane2.setViewportView(jList1);
		getContentPane().add(jScrollPane2);
		jScrollPane2.setBounds(90, 40, 370, 260);
		jLabel2.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
		jLabel2.setForeground(new java.awt.Color(254, 254, 254));
		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("Select an IP to connect to");
		getContentPane().add(jLabel2);
		jLabel2.setBounds(120, 20, 320, 20);

		jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/connect4.png")));
		jLabel3.setText("jLabel1");
		jLabel3.setMaximumSize(new java.awt.Dimension(550, 429));
		jLabel3.setMinimumSize(new java.awt.Dimension(300, 400));
		getContentPane().add(jLabel3);
		jLabel3.setBounds(0, -10, 560, 410);

		pack();
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		if (jList1.isSelectionEmpty() == false)
			System.out.println(jList1.getSelectedValue().toString());
		else
			System.out.println("Please Select an IP");
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new NewJFrame().setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"You are not connected to a network");
					System.exit(1);
					// e.printStackTrace();
				}
			}
		});
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JList jList1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;

}