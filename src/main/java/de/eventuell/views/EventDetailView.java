package de.eventuell.views;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IBookingService;
import de.eventuell.services.interfaces.IEventService;

@Named
@RequestScoped
public class EventDetailView implements IBookingService{

	private Event currentEvent;
	private IEventService eventService;
	
	private int amount;
	private double pricePerTicket;
	private double overallPrice;
	
	public EventDetailView(){
		try {
			eventService = new MockEventService();
		} catch (LoginFailedException e1) {
			// TODO Fehlerseite f�r Loginfehler anzeigen
			e1.printStackTrace();
		}	

		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		try {
			currentEvent = eventService.getEventByID(Integer.parseInt(id));
		} catch (Exception e) {
			//TODO: weiterleiten auf 404 page
			currentEvent = null;
		}	
	}

	
	public IEventService getEventService() {
		return eventService;
	}


	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
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


	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}

	@Override
	public List<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking getBookingByBookingCode(int bookingCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String conductBooking() {
		// TODO Buchung persistieren
		return null;
	}
	
	public double calculateOverallPrice(){
		overallPrice = 0.0;
		return overallPrice;
	}

	
}