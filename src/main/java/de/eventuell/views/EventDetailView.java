package de.eventuell.views;

import java.time.LocalDateTime;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.exceptions.BookingFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.services.interfaces.IBookingService;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class EventDetailView {

	private Event currentEvent;
	private Booking currentBooking = null;

	@Inject
	private IEventService eventService;

	private int amount;
	private boolean bookingSuccess = true;

	@Inject
	UserSession session;

	@Inject
	IBookingService bookingService;

	public EventDetailView() {

	}

	/**
	 * Auslesen des id-Parameters aus der URL und holen des entsprechenden Events aus der Datenbank.
	 */
	@PostConstruct
	public void populatePage() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String id = urlParameter.get("id");

		if (id != null) {
			currentEvent = eventService.getEventByID(Integer.parseInt(id));
		}
	}

	public String conductBooking() {
		try {
			Booking booking = bookingService.persistBooking(amount, currentEvent);
			setCurrentBooking(booking);
			amount = 0;
		} catch (BookingFailedException bfe) {
			setBookingSuccess(false);
			amount = 0;
			return null;
		}
		return "event.jsf";
	}

	public boolean isEventExpired() {
		if (currentEvent.getStartDateTime().isBefore(LocalDateTime.now())) {
			return true;
		} else {
			return false;
		}
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public IBookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(IBookingService bookingService) {
		this.bookingService = bookingService;
	}

	public IEventService getEventService() {
		return eventService;
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}

	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}

	public Booking getCurrentBooking() {
		return currentBooking;
	}

	public void setCurrentBooking(Booking currentBooking) {
		this.currentBooking = currentBooking;
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	public boolean isBookingSuccess() {
		return bookingSuccess;
	}

	public void setBookingSuccess(boolean bookingSuccess) {
		this.bookingSuccess = bookingSuccess;
	}
}
