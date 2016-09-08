/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import client.Client;

/**
 *
 * @author xboxm
 */
public class Listener extends Thread {
    private Client myClient;
    private String myString;
    
    public Listener(Client c) {
        myClient = c;
    }
    
    @Override
    public void run() {
        myString = myClient.getInput().nextLine();
    }
    
    public boolean testMatch(String command) {
        /*
        if(myString.substring(0,9).equals("CLIENTLIST")) return true;
        return false; /*
        */
        System.out.println("Command is: " + command);
        return myString.contains(command);
    }
    
    public String getInput() {
        System.out.println("Response is: " + myString);
        return myString;
    }
    
}
