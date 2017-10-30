/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Jyotish
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class client1 extends JFrame {
    private JTextField userText;
private JTextArea chatwindow;
private ObjectOutputStream output;
private ObjectInputStream input;
private String message="";
private String serverIp;
private Socket connection;

//constructor
public client1(String host){
super("Client one");
serverIp=host;
userText=new JTextField();
userText.setEditable(false);
userText.addActionListener(
        new ActionListener(){
    public void actionPerformed(ActionEvent event){
        sendMessage(event.getActionCommand());
        userText.setText("");}});
    add(userText, BorderLayout.NORTH);
    chatwindow=new JTextArea();
    add(new JScrollPane(chatwindow), BorderLayout.CENTER);
    setSize(500,350);
    setVisible(true);
    setLocation(500,200);
}

   
//connect to server
public void startRunning(){
    
        
        try{
            connectToServer();
            setupStreams();
            whileChatting();
        }catch(EOFException eofException){
            showMessage("\n Client Terminited the connection !");
            }catch(IOException ioException){
        ioException.printStackTrace();
    } finally{
            closecrap();}
       
}
//Connect to server
private void connectToServer() throws IOException{
    showMessage("Attemting Connection...\n");
    connection=new Socket(InetAddress.getByName(serverIp),6789);
showMessage("connected to:"+connection.getInetAddress().getHostName());


}
//setup streams to send and recieve
private void setupStreams() throws IOException{
output=new ObjectOutputStream(connection.getOutputStream());

output.flush();
input=new ObjectInputStream(connection.getInputStream());
showMessage("\n dude your streams are good to go");

}
//while  chatting
private void whileChatting() throws IOException{
ableToType(true);
do{
    try{
        message=(String)input.readObject();
        showMessage("\n"+message);
    }catch(ClassNotFoundException classNotFoundException){
        showMessage("\n i dont know that object type");
    }
}while(!message.equals("SERVER - END"));
    }
//close the streams and sockets
private void closecrap(){
showMessage("\n Closing crap down");
ableToType(false);
try{
    output.close();
    input.close();
    connection.close();
}catch(IOException ioException){
ioException.printStackTrace();
}
}
//send messages to server
private void sendMessage(String message){
try{
    output.writeObject("CLIENT - "+message);
    output.flush();
    showMessage("\nCLIENT - "+ message);
}catch(IOException ioException){
chatwindow.append("\n ERROR I CANT SEND MESSAGE");
}
}
//change/update chatwindow
private void showMessage(final String text){
SwingUtilities.invokeLater(
        new Runnable(){
            public void run(){
            chatwindow.append(text);
            }
        }
        );
}
//gives user to type
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



    
    

