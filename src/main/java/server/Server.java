package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private String myIP;
    private int myPort;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private final String serverName = "Athena";

    public static void main(String[] args) {
        try {
            // The servers runmethod is called
            new Server().runServer("localhost", 7777); // starter serveren

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            client.sendMessage(from + "=" + text);
        }
    }

    //This method sends a message to sepcified user(s)
    public void sendSpecific(String[] receivers, String text, String from){

        for (ClientHandler client : clients) {
            for (String receiver : receivers) {
                if (client.getUserName().equals(receiver)) {
                    client.sendMessage(from  +"=" + text);
                }
            }
        }
    }
    //when a user joins the server this method is called to send a greeting.
    public String getServerGreeting() {
        return "Hi my name is " + serverName + ". What is your name?";
    }
    
    //This method is called when the user write his name. 
    public String getSuccessMsg(String toUser) {
        return "Hi " + toUser + ". You are now connected.";
    }
    
    //This method extracts the list of online users and sends out the list
    //with a message.
    public String getClientList() {
        String clientList = "The following users are online: ";
        for (ClientHandler client : clients) {
            clientList += client.getUserName() + ", ";
        }
        return clientList;
    }
}
