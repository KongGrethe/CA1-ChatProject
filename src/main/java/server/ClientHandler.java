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
    public void run() { //The ClientHandler needs a run method, because it 
        //extends Thread.
        try {
            //Declares the OutputStream.
            writer = new PrintWriter(socket.getOutputStream(), true);
            //Declares the InputStream.
            input = new Scanner(socket.getInputStream());
            //Writes Greeting Message from the server to the client.
            //writer.println(server.getServerGreeting());

            while (true) {
                try {
                   // message = processBackspace(input.nextLine()); // Blocker
                   message =input.nextLine();
                   String[] inputsFromClient = message.split(":");

                    //This block of code is a CommandSwitch. It handles the
                    //different possible commands.
                    inputsFromClient[0] = inputsFromClient[0].toUpperCase();
                    switch (inputsFromClient[0]) {
                        //Login command
                        case "LOGIN":
                            setUserName(inputsFromClient[1]);
                            writer.println(server.getClientList());
                            break;

                        //Message command
                        case "MSG":
                            //Creates an array with recipients of a message and
                            //split the names with a ",".
                            String[] recipients = inputsFromClient[1].split(",");
                            //Creates a String called text. 
                            String text = inputsFromClient[2];
                            //If no recipients are given, the message is sent to
                            //all online users on the server.
                            if (recipients[0].equals("")) {
                                server.sendToAllClients(text, username);
                            } else {
                                // Send to 1 or more clients
                                server.sendSpecific(recipients, text, username);
                            }
                            break;

                        //Logout Command
                        case "LOGOUT":
                            socket.close();
                            break;
                        
                        default:
                            
                            break;
                    }
                    //If a person in the chat writes invalid commands, this 
                    //catch block will handle the exception and write a message 
                    //to the user about the problem.
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException e) {
            //If the user shut down his terminal window, this exteption will be 
            //caught and a message is printed to the output in netbeans.
        } catch (NoSuchElementException e2) {
            server.removeClient(this);

            //No matter how the runmethod ends, this finally will be executed.
            //It will close the socket.
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    //Writes the message, flushes and print to Output "Send message"
    public void sendMessage(String msg) {
        writer.println(msg);
        writer.flush();
    }

    //UsernameSette.
    private void setUserName(String name) {
        username = message = name;
    }

    //UsernameGetter
    public String getUserName() {
        return username;
    }

    //This method is taken from a stackoverflow solution to solve our telnet issue
    /*private String processBackspace(String input) {
    StringBuilder sb = new StringBuilder();
    for (char c : input.toCharArray()) {
        if (c == '\b') {
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        } else {
            sb.append(c);
        }
    }
    return sb.toString();
    }*/
}
