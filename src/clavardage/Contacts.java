package clavardage;

import java.util.ArrayList;

//Base de donnees des ptits gens
public class Contacts extends ArrayList<User>{
	
	private static final long serialVersionUID = 1L;
	static ArrayList<User> contacts = new ArrayList<User>();
	
	//Constructeur
	public Contacts() {
		super();
	}
	
	//Methode affichant tous les contact de la liste
	public static void showContacts() {
		System.out.println("Contacts list:");
		for (User user : contacts) {
			System.out.println(user.getPseudo());
		}
	}
	
	public void addContact(User e) {
		contacts.add(e);
	}
	
	public void deleteContact (User e) {
		contacts.remove(e);
	}
	
	public boolean appartient (String pseudo) {
		for (User user : contacts) {
			if (user.getPseudo().equals(pseudo)) {
				return true;
			}
		}
		return false;
	}
	

	//pour récupérer un tableau de string des pseudos
	public String[] getListPseudo() {
		if (!(contacts.isEmpty())) {
			String[] tab= new String[length()];
			int i=0;
			for (User user : contacts) {
				tab[i]=user.getPseudo();
				i++;
			}
			return tab;
		}
		else {
			String[] tab= {};
			return tab;
		}
	}
	
	//Recuperer un utilisateur d'apres son pseudo
	public User getUserfromPseudo (String pseudo) {
		User toreturn = null;
		for (User user : contacts) {
			if (user.getPseudo().equals(pseudo)) {
				toreturn=user;
			}
		}
		return toreturn;
	}
	
	//Recuperer un utilisateur d'apres son ip
	public User getUserfromIP (String ip) {
		User toreturn = null;
		for (User user : contacts) {
			if (user.getIP().equals(ip)) {
				toreturn=user;
			}
		}
		return toreturn;
	}
		
	//Recuperer le pseudo de l'utilisateur d'apres son IP
	public String getPseudofromIP (String IP) {
		String toreturn = null;
		for (User user : contacts) {
			if (user.getIP().equals(IP)) {
				toreturn=user.getPseudo();
			}
		}
		return toreturn;
	}
		
	public int length () {
		int n=0;
		for (User user : contacts) {
			n++;
		}
		return n;
	}
	
	

}
