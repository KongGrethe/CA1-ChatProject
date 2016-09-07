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
    private String username;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            input = new Scanner(socket.getInputStream());
            writer.println(server.getServerGreeting());
            message = "";
            while (true) {
                try {
                    message = input.nextLine(); // Blocker
                    String[] inputsFromClient = message.split(":");
                    
                    System.out.println("Command is " + inputsFromClient[0]);
                    inputsFromClient[0] = inputsFromClient[0].toUpperCase();
                    switch (inputsFromClient[0]) {
                        case "LOGIN":
                            setUserName(inputsFromClient[1]);
                            writer.println(server.getClientList());
                            break;
                        case "MSG":
                            String[] recipients = inputsFromClient[1].split(",");
                            System.out.println("There are " + recipients.length + " recipients");
                            String text = inputsFromClient[2];
                            System.out.println("The text is " + text);
                            if (recipients[0].equals("")) {
                                // Send to all clients
                                System.out.println("Send this to all clients");
                                server.sendToAllClients(text, username);
                            } else {
                                // Send to 1 or more clients
                                server.sendSpecific(recipients, text, username);
                            }
                            break;
                        case "LOGOUT":
                            writer.println("Signed out.");
                            socket.close();
                            break;
                        default:
                            System.out.println("The command was ");
                            writer.println("Unknown command. Please reply with proper protocol"
                                    + "<\n Ex: MSG:<message>  LOGIN:<username>  LOGOUT: ");
                            break;
                    }

                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println(ex.getMessage());
                    writer.println("The protocol is: MSG:<recipients seperated by ,>:<message> \n"
                            + "example: MSG::Hi everyone");
                }
            }
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

    private void setUserName(String name) {
        username = message = name;
    }

    public String getUserName() {
        return username;
    }
}
