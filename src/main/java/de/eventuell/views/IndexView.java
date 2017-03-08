package de.eventuell.views;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IEventService;

@ManagedBean
@SessionScoped
public class IndexView {
	private List<Event> actualEvents;
	private String searchText;
	private IEventService eventService;
	
	
	public IndexView() {
		eventService=new MockEventService();
	}

	
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	
	public String getSearchText() {
		return searchText;
	}

	public String search() {
		actualEvents = eventService.searchAllActualEvents(searchText);
		searchText = "";
		return "index.jsf";
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
