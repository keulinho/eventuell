package de.eventuell.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import de.eventuell.session.UserSession;

@ManagedBean
@RequestScoped
public class LogoutView {
	
	@ManagedProperty(value = "#{userSession}")
	private UserSession userSession;
	
	public LogoutView() {
		
	}
	
	public String logout() {
		userSession.removeUser();
		return "login.jsf";
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
	
}
