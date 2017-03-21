package de.eventuell.views;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
	@Inject
	private IEventService eventService;
	
	private int amount;
	private double pricePerTicket;
		
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
		currentEvent = eventService.getEventByID(Integer.parseInt(id));
	}
	
	public String conductBooking(){
		Booking booking;
		try{
			booking = bookingService.conductBooking(amount, pricePerTicket, currentEvent);
		}catch(BookingFailedException bfe){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Die Buchung konnte nicht durchgeführt werden!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
		
		return "index.jsf?faces-redirect=true";
	}

	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPricePerTicket() {
		return pricePerTicket;
	}

	public void setPricePerTicket(double pricePerTicket) {
		this.pricePerTicket = pricePerTicket;
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

	public UserSession getSession() {
		return session;
	}


	public void setSession(UserSession session) {
		this.session = session;
	}

	
}
