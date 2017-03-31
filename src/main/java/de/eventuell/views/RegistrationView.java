package de.eventuell.views;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.exceptions.RegistrationFailedException;
import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class RegistrationView {
	
	@Inject
	private UserSession userSession;
	
	@Inject
	private IUserService userService;
	
	private String firstName;
	private String name;
	private String eMail;
	private boolean manager;
	private String passwd1;
	private String passwd2;
	
	public RegistrationView() {
		
	}
	
	public String register() {
		System.out.println("----- hier wird sich regisitriert");
		if (passwd1.length() < 6 || firstName.isEmpty() || name.isEmpty() || eMail.isEmpty() || !isValidEmailAddress(eMail) || !checkPasswdEquality()) {
			return "register.jsf";
		} else {
			System.out.println("Bullshit");
			try {
				User user = userService.register(firstName, name, passwd1, eMail, manager);
				userSession.setUser(user);
				System.out.println("---- regisiriert");
				return "index.jsf?faces-redirect=true";
			} catch (RegistrationFailedException e) {
				System.out.println("---- registriereiung fehlgeschlagen");
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail ist nicht mehr verfügbar!", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "register.jsf";
			}
		}
	}
	
	private boolean checkPasswdEquality() {
		if (passwd1.equals(passwd2))
			return true;
		else
			return false;
	}
	
	private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	// getter und setter
	public IUserService getUserService() {
		return userService;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public String getPasswd1() {
		return passwd1;
	}

	public void setPasswd1(String passwd1) {
		this.passwd1 = passwd1;
	}

	public String getPasswd2() {
		return passwd2;
	}

	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}
}
