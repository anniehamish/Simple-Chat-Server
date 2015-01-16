/*
 * construct client to connect server
 * read the command from client and then transfer and execute it in server
 */


package g54ubi.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/*create a Client which implements the runnable so that it can run all the threads all the time 
 */
public class Client implements Runnable {     
	private static final int SERVER_PORT = 9000;

	private volatile boolean running;
	private Socket sk;
	private PrintWriter pw;
	private BufferedReader br;

	public Client() throws IOException {
		sk = new Socket("127.0.0.1", SERVER_PORT);
		pw = new PrintWriter(sk.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		running = true;

		new Thread(this).start();
	}
	/* construct run method, output the result from server
	@Override
	*/
	public void run() {
		while (running) {
			try {
				String line = br.readLine(); // read from br (from connection)
				if (line == null || line.isEmpty())
					continue;
			
				System.out.println(line);
				if ((!line.startsWith("PM"))&&(line.indexOf("goodbye"))==-1){
					System.out.print("Command:");
					
					
					
				}

				if (!running) {
					close(); // add @close() at final version to fix the problem in integration test case
					break;
				}
				} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 
	/*define the close(). It close the pw, br, sk function so that other methods can be execute
	 */
	private void close() {
		try {
			pw.close();
			br.close();
			sk.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void read() { 
		Scanner scanner = new Scanner(System.in);// read the command from system standard input
		while (running) {
			String line = scanner.nextLine().trim(); // extract the space in the beginning and endding
			pw.println(line);
			if ("QUIT".equals(line))
				running = false;
		}
		scanner.close();
	}
	/*the entrance of the whole package **/
	public static void main(String[] args) {  
		try {
			new Client().read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
