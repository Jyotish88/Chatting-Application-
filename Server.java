/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import javax.swing.JFrame;
/**
 *
 * @author Jyotish
 */
public class Server {

    public static void main(String[] args) {
       Server1 obj=new Server1();
       obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       obj.startRunning();
    }
}
