package de.eventuell.services;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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
public class EventService implements IEventService {
	@Inject
	private EntityManager em;

	@PostConstruct
	public void generateTestData() {
		List<Event> eventList = (List<Event>) em.createQuery("SELECT e FROM Event e").getResultList();
		if (eventList.size()<1)
		{
			EventBuilder e = new EventBuilder();
			em.getTransaction().begin();
			User admin = new User();
			admin.setFirstName("Sudo");
			admin.setLastName("Admin");
			admin.setEmail("admin@admin.gws");
			admin.setManager(true);
			admin.setPassword("admin");
			admin.setUserID(0);
			em.persist(admin);
			User admin2 = new User();
			admin2.setFirstName("Sudo");
			admin2.setLastName("Admin");
			admin2.setEmail("admin@admin.de");
			admin2.setManager(true);
			admin2.setPassword("admin");
			admin2.setUserID(2);
			em.persist(admin2);
			User u = new User();
			u.setFirstName("Sudo");
			u.setLastName("Admin");
			u.setEmail("test@test.de");
			u.setManager(false);
			u.setPassword("test");
			u.setUserID(3);
			em.persist(u);
			Event event = e.setCity("Münster").setDescription("Hammer Konzert").setLocation("Halle Münsterland")
					.setMaxTickets(2000).setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0))
					.setStatus(EventStatus.PUBLISHED).setTitle("Die Kassierer Konzert Münster").setCreator(admin)
					.setPrice(14560.50).build();

			Booking b = new Booking();
			b.setAmount(10);
			b.setBookingCode(1);
			b.setEvent(event);
			b.setUser(u);
			em.persist(b);
			// List<Booking> bs = new LinkedList<Booking>();
			// bs.add(b);
			// event.setBookings(bs);
			em.persist(event);

			Event e2 = e.setCity("Münster").setDescription("Hammer Konzert").setLocation("Halle Münsterland")
					.setMaxTickets(2000).setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0))
					.setStatus(EventStatus.PUBLISHED).setTitle("Test").setCreator(admin).setPrice(14560.50).build();
			em.persist(e2);

			Event e3 = e.setCity("Test").setDescription("Test Hammer Konzert").setLocation("Test").setMaxTickets(2000)
					.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0)).setStatus(EventStatus.CREATED)
					.setTitle("Test").setCreator(admin).setPrice(145.55).build();
			em.persist(e3);
			em.getTransaction().commit();
		}
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Event> getAllActualEvents() {
		List<Event> eventList = (List<Event>) em.createQuery("SELECT e FROM Event e").getResultList();
		return eventList.stream().filter(e -> e.getStatus() == EventStatus.PUBLISHED && e.isAgo() == false)
				.collect(Collectors.toList());
	}

	@Override
	public Event getEventByID(int eventID) {
		return em.find(Event.class, eventID);
	}

	@Override
	public List<Event> searchAllActualEvents(String searchString) {
		List<Event> eventList = (List<Event>) em.createQuery("SELECT e FROM Event e").getResultList();
		return eventList.stream().filter(e -> (e.getTitle() + e.getDescription() + e.getCity() + e.getLocation())
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
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
	}

	@Override
	public List<Event> getAllNotPublishedEventsByManager(User user) {
		if (user.getManager()) {
			List<Event> eventList = (List<Event>) em.createQuery("SELECT e FROM Event e").getResultList();
			return eventList.stream()
					.filter(e -> e.getCreator().getUserID() == user.getUserID() && e.getStatus() == EventStatus.CREATED)
					.collect(Collectors.toList());
		} else {
			return null;
		}
	}

	@Override
	public void changeEvent(Event e) {
		em.getTransaction().begin();
		em.merge(e);
		em.getTransaction().commit();
	}

	@Override
	public void deleteEventByID(int eventID) {
		em.getTransaction().begin();
		em.remove(getEventByID(eventID));
		em.getTransaction().commit();
	}

}
