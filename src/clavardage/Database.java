package clavardage;

import java.sql.Statement;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe représentant la base de données permettant l'historique des conversations 
 * app : instance de la classe Application 
 *
 * 	Une table est crée pour chaque conversation du nom de l'adresse ip de la personne avec qui
 * on discute. On mets dedans l'ensemble des messages 
 * 
 */

public class Database {
	
	private static Application app;
	// SQLite connection string
	//créer un dossier
    String url = "jdbc:sqlite:./sqlite/test.db";
    
	/**
	 * Constructeur de Database : association de l'application et création de la base de données
	 * @param app Application associée
	 */
    public Database(Application app) {
    	this.setApp(app);
    	createNewDatabase(); 
    	
    }
    
   /**
	* Création de la base de données 
	* (si elle est déjà existante, les messages de création s'affichent mais rien ne se passe
	* au niveau de l'ancien fichier)
	*
	*/
	public void createNewDatabase() {
	   	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	   	
	   	try (Connection conn = DriverManager.getConnection(url)) {
	   		if (conn != null) {
	   			DatabaseMetaData meta = conn.getMetaData();
	   			System.out.println("The driver name is " + meta.getDriverName());
	   			System.out.println("A new database has been created.");
	   		}

	   	} catch (SQLException e) {	
	   		System.out.println(e.getMessage());
	   	}
	}
	
	/**
	 * Connection à la base de données	
	 * @return l'objet de connection
	 */
	private Connection connect() {
		Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
 	}	 
	 
	 
	/** 
	 * Création d'une table de conversation 
	 * name : Chatwith_IP2 (avec ip2 adresse ip de l'user avec qui on discute)
	 * id : numéros des messages (1 étant le plus ancien)
	 * time : horodatage des message
	 * message : textes envoyés par les participants de la conversation
	 * sender : 0 -> on a envoyé le message 1 -> on a reçu le message
     * 
     * (Si on lance createTableConvo avec un correspondant avec lequel on a déjà conversé on 
     * ne recrée pas une nouvelle table (IF NOT EXISTS))
     * @param ip2 adresse ip de la personne avec qui on communique 
     *
     */
	public void createTableConvo(String ip2) {
        String sqlconvo= "CREATE TABLE IF NOT EXISTS `" +getNomTable(ip2)+"`(\n"
        		+ "	id integer PRIMARY KEY,\n"
                + "	time text NOT NULL, \n"
        		+ " message text NOT NULL, \n"
                + " sender integer NOT NULL"
                + ");"; 
        
        try (Connection conn = DriverManager.getConnection(url);
        		Statement stmt = conn.createStatement()) {
        		stmt.execute(sqlconvo);
        } catch (SQLException e) {
         	System.out.println("erreur at createTableConvo\n");
         	System.out.println(e.getMessage());
        }
	}
	
	/**
     * Recupération de l'historique d'une conversation
     * @param ip2 adresse ip de la personne avec qui on communique
     * @return liste des messages envoyés correspondant à l'historique de la conversation
     *
     */
	public ArrayList<Message> recupHistory(String ip2) {
        ArrayList<Message> historique = new ArrayList<Message>();
		String nomtable= getNomTable(ip2);
		String sql = "SELECT id, time, message, sender FROM `"+nomtable+"`";
	        
	    try (Connection conn = this.connect();
			 Statement stmt  = conn.createStatement();
	         ResultSet rs    = stmt.executeQuery(sql)){

	    	// loop through the result set
	    	while (rs.next()) {
	    		Message msg= new Message();
	    		msg.setData(rs.getString("message"));
	    		msg.setTimeString(rs.getString("time"));
	    		if (rs.getInt("sender")==0) {
	    			msg.setSender(getApp().getMe());
		    		msg.setReceiver(getApp().getFriends().getUserfromIP(ip2));
	    		}
	    		else {
	    			msg.setSender(getApp().getFriends().getUserfromIP(ip2));
		    		msg.setReceiver(getApp().getMe());
	    		}
	    		historique.add(msg);
	         	}
	        } catch (SQLException e) {
	            System.out.println("error at recupHistory\n");
	            System.out.println(e.getMessage());
	        }
		return historique;
	}


	/**
     * Ajout de message dans l'historique (representée par la table Chatwith_ip2)
     * @param ip2 adresse ip de la personne avec qui on communique
     * @param msg message à ajouter dans la table
     *
     */
	public void addMessage(String ip2, Message msg) {
		String nomtable= getNomTable(ip2);
		String sql = "INSERT INTO `"+nomtable+"`(id,time,message,sender) VALUES(?,?,?,?)";

		try (Connection conn =  this.connect() ; PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(2, msg.getTimeString());
	        pstmt.setString(3, msg.getData());
	        // 0 -> j'ai envoyé le message
	        if (msg.getSender().equals(getApp().getMe())) {
	        	pstmt.setInt(4, 0);
	        }
	        // 1 -> j'ai reçu le message
	        else {
	        	pstmt.setInt(4, 1);

	        }
	    	System.out.println("on ajoute le msg");

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	    	System.out.println("error at addMessage\n");
	    	System.out.println(e.getMessage());
	    }
	}
	
	/**
     * Récupérer nom de table de conversation
     * @param ip2 adresse ip de la personne avec qui on communique
     * @return nom de la table correspondante
     *
     */
	public String getNomTable(String ip2) {
		return "Chatwith_"+ip2;
	}
	
	
	/**
     * Suppression de table de conversation
     * @param ip2 adresse ip de la personne avec qui on communique
     *
     */
	public void deleteConvo(String ip2) {
		String nomtable= getNomTable(ip2);
		String sql = "DROP TABLE `"+nomtable+"`";
		System.out.println("DROP TABLE `"+nomtable+"`");
	        try (Connection conn = DriverManager.getConnection(url);
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.executeUpdate();

	        } catch (SQLException e) {
	            System.out.println("erreur deleteconvo");
	        }
	}

	    
	//-------------------- GETTEURS & SETTEURS -----------------------------//

	public static Application getApp() {
		return app;
	}
	
	public void setApp(Application app) {
		Database.app = app;
	}
		
}

