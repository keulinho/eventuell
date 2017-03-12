package de.eventuell.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;
import de.eventuell.session.UserSession;

@ManagedBean
@RequestScoped
public class RegistrationView {
	
	@ManagedProperty(value = "#{userSession}")
	private UserSession userSession;
	
	@ManagedProperty(value = "#{userService}")
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
		if (passwd1.length() < 6 || firstName.isEmpty() || name.isEmpty() || eMail.isEmpty() || !isValidEmailAddress(eMail) || !checkPasswdEquality()) {
			return "register.jsf";
		} else {
			User user = userService.register(firstName, name, passwd1, eMail, manager);
			userSession.setUser(user);
			return "index.jsf?faces-redirect=true";
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
