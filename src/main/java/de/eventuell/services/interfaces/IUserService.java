package de.eventuell.services.interfaces;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.User;

public interface IUserService {
	
	public User register(String firstName, String lastName, String password, String mail, boolean isAdmin);
	
	public User login(String mail, String password) throws LoginFailedException;

	public User getUserByMail(String value);
	
	public boolean isMailInUse(String value);
	
}
