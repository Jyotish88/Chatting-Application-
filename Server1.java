/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Jyotish
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Server1 extends JFrame{

private JTextField userText;
private JTextArea chatwindow;
private ObjectOutputStream output;
private ObjectInputStream input;
private ServerSocket server;
private Socket connection;

//constructor
public Server1()
{
super("Jyotish Instant Messenger");
userText = new JTextField();
userText.setEditable(false);
userText.addActionListener(
new ActionListener(){
public void actionPerformed(ActionEvent event)
{
sendMessage(event.getActionCommand());
userText.setText("");
}
}
);
add(userText, BorderLayout.NORTH);
chatwindow=new JTextArea();
add(new JScrollPane(chatwindow));
setSize(500,350);
setVisible(true);
setLocation(500,200);


}
public void startRunning(){
    try{
        server=new ServerSocket(6789,100);
        while(true)
        {
        try{
            waitForConnection();
            setupStreams();
            whileChatting();
        }
            catch(EOFException eofException){
            showMessage("\n server Ended the connection !");
            }
        finally{
            closecrap();}
        }    
        
    }catch(IOException ioException){
        ioException.printStackTrace();
    }
}
// wait for connection

private void waitForConnection() throws IOException{

showMessage("Waiting for someone to connect...\n");
connection=server.accept();
showMessage("Now connected to"+connection.getInetAddress().getHostName());
}
//get stream to send and recieve data
private void setupStreams() throws IOException{
output=new ObjectOutputStream(connection.getOutputStream());

output.flush();
input=new ObjectInputStream(connection.getInputStream());
showMessage("\n streams are now setup");

}
//during chatting
private void whileChatting() throws IOException{
String message="You are now Connected !";
sendMessage(message);
ableToType(true);
do{
try{
message=(String) input.readObject();
showMessage("\n"+message);
}catch(ClassNotFoundException classNotFoundException){
showMessage("\n idk wtf that user send");
}
}while(!message.equals("CLIENT - END"));
}

//close streams and sockets after done chatting..

private void closecrap(){
showMessage("\n closing connection....\n");
ableToType(false);
try{
output.close();
input.close();
connection.close();

}catch(IOException ioException ){
ioException.printStackTrace();
}
}
//send message to client
private void sendMessage(String message){
try{
    output.writeObject("SERVER - "+message);
    output.flush();
    showMessage("\nSERVER - "+message);
}catch(IOException ioException){
chatwindow.append("\n ERROR I CANT SEND MESSAGE");
}
}
//update chatwindow
private void showMessage(final String text){
SwingUtilities.invokeLater(
        new Runnable(){
            public void run(){
            chatwindow.append(text);
            }
        }
        );
}
//let the user type
private void ableToType(final boolean tof){
SwingUtilities.invokeLater(
        new Runnable(){
            public void run(){
                userText.setEditable(tof);
            }
        }
        );

}
}

