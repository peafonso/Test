package clavardage;

/**
 * Classe repr�sentant un user de l'application, associ�e � ces 3 attributs :
 * ip : addresse ip de l'user (en string)
 * pseudo : pseudo de l'user
 * port : num�ro de port de l'user
 *
 */
public class User {
	
	private String iP;
	private String pseudo;
	private int port;
	
	/**
	 * Constructeur1 d'un User sans attributs 
	 * qui associe le port 1234 et r�cup�re l'addresse IP correspondante
	 * (le pseudo sera determin� par la suite apr�s v�rification d'unicit�)
	 */
	public User() {
		this.setIP(Application.getCurrentIp().getHostAddress());
		this.setPort(1234);
	}
	
	/**
	 * Constructeur2 d'un User
	 * @param address ip de l'user � cr�er
	 * @param port port de l'user � cr�er
	 * @param pseudonym pseudo de l'user � cr�er
	 */
	public User(String address, int port, String pseudonym) {
		this.setIP(address);
		this.setPort(port);
		this.setPseudo(pseudonym);
	}
	
	/**
	 * Renvoie les informations d'un user
	 * @return informations de l'user en string
	 */
	@Override
	public String toString() {
		return "_"+this.pseudo+"_"+this.iP+"_"+String.valueOf(this.port);
	}
	
	/**
	 * Cr�ation d'un user � partir d'informations sous une chaine de caract�res
	 * @param s chaine de caract�res contenant les informations du user
	 * @return l'user correspondant
	 */
	public static User toUser(String s) {
		String[] parametersuser=s.split("_");
		//String validate= parametersuser[0];
		String userpseudo = parametersuser[1];
		String userip = parametersuser[2];
		String userport = parametersuser[3];
		User people= new User(userip, Integer.parseInt(userport), userpseudo);
		return people;
	}
	
	
	//-------------------- GETTEURS & SETTEURS -----------------------------//

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


}
