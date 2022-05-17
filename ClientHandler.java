package paket1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

public class ClientHandler extends Thread {

	BufferedReader clientInput = null;
	PrintStream clientOutput = null;
	Socket soketZaKomunikaciju= null;
	String username,sifra, ime, prezime,jmbg, pol, email;
	String pom;
	String prvaVakcina=null, drugaVakcina=null,trecaVakcina=null;
	boolean prva=false,druga=false,treca= false;
	boolean potvrda=true;
	boolean meni=true;
	String usernameP, sifraP;
	String year,month,day;
	
	
	BufferedWriter bw;
	BufferedReader br;// = new BufferedReader(new FileReader("tekst.txt"));	
	
	
	public ClientHandler(Socket soket) {
		
		soketZaKomunikaciju=soket;
		
	}
	@Override
	public void run() {
	
		try {
			
			clientInput= new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			clientOutput= new PrintStream(soketZaKomunikaciju.getOutputStream());
			clientOutput.println(">>> U svakom trenutku mozete ukucati ***quit da izadjete");
			while(meni) {
			clientOutput.println(">>> Izaberite jednu od opcija: "
					+ "\n1.Registracija "
					+ "\n2.Prijava ");
			pom= clientInput.readLine();
			
			if(pom.equals("***quit")) {
				
				clientOutput.println(">>> Dovidjenja! ");
				meni=false;
				soketZaKomunikaciju.close();
				break;
			}						
			if(pom.equals("1"))
			meni=registracija();
			if(pom.equals("2"))
			{	
				prijava();
				if(meni==false)
					continue;
			if(!(username.equals("admin"))) {
			
			while(true) {
			clientOutput.println(">>> Izaberite jednu od sledecih opcija:"
					+ "\n1.Unos statusa o vakcinaciji"
					+ "\n2.Provera kovid propusnice"
					+ "\n3.Generisi kovid propusnicu");
			String pomocna = clientInput.readLine();
			if(pomocna.equals("***quit"))
			{
				clientOutput.println(">>> Dovidjenja! " + username);
				meni=false;
				soketZaKomunikaciju.close();
				break;
			}
			if(pomocna.equals("1"))
			{
				unosStatusa();
				if(meni==false)
					break;
			}
			if(pomocna.equals("2"))
			{
				proveraPropusnice();
			}
			if(pomocna.equals("3"))
			{	generisuPropusnicu();
				if(meni==false)
					break;
			}
				
			}
				}
			
			if(username.equals("admin")) {
				admin();
				if(meni==false)
					break;
			}
				}
			
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Greska u meniju");;
		}
	
	}
	
	
	boolean registracija() {
		try {
			bw = new BufferedWriter(new FileWriter("tekst.txt",true));
			potvrda=true;
			
			//Registracija
			
			while(true) {
			clientOutput.println(">>> Unesite y ako ste registrovani, a n ako niste registrovani");
			pom= clientInput.readLine();
			if(pom.equals("***quit"))
				{clientOutput.println(">>> Dovidjenja! " );
				
				soketZaKomunikaciju.close();
				bw.close();
				return false;
				
				}
			if(pom.equals("y")) {
				
				break;
				
				
				
				
			}
			if(pom.equals("n"))
			{
					while(potvrda) {
				clientOutput.println(">>> Unesite korisnicko ime: ");
				username=clientInput.readLine();
				
				if(username.equals("***quit"))
				{clientOutput.println(">>> Dovidjenja! " );
				
				soketZaKomunikaciju.close();
				return false;
				
				}
				
				
				clientOutput.println(">>> Unesite sifru: ");
				sifra=clientInput.readLine();
				
				
				if(sifra.equals("***quit"))
				{clientOutput.println(">>> Dovidjenja! " );
				
				soketZaKomunikaciju.close();
				return false;
				
				}
				
				
				clientOutput.println(">>> Unesite ime: ");
				ime=clientInput.readLine();
				
				
				if(ime.equals("***quit"))
				{clientOutput.println(">>> Dovidjenja! " );
				
				soketZaKomunikaciju.close();
				return false;
				
				}
				
				
				
				clientOutput.println(">>> Unesite prezime: ");
				prezime=clientInput.readLine();
				
				if(prezime.equals("***quit"))
				{clientOutput.println(">>> Dovidjenja! " );
				
				soketZaKomunikaciju.close();
				return false;
				
				}
				
				
				clientOutput.println(">>> Unesite jmbg: ");
				jmbg=clientInput.readLine();
				
				if(prezime.equals("***quit"))
				{clientOutput.println(">>> Dovidjenja! " );
				
				soketZaKomunikaciju.close();
				return false;
				
				}
				
				
				clientOutput.println(">>> Unesite pol: ");
				pol=clientInput.readLine();
				
				if(pol.equals("***quit"))
				{clientOutput.println(">>> Dovidjenja! " );
				
				soketZaKomunikaciju.close();
				return false;
				
				}
				
				
				clientOutput.println(">>> Unesite email: ");
				email=clientInput.readLine();
				
				if(email.equals("***quit"))
				{clientOutput.println(">>> Dovidjenja! " );
				
				soketZaKomunikaciju.close();
				return false;
				
				}
				
				if(provera1(username, sifra,ime, prezime,jmbg, pol, email)==true)
					potvrda=false;
				else
				{
					clientOutput.println(">>> Niste se uspesno registrovali! Pokusajte ponovo ");
				}
				
				
					}
					
					//Vakcina
					//Prva doza
					
					bw.write("\n" +username+" "+sifra+" "+ ime+" "+ prezime+" "+jmbg+ " "+pol+" "+email+" ");
					bw.close();
					bw = new BufferedWriter(new FileWriter("tekst.txt",true));
					
					clientOutput.println(">>>Usepsno ste se registrovali,ali molimo vas odgovorite na sledece pitanja");
					
					clientOutput.println(">>> Da li ste primili prvu dozu (y/n): ");
					pom=clientInput.readLine();					
					while((!(pom.equals("y"))) && (!(pom.equals("n")))  )
					{
						if(pom.equals("***quit"))
						{clientOutput.println(">>> Dovidjenja! " );
						
						soketZaKomunikaciju.close();
						return false;
						
						}
						
						clientOutput.println(">>>Uneli ste lose podatke"
								+ "\n Da li ste primili prvu dozu (y/n): ");
						pom=clientInput.readLine();
					}
				
					if(pom.equals("n"))
					{
						clientOutput.println(">>> Niste uneli prvu vakcinu."
								+ "\nUspesno ste se registrovali!");
								break;
					}
					if(pom.equals("y"))
					{	while(true) {
						prva=true;
						clientOutput.println(">>> Unesite koju:"
								+ "\n1.Fizer"
								+ "\n2.Sputnjik "
								+ "\n3.Sinopharm"
								+ "\n4.Oxford");
						prvaVakcina=clientInput.readLine();
						
						if(prvaVakcina.equals("***quit"))
						{clientOutput.println(">>> Dovidjenja! " );
						
						soketZaKomunikaciju.close();
						return false;
						
						}
						
						pom=vakcina(prvaVakcina);
						if(!(pom.equals("Greska")))
						{
							bw.write(pom);
							bw.close();
							break;
						}else
							clientOutput.println(">>> Greska pri unosu");
						}		
							//datumi prve vakcine 
								LocalDate datum;
								BufferedWriter buff= new BufferedWriter(new FileWriter("datumi.txt",true));
								buff.write("\n");
								buff.close();
								while(true)
								{
									
									clientOutput.println(">>> Unesite godinu kada ste vakcinisani(mora da se odnosi na 2021)");
									year=clientInput.readLine();
									
									if(year.equals("***quit"))
									{clientOutput.println(">>> Dovidjenja! " );
									
									soketZaKomunikaciju.close();
									return false;
									
									}								
									
									int godina=Integer.parseInt(year);
									if(godina!=2021)
									continue;
									clientOutput.println(">>> Unesite mesec kada ste vakcinisani");
									month=clientInput.readLine();
									
									if(month.equals("***quit"))
									{clientOutput.println(">>> Dovidjenja! " );
									
									soketZaKomunikaciju.close();
									return false;
									
									}	
																
									int mesec=Integer.parseInt(month);
									clientOutput.println(">>> Unesite dan kada ste vakcinisani");
									day=clientInput.readLine();
									if(day.equals("***quit"))
									{clientOutput.println(">>> Dovidjenja! " );
									
									soketZaKomunikaciju.close();
									return false;
									
									}	
									
									
									int dan=Integer.parseInt(day);
									
									 datum= LocalDate.of(godina,mesec,dan);
									buff= new BufferedWriter(new FileWriter("datumi.txt",true));
									buff.write(datum.toString() + " ");
									buff.close();
									break;
									
								}
							//kraj datumaa za prvu vakcinu
					
						//Druga doza
						bw = new BufferedWriter(new FileWriter("tekst.txt",true));
						clientOutput.println(">>> Da li ste primili drugu dozu (y/n): ");
						pom=clientInput.readLine();
						
						while((!(pom.equals("y"))) && (!(pom.equals("n")))  )
						{
							if(pom.equals("***quit"))
							{clientOutput.println(">>> Dovidjenja! " );
							
							soketZaKomunikaciju.close();
							return false;
							
							}						
							
							clientOutput.println(">>>Uneli ste lose podatke"
									+ "\n Da li ste primili drugu dozu (y/n): ");
							pom=clientInput.readLine();
						}
						if(pom.equals("n"))
						{
							clientOutput.println(">>> Niste uneli drugu vakcinu."
									+ "\nUspesno ste se registrovali!");
									break;
						}
						if(pom.equals("y"))
						{	while(true) {
							druga=true;
							clientOutput.println(">>> Unesite koju:(Napomena: druga vakcina mora da bude ista kao prva, u suprotnom ce program izbaciti gresku)"
									+ "\n1.Fizer"
									+ "\n2.Sputnjik "
									+ "\n3.Sinopharm"
									+ "\n4.Oxford");
							drugaVakcina=clientInput.readLine();
							
							if(drugaVakcina.equals("***quit"))
							{clientOutput.println(">>> Dovidjenja! " );
							
							soketZaKomunikaciju.close();
							return false;
							
							}
							
							
							pom=vakcina(drugaVakcina);
							if(!(pom.equals("Greska"))&&(drugaVakcina.equals(prvaVakcina)))
							{	bw.write(pom);
								bw.close();
								break;
							}else
								clientOutput.println(">>> Greska pri unosu,Napomena druga i prva vakcina moraju biti iste");
						}
						
						//datumi zaa drugu vakcinu
						LocalDate datum1;
									while(true)
									{
										
										clientOutput.println(">>> Unesite godinu kada ste vakcinisani(mora da se odnosi na 2021)");
										year=clientInput.readLine();
										
										if(year.equals("***quit"))
										{clientOutput.println(">>> Dovidjenja! " );
										
										soketZaKomunikaciju.close();
										return false;
										
										}								
										
										int godina=Integer.parseInt(year);
										if(godina!=2021)
										continue;
										clientOutput.println(">>> Unesite mesec kada ste vakcinisani");
										month=clientInput.readLine();
										
										if(month.equals("***quit"))
										{clientOutput.println(">>> Dovidjenja! " );
										
										soketZaKomunikaciju.close();
										return false;
										
										}	
																	
										int mesec=Integer.parseInt(month);
										clientOutput.println(">>> Unesite dan kada ste vakcinisani");
										day=clientInput.readLine();
										if(day.equals("***quit"))
										{clientOutput.println(">>> Dovidjenja! " );
										
										soketZaKomunikaciju.close();
										return false;
										
										}	
										
										
										int dan=Integer.parseInt(day);
										
										datum1= LocalDate.of(godina,mesec,dan);
										
										if(Period.between(datum, datum1).getMonths()>=1||Period.between(datum, datum1).getDays()>=21) {
										buff= new BufferedWriter(new FileWriter("datumi.txt",true));
										buff.write(datum1.toString() + " ");
										buff.close();
										break;
										}
										clientOutput.println("Razmak izmedju prve i druge doze mora da bude bar 21 dan");
									}
									
									
								//kraj datuma za drugu vakcinu
								
							//Treca doza
						    bw = new BufferedWriter(new FileWriter("tekst.txt",true));
							clientOutput.println(">>> Da li ste primili trecu dozu (y/n): ");
							pom=clientInput.readLine();
							
							while((!(pom.equals("y"))) && (!(pom.equals("n")))  )
							{
								if(pom.equals("***quit"))
								{clientOutput.println(">>> Dovidjenja! " );
								
								soketZaKomunikaciju.close();
								return false;
								
								}
								
								
								clientOutput.println(">>>Uneli ste lose podatke"
										+ "\n Da li ste primili trecu dozu (y/n): ");
								pom=clientInput.readLine();
							}
							
							if(pom.equals("n"))
							{
								clientOutput.println(">>> Niste uneli trecu vakcinu."
										+ "\nUspesno ste se registrovali!");
										break;
							}
							if(pom.equals("y"))
							{	while(true) {
								treca=true;
								clientOutput.println(">>> Unesite koju:"
										+ "\n1.Fizer"
										+ "\n2.Sputnjik "
										+ "\n3.Sinopharm"
										+ "\n4.Oxford");
								trecaVakcina=clientInput.readLine();
								if(trecaVakcina.equals("***quit"))
								{clientOutput.println(">>> Dovidjenja! " );
								
								soketZaKomunikaciju.close();
								return false;
								
								}
								
								
								pom=vakcina(trecaVakcina);
								if(!(pom.equals("Greska")))
								{
									
									bw.write(pom);
									bw.close();
									break;
								}
								else
									clientOutput.println(">>> Greska pri unosu");
							}
							
							//datumi trece doze
							LocalDate datum2;
							while(true)
							{
								
								clientOutput.println(">>> Unesite godinu kada ste vakcinisani(mora da se odnosi na 2021)");
								year=clientInput.readLine();
								
								if(year.equals("***quit"))
								{clientOutput.println(">>> Dovidjenja! " );
								
								soketZaKomunikaciju.close();
								return false;
								
								}								
								
								int godina=Integer.parseInt(year);
								if(godina!=2021)
								continue;
								clientOutput.println(">>> Unesite mesec kada ste vakcinisani");
								month=clientInput.readLine();
								
								if(month.equals("***quit"))
								{clientOutput.println(">>> Dovidjenja! " );
								
								soketZaKomunikaciju.close();
								return false;
								
								}	
															
								int mesec=Integer.parseInt(month);
								clientOutput.println(">>> Unesite dan kada ste vakcinisani");
								day=clientInput.readLine();
								if(day.equals("***quit"))
								{clientOutput.println(">>> Dovidjenja! " );
								
								soketZaKomunikaciju.close();
								return false;
								
								}	
								
								
								int dan=Integer.parseInt(day);
								
								datum2= LocalDate.of(godina,mesec,dan);
								if(Period.between(datum1, datum2).getMonths()>=6)
								{ buff= new BufferedWriter(new FileWriter("datumi.txt",true));
								buff.write(datum2.toString() + " ");
								buff.close();
								break;
								}
								clientOutput.println("Razmak izmedju druge i trece doze mora da bude bar 6 meseci");
							}
							//kraj datuma za trecu dozu
							
							
							}//kraj trece
							
							
							
							
						}//kraj druge
						
						
						
					}//kraj prve
					
					
					
					//bw.close();
					clientOutput.println(">>> Uspesno ste se registrovali! ");
					return true;
			}
			
			clientOutput.println(">>> Niste uneli niti y niti n pokusajte ponovo. ");
			
			}
			
			// Kraj Registracije
			
			bw.close();
			return true;
	
			
			
				
			
		}  
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Greska u registraciji");
		}
		return true;
	}
	
	boolean provera1(String username,String sifra,String ime,String prezime,String jmbg,String pol, String email) {
		
		try {
			br= new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
			String s;
			while((s=br.readLine())!=null) {
				usernameP="";
				int broj=s.indexOf(" ");
				for(int i = 0; i<broj;i++)
				usernameP+=s.charAt(i);
				
				if(username.equals(usernameP))
					{
					clientOutput.println("Korisnicko ime je vec zauzeto");
					br.close();
					return false;
					}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Greska pri citanju iz fajla");
		}
		if(username==null)
			return false;
		if(sifra==null)
			return false;
		if(ime==null)
			return false;
		if(prezime == null)
			return false;
		if((jmbg.length()!=13) || jmbg == null)
			return false;
		if(pol==null)
			return false;
		if(!(email.contains("@")))
			return false;
		if(email==null)
			return false;
		
		if(username.contains(" ")||sifra.contains(" ")||ime.contains(" ")||prezime.contains(" ")
				||jmbg.contains(" ")||pol.contains(" ")||email.contains(" "))
			return false;
		
		return true;
	}
	
	String vakcina(String broj)
	{
		if(broj.equals("1"))
			return "Fizer ";
		if(broj.equals("2"))
			return "Sputnjik ";
		if(broj.equals("3"))
			return "Sinopharm ";
		if(broj.equals("4"))
			return "Oxford ";
		return "Greska";
		
		
		
	}
	
	
		void prijava() {
			
			// Prijava
			try {
				br= new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
					boolean potvrda1=true,potvrda2=true;
				
					while(potvrda1||potvrda2) {
					clientOutput.println(">>> Unesite username ");
					username= clientInput.readLine();
					if(username.equals("***quit")) {
						br.close();
						clientOutput.println(">>> Dovidjenja! ");
						meni=false;
						soketZaKomunikaciju.close();
						return;
					}	
					
					
					
					clientOutput.println(">>> Unesite sifru ");
					sifra=clientInput.readLine();
					if(sifra.equals("***quit")) {
						br.close();
						clientOutput.println(">>> Dovidjenja! ");
						meni=false;
						soketZaKomunikaciju.close();
						return;
					}	
					
					
					
					String s;
					
					while((s=br.readLine())!=null)
					{
						usernameP="";
						int broj = s.indexOf(" ");
						for(int i=0;i<broj;i++)
							usernameP+=s.charAt(i);
						if(username.equals(usernameP))
						{	potvrda1=false;
							break;
							
						}
						
						
					}
					br.close();
					br= new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
					while((s=br.readLine())!=null)
					{
						sifraP="";
						int broj1=s.indexOf(" ");
						int broj2=s.indexOf(" ", broj1+1);
						for(int i=broj1+1;i<broj2;i++)
						sifraP+=s.charAt(i);
						if(sifra.equals(sifraP))
						{
							potvrda2=false;
							break;
						}
					}
					br.close();
					if(potvrda1||potvrda2)
						clientOutput.println(">>> Niste se uspesno ulogovali molimo vas da se prijavite ponovo");
					
				}
					clientOutput.println(">>> Uspesno ste se ulogovali!Dobrodosao "+username);
					
			
				
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska u prijavi");
			}
		}
		
		void unosStatusa()
		{	
			try {
				String ceo="";
				int broj=brojVakcina(username);
				if(broj==3) {
					
					clientOutput.println(">>>Vakcinisali ste se 3 puta vec");
					return;}
				int brojac=0;
				br= new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
				String s;
				boolean pr=true;
				while((s=br.readLine())!=null)
				{	if(pr)
					brojac++;
					if(formirajUsername(s).equals(username))
					{	pr=false;
					if(broj==1)	
					{	
						while(true)
						{
							clientOutput.println(">>> Unesite koju vakcinu zelite da dodate:"
									+ "\n(Napomena: Morate izabrati istu vakcinu kao i prvu u suprotnom ce biti greske)"
									+ "\n1.Fizer"
									+ "\n2.Sputnjik "
									+ "\n3.Sinopharm"
									+ "\n4.Oxford");
							pom=clientInput.readLine();
							
							if(pom.equals("***quit"))
							{	
								clientOutput.println(">>> Dovidjenja! " + username);
								soketZaKomunikaciju.close();
								br.close();
								meni=false;
								return;
							}
							
							String pomocni= vakcina(pom);
							if(pomocni.equals("Greska"))
							{
								clientOutput.println(">>>Greska pri kucanju molimo vas pokusajte ponovo");
								continue;
							}
							
							
							String nasaPrva="";
							int zadnji=s.lastIndexOf(" ");
							int at=s.indexOf("@");
							int pret=s.indexOf(" ",at);
							for(int i=pret+1;i<=zadnji;i++)
								nasaPrva+=s.charAt(i);
							
							if(pomocni.equals(nasaPrva))
							{
								s+=pomocni;
								break;								
							}
							else
							{
								clientOutput.println(">>> LOS unos");
							}
								
						}
						
					}
					if(broj!=1&&broj!=3)
					{	while(true) {
						clientOutput.println(">>> Unesite koju vakcinu zelite da dodate:"
								+ "\n1.Fizer"
								+ "\n2.Sputnjik "
								+ "\n3.Sinopharm"
								+ "\n4.Oxford");
						
						pom=clientInput.readLine();
						String pomocna=vakcina(pom);
						
						if(pom.equals("***quit"))
						{
							clientOutput.println(">>> Dovidjenja! " + username);
							soketZaKomunikaciju.close();
							br.close();
							meni=false;
							return;
						}
						
						if(pomocna.equals("Greska"))
						{
							clientOutput.println(">>>Greska pri kucanju molimo vas pokusajte ponovo");
							continue;
						}
						
						
						
						
						String pomocni= vakcina(pom);
						s+=pomocni;
						break;
						}
					}
					}
					ceo+=s+"\n";
				}
				br.close();
				//clientOutput.println(ceo);
				bw=new BufferedWriter(new FileWriter("tekst.txt"));
				bw.write(ceo,0,ceo.length()-1);
				//bw.
				bw.close();
				
				String pun="";
				
				int dopuna=0;
				BufferedReader brm= new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\datumi.txt"));
				while((s=brm.readLine())!=null) {
				dopuna++;
				
				if(dopuna!=brojac)
					pun+=s+"\n";
				if(dopuna==brojac) {
					
					while(true)
					{
						
						clientOutput.println(">>> Unesite godinu kada ste vakcinisani(mora da se odnosi na 2021)");
						year=clientInput.readLine();
						
						if(year.equals("***quit"))
						{clientOutput.println(">>> Dovidjenja! " );
							meni=false;
							brm.close();
						soketZaKomunikaciju.close();
						return ;
						
						}								
						
						int godina=Integer.parseInt(year);
						if(godina!=2021) {
							clientOutput.println(">>> Godina lose uneta");
						continue;}
						clientOutput.println(">>> Unesite mesec kada ste vakcinisani");
						month=clientInput.readLine();
						
						if(month.equals("***quit"))
						{
						clientOutput.println(">>> Dovidjenja! " );
						meni=false;
						brm.close();
						soketZaKomunikaciju.close();
						return ;
					
					}	
													
						int mesec=Integer.parseInt(month);
						clientOutput.println(">>> Unesite dan kada ste vakcinisani");
						day=clientInput.readLine();
						if(day.equals("***quit"))
						{
							clientOutput.println(">>> Dovidjenja! "  );
							meni=false;
							brm.close();
							soketZaKomunikaciju.close();
							return ;
						
						}	
						
						
						int dan=Integer.parseInt(day);
						
						
						LocalDate datum2=LocalDate.of(godina,mesec,dan);
						//clientOutput.println("Uneli ste ovaj datum "+datum2.toString()+" broj reda je " + brojac+ " dopuna "+ dopuna);
						if(broj==0)
						{
							pun+=s+datum2.toString()+" \n";
							break;
							
						}
						if(broj==1) {
							
							year="";
							month="";
							day="";
							for(int i=0;i<4;i++)
								year+=s.charAt(i);
							for(int i=5;i<7;i++)
									month+=s.charAt(i);
							for(int i=8;i<10;i++)
								day+=s.charAt(i);
						clientOutput.println(year +" "+ month+ " "+ day);
							 godina= Integer.parseInt(year);
							 mesec=Integer.parseInt(month);
							 dan= Integer.parseInt(day);
							
							LocalDate datum1=LocalDate.of(godina,mesec, dan);
							clientOutput.println("Prvi datum je "+ datum1.toString());
							if(Period.between(datum1, datum2).getMonths()>=1||
									Period.between(datum1, datum2).getDays()>=21)
							{
								pun+=s+datum2.toString()+" \n";
								break;
							}
							else
								clientOutput.println("Razmak mora da bude barem 3 nedelje");
							
						}
						if(broj ==2)
						{
							year="";
							month="";
							day="";
							for(int i=11;i<15;i++)
								year+=s.charAt(i);
							for(int i=16;i<18;i++)
									month+=s.charAt(i);
							for(int i=19;i<21;i++)
								day+=s.charAt(i);
						clientOutput.println(year +" "+ month+ " "+ day);
							 godina= Integer.parseInt(year);
							 mesec=Integer.parseInt(month);
							 dan= Integer.parseInt(day);
							 LocalDate datum1=LocalDate.of(godina,mesec, dan);
							clientOutput.println("Prvi datum je "+ datum1.toString());
							if(Period.between(datum1, datum2).getMonths()>=6)
							{
								pun+=s+datum2.toString()+" \n";
								break;
							}
							else
							{clientOutput.println("razmak mora da bude barem 6 meseci");}
							
							
						}
						
					}
					
					
					
					
					
				}
					
					
				}
				brm.close();
				BufferedWriter buff = new BufferedWriter(new FileWriter("datumi.txt"));
				buff.write(pun,0,pun.length()-1);
					buff.close();
					
				
				
			
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska u citanju");;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska prilikom s=br.readLine()");;
			}
			
			
			
		}
		

		void proveraPropusnice() {
			   int broj=brojVakcina(username);
			   if(broj>=2)
				   clientOutput.println(">>>Posedujete kovid propusnicu jer ste se vakcinisali"
				   		+ " "+ broj+" puta");
			   else
				   clientOutput.println(">>>Ne posedujete kovid propusnicu jer se niste vakcinisali"
					   		+ " 2 ili 3 puta");  
		   }
	
		   String formirajUsername(String s) {
			   String username="";
			  int index=s.indexOf(" ");
			   for(int i=0;i<index;i++)
			   username+=s.charAt(i);
			   return username;
		   }
		   
		   
		   int brojVakcina(String username)
			{
			   try {
				br= new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
				int brojac=0;
				String s;
			   
			   while((s=br.readLine())!=null)
				{	String pom="";
					int broj=s.indexOf(" ");
					for(int i=0;i<broj;i++)
						pom+=s.charAt(i);
					
					if(pom.equals(username))
						{
							for(int i=0;i<s.length();i++)
							{
								if(s.charAt(i) == ' ')
									brojac++;
							}
						
						}
					
				}
			   br.close();
			   switch (brojac) {
			case 7:
				return 0;
			case 8:
				return 1;
			case 9:
				return 2;
			case 10:
				return 3;

			default:
				return -1;
			}
			   
			   
			   } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("greska prilikom citanja iz dat");
			}
				return -1;
				
				
				
			}


		   void admin() {
			   
			   try {
				while(true) {
				   clientOutput.println(">>> Izaberite jednu od sledecih opcija:"
							+ "\n1.Proveriti kovid propusnicu na osnovu jmbg-a"
							+ "\n2.Prikazi listu svih korisnika i njihov status vakcinacije"
							+ "\n3.Pregled o ukupnoj dozi vakcinisanih"
							+ "\n4.Pregled podataka u zavisnosti od prve dve vakcine ");
				   	String pomocna= clientInput.readLine();
				   	if(pomocna.equals("***quit"))
					{
						clientOutput.println(">>> Dovidjenja! " + username);
						meni=false;
						soketZaKomunikaciju.close();
						return;
					}
				   	
				   	
				   	if(pomocna.equals("1"))
				   	{	 clientOutput.println(">>> Unesi jmbg:");
				   		pom=clientInput.readLine();
				   		String us=usernameFromJmbg(pom);
				   		
				   		if(pom.equals("***quit"))
						{
							clientOutput.println(">>> Dovidjenja! " + username);
							meni=false;
							soketZaKomunikaciju.close();
							return;
						}
				   		
				   		
				   		int broj=brojVakcina(us);
				   		if(broj>=2)
				   			clientOutput.println(">>> Korisnik ima pravilnu propusnicu");
				   		else
				   			clientOutput.println(">>> Korisnik nema pravilnu propusnicu");
				   	}
				   	if(pomocna.equals("2")) {
				   		
				   		prikazKorisnikaIStatus();
				   		
				   		
				   	}
				   	if(pomocna.equals("3"))
				   	{
				   		ukupnoVakcina();
				   	}
				   	if(pomocna.equals("4"))
				   	{
				   		brojVakcinaProizvodjaca();
				   	}
				   	
				   }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska pri upisivanju");
			}
			   
		   }


		   String usernameFromJmbg(String jmbg)
		   {
			   try {
				br=new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
				   String s;
				   while((s=br.readLine())!=null)
				   {	String us="";
					   int index1=s.indexOf(" ");
					   int index2=s.indexOf(" ",index1+1);
					   int index3=s.indexOf(" ",index2+1);
					   int index4=s.indexOf(" ",index3+1);
					   int index5=s.indexOf(" ",index4+1);
					   
					   for(int i=0;i<index1;i++)
					   us+=s.charAt(i);
						   
					   String maticniBroj="";
					   for(int i=index4+1;i<index5;i++)
					   maticniBroj+=s.charAt(i);
					   
					   if(maticniBroj.equals(jmbg)) {
						   br.close();
					   return us;}
				   }
				   br.close();
				   return "Ne postoji korisnik sa takvim jmbgom";
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska pri ucitavanju fajla");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska pri citanju iz fajla");
			}
			   
			   
			   
			   return "Ne postoji korisnik sa takvim jmbgom";
			   
			   
			   
		   }

		   String jmbgFromString(String s) {
			   
			   int index1=s.indexOf(" ");
			   int index2=s.indexOf(" ",index1+1);
			   int index3=s.indexOf(" ",index2+1);
			   int index4=s.indexOf(" ",index3+1);
			   int index5=s.indexOf(" ",index4+1);
			   String matBr="";
			   for(int i=index4+1;i<index5;i++)
				   matBr+=s.charAt(i);
			   
			   return matBr;
		   }
		   
		   
		   void prikazKorisnikaIStatus() { 
			   try {
				br=new BufferedReader(
					new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
				String s;
				String rec="";
				while((s=br.readLine())!=null)
				{	
					int broj=brojVakcinaPoStringu(s);
					usernameP=formirajUsername(s);
					
					if(!(usernameP.equals("admin"))) {
					rec+=usernameP + " "+ime(s)+" "+prezime(s)+ " ";
					clientOutput.println(rec+"Status:Korisnik se vakcinisao "+ broj +" puta");
					rec="";
					}
						
				}
			
				br.close();	   
			   
			   }
			 
			   catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Greksa pri ucitanju fajla");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska pri citanju iz fajla");
			}
			   
			  
			   
			   
			   
		   }

		   
		  int brojVakcinaPoStringu(String s) {
			int brojac=0;
			   for(int i=0;i<s.length();i++)
				{
					if(s.charAt(i) == ' ')
						brojac++;
				}
			   switch (brojac) {
				case 7:
					return 0;
				case 8:
					return 1;
				case 9:
					return 2;
				case 10:
					return 3;

				default:
					return -1;
				}
			   
			   
			
			   
			   
		   }



		   void ukupnoVakcina()
		   {
			   try {
				//broj ljudi sa jednom vakcinom
				   br=new BufferedReader(
							new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
			
				String s;
				int brojac=0;
				while((s=br.readLine())!=null)
				{
					if(brojVakcinaPoStringu(s)==1)
						brojac++;
					
					
				}
			   br.close();
			   clientOutput.println("Broj ljudi vakcinisanih samo sa 1 vakcinom je "+brojac);
			   
			   //broj ljudi sa 2 vakcine
			   br=new BufferedReader(
						new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
		
		
			brojac=0;
			while((s=br.readLine())!=null)
			{
				if(brojVakcinaPoStringu(s)==2)
					brojac++;
				
				
			}
		   br.close();
		   clientOutput.println("Broj ljudi vakcinisanih samo sa 2 vakcine je "+brojac); 
			   
		   
		   //broj ljudi sa 3 vakcine
		   br=new BufferedReader(
					new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
	
	
		brojac=0;
		while((s=br.readLine())!=null)
		{
			if(brojVakcinaPoStringu(s)==3)
				brojac++;
			
			
		}
	   br.close();
	   clientOutput.println("Broj ljudi vakcinisanih samo sa 3 vakcine je "+brojac); 
			   
			   
			   
			   } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska pri ucitavanju fajla");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska pri citanju");
			}
				
			   
			   
		   }

		   void brojVakcinaProizvodjaca() {
			   
			   try {
				br=new BufferedReader(
						new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
					String s;
					int brojacFizer=0,brojacSinop=0,brojacSput=0,brojacOxford=0;
					while((s=br.readLine())!=null)
					{
						int broj= brojVakcinaPoStringu(s);
						if(broj>=2)
						{
							String novi=nazivVakcine(s);
							if(novi.equals("Fizer"))
								brojacFizer++;
							if(novi.equals("Oxford"))
								brojacOxford++;
							if(novi.equals("Sinopharm"))
									brojacSinop++;
							if(novi.equals("Sputnjik"))
										brojacSput++;
						}
						
						
					}
					clientOutput.println("Broj korisnika koji su primili barem drugu dozu vakcine, u zavisnosti vakcine je sledeci:"
							+ "\nBroj vakcinisanih Fizer vakcinom je: "+brojacFizer+"\nBroj vaksinisanih "
									+ "sa Sinopharm vakcinom je "+ brojacSinop+"\nBroj vakcinisanih "
											+ "sa Sputnjik vakcinom je "+brojacSput +"\nBroj vakcinisanih "
													+ "sa Oxford vakcinom je "+brojacOxford);
			br.close();
			 } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				 System.out.println("Greska pri ucitavanju fajla");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Greska pri citanju iz fajla");
			}
		
		
			   
		   }
		   
		   String generisiPrvuVakcinu(String s)
		   {
			   String povratna="";
			   int index1=s.indexOf(" ");
			   int index2=s.indexOf(" ",index1+1);
			   int index3=s.indexOf(" ",index2+1);
			   int index4=s.indexOf(" ",index3+1);
			   int index5=s.indexOf(" ",index4+1);
			  
			   int index6=s.indexOf(" ",index5+1);
			   int index7=s.indexOf(" ",index6+1);
			   int index8=s.indexOf(" ",index7+1);
			  for(int i=index7+1;i<index8;i++)
			   povratna+=s.charAt(i);
			   return povratna;
			   
		   }

		   String generisiDruguVakcinu(String s) {
			   String povratna="";
			   int index1=s.indexOf(" ");
			   int index2=s.indexOf(" ",index1+1);
			   int index3=s.indexOf(" ",index2+1);
			   int index4=s.indexOf(" ",index3+1);
			   int index5=s.indexOf(" ",index4+1);
			  
			   int index6=s.indexOf(" ",index5+1);
			   int index7=s.indexOf(" ",index6+1);
			   int index8=s.indexOf(" ",index7+1);
			   int index9=s.indexOf(" ",index8+1);
			  for(int i=index8+1;i<index9;i++)
			   povratna+=s.charAt(i);
			   return povratna;
		   }

		   String generisiTrecuVakcinu(String s) {
			   String povratna="";
			   int index1=s.indexOf(" ");
			   int index2=s.indexOf(" ",index1+1);
			   int index3=s.indexOf(" ",index2+1);
			   int index4=s.indexOf(" ",index3+1);
			   int index5=s.indexOf(" ",index4+1);
			  
			   int index6=s.indexOf(" ",index5+1);
			   int index7=s.indexOf(" ",index6+1);
			   int index8=s.indexOf(" ",index7+1);
			   int index9=s.indexOf(" ",index8+1);
			   int index10=s.indexOf(" ",index9+1);
			  for(int i=index9+1;i<index10;i++)
			   povratna+=s.charAt(i);
			   return povratna;
		   }
	   
		   String nazivVakcine(String s)
		   {
			   int index1=s.indexOf(" ");
			   int index2= s.indexOf(" ",index1+1);
			   int index3= s.indexOf(" ",index2+1);
			   int index4= s.indexOf(" ",index3+1);
			   int index5= s.indexOf(" ",index4+1);
			   int index6= s.indexOf(" ",index5+1);
			   int index7= s.indexOf(" ",index6+1);
			   int index8= s.indexOf(" ",index7+1);
			   String pomocna="";
			   for(int i=index7+1;i<index8;i++)
				   pomocna+=s.charAt(i);
			   
			   if(pomocna.equals("Fizer"))
			   return "Fizer";
			   
			   if(pomocna.equals("Sinopharm"))
				   return "Sinopharm";
			   
			   if(pomocna.equals("Sputnjik"))
				   return "Sputnjik";
			   
			   if(pomocna.equals("Oxford"))
				   return "Oxford";
			   
			   return "Greska";
		   }
		   
		   	String ime(String s)
		   	{
		   		int index1=s.indexOf(" ");
		   		int index2=s.indexOf(" ",index1+1);
		   		int index3=s.indexOf(" ",index2+1);
		   		String ime="";
		   		for(int i=index2+1;i<index3;i++)
		   		ime+=s.charAt(i);
		   		
		   		return ime;
		   		
		   	}
		   	
		   	String prezime(String s)
		   	{
		   		int index1=s.indexOf(" ");
		   		int index2=s.indexOf(" ",index1+1);
		   		int index3=s.indexOf(" ",index2+1);
		   		int index4=s.indexOf(" ",index3+1);
		   		String prezime="";
		   		for(int i=index3+1;i<index4;i++)
		   		prezime+=s.charAt(i);
		   		
		   		return prezime;
		   		
		   	}

		   	public void generisuPropusnicu()
		   	{
		   		int broj=brojVakcina(username);
		   		if(broj<2)
		   		{
		   			clientOutput.println("Niste primili dovoljno vakcina");
		   			return;
		   		}
		   		String kraj="";
		   		String s;
		   		int brojac=0;
		   		try {
					br= new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\tekst.txt"));
					while((s=br.readLine())!=null) {
						brojac++;
						if(username.equals(formirajUsername(s)))
						{
							kraj+=ime(s)+" "+ prezime(s) + " "+jmbgFromString(s)+"\n";
							prvaVakcina=generisiPrvuVakcinu(s);
							drugaVakcina=generisiDruguVakcinu(s);
							if(broj==3)
							trecaVakcina=generisiTrecuVakcinu(s);
							//br.close();
							break;
						}
					}
					
					br.close();
					String datum1="",datum2="",datum3="";
					br= new BufferedReader(new FileReader("C:\\Users\\Korisnik\\eclipse-workspace\\Domaci2_proba1\\datumi.txt"));
					int dodatno=0;
					while((s=br.readLine())!=null) {
						dodatno++;
						if(dodatno==brojac)
						{	
							for(int i=0;i<10;i++)
								datum1+=s.charAt(i);
							for(int i=11;i<21;i++)
								datum2+=s.charAt(i);
							
						if(broj==3)
						{
							for(int i=22;i<32;i++)
								datum3+=s.charAt(i);
							
						}
						  br.close();
							break;
						}
						
					}
					br.close();
					bw= new BufferedWriter(new FileWriter("generisi.txt"));
					kraj+=prvaVakcina+" "+ datum1+ "\n" + drugaVakcina + " "+ datum2+ "\n";
					if(broj==3)
						kraj+=trecaVakcina+ " "+ datum3;
		   		bw.write("Kovid propusnica\n"+ kraj);
		   		bw.close();
					
		   		} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Grekska pri ucitavanju fajla");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Greks pri citanju iz fajla");
				}
		   		
		   		
		   		
		   	}
		   	
		   	
}	
	
	