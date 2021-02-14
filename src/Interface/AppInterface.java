package Interface;

import clavardage.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/**
 * Classe AppInterface correspondant à la première fenêtre de Connexion du système
 * 
 * app : instance de la classe Application associée
 * frame : panel de contenu principal de la frame
 * panel : panel de contenu principal de la frame
 * textfield : zone de texte pour pouvoir rentrer le pseudo choisi par l'user
 * 
 */

public class AppInterface {
	
	private Application app;
	private JFrame frame;
	private JPanel panel;
	private JTextField textField;

	/**
	 * Constructeur de la page AppInterface
	 * Création de l'application et du user associé 
	 *
	 */
	public AppInterface() {
		app=new Application(new User());
		app.setDb(new Database(app));
		initialize();
	}

	
	/**
	 * Lancement de la première fenêtre de connexion de l'application
	 *
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppInterface window = new AppInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Initialisation des composants de la frame
	 */
	private void initialize() {
		frame = new BackgroundJFrame("Connexion");
		frame.setForeground(new Color(26, 104, 104));
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBackground(new Color(26, 104, 104));
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 885, 579);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
    	//centrer la fenetre au milieu de l'ecran
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2 - frame.getWidth()/2, dim.height/2 - frame.getHeight()/2);
		
        panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBackground(new Color(102, 153, 153));
		panel.setBounds(260, 62, 365, 401);
		frame.getContentPane().add(panel);
		
		
    	ImageIcon fond_conn = new ImageIcon();
    	fond_conn = BackgroundJFrame.createImageIcon("/images/LOGONetwork.png");
		JLabel label = new JLabel(fond_conn);
		label.setBounds(78, 7, 213, 213);
		
		textField = new JTextField("Enter pseudonym");
		textField.setBounds(53, 252, 270, 37);
		textField.setBackground(new Color(204, 204, 204));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Bahnschrift", Font.PLAIN, 11));
		textField.setColumns(10);
		
		textField.getFont().deriveFont(Font.ITALIC);
		textField.setForeground(Color.gray);
		textField.addMouseListener(new MouseListener() {           
			@Override
			public void mouseReleased(MouseEvent e) {}         
			@Override
			public void mousePressed(MouseEvent e) {}          
			@Override
			public void mouseExited(MouseEvent e) {}           
			@Override
			public void mouseEntered(MouseEvent e) {}          
			@Override
			public void mouseClicked(MouseEvent e) {
				JTextField texteField = ((JTextField)e.getSource());
				texteField.setText("");
				texteField.getFont().deriveFont(Font.PLAIN);
				texteField.setForeground(Color.black);
				texteField.removeMouseListener(this);
			}
		});
		
		//pour qu'on puisse rentrer en apuyant sur la touche entree aussi 
		textField.addActionListener(new Connect());
		
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.setBounds(103, 307, 169, 61);
		btnNewButton.setBackground(new Color(204, 204, 204));
		btnNewButton.setFont(new Font("Bahnschrift", Font.PLAIN, 17));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.addActionListener(new Connect());
		
		panel.setLayout(null);
		panel.add(label);
		panel.add(textField);
		panel.add(btnNewButton);
		
	}
	
	
	/**
	 * Action Listener sur le bouton et la touche entrée pour tester
	 *   l'unicité du pseudo et permettre la connexion de 'luser
	 *
	 */
	public class Connect implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String pseudo=textField.getText();
			
			if(pseudo.length()>12) {
				JTextPane txtlongpseudo = new JTextPane();
				txtlongpseudo.setBackground(new Color(102, 153, 153));
				txtlongpseudo.setText("Only 12 caracters are allowed");
				txtlongpseudo.setForeground(new Color(255, 51, 51));
				txtlongpseudo.setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 11));
				txtlongpseudo.setBounds(88, 231, 203, 20);
				panel.add(txtlongpseudo);
				textField.addMouseListener(new MouseListener() {           
					@Override
					public void mouseReleased(MouseEvent e) {}         
					@Override
					public void mousePressed(MouseEvent e) {}          
					@Override
					public void mouseExited(MouseEvent e) {}           
					@Override
					public void mouseEntered(MouseEvent e) {}          
					@Override
					public void mouseClicked(MouseEvent e) {
						JTextField texteField = ((JTextField)e.getSource());
						texteField.setText("");
						texteField.getFont().deriveFont(Font.PLAIN);
						texteField.setForeground(Color.black);
						texteField.removeMouseListener(this);
					}
				});
			}
			else {
				//connexion
				if (app.Connexion(pseudo)) {
					app.getMe().setPseudo(pseudo);
					openHome();
				} else {
					JTextPane txtpnPseudonymAlreadyIn = new JTextPane();
					txtpnPseudonymAlreadyIn.setBackground(new Color(102, 153, 153));
					txtpnPseudonymAlreadyIn.setText("Pseudonym already in use. Try Again.");
					txtpnPseudonymAlreadyIn.setForeground(new Color(255, 51, 51));
					txtpnPseudonymAlreadyIn.setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 11));
					txtpnPseudonymAlreadyIn.setBounds(88, 231, 203, 20);
					panel.add(txtpnPseudonymAlreadyIn);
					textField.addMouseListener(new MouseListener() {           
						@Override
						public void mouseReleased(MouseEvent e) {}         
						@Override
						public void mousePressed(MouseEvent e) {}          
						@Override
						public void mouseExited(MouseEvent e) {}           
						@Override
						public void mouseEntered(MouseEvent e) {}          
						@Override
						public void mouseClicked(MouseEvent e) {
							JTextField texteField = ((JTextField)e.getSource());
							texteField.setText("");
							texteField.getFont().deriveFont(Font.PLAIN);
							texteField.setForeground(Color.black);
							texteField.removeMouseListener(this);
						}
					});
				}
    		
			}
		}
	}
	

    /**
     * Méthode permettant l'ouverture de la fenêtre principale Home
     *  une fois le pseudo vérifié et la connexion réussie 
     *  
     */
    private void openHome () {
    	frame.setVisible(false);
		new Home(app);
		frame.dispose();
    }

    

    
}
