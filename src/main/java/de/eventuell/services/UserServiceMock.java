package de.eventuell.services;

import java.util.LinkedList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;

@Named
@ApplicationScoped
public class UserServiceMock implements IUserService {
// Dummy UserService bis wir in der Vorlesung weiter sind
	
	private LinkedList<User> users;
	private int counter;
	
	public UserServiceMock() {
		users = new LinkedList<User>();
		counter = 2;
		User admin = new User();
		admin.setFirstName("Sudo");
		admin.setLastName("Admin");
		admin.setEmail("admin@admin.gws");
		admin.setManager(true);
		admin.setPassword("admin");
		admin.setUserID(1);
		users.add(admin);
		User admin2 = new User();
		admin2.setFirstName("Sudo");
		admin2.setLastName("Admin");
		admin2.setEmail("admin@admin.de");
		admin2.setManager(true);
		admin2.setPassword("admin");
		admin2.setUserID(2);
		users.add(admin2);
		User u = new User();
		u.setFirstName("Sudo");
		u.setLastName("Admin");
		u.setEmail("test@test.de");
		u.setManager(false);
		u.setPassword("test");
		u.setUserID(3);
		users.add(u);
	}

	public User register(String firstName, String lastName, String password, String mail, boolean isManager) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(mail);
		user.setManager(isManager);
		user.setPassword(password);
		user.setUserID(counter++);
		users.add(user);
		return user;
	}

	public User login(String mail, String password) throws LoginFailedException {
		for (User user : users) {
			if (user.getEmail().equals(mail)) {
				if (user.getPassword().equals(password)) {
					return user;
				} else {
					throw new LoginFailedException();
				}
			}
		}
		throw new LoginFailedException();
	}

}
