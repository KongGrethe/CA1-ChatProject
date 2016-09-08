package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static Handler fileHandler = null;
    
    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private String myIP;
    private int myPort;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("Error: Use like: java -jar EchoServer.jar <ip> <port>");
        }
        
             
        try {
            fileHandler = new FileHandler("ServerLog.xml", true);
            LOGGER.addHandler(fileHandler);
            
            new Server().runServer(args[0], Integer.parseInt(args[1])); // starter serveren
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            if(fileHandler != null) fileHandler.close();
            fileHandler = null;
            //System.out.println(e.getMessage());
        }
    }

    //This method makes the server run.
    private void runServer(String ip, int port) {
        myIP = ip;
        myPort = port;

        try {            
            //Creates new socket 
            serverSocket = new ServerSocket();
            //
            serverSocket.bind(new InetSocketAddress(myIP, myPort));

            System.out.println("Server listens on " + myPort + " at " + myIP);

            //While the server is running the code below runs.
            while (keepRunning) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected to a client");

                ClientHandler cH = new ClientHandler(socket, this);
                addClient(cH);
                cH.start();
                //handleClient(socket);
            }
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    // when this method is called, the server will stop running.
    public void stopServer() {
        keepRunning = false;
    }

    //This method adds client to list of clients.
    public void addClient(ClientHandler client) {
        clients.add(client);
        System.out.println("A user has joined!");
        System.out.println("Total number of users is: " + clients.size());
    }

    //This method removes client to list of clients.
    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("A user has left!");
        System.out.println("Total number of users is: " + clients.size());
    }

    //This method sends a message to all users online.
    public void sendToAllClients(String text, String from) {
        for (ClientHandler client : clients) {
            client.sendMessage("MSGRES:" + from + ":" + text);
        }
    }

    //This method sends a message to sepcified user(s)
    public void sendSpecific(String[] receivers, String text, String from) {

        for (ClientHandler client : clients) {
            for (String receiver : receivers) {
                if (client.getUserName().equals(receiver)) {
                    client.sendMessage("MSGRES:" + from + ":" + text);
                }
            }
        }
    }

    //when a user joins the server this method is called to send a greeting.
    /*
    public String getServerGreeting() {
        return "Hi my name is " + serverName + ". Welcome. Please LOGIN:";
    }
     */

    //This method is called when the user write his name. 
    public String getSuccessMsg(String toUser) {
        return "Hi " + toUser + ". You are now connected.";
    }

    //This method extracts the list of online users and sends out the list
    //with a message.
    public String getClientList() {
        String clientList = "CLIENTLIST:";
//        for (ClientHandler client : clients) {
//            if (clients.size() == 1) {
//                clientList += client.getUserName();
//            } else {
//                clientList += "," + client.getUserName();
//            }
//        }
        for (int i = 0; i != clients.size()-1; i++) {
            clientList += clients.get(i).getUserName()+",";
        }
        clientList += clients.get(clients.size()-1).getUserName();
        return clientList;
    }
    
    public void notifyServer() {
        updateAndSendClientList();
    }
    
    private void updateAndSendClientList() {
        for (ClientHandler client : clients) {
            client.sendMessage(getClientList());
        }
    }
}