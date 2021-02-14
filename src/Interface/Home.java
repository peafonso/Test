package Interface;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;

import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clavardage.*;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;

import Interface.*;

/**
 * Classe Home correspondant à la fenêtre principale (main) de l'application 
 * 
 * app : instance de la classe Application associée
 * frame : panel de contenu principal de la frame
 * panel : panel de contenu principal de la frame
 * textfield : zone de texte pour pouvoir rentrer le pseudo choisi par l'user
 * btnSend : bouton permettant l'envoi de messages
 * textArea : zone de lecture de la conversation
 * talkingto : pseudo de l'user avec qui on clavarde
 * usertalking : user avec qui on clavarde
 * txtrB : notre pseudo
 * usersconnected : liste des users connectés sur le réseau
 * 
 * tcpListen : instance de TCPRunner permettant l'écoute de conversation 
 * 
 */

public class Home {

	private static Application app;
	private static JFrame frame;
	private JTextField textField;
	private static JPanel panel;
	private JButton btnSend;
	private static JEditorPane textArea;
	private static JTextArea talkingto;
	private static User usertalking;
	private static JTextArea txtrB;
	private static JEditorPane notifPane;
	private static JList<String> usersconnected;
	private TCPRunner tcpListen;
	private static Timer timer;


	/**
	 * Constructeur de la page Home 
	 * @param app Application associée
	 */
	public Home(Application app) {
		setApp(app);
		app.setFriends();
		initialize();
	}

	/**
	 * Initialisation des composants de la frame
	 */
	private void initialize() {
		frame = new BackgroundJFrame("Home");
		frame.setBackground(new Color(240, 240, 240));
		// frame.setBounds(100, 100, 1640, 920);
		// centrer la fenetre au milieu de l'ecran
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, dim.width, dim.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new windowClosingListener());

		// frame.setSize(1600,900);
		//ImageIcon homePicture = new ImageIcon();
		//homePicture = createImageIcon("/images/ACCUEIL_FOND2.jpg");

		frame.setLocation(dim.width / 3 - frame.getWidth() / 3, dim.height / 3 - frame.getHeight() / 3);
		frame.getContentPane().setBounds(0, 0, dim.width, dim.height);
		frame.pack();

		JMenuBar menuBar = new JMenuBar();
		// menuBar.setMargin(new Insets(1000, 0, 1000, 0));
		frame.setJMenuBar(menuBar);

		// Bouton Home
		JButton btnNewButton = new JButton("Home");
		btnNewButton.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(153, 153, 153));
		//menuBar.add(btnNewButton);

		// Bouton Settings
		JMenu mntmNewMenuItem_1 = new JMenu("Settings");
		JMenuItem mPseudo = new JMenuItem("Change Pseudo");
		mPseudo.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		mPseudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Settings(getApp());
			}
		});

		JMenuItem clearConv = new JMenuItem("Clear conversation");
		clearConv.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		clearConv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DeleteConv(getApp());
			}
		});

		mntmNewMenuItem_1.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		mntmNewMenuItem_1.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mntmNewMenuItem_1);
		mntmNewMenuItem_1.add(mPseudo);
		mntmNewMenuItem_1.add(clearConv);

		// Boutton Deconnexion
		JButton btnDeconnexion = new JButton("Disconnect");
		btnDeconnexion.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		btnDeconnexion.setBackground(new Color(153, 153, 153));
		menuBar.add(btnDeconnexion);
		btnDeconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Disconnect(getApp());
			}
		});

		panel = new JPanel();
		panel.setBounds(151, 137, 820, 472);
		panel.setBackground(new Color(211, 211, 211));
		panel.setLayout(null);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(990, 88, 307, 552);// 690
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBackground(new Color(95, 158, 160));
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(41, 45, 224, (panel.getHeight() - 5));
		panel_1.add(scrollPane_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), new Color(95, 158, 160)));
		panel_2.setBackground(new Color(0, 128, 128));
		// panel_2.setBounds(1000, 66, (panel_1.getWidth()-40), (panel_1.getHeight()-40)
		// );//690
		scrollPane_1.setViewportView(panel_2);

		JLabel lblcontacts = new JLabel("CONTACTS\r\n");
		lblcontacts.setBackground(new Color(192, 192, 192));
		lblcontacts.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		lblcontacts.setBounds(87, 11, 133, 23);
		panel_1.add(lblcontacts);

		ImageIcon profil_pic = new ImageIcon();
		profil_pic = createImageIcon("/images/profil_picture.png");
		frame.getContentPane().add(panel_1);

		// TextField pour rédiger son message
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setFont(new Font("Bahnschrift", Font.PLAIN, 11));
		textField.setBounds(80, 407, 453, 33);
		textField.setColumns(10);

		// Bouton Send
		btnSend = new JButton("send");
		btnSend.setBackground(SystemColor.activeCaption);
		btnSend.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		btnSend.setBounds(558, 403, 76, 38);

		btnSend.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (getUsertalking()==null) {
					// nothing to do
				}
				else {
					String msg = textField.getText();
					TCPChat chat = new TCPChat(getApp(), usertalking);
					chat.SendMessage(msg);
					textField.setText("");
					loadconvo(usertalking);
				}
			}
		});

		textField.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (getUsertalking()==null){
					// nothing to do
				}
				else {
					String msg = textField.getText();
					TCPChat chat = new TCPChat(getApp(), usertalking);
					chat.SendMessage(msg);
					textField.setText("");
					loadconvo(usertalking);
				}
			}
	
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 96, 664, 296);
		panel.add(scrollPane);

		textArea = new JEditorPane();
		scrollPane.setViewportView(textArea);
		textArea.setBackground(SystemColor.controlHighlight);
		textArea.setEditable(false);

		JLabel lblTalkingwith = new JLabel("Talking with");
		lblTalkingwith.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		lblTalkingwith.setBounds(60, 57, 126, 31);
		setTalkingto(new JTextArea());
		getTalkingto().setBackground(new Color(211, 211, 211));
		getTalkingto().setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 18));
		getTalkingto().setBounds(174, 60, 126, 25);
		frame.getContentPane().setLayout(null);

		
		//Panneau des notifications (d'arrivées et de départs des contacts + des messages recus)
		JScrollPane scrollnotif = new JScrollPane();
		scrollnotif.setBounds(336, 0, 378, 49);
		panel.add(scrollnotif);
		
		notifPane = new JEditorPane();
		scrollnotif.setViewportView(notifPane);
		notifPane.setBackground(SystemColor.controlHighlight);
		notifPane.setEditable(false);
		
		panel.add(getTalkingto());
		panel.add(lblTalkingwith);

		panel.add(btnSend);
		panel.add(textField);

		/*
		 * ImageIcon profil_pic = new ImageIcon(); profil_pic =
		 * createImageIcon("/images/profil_picture.png");
		 */

		txtrB = new JTextArea();
		txtrB.setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 20));
		txtrB.setBackground(new Color(211, 211, 211));
		txtrB.setBounds(97, 35, 126, 25);
		txtrB.setText(app.getMe().getPseudo());
		panel.add(txtrB);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(45, 28, 48, 28);
		lblNewLabel.setIcon(new ImageIcon(profil_pic.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
		panel.add(lblNewLabel);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(panel_1);

		// panel.add(scrolltextArea);
		// panel.add(textArea);

		usersconnected = new JList<String>(getApp().getFriends().getListPseudo());
		usersconnected.setBounds(0, 646, 272, -599);
		usersconnected.setBackground(new Color(95, 158, 160));
		usersconnected.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
		// usersconnected.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		usersconnected.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				if (evt.getValueIsAdjusting()) {
					int userselect = usersconnected.getSelectedIndex();
					if (userselect != -1) {
						String usertalk = usersconnected.getSelectedValue();
						loadconvo(getApp().getFriends().getUserfromPseudo(usertalk));
						getTalkingto().append("");
						usertalking=getApp().getFriends().getUserfromPseudo(usersconnected.getSelectedValue());
						Chats(usertalking);
					}
				}

			}
		});

		panel_2.add(usersconnected);

		frame.getContentPane().add(panel_1);
		frame.setVisible(true);
		
		//On lance un runner udp et tcp pour les connexion et déconnexion et messages
		tcpListen = new TCPRunner(getApp());
		tcpListen.start();
		miseAJourContact();

	}

	/**
     * Récupération d'une image par son path 
     * @param path chemin d'accès à l'image
     * @return ImageIcon correspondant au chemin , ou null si path invalide
     */	
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imageURL = Home.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return new ImageIcon(imageURL);
		}
	}

	/**
	 * Redimensionner une image
	 * @param icon image à modifier
	 * @param w largeur voulue 
	 * @param h hauteur voulue
	 * @return image redimensionnée 
	 */
	public static ImageIcon scaleImage(ImageIcon icon, int w, int h) {
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();

		if (icon.getIconWidth() > w) {
			nw = w;
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
		}

		if (nh > h) {
			nh = h;
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
		}

		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
	}

	/**
	 * Ouverture d'une conversation (lancé lorsqu'on appuie sur le pseudo d'un des users connectés)
	 * @param u2 user en clavardage
	 */
	public void Chats(User u2) {
		//System.out.println("talking to" + u2.getPseudo());
		getTalkingto().setText(u2.getPseudo()); // pour afficher à qui on parle
	}
	
	/**
	 * Remets à vide la zone de lecture des messages
	 */
	public static void clearMessagesArea() {
		textArea.setText("");

	}
	
	/**
	 * Charge une conversation (historique) sur la zone de lecture 
	 * @param u2 user en clavardage
	 */
	public static void loadconvo(User u2) {
		ArrayList<Message> history = getApp().getDb().recupHistory(u2.getIP());
		String messages = "";
		for (Message msg : history) {

			if (msg.getSender().equals(getApp().getMe())) {
				messages += "me: "+ msg.getData() + "  " + msg.getTime() + "  \n";
			}
			else {
				messages += "                                                                      "+u2.getPseudo()+":" + msg.getData() + "  " + msg.getTime()+ "  \n";

			}

		}

		//on affiche et on scrolle jusqu'en bas
		textArea.setText(messages);
		textArea.setCaretPosition(textArea.getDocument().getLength());

	}

	/**
	 * Mise à jour de la liste de contacts associée après les différentes connexions,
	 *  déconnexions ou changement de pseudo (on récupère auprès du serveur la liste des personnes
	 *  connectés toutes les 4s)
	 */
	public void miseAJourContact() {
        long delay = 4000; 
        timer = new Timer();
        timer.scheduleAtFixedRate(new FreshList(), 0, delay);
	}

	/**
	 * Affichage de notre pseudo
	 */
	public static void pseudoModif() {
		txtrB.setText(app.getMe().getPseudo());
	}

	/**
	 * Affichage des messages entrants si la zone de lecture correspond à cette conversation
	 * @param friend pseudo de l'user qui nous envoie un message
	 */
	public static void display(String friend) {
		if (getTalkingto().getText().equals(friend)) {
			loadconvo(usertalking);
		}
	}

	/**
	 * Affichage des notifications d'arrivées des messages entrants 
	 * @param todisplay phrase à notifier à l'user
	 * @param IPfrom adresse ip de l'expéditeur du message
	 */
	public static void displayNotification(String todisplay, String IPfrom) {
		String notifs="";
		notifs+=notifPane.getText();
		notifs+=getApp().getFriends().getPseudofromIP(IPfrom)+todisplay+"\n";
		
		//on affiche et on scrolle jusqu'en bas
		notifPane.setText(notifs);
		notifPane.setCaretPosition(notifPane.getDocument().getLength());
		

	}
	
	/**
	 * Affichage des notifications des connexions, déconnexions et changement de pseudos des users
	 * @param pseudo pseudo de l'user se connectant/se déconnectant
	 * @param todisplay phrase à notifier à l'user
	 */
	public static void displayNotifUsers(String pseudo, String todisplay ) {
		String notifs="";
		notifs+=notifPane.getText();
		notifs+=pseudo + todisplay;
		
		//on affiche et on scrolle jusqu'en bas
		notifPane.setText(notifs);
		notifPane.setCaretPosition(notifPane.getDocument().getLength());
		
	}

	/**
	 * Window Listener permettant la déconnexion de l'user et l'arrêt des sockets d'écoute
	 *
	 */
	public class windowClosingListener implements WindowListener {

		public void windowClosing(WindowEvent e) {
			TCPRunner.setOuvert(false);
			app.Deconnexion();
		}

		public void windowOpened(WindowEvent arg0) {
		}

		public void windowClosed(WindowEvent arg0) {
		}

		public void windowIconified(WindowEvent arg0) {
		}

		public void windowDeiconified(WindowEvent arg0) {
		}

		public void windowActivated(WindowEvent arg0) {
		}

		public void windowDeactivated(WindowEvent arg0) {
		}

	}
	
	/**
	 * Fermeture de la page Home
	 */
	public static void dispose() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.dispose();
	}

	
	//-------------------- GETTEURS & SETTEURS -----------------------------//
	
	public static User getUsertalking() {
		return usertalking;
	}

	public static JTextArea getTalkingto() {
		return talkingto;
	}

	public void setTalkingto(JTextArea talkingto) {
		Home.talkingto = talkingto;
	}
	
	public static Application getApp() {
		return app;
	}

	public static void setApp(Application app) {
		Home.app = app;
	}
	
	//---------------- class timer ------------------------//
	public class FreshList extends TimerTask {

	   @Override
	   public void run() {
		   getApp().setFriends();
			usersconnected.setListData(getApp().getFriends().getListPseudo());
	    }
	}
}
