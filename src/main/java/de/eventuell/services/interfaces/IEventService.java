package de.eventuell.services.interfaces;

import java.util.List;

import de.eventuell.models.Event;

public interface IEventService {
	
	List<Event> getAllActualEvents();
	Event getEventByID(int eventID);
	List<Event> searchAllActualEvents(String searchString);
}
