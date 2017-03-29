package de.eventuell.views;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class SettingsMailView {
	
	@Inject
	UserSession session;
	
	@Inject
	IUserService userService;
	
	@Inject
	EntityManager em;
	
	private String newMail;
	
	public SettingsMailView() {
		
	}
	
	public String changeMail() {
		if (userService.isMailInUse(newMail)) {
			// Fehlermeldung
		} else {
			User user = session.getUser();
			user.setEmail(newMail); // hier kann nen Fehler auftreten, wenn Mail inzwischen doch genutzt wird
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
			// Erfolgsmeldung
		}
		return "";
	}

	// getter und setter
	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	public String getNewMail() {
		return newMail;
	}

	public void setNewMail(String newMail) {
		this.newMail = newMail;
	}
	
}
