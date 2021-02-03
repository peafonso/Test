package clavardage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

import servlet.ChatServlet;

public class Application {
	private User me;
	private Contacts friends;
	private ChatServlet servlet;

	
	private static int serverport;
	private static String serverip;
	
	private static final String urlpage = "/Test/welcome";

	
	public Application(User u1) {
		//on crée notre liste de contacts
		this.setMe(u1);
		setFriends(new Contacts());
		
		serverport=1234;
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
		 
	public void Connexion() {
		try {
			HttpURLConnection con = sendRequest("connexion", getMe().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void Deconnexion() {
		try {
			HttpURLConnection con = sendRequest("deconnexion", getMe().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void ChangePseudo() {
		try {
			HttpURLConnection con = sendRequest("changepseudo", getMe().toString());
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

	public void setFriends(Contacts friends) {
		this.friends = friends;
	}



}
