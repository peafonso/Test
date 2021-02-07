package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clavardage.Application;
import clavardage.Contacts;
import clavardage.User;
/**
 * Servlet implementation class Hello
 */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private Application app;
	private Contacts usersconnected;
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ChatServlet() {
    	super();
    	setApp(new Application(new User()));
    	usersconnected= getApp().getFriends();
    }
    
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		PrintWriter pw=response.getWriter();
		response.setContentType("text/html");

		String mode=request.getParameter("mode");
		String pseudo,port,ip;
		
		switch (mode) {
		case "test":
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out=response.getWriter();
			out.print("<html><body>");
			out.print("<h3>Hello holaa</h3>");
			out.print("</body></html>");
			System.out.println("test opé");

			break;
			
		case "connexion" :
			pseudo = request.getParameter("pseudo");
			ip = request.getParameter("ip");
			port = request.getParameter("port");
			//int intport = Integer.parseInt(port);
			User user = new User(ip, 1234, pseudo);
			usersconnected.addContact(user);
			System.out.println("connected"+user.toString());

			break;
			
		case "changepseudo":
			pseudo = request.getParameter("pseudo");
			boolean loginOK= usersconnected.appartient(pseudo);
			response.setHeader("result", Boolean.toString(loginOK));
			getApp().getMe().setPseudo(pseudo);
			System.out.println("pseudo changed");
			
			break;
			
		case "deconnexion":
			pseudo = request.getParameter("pseudo");
			User c = usersconnected.getUserfromPseudo(pseudo);
			usersconnected.deleteContact(c);;
			System.out.println("deconnected ");
			
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

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}
	

    
	//subscribe()to the server whenever they join
	//publish() their status
	//notify() in case of any changes
}
