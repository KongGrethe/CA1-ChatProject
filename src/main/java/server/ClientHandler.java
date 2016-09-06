/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author Lasse
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private PrintWriter writer;
    private Scanner input;
    private String message;
    private Server server;
    private boolean virgin;
    private String username;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        virgin = true;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            input = new Scanner(socket.getInputStream());
            writer.println(server.getServerGreeting());
            setUserName();
            writer.println(server.getSuccessMsg(username));
            while (!message.equals("#EXIT#")) {
                try {
                    message = input.nextLine(); // Blocker
                    String[] parts = message.split(":");
                    String[] recipients = parts[0].split(",");
                    String text = parts[1];
                    
                    if (recipients[0].equals("")) {
                        // Send to all clients
                        server.sendToAllClients(text);
                    } else {
                        // Send to 1 or more clients
                        server.sendSpecific(recipients, text);
                    }
                    
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println(ex.getMessage());
                    writer.println("The protocol is: <recipients seperated by , or blank for all>:<message> \n"
                            + "Example: Lars,Jens,Mats:Hej med jer");
                }
            }
            writer.println("#EXIT#");
            socket.close();
            System.out.println("Closed connection");
        } catch (IOException e) {
            System.out.println("oh no");
        } catch (NoSuchElementException e2) {
            System.out.println("Client closed window probably");
            server.removeClient(this);

        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Deep shit");
            }
        }
    }

    public void sendMessage(String msg) {
        writer.println(msg);
        writer.flush();
        System.out.println("Send message!");
    }

    private void setUserName() {
        username = message = input.nextLine();
    }

    public String getUserName() {
        return username;
    }
    
}
