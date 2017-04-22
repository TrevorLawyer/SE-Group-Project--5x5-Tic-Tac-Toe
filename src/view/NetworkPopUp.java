
package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.SocketNetwork;


public class NetworkPopUp {
    private JTextField iNetAddressField;
    private JTextField portNumberField;
    private JButton connect;
    private JButton cancel;
    public InetAddress address;
    public int port;
    JFrame popUp;
    private boolean connectAttempted;
    SocketNetwork network;
    
    
    public NetworkPopUp(SocketNetwork network){
        this.network = network;
        popUp = new JFrame("Connect to Peer");
        popUp.setSize(250, 200);
        popUp.setResizable(false);
        popUp.setLocationByPlatform(true);
        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        connectAttempted = false;
        JPanel popUpPanel = new JPanel();
        iNetAddressField = new JTextField(15);
        portNumberField  = new JTextField(15);
        
        popUpPanel.add(new JLabel("Enter Peer Information:"));
        popUpPanel.add(new JLabel(""));
        popUpPanel.add(new JLabel("IP  Address:"));
        popUpPanel.add(iNetAddressField);
        
        popUpPanel.add(new JLabel("Port Number:"));
        popUpPanel.add(portNumberField);
        
        popUpPanel.add(Box.createHorizontalStrut(10));
        ButtonListener buttonListener = new ButtonListener();
        connect= new JButton("Connect");
        connect.addActionListener(buttonListener);
        cancel = new JButton("Cancel");
        cancel.addActionListener(buttonListener);
        popUpPanel.add(connect);
        popUpPanel.add(cancel);

        popUp.add(popUpPanel);
        popUp.setVisible(true);
    }
    public boolean getConnectAttempted(){
        return connectAttempted;
    }
    
    
    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton source = (JButton)ae.getSource();
            if (source == connect){
                port = Integer.parseInt(portNumberField.getText());
                try {
                    address = InetAddress.getByName(iNetAddressField.getText());
                } catch (UnknownHostException ex) {
                    Logger.getLogger(NetworkPopUp.class.getName()).log(Level.SEVERE, null, ex);
                }
                connectAttempted = true;
                network.hostAddress = address;
                network.portNumber = port;
                network.connectAttempted = connectAttempted;
            } 
            else{
                popUp.setVisible(false);
            }
               
            
            
        }   
    }
}
