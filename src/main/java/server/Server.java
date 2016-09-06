package server;

import java.io.IOException;
import java.io.PrintWriter;
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
            /*if (args.length != 2) {
                throw new IllegalArgumentException("Use like java -jar file.jar <ip> <port>");
            }
            String ip = args[0];
            int port = Integer.parseInt(args[1]);*/
            new Server().runServer("localhost", 7777); // starter serveren

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stopServer() {
        keepRunning = false;
    }

    private void runServer(String ip, int port) {
        myIP = ip;
        myPort = port;

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(myIP, myPort));

            System.out.println("Server listens on " + myPort + " at " + myIP);

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

    public void addClient(ClientHandler client) {
        clients.add(client);
        System.out.println("A user has joined!");
        System.out.println("Total number of users is: " + clients.size());
    }
    
    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("A user has left!");
        System.out.println("Total number of users is: " + clients.size());
    }
    
    public void sendToAllClients(String text) {
        for (ClientHandler client : clients) {
            client.sendMessage(text);
        }
    }
    
    public void sendSpecific(String[] receivers, String text){
        for (ClientHandler client : clients) {
            for (String receiver : receivers) {
                if (client.getUserName().equals(receiver)) {
                    client.sendMessage(text);
                }
            }
        }
    }
    
    public String getServerGreeting() {
        return "Hi my name is " + serverName + ". What is your name?";
    }
    
    public String getSuccessMsg(String toUser) {
        return "Hi " + toUser + ". You are now connected. Start chatting";
    }
}
