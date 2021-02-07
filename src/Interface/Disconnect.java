package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clavardage.*;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe Disconnect héritant de JFrame
 * représentant la page de demande de déconnexion
 * 
 * app : instance de la classe Application associée
 * contentPane : panel de contenu principal de la frame
 * 
 */

public class Disconnect extends JFrame {

	private static final long serialVersionUID = 1L;
	private Application app;
	private JPanel contentPane;

	/**
	 * Constructeur de la page Disconnect 
	 * @param app Application associée
	 */
	public Disconnect(Application app) {
		this.app=app;
		initialize();
	}
	

	/**
	 * Initialisation des composants de la frame
	 */
	public void initialize() {
		setTitle("Disconnect");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 421, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("yes");
		btnNewButton.setFont(new Font("Bahnschrift", Font.PLAIN, 13));
		btnNewButton.setBounds(118, 107, 89, 23);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				app.Deconnexion();;
				dispose();
				Home.dispose();
				
			}
		});

		
		JLabel lblNewLabel = new JLabel("Are you certain you want to disconnect? ");
		lblNewLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		lblNewLabel.setBounds(30, 43, 377, 72);
		contentPane.add(lblNewLabel);
		
		JButton btnNo = new JButton("no");
		btnNo.setFont(new Font("Bahnschrift", Font.PLAIN, 13));
		btnNo.setBounds(217, 107, 89, 23);
		btnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		contentPane.add(btnNo);
		
		setVisible(true);
	}
}
