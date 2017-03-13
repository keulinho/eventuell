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

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.models.EventStatus;
import de.eventuell.models.User;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.services.interfaces.IUserService;

@ManagedBean(name = "mockEventService")
@ApplicationScoped
public class MockEventService implements IEventService{
	
	private List<Event> allEvents;
	
	
	public MockEventService() throws LoginFailedException {
		allEvents = new LinkedList<Event>();
		Event e = new Event();
		e.setEventID(1);
		e.setCity("M�nster");
		e.setDescription("Hammer Konzert");
		e.setLocation("Halle M�nsterland");
		e.setMaxTickets(2000);
		e.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0));
		e.setStatus(EventStatus.PUBLISHED);
		e.setTitle("Die Kassierer Konzert M�nster");
		IUserService us = new UserServiceMock();
		e.setCreator(us.login("admin@admin.gws","admin"));
		Booking b = new Booking();
		b.setAmount(10);
		b.setBookingCode(1);
		b.setEvent(e);
		b.setUser(us.login("admin@admin.gws","admin"));
		List<Booking> bs = new LinkedList<Booking>();
		bs.add(b);
		e.setBookings(bs);
		e.setTitle("Die Kassierer Konzert M�nster");
		allEvents.add(e);
		Event e2 = new Event();
		e2.setEventID(2);
		e2.setCity("M�nster");
		e2.setDescription("Hammer Konzert");
		e2.setLocation("Halle M�nsterland");
		e2.setMaxTickets(2000);
		e2.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0));
		e2.setStatus(EventStatus.PUBLISHED);
		e2.setTitle("Test");
		e2.setCreator(us.login("admin@admin.gws","admin"));
		allEvents.add(e2);
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
		List<Event> events = getAllActualEvents();
		return events.stream().filter(e -> (e.getTitle()+e.getDescription()+e.getCity()+e.getLocation()).toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());
	}



	@Override
	public List<Event> getAllActualEventsByActiveManager(User u) {
		if (u.getManager()) {
			List<Event> events = getAllActualEvents();
			return events.stream().filter(e -> e.getCreator().getUserID() == u.getUserID())
					.collect(Collectors.toList());
		} else {
			return null;
		}
	}

	@Override
	public void addEvent(Event e) {
		double i = Math.random()*100000000;
		System.out.println((int)i);
		e.setEventID((int)i);
		allEvents.add(e);
	}
}
