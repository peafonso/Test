package Interface;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clavardage.*;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

/**
 * Classe DeleteConv héritant de JFrame représentant la page permettant
 *  la suppression d'historique de conversation (uniquement possible
 *  avec les users connectés)
 *  
 * app : instance de la classe Application associée
 * contentPane : panel de contenu principal de la frame
 * scrollPane : scroll du contentPane (pour scroller dans les users connectés)
 * usersconnected : liste des users connectés correspondant à l'user du système
 * btnNewButton : bouton de validation de suppression d'hisorique
 * usertalk : user correspondant à l'historique de conversation à supprimer
 * 
 */

public class DeleteConv extends JFrame {

	private static final long serialVersionUID = 1L;
	private Application app;
	private JPanel contentPane;
	private JScrollPane scrollPane ;
	private JList<String> usersconnected;
	private JButton btnNewButton;
	private String userTalk;

	/**
	 * Constructeur de la page DeleteConv 
	 * @param app Application associée
	 */
	public DeleteConv(Application app) {
		this.app=app;
		initialize();
	}
	
	/**
	 * Initialisation des composants de la frame
	 */
	public void initialize() {
		setTitle("Delete Conversations");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 421, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label New Pseudo
		JLabel lblNewLabel = new JLabel("Delete conversation with");
		lblNewLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblNewLabel.setBounds(88, 23, 228, 43);
		contentPane.add(lblNewLabel);
		JPanel panel = new JPanel();

		usersconnected= new JList<String>(app.getFriends().getListPseudo());
		//si historique vide
		if ((usersconnected).getModel().getSize() == 0) {
			JLabel lblNewLabel_1 = new JLabel("Your history of conversations has been cleared.");
			lblNewLabel_1.setForeground(new Color(95, 158, 160));
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
			lblNewLabel_1.setBounds(42, 202, 244, 23);
			panel.add(lblNewLabel_1);
		}else {	
			usersconnected.setBounds(65, 60, 260, 125);
			usersconnected.addListSelectionListener(new ListSelectionListener() {
			      public void valueChanged(ListSelectionEvent evt) {
			    	  if(evt.getValueIsAdjusting()) {
							 int userselect = usersconnected.getSelectedIndex();
							 if(userselect != -1) {
								 userTalk = usersconnected.getSelectedValue();

							 }
			    	  }
			      		
			        }
			      }
			);
			panel.add(usersconnected);
			//contentPane.add(usersconnected);
		}
		
		
		btnNewButton = new JButton("okay");
		btnNewButton.setFont(new Font("Bahnschrift", Font.PLAIN, 13));
		btnNewButton.setBounds(158, 210, 89, 23);	
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!(userTalk.isEmpty())) {
					User userToForget;
					userToForget=app.getFriends().getUserfromPseudo(userTalk);
					app.getDb().deleteConvo(userToForget.getIP());
					app.getDb().createTableConvo(userToForget.getIP());
			    	Home.getTalkingto().setText(userToForget.getPseudo()); //pour afficher à qui on parle
					Home.loadconvo(userToForget);
			    	//System.out.println("Delete conv with"+ userTalk);
					dispose(); //ferme la fenetre

				}else{
					if ((usersconnected).getModel().getSize() == 0) {
						dispose(); //ferme la fenetre
					}
				}
			}
		});
		contentPane.add(btnNewButton);
	
		scrollPane = new JScrollPane();
		scrollPane.setBounds(70, 64, 264, 127);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(panel);
				

		
		setVisible(true);	

	}
}
