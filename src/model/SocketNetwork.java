package model;

import controller.NetworkImplementer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.NetworkPopUp;

public class SocketNetwork extends NetworkImplementer{
    public InetAddress hostAddress;
    public int portNumber;
    Socket client;
    ServerSocket host;
    private final int HOST_PORT = 9001;
    DataOutputStream out;
    DataInputStream in;
    public boolean connectAttempted;
    boolean connected;
    
    public SocketNetwork(){

        connectAttempted = false;
        //startServer();
        connectAttempted = false;
        connected = false;
        attemptConnection();
        NetworkPopUp popUp = new NetworkPopUp(this);
       
        int m = 1;
        
        
        
        
    }
//    public void startServer() {
//
//        Runnable serverTask = new Runnable() {
//            @Override
//            public void run() {
//                ServerSocket serverSocket = null;
//                try {
//                    serverSocket = new ServerSocket(HOST_PORT);
//                    System.out.println("Waiting for clients to connect...");
//                } catch (IOException e) {
//                    System.err.println("Couldn't create socket");
//                    e.printStackTrace();
//                }    
//                //while (true) {
//                        Socket clientSocket = null;
//                    try {
//                        clientSocket = serverSocket.accept();
//                    } catch (IOException ex) {
//                        Logger.getLogger(SocketNetwork.class.getName()).log(Level.SEVERE, null, ex);
//                       
//                    }
//                        System.out.println("External connection to socket server"); 
//                    try {
//                        in = new DataInputStream(clientSocket.getInputStream());
//                    } catch (IOException ex) {
//                        System.out.println("Could not Create input sream");
//                        Logger.getLogger(SocketNetwork.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        
//                    //}
//                
//            }
//        };
//        Thread serverThread = new Thread(serverTask);
//        serverThread.start();
//    }
    
    public void attemptConnection() {
        Runnable connection = new Runnable() {
            @Override
            public void run() {
                
                while(!connectAttempted){
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SocketNetwork.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                client = null;
                try{
                    client = new Socket(hostAddress, HOST_PORT);
                    System.out.println("Connected to external socket server");
                    out = new DataOutputStream(client.getOutputStream());   
                    connected = true;
                }
                    catch(IOException e){    
                        System.out.println("Did not connect to external");
                }
            }
            
        };
        Thread connectThread = new Thread(connection);
        connectThread.start();
        if(connected){
        connectThread.interrupt();
        }
    }
    
    @Override
    public void sendMove(int m) {
        try {
            
            out.writeInt(m);
            System.out.println("Sent " + m);
        } catch (IOException ex) {
            
        }
    }

   // @Override
////    public int getMove() {
//        int m = -1;
//        try {
//            m = in.readInt();
//            System.out.println("Recieved" + m);
//        } catch (IOException ex) {
//            
//        }
//        return m;
//    }
    

    
}
