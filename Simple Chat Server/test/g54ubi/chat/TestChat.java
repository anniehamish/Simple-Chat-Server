package g54ubi.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestChat {

	private Socket sk;
	private BufferedReader br;
	private PrintWriter pw;

	@Before
	public void setUp() throws Exception {
		sk = new Socket("127.0.0.1", 9000);
		br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		pw = new PrintWriter(sk.getOutputStream(), true);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("quit");
		pw.println("QUIT");
		sk.close();
	}

	@Test
	public void testConnect() throws Exception {
		String line = br.readLine();
		Assert.assertTrue(line.startsWith("OK"));
	}

	@Test
	public void testIdenWithoutLogin() throws Exception {
		br.readLine();
		pw.println("IDEN eric");
		String line = br.readLine();
		Assert.assertTrue(line.startsWith("OK"));
	}
	
	@Test
	public void testIdenWithLogin() throws Exception {
		br.readLine();
		pw.println("IDEN eric");
		br.readLine();
		pw.println("IDEN ann");
		String line = br.readLine();
		Assert.assertTrue(line.startsWith("BAD"));
	}
	
	@Test
	public void testListWithoutLogin() throws Exception {
		br.readLine();
		pw.println("LIST");
		String line = br.readLine();
		Assert.assertTrue(line.startsWith("BAD"));
	}
	
	@Test
	public void testListWithLogin() throws Exception {
		br.readLine();
		pw.println("IDEN eric");
		br.readLine();
		pw.println("LIST");
		String line = br.readLine();
		Assert.assertTrue(line.startsWith("OK"));
	}
	
	@Test
	public void testStatWithoutLogin() throws Exception {
		br.readLine();
		pw.println("STAT");
		String line = br.readLine();
		Assert.assertTrue(line.indexOf("not logged") != -1);
	}
	
	@Test
	public void testStatWithLogin() throws Exception {
		br.readLine();
		pw.println("IDEN eric");
		br.readLine();
		pw.println("STAT");
		String line = br.readLine();
		Assert.assertTrue(line.indexOf("are logged") != -1);
	}
	
	@Test
	public void testHailWithoutLogin() throws Exception {
		br.readLine();
		pw.println("HAIL hello world");
		String line = br.readLine();
		Assert.assertTrue(line.startsWith("BAD"));
	}
	
	@Test
	public void testHailWithLogin() throws Exception {
		br.readLine();
		pw.println("IDEN eric");
		br.readLine();
		pw.println("HAIL hello world");
		String line = br.readLine();
		Assert.assertTrue(line.startsWith("Broadcast"));
	}
	
	@Test
	public void testInvalid() throws Exception {
		br.readLine();
		pw.println("d");
		String line = br.readLine();
		Assert.assertTrue(line.startsWith("BAD"));
	}
	
	@Test
	public void testUnRecognised() throws Exception {
		br.readLine();
		pw.println("RRRRRR");
		String line = br.readLine();
		Assert.assertTrue(line.equals("BAD command not recognised"));
	}

}
