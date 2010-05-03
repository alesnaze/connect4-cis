package network;


import java.awt.Image;
import java.net.InetAddress;
import java.net.UnknownHostException;


import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import logic.Client;


@SuppressWarnings({"unused", "serial"})
public class ConnectForm extends javax.swing.JFrame {

    public String[] jListIPs = {""};
    public static byte[] byteArray = new byte[4];
    public static InetAddress ia;
    int[] integerArray = new int[4];
    String myStr;
    String[] temp = new String[4];

    public ConnectForm() throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	initComponents();
            }
        });
    }

    public String[] jListIPsContent() throws Exception {
        ScanOwnNetwork IPGetter = new ScanOwnNetwork();
        String[] tempz = IPGetter.getScannedIPs();
        return tempz;
    }

    private void initComponents() {
        setVisible(true);
        setTitle("Connect To Server");
        final Image icon = new ImageIcon("src/images/Connect4Logo.png")
		.getImage();
        setIconImage(icon);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(550, 400));
        setResizable(false);
        getContentPane().setLayout(null);
        
        try {
			jListIPs = jListIPsContent();
		} catch (Exception e) {
		}
        jScrollPane1 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList(jListIPs);
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        typeIP = new javax.swing.JLabel();
        manualIP = new javax.swing.JTextField();

        jButton1.setText("Connect");
        jButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);
        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(90, 40, 370, 260);
        getContentPane().add(jButton1);
        jButton1.setBounds(220, 340, 120, 31);
        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
        jLabel2.setForeground(new java.awt.Color(254, 254, 254));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Select an IP to connect to");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(120, 20, 320, 20);
        typeIP.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        typeIP.setForeground(new java.awt.Color(254, 254, 254));
        typeIP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        typeIP.setText("OR write server's IP manually");
        getContentPane().add(typeIP);
        typeIP.setBounds(80, 310, 200, 20);
        manualIP.setText("");
        manualIP.setBounds(290, 310, 150, 20);
        getContentPane().add(manualIP);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "/images/portscanner bg.png")));
        jLabel3.setText("jLabel1");
        jLabel3.setMaximumSize(new java.awt.Dimension(550, 429));
        jLabel3.setMinimumSize(new java.awt.Dimension(300, 400));
        getContentPane().add(jLabel3);
        jLabel3.setBounds(0, -10, 560, 410);

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jList1.isSelectionEmpty() == false) {
            manualIP.setText(jList1.getSelectedValue().toString());
        }
        if (manualIP.getText() != "") {
            myStr = manualIP.getText();
            temp = myStr.split("[.]");
            for (int i = 0; i < temp.length; i++) {
                integerArray[i] = Integer.parseInt(temp[i]);
                byteArray[i] = (byte) integerArray[i];
            }
            try {
                ia = InetAddress.getByAddress(byteArray);
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "Please make sure you typed the IP correctly");
            }
            setVisible(false);
            new Client();
            // System.out.println(manualIP.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Please Select or Manually write an IP");
        }
    }

    public byte[] toByteIP(String[] stringIP) {
        byte[] bA = new byte[4];
        for (int i = 0; i < stringIP.length; i++) {
            byte Obj = Byte.parseByte(stringIP[i]);
            bA[i] = Obj;
        }
        return bA;
    }
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel typeIP;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField manualIP;
}
