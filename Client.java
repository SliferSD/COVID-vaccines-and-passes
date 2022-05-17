package paket1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

	static Socket soketZaKomunikaciju= null;
	static BufferedReader serverInput= null;
	static PrintStream serverOutput= null;
	static BufferedReader unosSaTastature= null;
	
	
	public static void main(String[] args) {
	
		
		try {
			soketZaKomunikaciju = new Socket("localhost",9001);
			
			serverInput = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			serverOutput = new PrintStream(soketZaKomunikaciju.getOutputStream());
			
			unosSaTastature= new BufferedReader(new InputStreamReader(System.in));
			
			new Thread(new Client()).start(); // pokrecem run metodu
			
			String input; 
			
			while(true)
			{
				
				input = serverInput.readLine();
				System.out.println(input);
				
				if(input.startsWith(">>> Dovidjenja! "))
				{
					break;
				}
				
			}
			soketZaKomunikaciju.close();
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Nepoznat host");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Server je pao");
		}
		
		
		
		
	}
@Override
public void run() {
	// TODO Auto-generated method stub
	
	String message;
	while(true) {
		
		try {
			message= unosSaTastature.readLine();
			serverOutput.println(message);	
			
			if(message.equals("***quit"))
				break;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
	
	
}
