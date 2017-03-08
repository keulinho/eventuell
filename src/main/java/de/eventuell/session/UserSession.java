package de.eventuell.session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import de.eventuell.models.User;

@ManagedBean
@SessionScoped
public class UserSession {
	
	private User user;
	
	public UserSession() {
		user = new User();
	}
	
	// Getter und Setter
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void removeUser(){
		this.user = null;
	}

}