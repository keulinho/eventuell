package de.eventuell.views;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IBookingService;
import de.eventuell.services.interfaces.IEventService;

@ManagedBean
@ViewScoped
public class EventDetailView implements IBookingService{

	private Event currentEvent;
	private IEventService eventService;
	
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
	

	
}
