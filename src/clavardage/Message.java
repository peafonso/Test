package clavardage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe message pour toutes les communications tcp en clavardage
 * sender : user qui envoie le message
 * receiver : user qui reçoit le message
 * data : le message envoyé
 * time : horodatage (en string)
 * 
 */

public class Message implements Serializable {
	private User sender;
	private User receiver;
	private String data;
	private String time; 
	
	//-------------------- ENSEMBLE DE CONSTRUCTEURS -----------------------------//

	public Message() {
	}
	
	public Message(User from, User to, String msg) {
		this.setSender(from);
		this.setReceiver(to);
		this.setData(msg);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		this.setTime(dateFormat.format(new Date()));
	}
	
	public Message(User from, User to, String msg, String date) {
		this.setSender(from);
		this.setReceiver(to);
		this.setData(msg);
		this.setTimeString(date);
	}
	
	public Message(String msg) {
		this.setData(msg);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		this.setTime(dateFormat.format(new Date()));

	}

	/**
	 * Retranscrition des informations du message
	 * @return infos du message sous une chaine de caractères
	 */
	@Override
	public String toString() {
		String smsg= "Sender: "+this.getSender()+"\n"+"Receiver:  "+this.getReceiver()+"\n"
	+"Time:  "+ this.getTime()+"\n"+ "Data:  "+this.getData()+"\n";
		return smsg;	
	}
	
	/**
	 * Retranscription d'un message d'après ces informations
	 * @param smsg infos du message sous une chaine de caractères
	 * @return
	 */
	public static Message toMessage(String smsg) {
		String[] paramsg=smsg.split("\n");
		User sender= User.toUser(paramsg[0].split(":")[1]);
		User receiver= User.toUser(paramsg[1].split(":")[1]);
		String[] fulldate=paramsg[2].split(":");
		String date= (fulldate[1]+":"+fulldate[2]);
 		//typemsg type=toTypemsg(paramsg[3].split(":")[1]);
		String [] tabdata=paramsg[3].split(":");
		String data="";
		for (int i=1;i<tabdata.length;i++) {
			data+=tabdata[i];
		}
		return new Message(sender,receiver,data,date);
	
	}
	
	
	//-------------------- GETTEURS & SETTEURS -----------------------------//

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getTime() {
		return time;
	}
	
	public String getTimeString() {
		return time.toString();
	}

	public void setTime(String string) {
		this.time = string;
	}
	
	public void setTimeString (String date) {
		this.time = date;
	}



}
