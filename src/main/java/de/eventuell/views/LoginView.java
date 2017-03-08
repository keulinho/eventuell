package de.eventuell.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.services.UserServiceMock;
import de.eventuell.services.Interfaces.IUserService;

@ManagedBean
@RequestScoped
public class LoginView {
	
	private IUserService userService;
	private String eMail;
	private String password;
	
	public LoginView () {
		userService = new UserServiceMock();
	}

	public String login() {
		// login über den UserService
		try {
			userService.login(this.eMail, this.password);
		} catch (LoginFailedException e) {
			System.out.println(" --> Login Failed");
			return "login.jsf";
		}
		// wenn erfolgreich user durch das Userobjekt ersetzen
		// beim scheitern die E-Mail im anonymen user setzen damit die im Login angezeigt werden kann
		System.out.println("Login");
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

}