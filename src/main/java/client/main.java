/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lasse
 */
public class main {
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.connect("localhost", 7777);
            System.out.println("success");
        } catch (IOException ex) {
            System.out.println("failification");
        }
        
    }
}
