/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.net.Socket;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import server.ClientHandler;
import server.Server;
import client.Client;
import java.io.IOException;

/**
 *
 * @author Michael
 */
public class ServerTest {
    
    private static final String ip = "localhost";
    private static final String port = "7777";
    
    public ServerTest() {
    }
    
    private static Server server;
    
    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] args = new String[2];
                args[0] = ip;
                args[1] = port;
                server = new Server();
                server.main(args);
            }
        }).start();
    }

    
    @AfterClass
    public static void tearDownClass() {
        server.stopServer();
    }

    @Test
    public void testAddRemoveClient() throws IOException {
        
    }

    
    @Test
    public void testProtocolLogin() throws IOException, InterruptedException {
        Client client = new Client();
        client.connect(ip, Integer.parseInt(port));
        Listener l = new Listener(client);
        l.start();
        client.WriteThis("LOGIN:myuser");
        l.join();
        assertTrue(l.testMatch("CLIENTLIST:"));
    }
    
    @Test
    public  void testProtocolMSG() throws IOException, InterruptedException {
        Client client = new Client();
        client.connect(ip, Integer.parseInt(port));
        Listener l = new Listener(client);
        l.start();
        client.WriteThis("MSG::hello everyone");
        l.join();
        String expectedResult = "MSGRES:null:hello everyone";
        String result = l.getInput();
        assertTrue(expectedResult.equals(result));
    }
    
    @Test
    public  void testProtocolLogout() throws IOException, InterruptedException {
        Client client = new Client();
        Client client2 = new Client();
        client.connect(ip, Integer.parseInt(port));
        client2.connect(ip, Integer.parseInt(port));
        Listener l = new Listener(client);
        Listener l2 = new Listener(client2);
        l.start();
        l2.start();
        client.WriteThis("LOGIN:user");
        client2.WriteThis("LOGIN:user2");
        l.join();
        l2.join();
        client.WriteThis("LOGOUT:");
        assertTrue(l2.testMatch("CLIENTLIST:"));
        
    }
    
}
