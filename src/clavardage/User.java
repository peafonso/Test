package clavardage;

import java.net.InetAddress;
import java.net.UnknownHostException;

//import system.UDPListener;

public class User {
	
	private String iP;
	private String pseudo;
	private int port;
	private int id; 
	
	public User() {
		this.setPort(1234);
		this.setIP(Application.getCurrentIp().getHostAddress());
	}

	public User(String address, int port, String pseudonym) {
		this.setIP(address);
		this.setPort(port);
		this.setPseudo(pseudonym);
	}
	
	//verifie l'adresse ip pour recuperer un user
	public boolean findUser (String adress) {
		return this.iP.equals(adress);
	}

	
	//teste l'egalite de deux users par rapport a leur pseudo
	public boolean equals (User user) {
		boolean bool= false;
		if (this.pseudo.equals(user.pseudo)) {
			bool=true;
		}
		return bool; 
	}
	
	public String toString() {
		return "_"+this.pseudo+"_"+this.iP+"_"+String.valueOf(this.port);
	}
	
	public static User toUser(String s) {
		String[] parametersuser=s.split("_");
		//String validate= parametersuser[0];
		String userpseudo = parametersuser[1];
		String userip = parametersuser[2];
		String userport = parametersuser[3];
		User people= new User(userip, Integer.parseInt(userport), userpseudo);
		return people;
	}
	
	
	//SETTEUR & GETTEUR

	public String getIP() {
		return iP;
	}

	public void setIP(String address) {
		this.iP = address;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	
	public int getPort() {
		return port;
	}

	public void setPort(int p) {
		this.port = p;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
