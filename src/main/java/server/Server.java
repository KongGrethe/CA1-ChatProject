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

    public static void main(String[] args) {
        try {
            new Server().runServer("localhost", 1239); // starter serveren

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


            //While the server is running the code below runs.
            while (keepRunning) {
                Socket socket = serverSocket.accept();

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
    }

    //This method removes client to list of clients.
    public void removeClient(ClientHandler client) {
        clients.remove(client);
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
}
