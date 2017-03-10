package de.eventuell.services;

import java.util.LinkedList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;

@ApplicationScoped
@ManagedBean(name="userService")
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
	}

	public User register(String firstName, String lastName, String password, String mail, boolean isManager) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(mail);
		user.setManager(isManager);
		user.setPassword(password);
		user.setUserID(counter++);
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
