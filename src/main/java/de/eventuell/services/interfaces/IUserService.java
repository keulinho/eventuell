package de.eventuell.services.interfaces;

import de.eventuell.models.User;

public interface IUserService {
	
	public User register(String firstName, String lastName, String password, String mail, boolean isAdmin);

	public User getUserByMail(String value);
	
	public boolean isMailInUse(String value);
	
}
