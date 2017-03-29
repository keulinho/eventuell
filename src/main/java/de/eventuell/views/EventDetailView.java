package de.eventuell.views;

import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omg.CORBA.Request;

import de.eventuell.exceptions.BookingFailedException;
import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IBookingService;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class EventDetailView{

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

	public EventDetailView(){

	}

	@PostConstruct
	public void populatePage() {			
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");

		if(id != null){
			currentEvent = eventService.getEventByID(Integer.parseInt(id));	
		}
	}	


	public String conductBooking(){
		try{
			Booking booking = bookingService.conductBooking(amount, currentEvent);
			setCurrentBooking(booking);
			amount=0;
		}catch(BookingFailedException bfe){
			setBookingSuccess(false);
			amount=0;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Die Buchung konnte nicht durchgeführt werden!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
		return "event.jsf";
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
