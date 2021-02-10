package clavardage;

import java.util.ArrayList;


/**
 * Classe Contacts h�ritant de ArrayList<User> repr�sentant la liste des users connect�s 
 * par l'attribut contacts 
 *
 */
public class Contacts extends ArrayList<User>{
	
	private static final long serialVersionUID = 1L;
	static ArrayList<User> contacts = new ArrayList<User>();
	
	/**
	 * Constructeur de Contacts
	 */
	public Contacts() {
		super();
	}
	
	/**
	 * Affichage de l'ensemble des users de notre liste Contacts
	 */
	public static void showContacts() {
		System.out.println("Contacts list:");
		for (User user : contacts) {
			System.out.println(user.getPseudo());
		}
	}
	
	/**
	 * Ajout d'un user 
	 * @param e user � ajouter dans la liste
	 */
	public void addContact(User e) {
		contacts.add(e);
	}
	
	/**
	 * Suppression d'un user
	 * @param e user � supprimer de la liste
	 */
	public void deleteContact (User e) {
		contacts.remove(e);
	}
	
	/**
	 * Compte le nombre de users dans la liste afin de trouver la taille de celle-ci
	 * @return la taille de la liste
	 */
	public int length () {
		int n=0;
		for (User user : contacts) {
			n++;
		}
		return n;
	}
	
	/**
	 * Teste l'appartenance d'un user � la liste de contacts
	 * @param e user � v�rifier
	 * @return true si e appartient � la liste, false sinon
	 */
	public boolean appartient (User e) {
		for (User user : contacts) {
			if (user.equals(e)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Teste l'appartenance d'un pseudo � la liste de contacts
	 * @param pseudo pseudo � v�rifier
	 * @return true si e appartient � la liste, false sinon
	 */
	public boolean appartient (String pseudo) {
		for (User user : contacts) {
			if (user.getPseudo().equals(pseudo)) {
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Cr�ation d'un tableau de string correspondant aux users de la liste
	 * @return tableau des pseudos des users
	 */
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
	
	/**
	 * R�cup�ration d'un user d'apr�s son pseudo
	 * @param pseudo Pseudo de l'user � r�cup�rer
	 * @return user avec le pseudo correspondant
	 */
	public User getUserfromPseudo (String pseudo) {
		User toreturn = null;
		for (User user : contacts) {
			if (user.getPseudo().equals(pseudo)) {
				toreturn=user;
				return toreturn;
			}
		}
		return null;
	}
	
	/**
	 * R�cup�ration d'un user d'apr�s son ip
	 * @param ip Ip de l'user � r�cup�rer
	 * @return user avec l'ip correspondante
	 */
	public User getUserfromIP (String ip) {
		User toreturn = null;
		for (User user : contacts) {
			if (user.getIP().equals(ip)) {
				toreturn=user;
				return toreturn;
			}
		}
		return null;
	}
	
	/**
	 * R�cup�ration d'un pseudo d'user d'apr�s son ip
	 * @param IP Ip de l'user 
	 * @return pseudo de l'user � l'Ip correspondante
	 */
	public String getPseudofromIP (String IP) {
		String toreturn = null;
		for (User user : contacts) {
			if (user.getIP().equals(IP)) {
				toreturn=user.getPseudo();
				return toreturn;
			}
		}
		return null;
	}
	
	/**
	 * Renvoie la liste de contacts en chaine de caract�res
	 * @return la liste de contacts
	 */
	public String getList(){
		String liste="";
		for (User user : contacts) {
			liste=user.toString();
		}
		return liste;
	}
	
}
