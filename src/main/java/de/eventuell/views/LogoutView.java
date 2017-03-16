package de.eventuell.views;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class LogoutView {
	
	@Inject
	private UserSession userSession;
	
	public LogoutView() {
		
	}
	
	public String logout() {
		userSession.clearSession();
		return "login.jsf?faces-redirect=true";
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
	
}
