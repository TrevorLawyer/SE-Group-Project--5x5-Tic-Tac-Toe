/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author micha
 */
public class SocketNetwork extends NetworkImplementer{
    public InetAddress hostAddress;
    public int portNumber;
    Socket client;
    ServerSocket host;
    private final int HOST_PORT = 1500;
    DataOutputStream out;
    DataInputStream in;
    public boolean connectAttempted;
    
    public SocketNetwork(){

        connectAttempted = false;
        startServer();
        connectAttempted = false;
        attemptConnection();
        NetworkPopUp popUp= new NetworkPopUp(this);
       
        int m = 1;
        
        
        
        
    }
    public void startServer() {

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(HOST_PORT);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        in = new DataInputStream(clientSocket.getInputStream());
                        
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
    
    public void attemptConnection() {
        Runnable connection = new Runnable() {
            @Override
            public void run() {
                while(!connectAttempted){
                    
                }
                try{
                    client = new Socket(hostAddress, portNumber);
                    out = new DataOutputStream(client.getOutputStream());          
                }
                    catch(IOException e){         
                }
            }
            
        };
        Thread connectThread = new Thread(connection);
        connectThread.start();
        
    }
    
    @Override
    public void sendMove(int m) {
        try {
            out.writeInt(m);
        } catch (IOException ex) {
            
        }
    }

    @Override
    public int getMove() {
        int m = -1;
        try {
            m = in.readInt();
        } catch (IOException ex) {
            
        }
        return m;
    }
    

    
}