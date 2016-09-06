package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private String myIP;
    private int myPort;

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Use like java -jar file.jar <ip> <port>");
            }
            String ip = args[0];
            int port = Integer.parseInt(args[1]);

            new server().runServer(ip, port); // starter serveren
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
            serverSocket.bind(new InetSocketAddress(ip, port));

            System.out.println("Server listens on " + myPort + " at " + myIP);

            while (keepRunning) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected to a client");
                handleClient(socket);
            }
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    private static void handleClient(Socket socket) throws IOException {
        Scanner input = new Scanner(socket.getInputStream());
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        String message = input.nextLine(); // Blocker
        while(!message.equals("##STOP##")) {
            writer.println(message.toUpperCase());
            message = input.nextLine();
        }
        writer.println("##STOP##");
        socket.close();
        System.out.println("Closed connection");
    }
}
