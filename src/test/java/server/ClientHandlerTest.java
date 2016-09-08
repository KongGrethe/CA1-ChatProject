/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael
 */
public class ClientHandlerTest {
    
    public ClientHandlerTest() {
    }

    /**
     * Test of run method, of class ClientHandler.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        ClientHandler instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessage method, of class ClientHandler.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        String msg = "";
        ClientHandler instance = null;
        instance.sendMessage(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserName method, of class ClientHandler.
     */
    @Test
    public void testGetUserName() {
        System.out.println("getUserName");
        ClientHandler instance = null;
        String expResult = "";
        String result = instance.getUserName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
