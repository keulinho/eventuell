package de.eventuell.services.interfaces;

import java.util.List;

import de.eventuell.models.Event;
import de.eventuell.models.User;

public interface IEventService {
	
	List<Event> getAllActualEvents();
	Event getEventByID(int eventID);
	List<Event> searchAllActualEvents(String searchString);
	List<Event> getAllActualEventsByActiveManager(User u);
	void addEvent(Event e);
}
