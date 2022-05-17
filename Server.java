package paket1;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

	
	public static LinkedList<ClientHandler> onlineUsers = new LinkedList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int port = 9001;
		ServerSocket serverSoket = null;
		Socket soketZaKomunikaciju = null;
		
		
		try {
			
			serverSoket= new ServerSocket(port);
			
			while(true) {
				System.out.println("Cekam na konekciju...");
				soketZaKomunikaciju = serverSoket.accept();
				System.out.println("Doslo je do konekcije");
				
				ClientHandler klijent = new ClientHandler(soketZaKomunikaciju);
				
				onlineUsers.add(klijent);
				klijent.start();
				
				
				
				
			}
			
			
		} catch (IOException e) {
			System.out.println("Greska prilikom pokretanja servera!!!");
		} 
		
		
		// necemo da ignorisemo 
		
		
		
		
	}

}
