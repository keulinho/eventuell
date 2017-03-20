package de.eventuell.views;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IBookingService;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.session.UserSession;

@ManagedBean
@ViewScoped
public class EventDetailView implements IBookingService{

	private Event currentEvent;
	private IEventService eventService;
	
	private int amount;
	private double pricePerTicket;
	private double overallPrice;
	
	@ManagedProperty(value = "#{userSession}")
	UserSession session;
	
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
		System.out.println("conductBooking");
		Booking booking = new Booking();
		booking.setBookingCode((int) (Math.random()*100000000));
		System.out.println("BookingCode: " + booking.getBookingCode());
		booking.setUser(session.getUser());
		booking.setPrice(overallPrice);
		System.out.println(overallPrice);
		booking.setEvent(currentEvent);
		System.out.println(currentEvent);
		return "index.jsf";
	}
	
	public double calculateOverallPrice(){
		overallPrice = 0.0;
		return overallPrice;
	}


	public double getOverallPrice() {
		return overallPrice;
	}


	public void setOverallPrice(double overallPrice) {
		this.overallPrice = overallPrice;
	}


	public UserSession getSession() {
		return session;
	}


	public void setSession(UserSession session) {
		this.session = session;
	}

	
}
