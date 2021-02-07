package Interface;


import javax.swing.*;
import java.awt.*;

/**
 * Classe BackgroundJFrame héritant de JFrame permettant de mettre en place
 *  les images de fonds pour les classes AppInterface et Home
 */

class BackgroundJFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur BackgroundJFrame 
	 * > Mise en place du fond correspondant à la classe voulu
	 * @param name nom de la frame à décorer
	 */
	public BackgroundJFrame(String name){
		super(name);
		//setTitle("Background Color for JFrame");
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);


		// Another way
		setLayout(new BorderLayout());
		ImageIcon fond = new ImageIcon();
		if (name.contentEquals("Connexion")) {
			fond = createImageIcon("/images/fond_connexion.jpg");
			//setSize(1000,800);
		}else if (name.contentEquals("Home")) {
			fond = createImageIcon("/images/ACCUEIL_FOND2.jpg");
		}
		setContentPane(new JLabel(fond));
		setLayout(new FlowLayout());

		// Just for refresh :) Not optional!
	   setSize(501,501);
	   setSize(500,500);
	}
	
    /**
     * Récupération d'une image par son path 
     * @param path chemin d'accès à l'image
     * @return ImageIcon correspondant au chemin , ou null si path invalide
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imageURL = BackgroundJFrame.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: "
                               + path);
            return null;
        } else {
            return new ImageIcon(imageURL);
        }
    }

}
