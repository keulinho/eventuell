package de.eventuell.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;
import de.eventuell.exceptions.RegistrationFailedException;

@Named
@ApplicationScoped
public class UserService implements IUserService {

	@Inject
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public User register(String firstName, String lastName, String password, String mail, boolean isManager) throws RegistrationFailedException {
		if (this.isMailInUse(mail)) {
			throw new RegistrationFailedException();
		} else {
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(mail);
			user.setManager(isManager);
			user.setPassword(password);
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			return user;
		}
	}

	@Override
	public User getUserByMail(String mail) {
		List<User> users = (List<User>) em.createQuery("SELECT u FROM User u WHERE u.email=:mail")
				.setParameter("mail", mail).getResultList();
		for (User user : users) {
			return user;
		}
		return null;
	}

	@Override
	public boolean isMailInUse(String mail) {
		List<User> users = (List<User>) em.createQuery("SELECT u FROM User u WHERE u.email=:mail")  // eigentlich ne Exists-Query
				.setParameter("mail", mail).getResultList();
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

}
