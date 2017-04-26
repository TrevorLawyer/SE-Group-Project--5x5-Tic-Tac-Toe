/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trevo
 */
public class ServerHost {
    private final int HOST_PORT = 9001;
    DataInputStream in;
    
   public  ServerHost() {

       startServer();
   }
    public void startServer(){
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(HOST_PORT);
                    System.out.println("Waiting for clients to connect...");
                } catch (IOException e) {
                    System.err.println("Couldn't create socket");
                    e.printStackTrace();
                }    
                //while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException ex) {
                    Logger.getLogger(SocketNetwork.class.getName()).log(Level.SEVERE, null, ex);                    
                }
                
                
                try {
                    clientSocket.setSoTimeout(7000);
                } catch (SocketException ex) {
                    Logger.getLogger(ServerHost.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                System.out.println("External connection to socket server");                
                try {
                    in = new DataInputStream(clientSocket.getInputStream());                    
                } catch (IOException ex) {
                    System.out.println("Could not Create input sream");
                    Logger.getLogger(SocketNetwork.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
        serverThread.interrupt();
    } 
    public int getMove() {
        int m = -1;
        try {
            m = in.readInt();
            System.out.println("Recieved: " + m);
        } catch (IOException ex) {
            
        }
        return m;
    }
}

