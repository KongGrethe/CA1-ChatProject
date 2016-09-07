/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.swing.JList;
import javax.swing.JTextArea;

/**
 *
 * @author Michael
 */
public class InputThread extends Thread {
    private final JTextArea myJTextAreaReference;
    private final Client myClientReference;
    
    public InputThread(JTextArea myJTextArea, Client myClient) {
        myJTextAreaReference = myJTextArea;
        myClientReference = myClient;
    }
    
    @Override
    public void run() {
        String tmp = "unset";
        while(true) {
            try {
                tmp = myClientReference.getInput().nextLine();
                inputHandler(tmp);
                myJTextAreaReference.append(tmp + "\n");
                //System.out.println("Received message: " + tmp);
            } catch (Exception ex) {
                System.out.println("Exception in InputThread");
            }
        }
    }
    
    public void inputHandler(String in) {
        
    }
}
