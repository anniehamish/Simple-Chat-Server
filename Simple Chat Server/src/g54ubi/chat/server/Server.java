/*
 * construct server, initialise the port, put the client into Arraylist
 * define the methods which are used to execute the command from client
 */

package g54ubi.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;


public class Server {

	private ServerSocket server; // define a private instance server of ServerSocket
	private ArrayList<Connection> list; // define a private instance list of class of 
	
	public Server (int port) { // create a public method called Server
		try {
			server = new ServerSocket(port); 
			System.out.println("Server has been initialised on port " + port);
		} // initialise the port, bind the port 
		catch (IOException e) {
			System.err.println("error initialising server");
			e.printStackTrace();
		} // to handle the error
		list = new ArrayList<Connection>(); // list
		while(true) {    // while loop
				Connection c = null; // 
				try {
					c = new Connection(server.accept(), this);
				}
				catch (IOException e) {
					System.err.println("error setting up new client conneciton");
					e.printStackTrace();
				}
				Thread t = new Thread(c); // create a thread t for c
				t.start(); // run this thread
				list.add(c); // add this thread to the list , so the list will record what threads are running here
		}
	}
	
	public ArrayList<String> getUserList() {   // create a method to get the user list
		ArrayList<String> userList = new ArrayList<String>(); // create user list 
		for( Connection clientThread: list){
			if(clientThread.getState() == Connection.STATE_REGISTERED) {
				userList.add(clientThread.getUserName()); // add the user to userlist 
			}
		}
		return userList; //return the userlist
	}
	
	public boolean doesUserExist(String newUser) { // this method is to decide whether the new user existed
		boolean result = false;
		for( Connection clientThread: list){
			if(clientThread.getState() == Connection.STATE_REGISTERED) {
				result = clientThread.getUserName().compareTo(newUser)==0;
			}
		}
		return result;
	}
	
	public void broadcastMessage(String theMessage){
		System.out.println(theMessage);
		for( Connection clientThread: list){
			clientThread.messageForConnection(theMessage + System.lineSeparator());	
		}
	} // send thMmessage to everyone
	
	public boolean sendPrivateMessage(String message, String user) {
		for( Connection clientThread: list) {
			if(clientThread.getState() == Connection.STATE_REGISTERED) {
				if(clientThread.getUserName().compareTo(user)==0) {
					clientThread.messageForConnection(message + System.lineSeparator());
					return true; // search the target user in the list
				}
			}
		}
		return false;
	} // send message to a specific user 
	
	public void removeDeadUsers(){
		Iterator<Connection> it = list.iterator();
		while (it.hasNext()) {
			Connection c = it.next();
			if(!c.isRunning())
				it.remove();
		}
	} // remove a thread if it stop running 
	
	public int getNumberOfUsers() {
		return list.size();
	} // get the number of users
	
	protected void finalize() throws IOException{
		server.close();
	} // close the Serversocket
		
}
