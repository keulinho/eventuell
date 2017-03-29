package de.eventuell.services;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Event.class)));
		Long events = em.createQuery(cq).getSingleResult();
		if (events<1)
		{
			EventBuilder e = new EventBuilder();
			em.getTransaction().begin();
			User admin = new User();
			admin.setFirstName("Sudo");
			admin.setLastName("Admin");
			admin.setEmail("admin@admin.gws");
			admin.setManager(true);
			admin.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
			admin.setUserID(0);
			em.persist(admin);
			User admin2 = new User();
			admin2.setFirstName("Sudo");
			admin2.setLastName("Admin");
			admin2.setEmail("admin@admin.de");
			admin2.setManager(true);
			admin2.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
			admin2.setUserID(2);
			em.persist(admin2);
			User u = new User();
			u.setFirstName("Sudo");
			u.setLastName("Admin");
			u.setEmail("test@test.de");
			u.setManager(false);
			u.setPassword("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
			u.setUserID(3);
			em.persist(u);
			Event event = e.setCity("Münster").setDescription("Hammer Konzert").setLocation("Halle Münsterland")
					.setMaxTickets(2000).setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0))
					.setStatus(EventStatus.PUBLISHED).setTitle("Die Kassierer Konzert Münster").setCreator(admin)
					.setPrice(14560.50).setStreetNumber("Testallee 3").setZipCode("1234").build();

			Booking b = new Booking();
			b.setAmount(10);
			b.setBookingCode(1);
			b.setEvent(event);
			b.setUser(u);
			em.persist(b);
			em.persist(event);

			Event e2 = e.setCity("Münster").setDescription("Hammer Konzert").setLocation("Halle Münsterland")
					.setMaxTickets(2000).setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0))
					.setStatus(EventStatus.PUBLISHED).setTitle("Testaufführung").setCreator(admin).setPrice(14560.50).setStreetNumber("Testallee 3").setZipCode("1234").build();
			em.persist(e2);

			Event e3 = e.setCity("Teststadt").setDescription("Test Hammer Konzert").setLocation("Testhalle").setMaxTickets(2000)
					.setStartDateTime(LocalDateTime.of(2017, Month.JULY, 29, 19, 30, 0)).setStatus(EventStatus.CREATED)
					.setTitle("Testkonzert").setCreator(admin).setPrice(145.55).setStreetNumber("Teststraße 42").setZipCode("12345").build();
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Event> q = cb.createQuery(Event.class);
		Root<Event> e = q.from(Event.class);
		Predicate p = cb.conjunction();
		p = cb.and(p, cb.equal(e.get("status"), EventStatus.PUBLISHED));
		p = cb.and(p, cb.greaterThanOrEqualTo(e.get("startDateTime"),LocalDateTime.now()));
		q.where(p);
		TypedQuery<Event> query = em.createQuery(q);
		return query.getResultList();
	}

	@Override
	public Event getEventByID(int eventID) {
		return em.find(Event.class, eventID);
	}

	@Override
	public List<Event> searchAllActualEvents(String searchString) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Event> q = cb.createQuery(Event.class);
		Root<Event> e = q.from(Event.class);
		Predicate p = cb.conjunction();
		Predicate p2 = cb.disjunction();
		p = cb.and(p, cb.equal(e.get("status"), EventStatus.PUBLISHED));
		p = cb.and(p, cb.greaterThanOrEqualTo(e.get("startDateTime"),LocalDateTime.now()));
		p2 = cb.or(p2, cb.like(cb.upper(e.get("title")), "%" + searchString.toUpperCase() + "%"));
		p2 = cb.or(p2, cb.like(cb.upper(e.get("city")), "%" + searchString.toUpperCase() + "%"));
		p2 = cb.or(p2, cb.like(cb.upper(e.get("location")), "%" + searchString.toUpperCase() + "%"));
		p2 = cb.or(p2, cb.like(cb.upper(e.get("zipCode")), "%" + searchString.toUpperCase() + "%"));
		p = cb.and(p, p2);
		q.where(p);
		TypedQuery<Event> query = em.createQuery(q);
		return query.getResultList();
	}

	@Override
	public List<Event> getAllActualEventsByActiveManager(User u) {
		if (u.getManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Event> q = cb.createQuery(Event.class);
			Root<Event> e = q.from(Event.class);
			Predicate p = cb.conjunction();
			p = cb.and(p, cb.equal(e.get("status"), EventStatus.PUBLISHED));
			p = cb.and(p, cb.greaterThanOrEqualTo(e.get("startDateTime"),LocalDateTime.now()));
			p = cb.and(p, cb.equal(e.get("creator"), u));
			q.where(p);
			TypedQuery<Event> query = em.createQuery(q);
			return query.getResultList();
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
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Event> q = cb.createQuery(Event.class);
			Root<Event> e = q.from(Event.class);
			Predicate p = cb.conjunction();
			p = cb.and(p, cb.equal(e.get("status"), EventStatus.CREATED));
			p = cb.and(p, cb.equal(e.get("creator"), user));
			q.where(p);
			TypedQuery<Event> query = em.createQuery(q);
			return query.getResultList();
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
