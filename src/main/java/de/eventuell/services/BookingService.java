package de.eventuell.services;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.eventuell.exceptions.BookingFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.services.interfaces.IBookingService;
import de.eventuell.session.UserSession;

public class BookingService  implements IBookingService {

	@Inject
	private EntityManager em;
	
	@Inject
	UserSession session;

	public Booking getBookingByBookingCode(int bookingCode) {
		return em.find(Booking.class, bookingCode);
	}

	
	public Booking persistBooking(int amount, Event currentEvent) throws BookingFailedException {
		em.refresh(currentEvent);
		if (currentEvent.availableTickets()>=amount && amount > 0)
		{
			Booking booking = new Booking();
			booking.setAmount(amount);
			booking.setUser(session.getUser());
			booking.setEvent(currentEvent);
			currentEvent.addBooking(booking);
			em.getTransaction().begin();
			em.persist(booking);
			em.getTransaction().commit();
			em.refresh(currentEvent);
			return booking;
		}
		throw new BookingFailedException();
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}


	public UserSession getSession() {
		return session;
	}


	public void setSession(UserSession session) {
		this.session = session;
	}

}
