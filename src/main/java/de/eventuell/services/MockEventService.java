package de.eventuell.services;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
public class MockEventService implements IEventService {

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
		e.setPrice(14560.50);
		Booking b = new Booking();
		b.setAmount(10);
		b.setBookingCode(1);
		b.setEvent(e);
		b.setUser(us.login("admin@admin.gws","admin"));
		List<Booking> bs = new LinkedList<Booking>();
		bs.add(b);
		e.setBookings(bs);
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
		e2.setPrice(145.55);
		e2.setCreator(us.login("admin@admin.gws","admin"));
		allEvents.add(e2);
		
		Event e3 = new Event();
		e3.setEventID(3);
		e3.setCity("Test");
		e3.setDescription("Test Hammer Konzert");
		e3.setLocation("Test");
		e3.setMaxTickets(2000);
		e3.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0));
		e3.setStatus(EventStatus.CREATED);
		e3.setTitle("Test");
		e3.setPrice(145.55);
		e3.setCreator(us.login("admin@admin.gws","admin"));
		allEvents.add(e3);
	}

	public List<Event> getAllActualEvents() {

		return allEvents.stream().filter(e -> e.getStatus() == EventStatus.PUBLISHED && e.isAgo() == false)
				.collect(Collectors.toList());
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
		return events.stream().filter(e -> (e.getTitle() + e.getDescription() + e.getCity() + e.getLocation())
				.toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());
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
		e.setEventID((int)i);
		allEvents.add(e);
	}

	@Override
	public List<Event> getAllNotPublishedEventsByManager(User user) {
		if (user.getManager()) {
			return allEvents.stream().filter(e -> e.getCreator().getUserID() == user.getUserID() && e.getStatus()==EventStatus.CREATED)
					.collect(Collectors.toList());
		} else {
			return null;
		}
	}

	@Override
	public void changeEvent(Event e) {
		Optional<Event> oldEvent = allEvents.stream().filter(ev -> ev.getEventID()==e.getEventID()).findFirst();
		if (oldEvent.isPresent()) {
			allEvents.remove(oldEvent.get());
			allEvents.add(e);
		}
	}

	@Override
	public void deleteEventByID(int eventID) {
		Optional<Event> oldEvent = allEvents.stream().filter(ev -> ev.getEventID()==eventID).findFirst();
		if (oldEvent.isPresent()) {
			allEvents.remove(oldEvent.get());
		}
	}

}
