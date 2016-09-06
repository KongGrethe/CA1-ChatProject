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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lasse
 */
public class clientHandler extends Thread {

    private Socket socket;
    private PrintWriter writer;
    private Scanner input;
    private String message;
    private Server server; 
    
    public clientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            input = new Scanner(socket.getInputStream());
            writer.println("Welcome to the best server ever");
            message = input.nextLine(); // Blocker

            while (!message.equals("##STOP##")) {
                writer.println(message.toUpperCase());
                message = input.nextLine();
            }
            writer.println("##STOP##");
            socket.close();
            System.out.println("Closed connection");
        } catch (IOException e) {
            System.out.println("oh no");
        } catch (NoSuchElementException e2) {
            System.out.println("Client closed window probably");
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Deep shit");
            }
        }
    }

}
