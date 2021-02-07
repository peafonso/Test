package clavardage;

import java.net.ServerSocket;
import java.net.Socket;

import clavardage.*;


/**
 * Classe Runner TCP, � l'�coute d'un lancement de conversation avec d'autre users 
 * 
 * app : application associ�e
 * link : socket d'�coute des conversations tcp 
 * ouvert : bool�en permettant de g�rer la fermeture du socket
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
	 * @param app Application associ�e
	 */
	public TCPRunner(Application app) {
        this.app=app;
		setOuvert(true);

    }

	/**
	 * M�thode run permettant d'�couter les demandes de clavardage
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