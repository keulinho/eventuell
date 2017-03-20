package de.eventuell.views;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class LoginView {
	
	@Inject
	private UserSession userSession;
	
	@Inject
	private IUserService userService;
	private String eMail;
	private String password;
	
	public LoginView () {
		// userService = new UserServiceMock();
	}

	public String login() {
		User user;
		try {
			user = userService.login(this.eMail, this.password);
		} catch (LoginFailedException e) {
			System.out.println("----> Login Failed, but where is the message?");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail und Paswort sind nicht gültig!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "login.jsf";
		}
		userSession.setUser(user);
		return "index.jsf?faces-redirect=true";
	}
	
	// Getter und Setter
	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
}