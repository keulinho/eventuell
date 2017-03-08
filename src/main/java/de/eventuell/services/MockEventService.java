package de.eventuell.services;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;

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
	}
	


	public List<Event> getAllActualEvents() {
		
		return allEvents.stream().filter(e -> e.getStatus()==EventStatus.PUBLISHED && e.isAgo()==false).collect(Collectors.toList());
	}

	@Override
	public Event getEventByID(int eventID) {		
		Event currentEvent = null;
		for (Event event : allEvents) {
			if (event.getEventID() == eventID) {
				currentEvent = event;
			}
		}
		return currentEvent;
	}

	public List<Event> searchAllActualEvents(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}
}
