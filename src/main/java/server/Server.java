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
    private ArrayList<clientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            /*if (args.length != 2) {
                throw new IllegalArgumentException("Use like java -jar file.jar <ip> <port>");
            }
            String ip = args[0];
            int port = Integer.parseInt(args[1]);*/
            new Server().runServer("localhost", 7777); // starter serveren
            int port = Integer.parseInt(args[1]);

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

                clientHandler cH = new clientHandler(socket, this);
                addClient(cH);
                cH.start();
                //handleClient(socket);
            }
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public void addClient(clientHandler client) {
        clients.add(client);
        System.out.println("A user has joined!");
        System.out.println("Total number of users is: " + clients.size());
    }
    
    public void removeClient(clientHandler client) {
        clients.remove(client);
        System.out.println("A user has left!");
        System.out.println("Total number of users is: " + clients.size());
    }
    
}
