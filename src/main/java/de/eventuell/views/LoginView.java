package de.eventuell.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.User;
import de.eventuell.services.UserServiceMock;
import de.eventuell.services.interfaces.IUserService;
import de.eventuell.session.UserSession;

@ManagedBean
@RequestScoped
public class LoginView {
	
	@ManagedProperty(value = "#{userSession}")
	private UserSession userSession;
	
	private IUserService userService;
	private String eMail;
	private String password;
	
	public LoginView () {
		userService = new UserServiceMock();
	}

	public String login() {
		User user;
		try {
			user = userService.login(this.eMail, this.password);
		} catch (LoginFailedException e) {
			return "login.jsf";
		}
		userSession.setUser(user);
		return "index.html";
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
	
}