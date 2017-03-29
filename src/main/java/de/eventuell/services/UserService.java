package de.eventuell.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;

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
	public User register(String firstName, String lastName, String password, String mail, boolean isManager) {
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

	@Override
	public User login(String mail, String password) throws LoginFailedException {
		List<User> users = (List<User>) em.createQuery("SELECT u FROM User u WHERE u.email=:mail")
				.setParameter("mail", mail).getResultList();
		for (User user : users) {
			if (user.getPassword().equals(password)) {
				return user;
			}
		}
		throw new LoginFailedException();
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
		List<User> users = (List<User>) em.createQuery("SELECT u FROM User u WHERE u.email=:mail")
				.setParameter("mail", mail).getResultList();
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

}
