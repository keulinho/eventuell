package de.eventuell.services;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.eventuell.models.Event;
import de.eventuell.models.EventStatus;
import de.eventuell.services.interfaces.IEventService;

@ManagedBean
@ApplicationScoped
public class MockEventService implements IEventService{
	
	private List<Event> allEvents;
	
	
	public MockEventService() {
		allEvents = new LinkedList<Event>();
		Event e = new Event();
		e.setEventID(1);
		e.setCity("Münster");
		e.setDescription("Hammer Konzert");
		e.setLocation("Halle Münsterland");
		e.setMaxTickets(2000);
		e.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0));
		e.setStatus(EventStatus.PUBLISHED);
		e.setTitle("Die Kassierer Konzert Münster");
		allEvents.add(e);
		Event e2 = new Event();
		e2.setEventID(2);
		e2.setCity("Münster");
		e2.setDescription("Hammer Konzert");
		e2.setLocation("Halle Münsterland");
		e2.setMaxTickets(2000);
		e2.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0));
		e2.setStatus(EventStatus.PUBLISHED);
		e2.setTitle("Test");
		allEvents.add(e2);
	}
	


	public List<Event> getAllActualEvents() {
		
		return allEvents.stream().filter(e -> e.getStatus()==EventStatus.PUBLISHED && e.isAgo()==false).collect(Collectors.toList());
	}

	public Event getEventByID(int eventID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Event> searchAllActualEvents(String searchString) {
		List<Event> events = getAllActualEvents();
		return events.stream().filter(e -> (e.getTitle()+e.getDescription()+e.getCity()+e.getLocation()).toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());
	}
}
