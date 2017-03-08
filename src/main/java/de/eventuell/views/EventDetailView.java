package de.eventuell.views;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IEventService;

@ManagedBean
@RequestScoped
public class EventDetailView {

	private Event currentEvent;
	private IEventService eventService;
	
	public EventDetailView(){
		eventService = new MockEventService();	
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
	
}
