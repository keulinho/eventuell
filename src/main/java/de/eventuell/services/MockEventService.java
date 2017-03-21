package de.eventuell.services;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.models.EventBuilder;
import de.eventuell.models.EventStatus;
import de.eventuell.models.User;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.services.interfaces.IUserService;

@Named
@ApplicationScoped
public class MockEventService implements IEventService {

	private List<Event> allEvents;

	public MockEventService() throws LoginFailedException {
		allEvents = new LinkedList<Event>();
		EventBuilder e = new EventBuilder();
		IUserService us = new UserServiceMock();
		
		Event event = e.setCity("Münster")
				.setDescription("Hammer Konzert")
				.setLocation("Halle Münsterland")
				.setMaxTickets(2000)
				.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0))
				.setStatus(EventStatus.PUBLISHED)
				.setTitle("Die Kassierer Konzert Münster")
				.setCreator(us.login("admin@admin.gws","admin"))
				.setPrice(14560.50)
				.build();
		
		Booking b = new Booking();
		b.setAmount(10);
		b.setBookingCode(1);
		b.setEvent(event);
		b.setUser(us.login("admin@admin.gws","admin"));
		List<Booking> bs = new LinkedList<Booking>();
		bs.add(b);
		event.setBookings(bs);
		allEvents.add(event);

		
		Event e2 = e.setCity("Münster")
				.setDescription("Hammer Konzert")
				.setLocation("Halle Münsterland")
				.setMaxTickets(2000)
				.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0))
				.setStatus(EventStatus.PUBLISHED)
				.setTitle("Test")
				.setCreator(us.login("admin@admin.gws","admin"))
				.setPrice(14560.50)
				.build();
		allEvents.add(e2);
		
		
		Event e3 = e.setCity("Test")
				.setDescription("Test Hammer Konzert")
				.setLocation("Test")
				.setMaxTickets(2000)
				.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0))
				.setStatus(EventStatus.CREATED)
				.setTitle("Test")
				.setCreator(us.login("admin@admin.gws","admin"))
				.setPrice(145.55)
				.build();
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
