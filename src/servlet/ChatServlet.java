package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import clavardage.Contacts;
import clavardage.User;
/**
 * Servlet implementation class 
 */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private Contacts usersconnected; //personnes connectés sur le server
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ChatServlet() {
    	super();
    	usersconnected= new Contacts();
    }
    
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		PrintWriter pw=response.getWriter();
		response.setContentType("text/html");

		String mode=request.getParameter("action");
		String oldpseudo,pseudo,ip;
		
		switch (mode) {
		case "connexion" :
			pseudo = request.getParameter("pseudo");
			ip= request.getParameter("ip");
			User user = new User(ip, 1234, pseudo);
			boolean loginOK= (!usersconnected.appartient(pseudo));
			response.setHeader("disponible", Boolean.toString(loginOK));
			if (loginOK) {
				usersconnected.addContact(user);
			}
			
			break;
			
		case "changepseudo":
			oldpseudo= request.getParameter("oldpseudo");
			pseudo = request.getParameter("pseudo");
			boolean disponible= (!usersconnected.appartient(pseudo));
			response.setHeader("disponible", Boolean.toString(disponible));
			if (disponible) {
				usersconnected.getUserfromPseudo(oldpseudo).setPseudo(pseudo);
				System.out.println("disponible" + pseudo);
			}
			
			break;
			
		case "deconnexion":
			pseudo = request.getParameter("pseudo");
			User c = usersconnected.getUserfromPseudo(pseudo);
			usersconnected.deleteContact(c);;
			System.out.println("deconnected ");
			
			break;
		
		case "getcontacts":
			response.setHeader("contacts", usersconnected.getList());
			break;

		}
		pw.println("Welcome to The ChitChat");
		pw.close();

	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


    
	//subscribe()to the server whenever they join
	//publish() their status
	//notify() in case of any changes
}