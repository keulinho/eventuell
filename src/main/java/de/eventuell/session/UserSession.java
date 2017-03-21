package de.eventuell.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.eventuell.models.User;

@Named
@SessionScoped
public class UserSession  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5647571219555890780L;
	private User user;
	
	public UserSession() {
	}
	
	// Getter und Setter
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void clearSession() {
		this.removeUser();
	}
	
	private void removeUser(){
		this.user = null;
	}

}
