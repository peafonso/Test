package clavardage;

import java.net.ServerSocket;
import java.net.Socket;

import clavardage.*;


/**
 * Classe Runner TCP, à l'écoute d'un lancement de conversation avec d'autre users 
 * 
 * app : application associée
 * link : socket d'écoute des conversations tcp 
 * ouvert : booléen permettant de gérer la fermeture du socket
 * 
 */

public class TCPRunner extends Thread {
	private Application app;
	private Socket link;
    byte[] array = new byte[100000000];
	private static boolean ouvert;
    
	/**
	 * Constructeur Runner
	 * (permettant l'ouverture du socket)
	 * @param app Application associée
	 */
	public TCPRunner(Application app) {
        this.app=app;
		setOuvert(true);

    }

	/**
	 * Méthode run permettant d'écouter les demandes de clavardage
	 */
    public void run() {
        ServerSocket server;
        try {
            server = new ServerSocket(2000); 
            System.out.println("listening on port 2000 ready to have conversation");
            while(ouvert) { 
                link = server.accept(); 
                TCPChat chat = new TCPChat(app,link);
                
            }
            //link.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
	//-------------------- GETTEURS & SETTEURS -----------------------------//

    public boolean isOuvert() {
		return ouvert;
	}

	public static void setOuvert(boolean ouvert) {
		TCPRunner.ouvert = ouvert;
	}

}