/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Michael
 */
public class ServerTest {
    
    public ServerTest() {
    }
    
    private static Server server;
    
    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] args = new String[2];
                args[0] = "localhost";
                args[1] = "7777";
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
    public void testAddClient() {
        ArrayList<ClientHandler> clients = new ArrayList<>();
        Socket socket = new Socket();
        Server server = new Server();
        ClientHandler client = new ClientHandler(socket, server);
        assertTrue(clients.add(client));
        
    }
    
    @Test
    public void testRemoveClient() {
        ArrayList<ClientHandler> clients = new ArrayList<>();
        Socket socket = new Socket();
        Server server = new Server();
        ClientHandler client = new ClientHandler(socket, server);
        assertFalse(clients.remove(client));
    }
    
    @Test
    public void testSendToAllClients() {
        
    }

//    /**
//     * Test of main method, of class Server.
//     */
//    @Test
//    public void testMain() {
//        System.out.println("main");
//        String[] args = null;
//        Server.main(args);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of stopServer method, of class Server.
//     */
//    @Test
//    public void testStopServer() {
//        System.out.println("stopServer");
//        Server instance = new Server();
//        instance.stopServer();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addClient method, of class Server.
//     */
//    @Test
//    public void testAddClient() {
//        System.out.println("addClient");
//        ClientHandler client = null;
//        Server instance = new Server();
//        instance.addClient(client);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of removeClient method, of class Server.
//     */
//    @Test
//    public void testRemoveClient() {
//        System.out.println("removeClient");
//        ClientHandler client = null;
//        Server instance = new Server();
//        instance.removeClient(client);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of sendToAllClients method, of class Server.
//     */
//    @Test
//    public void testSendToAllClients() {
//        System.out.println("sendToAllClients");
//        String text = "";
//        String from = "";
//        Server instance = new Server();
//        instance.sendToAllClients(text, from);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of sendSpecific method, of class Server.
//     */
//    @Test
//    public void testSendSpecific() {
//        System.out.println("sendSpecific");
//        String[] receivers = null;
//        String text = "";
//        String from = "";
//        Server instance = new Server();
//        instance.sendSpecific(receivers, text, from);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getSuccessMsg method, of class Server.
//     */
//    @Test
//    public void testGetSuccessMsg() {
//        System.out.println("getSuccessMsg");
//        String toUser = "";
//        Server instance = new Server();
//        String expResult = "";
//        String result = instance.getSuccessMsg(toUser);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getClientList method, of class Server.
//     */
//    @Test
//    public void testGetClientList() {
//        System.out.println("getClientList");
//        Server instance = new Server();
//        String expResult = "";
//        String result = instance.getClientList();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of notifyServer method, of class Server.
//     */
//    @Test
//    public void testNotifyServer() {
//        System.out.println("notifyServer");
//        Server instance = new Server();
//        instance.notifyServer();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
