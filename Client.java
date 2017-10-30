/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Jyotish
 */
import javax.swing.JFrame;
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       client1 obj1;
       obj1=new client1("127.0.0.1");
       obj1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       obj1.startRunning();
    }
}
