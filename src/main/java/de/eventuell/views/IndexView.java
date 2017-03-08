package de.eventuell.views;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IEventService;

@ManagedBean
@RequestScoped
public class IndexView {
	private List<Event> actualEvents;
	private IEventService eventService;
	
	public IndexView() {
		eventService=new MockEventService();
	}
	
	public List<Event> getActualEvents() {
		return actualEvents;
	}
	public void setEventService(MockEventService eventService) {
		this.eventService = eventService;
	}
	
	public void getAllActualEvents() {
		actualEvents = eventService.getAllActualEvents();
	}
	
}
