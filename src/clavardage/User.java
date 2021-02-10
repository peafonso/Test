package clavardage;

/**
 * Classe représentant un user de l'application, associée à ces 3 attributs :
 * ip : addresse ip de l'user (en string)
 * pseudo : pseudo de l'user
 * port : numéro de port de l'user
 *
 */
public class User {
	
	private String iP;
	private String pseudo;
	private int port;
	
	/**
	 * Constructeur1 d'un User sans attributs 
	 * qui associe le port 1234 et récupère l'addresse IP correspondante
	 * (le pseudo sera determiné par la suite après vérification d'unicité)
	 */
	public User() {
		this.setIP(Application.getCurrentIp().getHostAddress());
		this.setPort(1234);
	}
	
	/**
	 * Constructeur2 d'un User
	 * @param address ip de l'user à créer
	 * @param port port de l'user à créer
	 * @param pseudonym pseudo de l'user à créer
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
	 * Création d'un user à partir d'informations sous une chaine de caractères
	 * @param s chaine de caractères contenant les informations du user
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
