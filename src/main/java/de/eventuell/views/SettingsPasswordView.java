package de.eventuell.views;

import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.models.User;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class SettingsPasswordView {
	
	@Inject
	UserSession session;
	
	private String oldPasswd;
	private String newPasswd1;
	private String newPasswd2;
	
	public SettingsPasswordView() {
		
	}
	
	public String changePassword() {
		System.out.println(" --> Passwort ändern: " + oldPasswd + " to " + newPasswd1 + " and " + newPasswd2);
		User user = session.getUser();
		if (!user.evaluateCredentials(oldPasswd)) {
			// altes passwort falsch
			System.out.println("alter Passwort war falsch");
		} else if (!newPasswd1.equals(newPasswd2)) {
			// neue Passwörter ungleich
			System.out.println("Die Passwörter waren nicht identisch");
		} else {
			// neues Passwort setzen
			System.out.println("Das Passwort wurde geändert");
			user.setPassword(newPasswd1);
		}
		return "";
	}

	// Getter und Setter
	public String getOldPasswd() {
		return oldPasswd;
	}

	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	public String getNewPasswd1() {
		return newPasswd1;
	}

	public void setNewPasswd1(String newPasswd1) {
		this.newPasswd1 = newPasswd1;
	}

	public String getNewPasswd2() {
		return newPasswd2;
	}

	public void setNewPasswd2(String newPasswd2) {
		this.newPasswd2 = newPasswd2;
	}
	
}
