/*
 * construct the main entrance of server package/
 */

package g54ubi.chat.server;

public class Runner
{
	static Server server; // create instance named server of Server class
	final static int PORT = 9000; // establish port of 9000
	
	public static void main(String[] args){
		server = new Server(PORT);
	}
	
	
}