package clavardage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Enumeration;

import Interface.Home;
import servlet.ChatServlet;

public class Application {
	private User me;
	private Contacts friends;
	//private ChatServlet servlet;
	private Database db;
	
	private static int serverport;
	private static String serverip;
	
	private static final String urlpage = "/Test/welcome";

	
	public Application(User u1) {
		//on crée notre liste de contacts
		this.setMe(u1);
		serverport=8080;
		serverip=getCurrentIp().getHostAddress();
	}
	
	//Recupere l'adresse IP de l'hote (si plusieurs disponibles, prend la première)
	 public static InetAddress getCurrentIp() { 
		 try { 
			 Enumeration networkInterfaces = NetworkInterface .getNetworkInterfaces();
			 while (networkInterfaces.hasMoreElements()) { 
				 NetworkInterface ni = (NetworkInterface) networkInterfaces .nextElement(); 
				 Enumeration nias = ni.getInetAddresses(); 
				 while(nias.hasMoreElements()) { 
					 InetAddress ia= (InetAddress) nias.nextElement();
		
					 if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) { 
						 return ia; 
					 } 
				 } 
			 } 
		 } 
		 catch (SocketException e) {
			 System.out.println("unable to get current IP " + e.getMessage());
		 } 
	return null;
	} 
		 
	public boolean Connexion(String pseudo) {
		boolean disponible=true;
		try {
			HttpURLConnection con = sendRequest("connexion","pseudo="+pseudo+"&ip="+getMe().getIP());
			String response= con.getHeaderField("disponible");
			if (response.equals("true")) {
				disponible=true;
			}
			else {
				disponible=false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return disponible;

	}

	public void Deconnexion() {
		try {
			HttpURLConnection con = sendRequest("deconnexion", "pseudo="+getMe().getPseudo());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public boolean ChangePseudo(String pseudo) {
		boolean disponible=true;
		try {
			HttpURLConnection con = sendRequest("changepseudo", "oldpseudo="+getMe().getPseudo()+"&pseudo="+pseudo);
			String response= con.getHeaderField("disponible");
			if (response.equals("true")) {
				disponible=true;
			}
			else {
				disponible=false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return disponible;
	}
	
	public void setFriends() {
		try {
			HttpURLConnection con = sendRequest("getcontacts", "");
			String liste= con.getHeaderField("contacts");
			Contacts contacts= new Contacts();
			String[] users=liste.split("/");
			for (int i=0; i<users.length; i++) {
				String[] pseudoandip= users[i].split("_");
				User u=new User(pseudoandip[2],1234,pseudoandip[1]);
				if(!(contacts.appartient(u))) {
					contacts.addContact(u);
					db.createTableConvo(pseudoandip[2]);
				}
			}
			System.out.println("There is "+ contacts.length());
			this.friends = contacts ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//méthode pour envoyer une requete au serveur 
	public static HttpURLConnection sendRequest(String action, String paramValue) throws IOException {
		
		URL url = new URL("http://" + serverip + ":" + serverport + urlpage +"?action=" + action + "&" + paramValue);
		System.out.println(url);
		
		//envoi requete
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
			
		return con;
	}
	
	

	public User getMe() {
		return me;
	}

	public void setMe(User me) {
		this.me = me;
	}

	public Contacts getFriends() {
		return friends;
	}


	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}



}
