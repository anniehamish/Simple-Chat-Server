package g54ubi.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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

	@Override
	public void run() {
		while (running) {
			try {
				String line = br.readLine();
				if (line == null || line.isEmpty())
					continue;
				System.out.println(line);

				if (!running) {
					close();
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

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
		Scanner scanner = new Scanner(System.in);
		while (running) {
			String line = scanner.nextLine().trim();
			pw.println(line);
			if ("QUIT".equals(line))
				running = false;
		}
		scanner.close();
	}

	public static void main(String[] args) {
		try {
			new Client().read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
