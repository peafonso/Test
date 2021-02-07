package Interface;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clavardage.*;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.SystemColor;

/**
 * Classe Settings héritant de JFrame
 * représentant la page de changement de pseudo de l'user
 * 
 * app : instance de la classe Application associée
 * contentPane : panel de contenu principal de la frame
 * textField : zone de texte pour pouvoir rentrer le nouveau pseudo
 * 
 */

public class Settings extends JFrame {


	private static final long serialVersionUID = 1L;
	private Application app;
	private JPanel contentPane;
	private JTextField textField;
	
	/**
	 * Constructeur de la page Settings 
	 * @param app Application associée
	 */
	public Settings(Application app) {
		this.app=app;
		initialize();
	}
	
	/**
	 * Initialisation des composants de la frame
	 */
	public void initialize() {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 421, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label New Pseudo
		JLabel lblNewLabel = new JLabel("Your new pseudo\r\n");
		lblNewLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblNewLabel.setBounds(118, 47, 266, 43);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(35, 108, 214, 36);
		contentPane.add(textField);
		textField.setColumns(10);
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
		textField.addActionListener(new Connect());

		JButton btnNewButton = new JButton("okay");
		btnNewButton.setFont(new Font("Bahnschrift", Font.PLAIN, 13));
		btnNewButton.setBounds(277, 116, 89, 23);
		btnNewButton.addActionListener(new Connect());
		contentPane.add(btnNewButton);
		setVisible(true);
	}
	
	/**
	 * Action Listener sur le bouton et la touche entrée pour tester
	 *   l'unicité du pseudo renseignée sur le textField 
	 *
	 */
	public class Connect implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String pseudo=textField.getText();
			
			if(pseudo.length()>12) {
				JTextPane txtlongpseudo = new JTextPane();
				txtlongpseudo.setText("Only 12 caracters are allowed");
				txtlongpseudo.setForeground(new Color(255, 51, 51));
				txtlongpseudo.setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 11));
				txtlongpseudo.setBackground(SystemColor.menu);
				txtlongpseudo.setBounds(103, 80, 204, 14);
				contentPane.add(txtlongpseudo);
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
				if (app.ChangePseudo(pseudo)) {
					app.getMe().setPseudo(pseudo);
					Home.pseudoModif();

					dispose(); //ferme la fenetre
				} else {
					JTextPane txtpnPseudonymAlreadyIn = new JTextPane();
					txtpnPseudonymAlreadyIn.setText("Pseudonym already in use. Try Again.");
					txtpnPseudonymAlreadyIn.setBackground(SystemColor.menu);
					txtpnPseudonymAlreadyIn.setForeground(new Color(255, 51, 51));
					txtpnPseudonymAlreadyIn.setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 11));
					txtpnPseudonymAlreadyIn.setBounds(103, 80, 204, 14);

					contentPane.add(txtpnPseudonymAlreadyIn);
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
}
