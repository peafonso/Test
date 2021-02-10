package clavardage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

/**
 *  Classe de l'application, rassemblant l'instance des arguments nécessaires à l'utilisation 
 *  
 *  me : instance de la classe User représentant le user connecté sur le sytème

 *  db : instance de la classe Database représentant la base de données de l'user (permettant la gestion
 *       de l'historique des conversations)
 *  
 *  serverport & serverip : attributs du serveur
 *  
 */

public class Application {
	private User me;
	private Contacts friends;
	private Database db;
	
	private static int serverport;
	private static String serverip;
	
	private static final String urlpage = "/Test/welcome";

	/**
	 * Constructeur de l'application 
	 * @param u1 utilisateur de l'application
	 */
	public Application(User u1) {
		this.setMe(u1);
		serverport=8080;
		serverip=getCurrentIp().getHostAddress();
	}
	
	/**
	 * Recuperation de l'adresse IP de l'hote 
	 * @return l'adresse ip correspondante 
	 */	 
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
		 
	/**
	 * Connexion de l'user au serveur (test de l'unicité du pseudo choisi)
	 * @param pseudo pseudonym choisi par l'user
	 * @return true si le pseudo est disponible et que la connexion est valide, false sinon
	 * 
	 */
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
	
	/**
	 * Déconnexion de l'user au serveur
	 * 
	 */
	public void Deconnexion() {
		try {
			sendRequest("deconnexion", "pseudo="+getMe().getPseudo());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Demande de changement de pseudo (test de l'unicité de celui-ci)
	 * @param pseudo nouveau pseudonym choisi par l'user
	 * @return true si le pseudo est disponible, false sinon
	 */
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
	
	/**
	 * Permet de récupérer l'ensemble des users connectés sur le serveur
	 * 
	 */
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
	
	
	/**
	 * Méthode pour envoyer une requête au serveur
	 * @param action action à effectuer (connexion, deconnexion, changepseudo ou getcontacts)
	 * @param param paramètres utiles à l'actions
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	public static HttpURLConnection sendRequest(String action, String param) throws IOException {
		
		URL url = new URL("http://" + serverip + ":" + serverport + urlpage +"?action=" + action + "&" + param);
		System.out.println(url);
		
		//envoi requete
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
			
		return con;
	}
	
	
	
	//-------------------- GETTEURS & SETTEURS -----------------------------//

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
